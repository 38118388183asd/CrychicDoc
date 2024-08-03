package com.simibubi.create.content.fluids.tank;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.api.connectivity.ConnectivityHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

public class FluidTankItem extends BlockItem {

    public FluidTankItem(Block p_i48527_1_, Item.Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }

    @Override
    public InteractionResult place(BlockPlaceContext ctx) {
        InteractionResult initialResult = super.place(ctx);
        if (!initialResult.consumesAction()) {
            return initialResult;
        } else {
            this.tryMultiPlace(ctx);
            return initialResult;
        }
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos p_195943_1_, Level p_195943_2_, Player p_195943_3_, ItemStack p_195943_4_, BlockState p_195943_5_) {
        MinecraftServer minecraftserver = p_195943_2_.getServer();
        if (minecraftserver == null) {
            return false;
        } else {
            CompoundTag nbt = p_195943_4_.getTagElement("BlockEntityTag");
            if (nbt != null) {
                nbt.remove("Luminosity");
                nbt.remove("Size");
                nbt.remove("Height");
                nbt.remove("Controller");
                nbt.remove("LastKnownPos");
                if (nbt.contains("TankContent")) {
                    FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompound("TankContent"));
                    if (!fluid.isEmpty()) {
                        fluid.setAmount(Math.min(FluidTankBlockEntity.getCapacityMultiplier(), fluid.getAmount()));
                        nbt.put("TankContent", fluid.writeToNBT(new CompoundTag()));
                    }
                }
            }
            return super.updateCustomBlockEntityTag(p_195943_1_, p_195943_2_, p_195943_3_, p_195943_4_, p_195943_5_);
        }
    }

    private void tryMultiPlace(BlockPlaceContext ctx) {
        Player player = ctx.m_43723_();
        if (player != null) {
            if (!player.m_6144_()) {
                Direction face = ctx.m_43719_();
                if (face.getAxis().isVertical()) {
                    ItemStack stack = ctx.m_43722_();
                    Level world = ctx.m_43725_();
                    BlockPos pos = ctx.getClickedPos();
                    BlockPos placedOnPos = pos.relative(face.getOpposite());
                    BlockState placedOnState = world.getBlockState(placedOnPos);
                    if (FluidTankBlock.isTank(placedOnState)) {
                        boolean creative = this.m_40614_().equals(AllBlocks.CREATIVE_FLUID_TANK.get());
                        FluidTankBlockEntity tankAt = ConnectivityHandler.partAt(creative ? (BlockEntityType) AllBlockEntityTypes.CREATIVE_FLUID_TANK.get() : (BlockEntityType) AllBlockEntityTypes.FLUID_TANK.get(), world, placedOnPos);
                        if (tankAt != null) {
                            FluidTankBlockEntity controllerBE = tankAt.getControllerBE();
                            if (controllerBE != null) {
                                int width = controllerBE.width;
                                if (width != 1) {
                                    int tanksToPlace = 0;
                                    BlockPos startPos = face == Direction.DOWN ? controllerBE.m_58899_().below() : controllerBE.m_58899_().above(controllerBE.height);
                                    if (startPos.m_123342_() == pos.m_123342_()) {
                                        for (int xOffset = 0; xOffset < width; xOffset++) {
                                            for (int zOffset = 0; zOffset < width; zOffset++) {
                                                BlockPos offsetPos = startPos.offset(xOffset, 0, zOffset);
                                                BlockState blockState = world.getBlockState(offsetPos);
                                                if (!FluidTankBlock.isTank(blockState)) {
                                                    if (!blockState.m_247087_()) {
                                                        return;
                                                    }
                                                    tanksToPlace++;
                                                }
                                            }
                                        }
                                        if (player.isCreative() || stack.getCount() >= tanksToPlace) {
                                            for (int xOffset = 0; xOffset < width; xOffset++) {
                                                for (int zOffsetx = 0; zOffsetx < width; zOffsetx++) {
                                                    BlockPos offsetPos = startPos.offset(xOffset, 0, zOffsetx);
                                                    BlockState blockState = world.getBlockState(offsetPos);
                                                    if (!FluidTankBlock.isTank(blockState)) {
                                                        BlockPlaceContext context = BlockPlaceContext.at(ctx, offsetPos, face);
                                                        player.getPersistentData().putBoolean("SilenceTankSound", true);
                                                        super.place(context);
                                                        player.getPersistentData().remove("SilenceTankSound");
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}