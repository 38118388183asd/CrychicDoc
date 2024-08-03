package com.simibubi.create.content.equipment.bell;

import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.AllPartialModels;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PeculiarBellBlockEntity extends AbstractBellBlockEntity {

    public PeculiarBellBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public PartialModel getBellModel() {
        return AllPartialModels.PECULIAR_BELL;
    }
}