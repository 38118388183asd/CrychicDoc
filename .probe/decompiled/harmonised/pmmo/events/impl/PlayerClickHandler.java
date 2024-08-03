package harmonised.pmmo.events.impl;

import harmonised.pmmo.api.enums.EventType;
import harmonised.pmmo.api.enums.ReqType;
import harmonised.pmmo.config.Config;
import harmonised.pmmo.core.Core;
import harmonised.pmmo.features.party.PartyUtils;
import harmonised.pmmo.util.Messenger;
import harmonised.pmmo.util.RegistryUtil;
import harmonised.pmmo.util.TagUtils;
import java.util.List;
import java.util.Map;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;

public class PlayerClickHandler {

    public static void leftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        Player player = event.getEntity();
        Core core = Core.get(player.m_9236_());
        boolean serverSide = !player.m_9236_().isClientSide;
        if (!core.isActionPermitted(ReqType.BREAK, event.getPos(), player)) {
            event.setUseBlock(Result.DENY);
            Messenger.sendDenialMsg(ReqType.BREAK, player, new ItemStack(player.m_9236_().getBlockState(event.getPos()).m_60734_().asItem()).getDisplayName());
        }
        if (!core.isActionPermitted(ReqType.INTERACT, event.getItemStack(), player)) {
            event.setUseItem(Result.DENY);
            Messenger.sendDenialMsg(ReqType.INTERACT, player, player.m_21205_().getDisplayName());
        }
        if (!event.getUseBlock().equals(Result.DENY)) {
            CompoundTag hookOutput = new CompoundTag();
            if (serverSide) {
                hookOutput = core.getEventTriggerRegistry().executeEventListeners(EventType.HIT_BLOCK, event, hookOutput);
                if (hookOutput.getBoolean("is_cancelled")) {
                    event.setCanceled(true);
                    return;
                }
                if (hookOutput.getBoolean("deny_block")) {
                    event.setUseBlock(Result.DENY);
                }
                if (hookOutput.getBoolean("deny_item")) {
                    event.setUseItem(Result.DENY);
                }
            }
            hookOutput = TagUtils.mergeTags(hookOutput, core.getPerkRegistry().executePerk(EventType.HIT_BLOCK, player, new CompoundTag()));
            if (serverSide) {
                Map<String, Long> xpAward = core.getExperienceAwards(EventType.HIT_BLOCK, event.getPos(), player.m_9236_(), event.getEntity(), hookOutput);
                List<ServerPlayer> partyMembersInRange = PartyUtils.getPartyMembersInRange((ServerPlayer) event.getEntity());
                core.awardXP(partyMembersInRange, xpAward);
            }
        }
    }

    public static void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Core core = Core.get(player.m_9236_());
        boolean serverSide = !player.m_9236_().isClientSide;
        if (!core.isActionPermitted(ReqType.INTERACT, event.getPos(), player)) {
            event.setUseBlock(Result.DENY);
        }
        if (!core.isActionPermitted(ReqType.INTERACT, event.getItemStack(), player)) {
            event.setUseItem(Result.DENY);
        }
        if (event.getUseBlock().equals(Result.DENY) && !serverSide && event.getHand().equals(InteractionHand.MAIN_HAND)) {
            Messenger.sendDenialMsg(ReqType.INTERACT, player, event.getLevel().getBlockState(event.getPos()).m_60734_().getName());
        } else {
            CompoundTag hookOutput = new CompoundTag();
            boolean isSalvage = player.m_6047_() && RegistryUtil.getId(event.getLevel().getBlockState(event.getPos()).m_60734_()).equals(new ResourceLocation(Config.SALVAGE_BLOCK.get()));
            if (serverSide) {
                hookOutput = core.getEventTriggerRegistry().executeEventListeners(EventType.ACTIVATE_BLOCK, event, hookOutput);
                if (hookOutput.getBoolean("is_cancelled")) {
                    event.setCanceled(true);
                    return;
                }
                if (isSalvage && event.getHand().equals(InteractionHand.MAIN_HAND)) {
                    core.getSalvage((ServerPlayer) player);
                }
            }
            if (isSalvage) {
                event.setCanceled(true);
            }
            hookOutput = TagUtils.mergeTags(hookOutput, core.getPerkRegistry().executePerk(EventType.ACTIVATE_BLOCK, player, new CompoundTag()));
            if (serverSide) {
                Map<String, Long> xpAward = core.getExperienceAwards(EventType.ACTIVATE_BLOCK, event.getPos(), player.m_9236_(), event.getEntity(), hookOutput);
                List<ServerPlayer> partyMembersInRange = PartyUtils.getPartyMembersInRange((ServerPlayer) event.getEntity());
                core.awardXP(partyMembersInRange, xpAward);
            }
        }
    }

    public static void rightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Core core = Core.get(player.m_9236_());
        boolean serverSide = !player.m_9236_().isClientSide;
        if (!core.isActionPermitted(ReqType.USE, event.getItemStack(), player)) {
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
            Messenger.sendDenialMsg(ReqType.USE, player, event.getItemStack());
        } else {
            CompoundTag hookOutput = new CompoundTag();
            if (serverSide) {
                hookOutput = core.getEventTriggerRegistry().executeEventListeners(EventType.ACTIVATE_ITEM, event, hookOutput);
                if (hookOutput.getBoolean("is_cancelled")) {
                    event.setCanceled(true);
                    return;
                }
            }
            hookOutput = TagUtils.mergeTags(hookOutput, core.getPerkRegistry().executePerk(EventType.ACTIVATE_ITEM, player, new CompoundTag()));
            if (serverSide) {
                Map<String, Long> xpAward = core.getExperienceAwards(EventType.ACTIVATE_ITEM, event.getItemStack(), event.getEntity(), hookOutput);
                List<ServerPlayer> partyMembersInRange = PartyUtils.getPartyMembersInRange((ServerPlayer) event.getEntity());
                core.awardXP(partyMembersInRange, xpAward);
            }
        }
    }
}