package net.minecraft.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractChestBlock<E extends BlockEntity> extends BaseEntityBlock {

    protected final Supplier<BlockEntityType<? extends E>> blockEntityType;

    protected AbstractChestBlock(BlockBehaviour.Properties blockBehaviourProperties0, Supplier<BlockEntityType<? extends E>> supplierBlockEntityTypeExtendsE1) {
        super(blockBehaviourProperties0);
        this.blockEntityType = supplierBlockEntityTypeExtendsE1;
    }

    public abstract DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState var1, Level var2, BlockPos var3, boolean var4);
}