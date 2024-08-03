package com.github.alexmodguy.alexscaves.mixin.client;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.ClientProxy;
import com.github.alexmodguy.alexscaves.server.block.EnergizedGalenaBlock;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = { LevelRenderer.class }, priority = 800)
public abstract class LevelRendererMixin {

    @Shadow
    private ClientLevel level;

    @Shadow
    private int ticks;

    private int aclastCameraChunkX;

    private int aclastCameraChunkY;

    private int aclastCameraChunkZ;

    @Shadow
    @Nullable
    private ViewArea viewArea;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Nullable
    private VertexBuffer skyBuffer;

    @Shadow
    @Final
    private static ResourceLocation SUN_LOCATION;

    @Shadow
    @Final
    private static ResourceLocation MOON_LOCATION;

    @Shadow
    @Nullable
    private VertexBuffer starBuffer;

    @Shadow
    @Nullable
    private VertexBuffer darkBuffer;

    @Shadow
    protected abstract void renderEndSky(PoseStack var1);

    @Shadow
    protected abstract boolean doesMobEffectBlockSky(Camera var1);

    @Inject(method = { "Lnet/minecraft/client/renderer/LevelRenderer;setupRender(Lnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/culling/Frustum;ZZ)V" }, at = { @At("HEAD") }, allow = 1)
    private void ac_setupRender(Camera camera, Frustum frustum, boolean b1, boolean b2, CallbackInfo ci) {
        if (Minecraft.getInstance().cameraEntity != null && Minecraft.getInstance().cameraEntity != Minecraft.getInstance().player) {
            double d0 = Minecraft.getInstance().cameraEntity.getX();
            double d1 = Minecraft.getInstance().cameraEntity.getY();
            double d2 = Minecraft.getInstance().cameraEntity.getZ();
            int i = SectionPos.posToSectionCoord(d0);
            int j = SectionPos.posToSectionCoord(d1);
            int k = SectionPos.posToSectionCoord(d2);
            if (this.aclastCameraChunkX != i || this.aclastCameraChunkY != j || this.aclastCameraChunkZ != k) {
                this.aclastCameraChunkX = i;
                this.aclastCameraChunkY = j;
                this.aclastCameraChunkZ = k;
                this.viewArea.repositionCamera(d0, d2);
            }
        }
    }

