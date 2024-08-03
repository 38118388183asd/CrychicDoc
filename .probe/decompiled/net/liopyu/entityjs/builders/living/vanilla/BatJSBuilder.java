package net.liopyu.entityjs.builders.living.vanilla;

import net.liopyu.entityjs.builders.living.entityjs.MobBuilder;
import net.liopyu.entityjs.entities.living.vanilla.BatEntityJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;

public class BatJSBuilder extends MobBuilder<BatEntityJS> {

    public BatJSBuilder(ResourceLocation i) {
        super(i);
    }

    @Override
    public EntityType.EntityFactory<BatEntityJS> factory() {
        return (type, level) -> new BatEntityJS(this, type, level);
    }

    @Override
    public AttributeSupplier.Builder getAttributeBuilder() {
        return BatEntityJS.m_27455_();
    }
}