package net.mehvahdjukaar.supplementaries.common.items;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.mehvahdjukaar.moonlight.api.item.IFirstPersonAnimationProvider;
import net.mehvahdjukaar.moonlight.api.item.IThirdPersonAnimationProvider;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.mehvahdjukaar.moonlight.api.util.math.MthUtils;
import net.mehvahdjukaar.supplementaries.common.block.blocks.BubbleBlock;
import net.mehvahdjukaar.supplementaries.configs.CommonConfigs;
import net.mehvahdjukaar.supplementaries.integration.CompatHandler;
import net.mehvahdjukaar.supplementaries.integration.FlanCompat;
import net.mehvahdjukaar.supplementaries.reg.ModParticles;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.mehvahdjukaar.supplementaries.reg.ModSounds;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BubbleBlowerItem extends Item implements IThirdPersonAnimationProvider, IFirstPersonAnimationProvider {

    public BubbleBlowerItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        int charges = this.getCharges(itemstack);
        if (charges > 0) {
            int ench = EnchantmentHelper.getItemEnchantmentLevel((Enchantment) ModRegistry.STASIS_ENCHANTMENT.get(), itemstack);
            if (ench > 0) {
                return this.deployBubbleBlock(itemstack, level, player, hand);
            } else {
                player.m_6672_(hand);
                return InteractionResultHolder.consume(itemstack);
            }
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    private InteractionResultHolder<ItemStack> deployBubbleBlock(ItemStack stack, Level level, Player player, InteractionHand hand) {
        if ((player.getAbilities().instabuild ? Utils.rayTrace(player, level, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY) : Utils.rayTrace(player, level, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, 2.6)) instanceof BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState first = level.getBlockState(pos);
            if (!first.m_247087_()) {
                pos = pos.relative(hitResult.getDirection());
            }
            first = level.getBlockState(pos);
            if (first.m_247087_()) {
                BlockState bubble = ((BubbleBlock) ModRegistry.BUBBLE_BLOCK.get()).m_49966_();
                if (CompatHandler.FLAN && !FlanCompat.canPlace(player, pos)) {
                    return InteractionResultHolder.fail(stack);
                }
                if (!level.isClientSide) {
                    level.setBlockAndUpdate(pos, bubble);
                    SoundType soundtype = bubble.m_60827_();
                    level.playSound(null, pos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                }
                if (!player.getAbilities().instabuild) {
                    int max = this.getMaxDamage(stack);
                    this.setDamage(stack, Math.min(max, this.getDamage(stack) + (Integer) CommonConfigs.Tools.BUBBLE_BLOWER_COST.get()));
                }
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (this.getCharges(stack) != 0) {
            tooltip.add(Component.translatable("message.supplementaries.bubble_blower_tooltip", stack.getMaxDamage() - stack.getDamageValue(), stack.getMaxDamage()));
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return this.getCharges(stack) > 0;
    }

    private int getCharges(ItemStack stack) {
        return this.getMaxDamage(stack) - this.getDamage(stack);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    @Override
    public int getEnchantmentValue() {
        return 0;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 15246564;
    }

    public boolean isRepairable(ItemStack stack) {
        return false;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        ListTag enchantments = EnchantedBookItem.getEnchantments(book);
        return enchantments.size() == 1 && EnchantmentHelper.getEnchantmentId(enchantments.getCompound(0)).equals(EnchantmentHelper.getEnchantmentId((Enchantment) ModRegistry.STASIS_ENCHANTMENT.get()));
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        int damage = this.getDamage(stack) + 1;
        if (damage > this.getMaxDamage(stack)) {
            entity.stopUsingItem();
        } else {
            if (!(entity instanceof Player player) || !player.getAbilities().instabuild) {
                this.setDamage(stack, damage);
            }
            int soundLength = 4;
            if (remainingUseDuration % soundLength == 0) {
                Player p = entity instanceof Player pl ? pl : null;
                level.playSound(p, entity, (SoundEvent) ModSounds.BUBBLE_BLOW.get(), entity.m_5720_(), 1.0F, MthUtils.nextWeighted(level.random, 0.2F) + 0.95F);
            }
            if (level.isClientSide) {
                Vec3 v = entity.m_20252_(0.0F).normalize();
                double x = entity.m_20185_() + v.x;
                double y = entity.m_20188_() + v.y - 0.12;
                double z = entity.m_20189_() + v.z;
                RandomSource r = entity.getRandom();
                v = v.scale(0.1 + (double) (r.nextFloat() * 0.1F));
                double dx = v.x + (0.5 - (double) r.nextFloat()) * 0.08;
                double dy = v.y + (0.5 - (double) r.nextFloat()) * 0.04;
                double dz = v.z + (0.5 - (double) r.nextFloat()) * 0.08;
                level.addParticle((ParticleOptions) ModParticles.SUDS_PARTICLE.get(), x, y, z, dx, dy, dz);
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    @Override
    public <T extends LivingEntity> boolean poseLeftArm(ItemStack stack, HumanoidModel<T> model, T entity, HumanoidArm mainHand) {
        if (entity.getUseItemRemainingTicks() > 0 && entity.getUseItem().getItem() == this) {
            this.animateHands(model, entity, true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public <T extends LivingEntity> boolean poseRightArm(ItemStack stack, HumanoidModel<T> model, T entity, HumanoidArm mainHand) {
        if (entity.getUseItemRemainingTicks() > 0 && entity.getUseItem().getItem() == this) {
            this.animateHands(model, entity, false);
            return true;
        } else {
            return false;
        }
    }

    public <T extends LivingEntity> void animateHands(HumanoidModel<T> model, T entity, boolean leftHand) {
        ModelPart mainHand = leftHand ? model.leftArm : model.rightArm;
        int dir = leftHand ? -1 : 1;
        float cr = entity.m_6047_() ? 0.3F : 0.0F;
        float headXRot = MthUtils.wrapRad(model.head.xRot);
        float headYRot = MthUtils.wrapRad(model.head.yRot);
        float pitch = Mth.clamp(headXRot, -1.6F, 0.8F) + 0.55F - cr;
        mainHand.xRot = (float) ((double) pitch - (Math.PI / 2)) - (float) dir * 0.3F * headYRot;
        float yaw = 0.7F * (float) dir;
        mainHand.yRot = Mth.clamp(-yaw * Mth.cos(pitch + cr) + headYRot, -1.1F, 1.1F);
        mainHand.zRot = -yaw * Mth.sin(pitch + cr);
        AnimationUtils.bobModelPart(mainHand, (float) entity.f_19797_, (float) (-dir));
    }

    @Override
    public void animateItemFirstPerson(LivingEntity entity, ItemStack stack, InteractionHand hand, PoseStack matrixStack, float partialTicks, float pitch, float attackAnim, float handHeight) {
        if (entity.isUsingItem() && entity.getUseItemRemainingTicks() > 0 && entity.getUsedItemHand() == hand) {
            float timeLeft = (float) stack.getUseDuration() - ((float) entity.getUseItemRemainingTicks() - partialTicks + 1.0F);
            float f12 = 1.0F;
            if (f12 > 0.1F) {
                float f15 = Mth.sin((timeLeft - 0.1F) * 1.3F);
                float f18 = f12 - 0.1F;
                float f20 = f15 * f18;
                matrixStack.translate(0.0F, f20 * 0.004F, 0.0F);
            }
            matrixStack.translate(0.0F, 0.0F, f12 * 0.04F);
            matrixStack.scale(1.0F, 1.0F, 1.0F + f12 * 0.2F);
        }
    }
}