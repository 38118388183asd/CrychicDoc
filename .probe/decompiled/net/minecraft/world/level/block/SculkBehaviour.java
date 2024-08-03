package net.minecraft.world.level.block;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public interface SculkBehaviour {

    SculkBehaviour DEFAULT = new SculkBehaviour() {

        @Override
        public boolean attemptSpreadVein(LevelAccessor p_222048_, BlockPos p_222049_, BlockState p_222050_, @Nullable Collection<Direction> p_222051_, boolean p_222052_) {
            if (p_222051_ == null) {
                return ((SculkVeinBlock) Blocks.SCULK_VEIN).getSameSpaceSpreader().spreadAll(p_222048_.m_8055_(p_222049_), p_222048_, p_222049_, p_222052_) > 0L;
            } else if (!p_222051_.isEmpty()) {
                return !p_222050_.m_60795_() && !p_222050_.m_60819_().is(Fluids.WATER) ? false : SculkVeinBlock.regrow(p_222048_, p_222049_, p_222050_, p_222051_);
            } else {
                return SculkBehaviour.super.attemptSpreadVein(p_222048_, p_222049_, p_222050_, p_222051_, p_222052_);
            }
        }

        @Override
        public int attemptUseCharge(SculkSpreader.ChargeCursor p_222054_, LevelAccessor p_222055_, BlockPos p_222056_, RandomSource p_222057_, SculkSpreader p_222058_, boolean p_222059_) {
            return p_222054_.getDecayDelay() > 0 ? p_222054_.getCharge() : 0;
        }

        @Override
        public int updateDecayDelay(int p_222061_) {
            return Math.max(p_222061_ - 1, 0);
        }
    };

    default byte getSculkSpreadDelay() {
        return 1;
    }

    default void onDischarged(LevelAccessor levelAccessor0, BlockState blockState1, BlockPos blockPos2, RandomSource randomSource3) {
    }

    default boolean depositCharge(LevelAccessor levelAccessor0, BlockPos blockPos1, RandomSource randomSource2) {
        return false;
    }

    default boolean attemptSpreadVein(LevelAccessor levelAccessor0, BlockPos blockPos1, BlockState blockState2, @Nullable Collection<Direction> collectionDirection3, boolean boolean4) {
        return ((MultifaceBlock) Blocks.SCULK_VEIN).getSpreader().spreadAll(blockState2, levelAccessor0, blockPos1, boolean4) > 0L;
    }

    default boolean canChangeBlockStateOnSpread() {
        return true;
    }

    default int updateDecayDelay(int int0) {
        return 1;
    }

    int attemptUseCharge(SculkSpreader.ChargeCursor var1, LevelAccessor var2, BlockPos var3, RandomSource var4, SculkSpreader var5, boolean var6);
}