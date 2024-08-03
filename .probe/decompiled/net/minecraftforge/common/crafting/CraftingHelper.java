package net.minecraftforge.common.crafting;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.Nullable;

public class CraftingHelper {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final Marker CRAFTHELPER = MarkerManager.getMarker("CRAFTHELPER");

    private static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private static final Map<ResourceLocation, IConditionSerializer<?>> conditions = new HashMap();

    private static final BiMap<ResourceLocation, IIngredientSerializer<?>> ingredients = HashBiMap.create();

    public static IConditionSerializer<?> register(IConditionSerializer<?> serializer) {
        ResourceLocation key = serializer.getID();
        if (conditions.containsKey(key)) {
            throw new IllegalStateException("Duplicate recipe condition serializer: " + key);
        } else {
            conditions.put(key, serializer);
            return serializer;
        }
    }

    public static <T extends Ingredient> IIngredientSerializer<T> register(ResourceLocation key, IIngredientSerializer<T> serializer) {
        if (ingredients.containsKey(key)) {
            throw new IllegalStateException("Duplicate recipe ingredient serializer: " + key);
        } else if (ingredients.containsValue(serializer)) {
            throw new IllegalStateException("Duplicate recipe ingredient serializer: " + key + " " + serializer);
        } else {
            ingredients.put(key, serializer);
            return serializer;
        }
    }

    @Nullable
    public static ResourceLocation getID(IIngredientSerializer<?> serializer) {
        return (ResourceLocation) ingredients.inverse().get(serializer);
    }

    public static <T extends Ingredient> void write(FriendlyByteBuf buffer, T ingredient) {
        IIngredientSerializer<T> serializer = ingredient.getSerializer();
        ResourceLocation key = (ResourceLocation) ingredients.inverse().get(serializer);
        if (key == null) {
            throw new IllegalArgumentException("Tried to serialize unregistered Ingredient: " + ingredient + " " + serializer);
        } else {
            if (serializer != VanillaIngredientSerializer.INSTANCE) {
                buffer.writeVarInt(-1);
                buffer.writeResourceLocation(key);
            }
            serializer.write(buffer, ingredient);
        }
    }

    public static Ingredient getIngredient(ResourceLocation type, FriendlyByteBuf buffer) {
        IIngredientSerializer<?> serializer = (IIngredientSerializer<?>) ingredients.get(type);
        if (serializer == null) {
            throw new IllegalArgumentException("Can not deserialize unknown Ingredient type: " + type);
        } else {
            return serializer.parse(buffer);
        }
    }

    public static Ingredient getIngredient(JsonElement json, boolean allowEmpty) {
        if (json == null || json.isJsonNull()) {
            throw new JsonSyntaxException("Json cannot be null");
        } else if (json.isJsonArray()) {
            List<Ingredient> ingredients = Lists.newArrayList();
            List<Ingredient> vanilla = Lists.newArrayList();
            json.getAsJsonArray().forEach(ele -> {
                Ingredient ing = getIngredient(ele, allowEmpty);
                if (ing.getClass() == Ingredient.class) {
                    vanilla.add(ing);
                } else {
                    ingredients.add(ing);
                }
            });
            if (!vanilla.isEmpty()) {
                ingredients.add(Ingredient.merge(vanilla));
            }
            if (ingredients.size() != 0) {
                return (Ingredient) (ingredients.size() == 1 ? (Ingredient) ingredients.get(0) : new CompoundIngredient(ingredients));
            } else if (allowEmpty) {
                return Ingredient.EMPTY;
            } else {
                throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
            }
        } else if (!json.isJsonObject()) {
            throw new JsonSyntaxException("Expcted ingredient to be a object or array of objects");
        } else {
            JsonObject obj = (JsonObject) json;
            String type = GsonHelper.getAsString(obj, "type", "minecraft:item");
            if (type.isEmpty()) {
                throw new JsonSyntaxException("Ingredient type can not be an empty string");
            } else {
                IIngredientSerializer<?> serializer = (IIngredientSerializer<?>) CraftingHelper.ingredients.get(new ResourceLocation(type));
                if (serializer == null) {
                    throw new JsonSyntaxException("Unknown ingredient type: " + type);
                } else {
                    return serializer.parse(obj);
                }
            }
        }
    }

