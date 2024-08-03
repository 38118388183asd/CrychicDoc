package dev.latvian.mods.kubejs.block;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.utils.value.IntValue;
import dev.latvian.mods.kubejs.bindings.event.BlockEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class KubeJSBlockEventHandler {

    public static void init() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register(KubeJSBlockEventHandler::rightClick);
        InteractionEvent.LEFT_CLICK_BLOCK.register(KubeJSBlockEventHandler::leftClick);
        BlockEvent.BREAK.register(KubeJSBlockEventHandler::blockBreak);
        BlockEvent.PLACE.register(KubeJSBlockEventHandler::blockPlace);
        InteractionEvent.FARMLAND_TRAMPLE.register(KubeJSBlockEventHandler::farmlandTrample);
    }

    private static EventResult rightClick(Player player, InteractionHand hand, BlockPos pos, Direction direction) {
        return BlockEvents.RIGHT_CLICKED.hasListeners() && !player.getCooldowns().isOnCooldown(player.m_21120_(hand).getItem()) ? BlockEvents.RIGHT_CLICKED.post(player, player.m_9236_().getBlockState(pos), new BlockRightClickedEventJS(player, hand, pos, direction)).arch() : EventResult.pass();
    }

    private static EventResult leftClick(Player player, InteractionHand hand, BlockPos pos, Direction direction) {
        return BlockEvents.LEFT_CLICKED.hasListeners() ? BlockEvents.LEFT_CLICKED.post(player, player.m_9236_().getBlockState(pos), new BlockLeftClickedEventJS(player, hand, pos, direction)).arch() : EventResult.pass();
    }

    private static EventResult blockBreak(Level level, BlockPos pos, BlockState state, ServerPlayer player, @Nullable IntValue xp) {
        return BlockEvents.BROKEN.hasListeners(state.m_60734_()) ? BlockEvents.BROKEN.post(level, state.m_60734_(), new BlockBrokenEventJS(player, level, pos, state, xp)).arch() : EventResult.pass();
    }

    private static EventResult blockPlace(Level level, BlockPos pos, BlockState state, @Nullable Entity placer) {
        return BlockEvents.PLACED.hasListeners(state.m_60734_()) ? BlockEvents.PLACED.post(level, state.m_60734_(), new BlockPlacedEventJS(placer, level, pos, state)).arch() : EventResult.pass();
    }

    private static EventResult farmlandTrample(Level level, BlockPos pos, BlockState state, float distance, @Nullable Entity entity) {
        return BlockEvents.FARMLAND_TRAMPLED.hasListeners(state.m_60734_()) ? BlockEvents.FARMLAND_TRAMPLED.post(level, state.m_60734_(), new FarmlandTrampledEventJS(level, pos, state, distance, entity)).arch() : EventResult.pass();
    }
}