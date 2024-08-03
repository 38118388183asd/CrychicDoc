package me.jellysquid.mods.lithium.mixin.alloc.chunk_ticking;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ ServerChunkCache.class })
public class ServerChunkManagerMixin {

    private final ArrayList<ChunkHolder> cachedChunkList = new ArrayList();

    @Redirect(method = { "tickChunks()V" }, at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayListWithCapacity(I)Ljava/util/ArrayList;", remap = false))
    private ArrayList<ChunkHolder> redirectChunksListClone(int initialArraySize) {
        ArrayList<ChunkHolder> list = this.cachedChunkList;
        list.clear();
        list.ensureCapacity(initialArraySize);
        return list;
    }

    @Inject(method = { "tick(Ljava/util/function/BooleanSupplier;Z)V" }, at = { @At("HEAD") })
    private void preTick(BooleanSupplier shouldKeepTicking, boolean tickChunks, CallbackInfo ci) {
        this.cachedChunkList.clear();
    }
}