    public static ItemStack getItemStack(JsonObject json, boolean readNBT) {
        return getItemStack(json, readNBT, false);
    }

    public static Item getItem(String itemName, boolean disallowsAirInRecipe) {
        ResourceLocation itemKey = new ResourceLocation(itemName);
        if (!ForgeRegistries.ITEMS.containsKey(itemKey)) {
            throw new JsonSyntaxException("Unknown item '" + itemName + "'");
        } else {
            Item item = ForgeRegistries.ITEMS.getValue(itemKey);
            if (disallowsAirInRecipe && item == Items.AIR) {
                throw new JsonSyntaxException("Invalid item: " + itemName);
            } else {
                return (Item) Objects.requireNonNull(item);
            }
        }
    }

    public static CompoundTag getNBT(JsonElement element) {
        try {
            return element.isJsonObject() ? TagParser.parseTag(GSON.toJson(element)) : TagParser.parseTag(GsonHelper.convertToString(element, "nbt"));
        } catch (CommandSyntaxException var2) {
            throw new JsonSyntaxException("Invalid NBT Entry: " + var2);
        }
    }

    public static ItemStack getItemStack(JsonObject json, boolean readNBT, boolean disallowsAirInRecipe) {
        String itemName = GsonHelper.getAsString(json, "item");
        Item item = getItem(itemName, disallowsAirInRecipe);
        if (readNBT && json.has("nbt")) {
            CompoundTag nbt = getNBT(json.get("nbt"));
            CompoundTag tmp = new CompoundTag();
            if (nbt.contains("ForgeCaps")) {
                tmp.put("ForgeCaps", nbt.get("ForgeCaps"));
                nbt.remove("ForgeCaps");
            }
            tmp.put("tag", nbt);
            tmp.putString("id", itemName);
            tmp.putInt("Count", GsonHelper.getAsInt(json, "count", 1));
            return ItemStack.of(tmp);
        } else {
            return new ItemStack(item, GsonHelper.getAsInt(json, "count", 1));
        }
    }

    public static boolean processConditions(JsonObject json, String memberName, ICondition.IContext context) {
        return !json.has(memberName) || processConditions(GsonHelper.getAsJsonArray(json, memberName), context);
    }

    public static boolean processConditions(JsonArray conditions, ICondition.IContext context) {
        for (int x = 0; x < conditions.size(); x++) {
            if (!conditions.get(x).isJsonObject()) {
                throw new JsonSyntaxException("Conditions must be an array of JsonObjects");
            }
            JsonObject json = conditions.get(x).getAsJsonObject();
            if (!getCondition(json).test(context)) {
                return false;
            }
        }
        return true;
    }

    public static ICondition getCondition(JsonObject json) {
        ResourceLocation type = new ResourceLocation(GsonHelper.getAsString(json, "type"));
        IConditionSerializer<?> serializer = (IConditionSerializer<?>) conditions.get(type);
        if (serializer == null) {
            throw new JsonSyntaxException("Unknown condition type: " + type.toString());
        } else {
            return serializer.read(json);
        }
    }

    public static <T extends ICondition> JsonObject serialize(T condition) {
        IConditionSerializer<T> serializer = (IConditionSerializer<T>) conditions.get(condition.getID());
        if (serializer == null) {
            throw new JsonSyntaxException("Unknown condition type: " + condition.getID().toString());
        } else {
            return serializer.getJson(condition);
        }
    }

    public static JsonArray serialize(ICondition... conditions) {
        JsonArray arr = new JsonArray();
        for (ICondition iCond : conditions) {
            arr.add(serialize(iCond));
        }
        return arr;
    }
}