package vectorwing.farmersdelight.common.world.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record WildCropConfiguration(int tries, int xzSpread, int ySpread, Holder<PlacedFeature> primaryFeature, Holder<PlacedFeature> secondaryFeature, @Nullable Holder<PlacedFeature> floorFeature) implements FeatureConfiguration {

    public static final Codec<WildCropConfiguration> CODEC = RecordCodecBuilder.create(config -> config.group(ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(64).forGetter(WildCropConfiguration::tries), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(4).forGetter(WildCropConfiguration::xzSpread), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(WildCropConfiguration::ySpread), PlacedFeature.CODEC.fieldOf("primary_feature").forGetter(WildCropConfiguration::primaryFeature), PlacedFeature.CODEC.fieldOf("secondary_feature").forGetter(WildCropConfiguration::secondaryFeature), PlacedFeature.CODEC.optionalFieldOf("floor_feature").forGetter(floorConfig -> Optional.ofNullable(floorConfig.floorFeature))).apply(config, (tries, xzSpread, yspread, primary, secondary, floor) -> (WildCropConfiguration) floor.map(placedFeatureHolder -> new WildCropConfiguration(tries, xzSpread, yspread, primary, secondary, placedFeatureHolder)).orElseGet(() -> new WildCropConfiguration(tries, xzSpread, yspread, primary, secondary, null))));
}