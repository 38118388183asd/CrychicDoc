package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class NetherForestVegetationConfig extends BlockPileConfiguration {

    public static final Codec<NetherForestVegetationConfig> CODEC = RecordCodecBuilder.create(p_191267_ -> p_191267_.group(BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(p_191273_ -> p_191273_.f_67540_), ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter(p_191271_ -> p_191271_.spreadWidth), ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter(p_191269_ -> p_191269_.spreadHeight)).apply(p_191267_, NetherForestVegetationConfig::new));

    public final int spreadWidth;

    public final int spreadHeight;

    public NetherForestVegetationConfig(BlockStateProvider blockStateProvider0, int int1, int int2) {
        super(blockStateProvider0);
        this.spreadWidth = int1;
        this.spreadHeight = int2;
    }
}