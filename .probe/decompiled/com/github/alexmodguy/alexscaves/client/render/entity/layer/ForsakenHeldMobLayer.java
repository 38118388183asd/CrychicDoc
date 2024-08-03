package com.github.alexmodguy.alexscaves.client.render.entity.layer;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.model.ForsakenModel;
import com.github.alexmodguy.alexscaves.client.render.entity.ForsakenRenderer;
import com.github.alexmodguy.alexscaves.server.entity.living.ForsakenEntity;
import com.github.alexmodguy.alexscaves.server.misc.ACMath;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class ForsakenHeldMobLayer extends RenderLayer<ForsakenEntity, ForsakenModel> {

    public ForsakenHeldMobLayer(ForsakenRenderer render) {
        super(render);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, ForsakenEntity forsaken, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        Entity heldMob = forsaken.getHeldMob();
        if (heldMob != null) {
            AlexsCaves.PROXY.releaseRenderingEntity(heldMob.getUUID());
            float vehicleRot = forsaken.f_20884_ + (forsaken.f_20883_ - forsaken.f_20884_) * partialTicks;
            float riderRot = 0.0F;
            float animationIntensity = ACMath.cullAnimationTick(forsaken.getAnimationTick(), 1.0F, forsaken.getAnimation(), partialTicks, 25, 30) * 0.75F;
            boolean right = forsaken.getAnimation() == ForsakenEntity.ANIMATION_RIGHT_PICKUP;
            float rightAmount = right ? 1.0F : -1.0F;
            if (heldMob instanceof LivingEntity living) {
                riderRot = living.yBodyRotO + (living.yBodyRot - living.yBodyRotO) * partialTicks;
            }
            matrixStackIn.pushPose();
            Vec3 offset;
            if (right) {
                offset = new Vec3((double) (0.8F + animationIntensity), (double) (0.8F - animationIntensity), (double) (0.35F * heldMob.getBbHeight() - animationIntensity * 0.5F));
            } else {
                offset = new Vec3((double) (-0.8F - animationIntensity), (double) (0.8F - animationIntensity), (double) (0.35F * heldMob.getBbHeight() - animationIntensity * 0.5F));
            }
            Vec3 handPosition = ((ForsakenModel) this.m_117386_()).getHandPosition(right, offset);
            matrixStackIn.translate(handPosition.x, handPosition.y, handPosition.z);
            matrixStackIn.mulPose(Axis.ZP.rotationDegrees(180.0F));
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(vehicleRot - riderRot));
            if (!AlexsCaves.PROXY.isFirstPersonPlayer(heldMob)) {
                this.renderEntity(heldMob, 0.0, 0.0, 0.0, 0.0F, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            }
            matrixStackIn.popPose();
            AlexsCaves.PROXY.blockRenderingEntity(heldMob.getUUID());
        }
    }

    public <E extends Entity> void renderEntity(E entityIn, double x, double y, double z, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render = null;
        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
        try {
            render = manager.getRenderer(entityIn);
            if (render != null) {
                try {
                    render.render(entityIn, yaw, partialTicks, matrixStack, bufferIn, packedLight);
                } catch (Throwable var19) {
                    throw new ReportedException(CrashReport.forThrowable(var19, "Rendering entity in world"));
                }
            }
        } catch (Throwable var20) {
            CrashReport crashreport = CrashReport.forThrowable(var20, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Entity being rendered");
            entityIn.fillCrashReportCategory(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.addCategory("Renderer details");
            crashreportcategory1.setDetail("Assigned renderer", render);
            crashreportcategory1.setDetail("Rotation", yaw);
            crashreportcategory1.setDetail("Delta", partialTicks);
            throw new ReportedException(crashreport);
        }
    }
}