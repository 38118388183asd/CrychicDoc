package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.fixes.References;

public class V3078 extends NamespacedSchema {

    public V3078(int int0, Schema schema1) {
        super(int0, schema1);
    }

    protected static void registerMob(Schema schema0, Map<String, Supplier<TypeTemplate>> mapStringSupplierTypeTemplate1, String string2) {
        schema0.register(mapStringSupplierTypeTemplate1, string2, () -> V100.equipment(schema0));
    }

    public Map<String, Supplier<TypeTemplate>> registerEntities(Schema schema0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerEntities(schema0);
        registerMob(schema0, $$1, "minecraft:frog");
        registerMob(schema0, $$1, "minecraft:tadpole");
        return $$1;
    }

    public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema schema0) {
        Map<String, Supplier<TypeTemplate>> $$1 = super.registerBlockEntities(schema0);
        schema0.register($$1, "minecraft:sculk_shrieker", () -> DSL.optionalFields("listener", DSL.optionalFields("event", DSL.optionalFields("game_event", References.GAME_EVENT_NAME.in(schema0)))));
        return $$1;
    }
}