package me.jellysquid.mods.lithium.mixin.chunk.entity_class_groups;

import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.TransientEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ TransientEntitySectionManager.class })
public interface ClientEntityManagerAccessor<T extends EntityAccess> {

    @Accessor
    EntitySectionStorage<T> getCache();
}