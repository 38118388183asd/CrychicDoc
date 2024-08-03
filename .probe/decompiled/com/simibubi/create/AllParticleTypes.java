package com.simibubi.create;

import com.simibubi.create.content.equipment.bell.SoulBaseParticle;
import com.simibubi.create.content.equipment.bell.SoulParticle;
import com.simibubi.create.content.fluids.particle.FluidParticleData;
import com.simibubi.create.content.kinetics.base.RotationIndicatorParticleData;
import com.simibubi.create.content.kinetics.fan.AirFlowParticleData;
import com.simibubi.create.content.kinetics.steamEngine.SteamJetParticleData;
import com.simibubi.create.content.trains.CubeParticleData;
import com.simibubi.create.foundation.particle.AirParticleData;
import com.simibubi.create.foundation.particle.ICustomParticleData;
import com.simibubi.create.foundation.utility.Lang;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public enum AllParticleTypes {

    ROTATION_INDICATOR(RotationIndicatorParticleData::new),
    AIR_FLOW(AirFlowParticleData::new),
    AIR(AirParticleData::new),
    STEAM_JET(SteamJetParticleData::new),
    CUBE(CubeParticleData::new),
    FLUID_PARTICLE(FluidParticleData::new),
    BASIN_FLUID(FluidParticleData::new),
    FLUID_DRIP(FluidParticleData::new),
    SOUL(SoulParticle.Data::new),
    SOUL_BASE(SoulBaseParticle.Data::new),
    SOUL_PERIMETER(SoulParticle.PerimeterData::new),
    SOUL_EXPANDING_PERIMETER(SoulParticle.ExpandingPerimeterData::new);

    private final AllParticleTypes.ParticleEntry<?> entry;

    private <D extends ParticleOptions> AllParticleTypes(Supplier<? extends ICustomParticleData<D>> typeFactory) {
        String name = Lang.asId(this.name());
        this.entry = new AllParticleTypes.ParticleEntry(name, typeFactory);
    }

    public static void register(IEventBus modEventBus) {
        AllParticleTypes.ParticleEntry.REGISTER.register(modEventBus);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerFactories(RegisterParticleProvidersEvent event) {
        for (AllParticleTypes particle : values()) {
            particle.entry.registerFactory(event);
        }
    }

    public ParticleType<?> get() {
        return this.entry.object.get();
    }

    public String parameter() {
        return this.entry.name;
    }

    private static class ParticleEntry<D extends ParticleOptions> {

        private static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, "create");

        private final String name;

        private final Supplier<? extends ICustomParticleData<D>> typeFactory;

        private final RegistryObject<ParticleType<D>> object;

        public ParticleEntry(String name, Supplier<? extends ICustomParticleData<D>> typeFactory) {
            this.name = name;
            this.typeFactory = typeFactory;
            this.object = REGISTER.register(name, () -> ((ICustomParticleData) this.typeFactory.get()).createType());
        }

        @OnlyIn(Dist.CLIENT)
        public void registerFactory(RegisterParticleProvidersEvent event) {
            ((ICustomParticleData) this.typeFactory.get()).register(this.object.get(), event);
        }
    }
}