package com.almostreliable.ponderjs.util;

import com.simibubi.create.content.fluids.particle.FluidParticleData;
import dev.architectury.fluid.FluidStack;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import java.util.stream.Stream;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public class PonderPlatform {

    public static FluidParticleData createFluidParticleData(FluidStackJS fluid, ParticleType<?> type) {
        FluidStack archFluidStack = fluid.getFluidStack();
        net.minecraftforge.fluids.FluidStack fs = new net.minecraftforge.fluids.FluidStack(archFluidStack.getFluid(), (int) archFluidStack.getAmount(), archFluidStack.getTag());
        return new FluidParticleData(type, fs);
    }

    public static Stream<ParticleType<?>> getParticleTypes() {
        return ForgeRegistries.PARTICLE_TYPES.getValues().stream();
    }

    public static ResourceLocation getParticleTypeName(ParticleType<?> particleType) {
        return ForgeRegistries.PARTICLE_TYPES.getKey(particleType);
    }

    public static ResourceLocation getBlockName(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    public static ResourceLocation getEntityTypeName(EntityType<?> entityType) {
        return ForgeRegistries.ENTITY_TYPES.getKey(entityType);
    }

    public static ResourceLocation getItemName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item);
    }
}