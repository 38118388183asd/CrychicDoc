package yesman.epicfight.world.capabilities.entitypatch.mob;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import java.util.Set;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.MobCombatBehaviors;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class VindicatorPatch<T extends PathfinderMob> extends AbstractIllagerPatch<T> {

    public VindicatorPatch() {
        super(Faction.ILLAGER);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        super.initAnimator(clientAnimator);
        clientAnimator.addLivingAnimation(LivingMotions.ANGRY, Animations.VINDICATOR_IDLE_AGGRESSIVE);
        clientAnimator.addLivingAnimation(LivingMotions.CHASE, Animations.VINDICATOR_CHASE);
        clientAnimator.setCurrentMotionsAsDefault();
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        this.original.m_21051_(EpicFightAttributes.IMPACT.get()).setBaseValue(1.0);
    }

    @Override
    protected void setWeaponMotions() {
        super.setWeaponMotions();
        this.weaponLivingMotions.put(CapabilityItem.WeaponCategories.GREATSWORD, ImmutableMap.of(CapabilityItem.Styles.TWO_HAND, Set.of(Pair.of(LivingMotions.WALK, Animations.ILLAGER_WALK), Pair.of(LivingMotions.CHASE, Animations.BIPED_WALK_TWOHAND))));
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.AXE, ImmutableMap.of(CapabilityItem.Styles.COMMON, MobCombatBehaviors.VINDICATOR_ONEHAND));
        this.weaponAttackMotions.put(CapabilityItem.WeaponCategories.SWORD, ImmutableMap.of(CapabilityItem.Styles.COMMON, MobCombatBehaviors.VINDICATOR_ONEHAND));
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        if (this.original.m_21223_() <= 0.0F) {
            this.currentLivingMotion = LivingMotions.DEATH;
        } else if (this.state.inaction() && considerInaction) {
            this.currentLivingMotion = LivingMotions.INACTION;
        } else {
            boolean isAngry = this.original.m_5912_();
            if (this.original.f_267362_.speed() > 0.01F) {
                this.currentLivingMotion = isAngry ? LivingMotions.CHASE : LivingMotions.WALK;
            } else {
                this.currentLivingMotion = isAngry ? LivingMotions.ANGRY : LivingMotions.IDLE;
            }
        }
    }

    @Override
    public void setAIAsMounted(Entity ridingEntity) {
    }
}