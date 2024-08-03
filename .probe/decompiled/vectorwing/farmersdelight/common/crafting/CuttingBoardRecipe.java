package vectorwing.farmersdelight.common.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModRecipeSerializers;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class CuttingBoardRecipe implements Recipe<RecipeWrapper> {

    public static final int MAX_RESULTS = 4;

    private final ResourceLocation id;

    private final String group;

    private final Ingredient input;

    private final Ingredient tool;

    private final NonNullList<ChanceResult> results;

    private final String soundEvent;

    public CuttingBoardRecipe(ResourceLocation id, String group, Ingredient input, Ingredient tool, NonNullList<ChanceResult> results, String soundEvent) {
        this.id = id;
        this.group = group;
        this.input = input;
        this.tool = tool;
        this.results = results;
        this.soundEvent = soundEvent;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.input);
        return nonnulllist;
    }

    public Ingredient getTool() {
        return this.tool;
    }

    public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
        return this.results.get(0).getStack().copy();
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return this.results.get(0).getStack();
    }

    public List<ItemStack> getResults() {
        return (List<ItemStack>) this.getRollableResults().stream().map(ChanceResult::getStack).collect(Collectors.toList());
    }

    public NonNullList<ChanceResult> getRollableResults() {
        return this.results;
    }

    public List<ItemStack> rollResults(RandomSource rand, int fortuneLevel) {
        List<ItemStack> results = new ArrayList();
        for (ChanceResult output : this.getRollableResults()) {
            ItemStack stack = output.rollOutput(rand, fortuneLevel);
            if (!stack.isEmpty()) {
                results.add(stack);
            }
        }
        return results;
    }

    public String getSoundEventID() {
        return this.soundEvent;
    }

    public boolean matches(RecipeWrapper inv, Level level) {
        return inv.isEmpty() ? false : this.input.test(inv.getItem(0));
    }

    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.getMaxInputCount();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CUTTING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.CUTTING.get();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            CuttingBoardRecipe that = (CuttingBoardRecipe) o;
            if (!this.getId().equals(that.getId())) {
                return false;
            } else if (!this.getGroup().equals(that.getGroup())) {
                return false;
            } else if (!this.input.equals(that.input)) {
                return false;
            } else if (!this.getTool().equals(that.getTool())) {
                return false;
            } else {
                return !this.getResults().equals(that.getResults()) ? false : Objects.equals(this.soundEvent, that.soundEvent);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.getId().hashCode();
        result = 31 * result + (this.getGroup() != null ? this.getGroup().hashCode() : 0);
        result = 31 * result + this.input.hashCode();
        result = 31 * result + this.getTool().hashCode();
        result = 31 * result + this.getResults().hashCode();
        return 31 * result + (this.soundEvent != null ? this.soundEvent.hashCode() : 0);
    }

    public static class Serializer implements RecipeSerializer<CuttingBoardRecipe> {

        public CuttingBoardRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String groupIn = GsonHelper.getAsString(json, "group", "");
            NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            JsonObject toolObject = GsonHelper.getAsJsonObject(json, "tool");
            Ingredient toolIn = Ingredient.fromJson(toolObject);
            if (inputItemsIn.isEmpty()) {
                throw new JsonParseException("No ingredients for cutting recipe");
            } else if (toolIn.isEmpty()) {
                throw new JsonParseException("No tool for cutting recipe");
            } else if (inputItemsIn.size() > 1) {
                throw new JsonParseException("Too many ingredients for cutting recipe! Please define only one ingredient");
            } else {
                NonNullList<ChanceResult> results = readResults(GsonHelper.getAsJsonArray(json, "result"));
                if (results.size() > 4) {
                    throw new JsonParseException("Too many results for cutting recipe! The maximum quantity of unique results is 4");
                } else {
                    String soundID = GsonHelper.getAsString(json, "sound", "");
                    return new CuttingBoardRecipe(recipeId, groupIn, inputItemsIn.get(0), toolIn, results, soundID);
                }
            }
        }

        private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();
            for (int i = 0; i < ingredientArray.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }
            return nonnulllist;
        }

        private static NonNullList<ChanceResult> readResults(JsonArray resultArray) {
            NonNullList<ChanceResult> results = NonNullList.create();
            for (JsonElement result : resultArray) {
                results.add(ChanceResult.deserialize(result));
            }
            return results;
        }

        @Nullable
        public CuttingBoardRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            Ingredient inputItemIn = Ingredient.fromNetwork(buffer);
            Ingredient toolIn = Ingredient.fromNetwork(buffer);
            int i = buffer.readVarInt();
            NonNullList<ChanceResult> resultsIn = NonNullList.withSize(i, ChanceResult.EMPTY);
            for (int j = 0; j < resultsIn.size(); j++) {
                resultsIn.set(j, ChanceResult.read(buffer));
            }
            String soundEventIn = buffer.readUtf();
            return new CuttingBoardRecipe(recipeId, groupIn, inputItemIn, toolIn, resultsIn, soundEventIn);
        }

        public void toNetwork(FriendlyByteBuf buffer, CuttingBoardRecipe recipe) {
            buffer.writeUtf(recipe.group);
            recipe.input.toNetwork(buffer);
            recipe.tool.toNetwork(buffer);
            buffer.writeVarInt(recipe.results.size());
            for (ChanceResult result : recipe.results) {
                result.write(buffer);
            }
            buffer.writeUtf(recipe.soundEvent);
        }
    }
}