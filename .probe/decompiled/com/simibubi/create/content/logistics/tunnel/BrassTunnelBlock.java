package com.simibubi.create.content.logistics.tunnel;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.foundation.block.IBE;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BrassTunnelBlock extends BeltTunnelBlock {

    public BrassTunnelBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState p_225533_1_, Level world, BlockPos pos, Player player, InteractionHand p_225533_5_, BlockHitResult p_225533_6_) {
        return this.onBlockEntityUse(world, pos, be -> {
            if (!(be instanceof BrassTunnelBlockEntity bte)) {
                return InteractionResult.PASS;
            } else {
                List<ItemStack> stacksOfGroup = bte.grabAllStacksOfGroup(world.isClientSide);
                if (stacksOfGroup.isEmpty()) {
                    return InteractionResult.PASS;
                } else if (world.isClientSide) {
                    return InteractionResult.SUCCESS;
                } else {
                    for (ItemStack itemStack : stacksOfGroup) {
                        player.getInventory().placeItemBackInInventory(itemStack.copy());
                    }
                    world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, 1.0F + world.random.nextFloat());
                    return InteractionResult.SUCCESS;
                }
            }
        });
    }

    @Override
    public BlockEntityType<? extends BeltTunnelBlockEntity> getBlockEntityType() {
        return (BlockEntityType<? extends BeltTunnelBlockEntity>) AllBlockEntityTypes.BRASS_TUNNEL.get();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return super.updateShape(state, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, level, pos, newState);
    }
}