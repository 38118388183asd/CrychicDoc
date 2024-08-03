package com.github.alexthe666.citadel;

import com.github.alexthe666.citadel.server.entity.IDancesToJukebox;
import com.github.alexthe666.citadel.server.event.EventChangeEntityTickRate;
import com.github.alexthe666.citadel.server.tick.ServerTickRateTracker;
import com.github.alexthe666.citadel.server.world.CitadelServerData;
import com.github.alexthe666.citadel.server.world.ModifiableTickRateServer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.MOD)
public class ServerProxy {

    public void onPreInit() {
    }

    public void handleAnimationPacket(int entityId, int index) {
    }

    public void handlePropertiesPacket(String propertyID, CompoundTag compound, int entityID) {
    }

    public void handleClientTickRatePacket(CompoundTag compound) {
    }

    public void handleJukeboxPacket(Level level, int entityId, BlockPos jukeBox, boolean dancing) {
        if (level.getEntity(entityId) instanceof IDancesToJukebox dancer) {
            dancer.setDancing(dancing);
            dancer.setJukeboxPos(dancing ? jukeBox : null);
        }
    }

    public void openBookGUI(ItemStack book) {
    }

    public Object getISTERProperties() {
        return null;
    }

    public void onClientInit() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onServerTick(TickEvent.ServerTickEvent event) {
        ServerTickRateTracker tickRateTracker = CitadelServerData.get(event.getServer()).getOrCreateTickRateTracker();
        if (event.getServer() instanceof ModifiableTickRateServer modifiableServer && event.phase == TickEvent.Phase.START) {
            long l = (long) tickRateTracker.getServerTickLengthMs();
            if (l == 50L) {
                modifiableServer.resetGlobalTickLengthMs();
            } else {
                modifiableServer.setGlobalTickLengthMs((long) tickRateTracker.getServerTickLengthMs());
            }
            tickRateTracker.masterTick();
        }
    }

    public boolean canEntityTickClient(Level level, Entity entity) {
        return true;
    }

    public boolean canEntityTickServer(Level level, Entity entity) {
        if (level instanceof ServerLevel) {
            ServerTickRateTracker tracker = ServerTickRateTracker.getForServer(((ServerLevel) level).getServer());
            if (tracker.isTickingHandled(entity)) {
                return false;
            }
            if (!tracker.hasNormalTickRate(entity)) {
                EventChangeEntityTickRate event = new EventChangeEntityTickRate(entity, tracker.getEntityTickLengthModifier(entity));
                MinecraftForge.EVENT_BUS.post(event);
                if (event.isCanceled()) {
                    return true;
                }
                tracker.addTickBlockedEntity(entity);
                return false;
            }
        }
        return true;
    }

    public boolean isGamePaused() {
        return false;
    }

    public float getMouseOverProgress(ItemStack itemStack) {
        return 0.0F;
    }

    public Player getClientSidePlayer() {
        return null;
    }
}