package dev.kosmx.playerAnim.mixin.firstPerson;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ Camera.class })
public interface CameraAccessor {

    @Accessor
    void setDetached(boolean var1);
}