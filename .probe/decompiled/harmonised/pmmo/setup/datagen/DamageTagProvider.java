package harmonised.pmmo.setup.datagen;

import harmonised.pmmo.util.Reference;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DamageTagProvider extends TagsProvider<DamageType> {

    public DamageTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, lookupProvider, "pmmo", existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.m_206424_(Reference.FROM_ENVIRONMENT).addTag(DamageTypeTags.IS_DROWNING).addTag(DamageTypeTags.IS_EXPLOSION).addTag(DamageTypeTags.IS_FIRE).addTag(DamageTypeTags.IS_FREEZING).addTag(DamageTypeTags.IS_LIGHTNING).add(DamageTypes.CACTUS).add(DamageTypes.CRAMMING).add(DamageTypes.DROWN).add(DamageTypes.FALLING_ANVIL).add(DamageTypes.FALLING_BLOCK).add(DamageTypes.FREEZE).add(DamageTypes.IN_FIRE).add(DamageTypes.LIGHTNING_BOLT).add(DamageTypes.ON_FIRE).add(DamageTypes.LAVA).add(DamageTypes.HOT_FLOOR).add(DamageTypes.IN_WALL).add(DamageTypes.STARVE).add(DamageTypes.SWEET_BERRY_BUSH);
        this.m_206424_(Reference.FROM_IMPACT).addTag(DamageTypeTags.IS_FALL).add(DamageTypes.FLY_INTO_WALL);
        this.m_206424_(Reference.FROM_MAGIC).add(DamageTypes.MAGIC).add(DamageTypes.INDIRECT_MAGIC);
    }
}