    @Inject(method = { "Lnet/minecraft/client/renderer/LevelRenderer;renderSky(Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;FLnet/minecraft/client/Camera;ZLjava/lang/Runnable;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void ac_renderSky(PoseStack poseStack, Matrix4f matrix4f2, float partialTick, Camera camera, boolean foggy, Runnable runnable, CallbackInfo ci) {
        float override = ClientProxy.acSkyOverrideAmount;
        float primordialBoss = AlexsCaves.PROXY.getPrimordialBossActiveAmount(partialTick);
        if (AlexsCaves.CLIENT_CONFIG.biomeSkyOverrides.get() && (!(override <= 0.0F) || !(primordialBoss <= 0.0F))) {
            ci.cancel();
            if (!this.level.effects().renderSky(this.level, this.ticks, partialTick, poseStack, camera, matrix4f2, foggy, runnable)) {
                runnable.run();
                if (!foggy) {
                    FogType fogtype = camera.getFluidInCamera();
                    if (fogtype != FogType.POWDER_SNOW && fogtype != FogType.LAVA && !this.doesMobEffectBlockSky(camera)) {
                        if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.END) {
                            this.renderEndSky(poseStack);
                        } else if (this.minecraft.level.effects().skyType() == DimensionSpecialEffects.SkyType.NORMAL) {
                            Vec3 vec3 = this.level.getSkyColor(this.minecraft.gameRenderer.getMainCamera().getPosition(), partialTick);
                            vec3 = ClientProxy.processSkyColor(vec3, partialTick);
                            float f = (float) vec3.x;
                            float f1 = (float) vec3.y;
                            float f2 = (float) vec3.z;
                            FogRenderer.levelFogColor();
                            BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                            RenderSystem.depthMask(false);
                            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                            ShaderInstance shaderinstance = RenderSystem.getShader();
                            this.skyBuffer.bind();
                            this.skyBuffer.drawWithShader(poseStack.last().pose(), matrix4f2, shaderinstance);
                            VertexBuffer.unbind();
                            RenderSystem.enableBlend();
                            float[] afloat = this.level.effects().getSunriseColor(this.level.m_46942_(partialTick), partialTick);
                            if (afloat != null && afloat.length >= 4 && AlexsCaves.CLIENT_CONFIG.biomeSkyOverrides.get()) {
                                afloat[3] *= 1.0F - override;
                            }
                            if (afloat != null) {
                                RenderSystem.setShader(GameRenderer::m_172811_);
                                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                                poseStack.pushPose();
                                poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
                                float f3 = Mth.sin(this.level.m_46490_(partialTick)) < 0.0F ? 180.0F : 0.0F;
                                poseStack.mulPose(Axis.ZP.rotationDegrees(f3));
                                poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
                                float f4 = afloat[0];
                                float f5 = afloat[1];
                                float f6 = afloat[2];
                                Matrix4f matrix4f = poseStack.last().pose();
                                bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
                                bufferbuilder.m_252986_(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
                                int i = 16;
                                for (int j = 0; j <= 16; j++) {
                                    float f7 = (float) j * (float) (Math.PI * 2) / 16.0F;
                                    float f8 = Mth.sin(f7);
                                    float f9 = Mth.cos(f7);
                                    bufferbuilder.m_252986_(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
                                }
                                BufferUploader.drawWithShader(bufferbuilder.end());
                                poseStack.popPose();
                            }
                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                            poseStack.pushPose();
                            float rainLevel = this.level.m_46722_(partialTick);
                            rainLevel = Math.max(override, rainLevel);
                            float f11 = 1.0F - rainLevel;
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, f11);
                            poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
                            poseStack.mulPose(Axis.XP.rotationDegrees(this.level.m_46942_(partialTick) * 360.0F));
                            Matrix4f matrix4f1 = poseStack.last().pose();
                            float f12 = 30.0F;
                            RenderSystem.setShader(GameRenderer::m_172817_);
                            RenderSystem.setShaderTexture(0, SUN_LOCATION);
                            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                            bufferbuilder.m_252986_(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                            bufferbuilder.m_252986_(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                            bufferbuilder.m_252986_(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                            bufferbuilder.m_252986_(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                            BufferUploader.drawWithShader(bufferbuilder.end());
                            f12 = 20.0F;
                            RenderSystem.setShaderTexture(0, MOON_LOCATION);
                            int k = this.level.m_46941_();
                            int l = k % 4;
                            int i1 = k / 4 % 2;
                            float f13 = (float) (l + 0) / 4.0F;
                            float f14 = (float) (i1 + 0) / 2.0F;
                            float f15 = (float) (l + 1) / 4.0F;
                            float f16 = (float) (i1 + 1) / 2.0F;
                            bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                            bufferbuilder.m_252986_(matrix4f1, -f12, -100.0F, f12).uv(f15, f16).endVertex();
                            bufferbuilder.m_252986_(matrix4f1, f12, -100.0F, f12).uv(f13, f16).endVertex();
                            bufferbuilder.m_252986_(matrix4f1, f12, -100.0F, -f12).uv(f13, f14).endVertex();
                            bufferbuilder.m_252986_(matrix4f1, -f12, -100.0F, -f12).uv(f15, f14).endVertex();
                            BufferUploader.drawWithShader(bufferbuilder.end());
                            float f10 = this.level.getStarBrightness(partialTick) * f11;
                            if (f10 > 0.0F) {
                                RenderSystem.setShaderColor(f10, f10, f10, f10);
                                FogRenderer.setupNoFog();
                                this.starBuffer.bind();
                                this.starBuffer.drawWithShader(poseStack.last().pose(), matrix4f2, GameRenderer.getPositionShader());
                                VertexBuffer.unbind();
                                runnable.run();
                            }
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.disableBlend();
                            RenderSystem.defaultBlendFunc();
                            poseStack.popPose();
                            RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);
                            double horizonHeight = this.level.getLevelData().getHorizonHeight(this.level);
                            double d0 = this.minecraft.player.m_20299_(partialTick).y - horizonHeight;
                            if (d0 < 0.0) {
                                poseStack.pushPose();
                                poseStack.translate(0.0F, 12.0F, 0.0F);
                                this.darkBuffer.bind();
                                this.darkBuffer.drawWithShader(poseStack.last().pose(), matrix4f2, shaderinstance);
                                VertexBuffer.unbind();
                                poseStack.popPose();
                            }
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.depthMask(true);
                        }
                    }
                }
            }
        }
    }

    @Inject(method = { "Lnet/minecraft/client/renderer/LevelRenderer;getLightColor(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)I" }, at = { @At("HEAD") }, cancellable = true)
    private static void ac_getLightColor(BlockAndTintGetter level, BlockState state, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (state.m_60734_() instanceof EnergizedGalenaBlock) {
            int i = level.getBrightness(LightLayer.SKY, pos);
            int j = level.getBrightness(LightLayer.BLOCK, pos);
            int k = state.getLightEmission(level, pos) - 1;
            if (j < k) {
                j = k;
            }
            cir.setReturnValue(i << 20 | j << 4);
        }
    }
}