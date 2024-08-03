package io.github.apace100.origins.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.apace100.origins.badge.Badge;
import io.github.apace100.origins.badge.BadgeManager;
import io.github.apace100.origins.mixin.DrawContextAccessor;
import io.github.apace100.origins.origin.Impact;
import io.github.edwinmindcraft.apoli.api.ApoliAPI;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.origins.api.OriginsAPI;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class OriginDisplayScreen extends Screen {

    private static final ResourceLocation WINDOW = new ResourceLocation("origins", "textures/gui/choose_origin.png");

    @NotNull
    private Holder<Origin> origin = unboundOrigin();

    @NotNull
    private Holder<OriginLayer> layer = unboundLayer();

    private boolean isOriginRandom;

    private Component randomOriginText;

    protected static final int windowWidth = 176;

    protected static final int windowHeight = 182;

    protected int scrollPos = 0;

    private int currentMaxScroll = 0;

    protected float time = 0.0F;

    protected int guiTop;

    protected int guiLeft;

    protected final boolean showDirtBackground;

    private final LinkedList<OriginDisplayScreen.RenderedBadge> renderedBadges = new LinkedList();

    private boolean scrolling = false;

    private int scrollDragStart = 0;

    private double mouseDragStart = 0.0;

    private static Holder<Origin> unboundOrigin() {
        return Holder.Reference.createStandAlone(OriginsAPI.getOriginsRegistry(null).holderOwner(), null);
    }

    private static Holder<OriginLayer> unboundLayer() {
        return Holder.Reference.createStandAlone(OriginsAPI.getLayersRegistry(null).holderOwner(), null);
    }

    public OriginDisplayScreen(Component title, boolean showDirtBackground) {
        super(title);
        this.showDirtBackground = showDirtBackground;
        this.showNone();
    }

    public void showNone() {
        this.showOrigin(unboundOrigin(), unboundLayer(), false);
    }

    public void showOrigin(Holder<Origin> origin, Holder<OriginLayer> layer, boolean isRandom) {
        this.origin = origin;
        this.layer = layer;
        this.isOriginRandom = isRandom;
        this.scrollPos = 0;
        this.time = 0.0F;
    }

    public void setRandomOriginText(Component text) {
        this.randomOriginText = text;
    }

    @Override
    protected void init() {
        super.init();
        this.guiLeft = (this.f_96543_ - 176) / 2;
        this.guiTop = (this.f_96544_ - 182) / 2;
    }

    @NotNull
    public Holder<Origin> getCurrentOrigin() {
        return this.origin;
    }

    @NotNull
    public Holder<OriginLayer> getCurrentLayer() {
        return this.layer;
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics) {
        if (this.showDirtBackground) {
            super.renderDirtBackground(graphics);
        } else {
            super.renderBackground(graphics);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderedBadges.clear();
        this.time += delta;
        this.renderBackground(graphics);
        this.renderOriginWindow(graphics, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, delta);
        if (this.origin.isBound()) {
            this.renderScrollbar(graphics, mouseX, mouseY);
            this.renderBadgeTooltip(graphics, mouseX, mouseY);
        }
    }

    private void renderScrollbar(GuiGraphics graphics, int mouseX, int mouseY) {
        if (this.canScroll()) {
            graphics.blit(WINDOW, this.guiLeft + 155, this.guiTop + 35, 188, 24, 8, 134);
            int scrollbarY = 36;
            int maxScrollbarOffset = 141;
            int u = 176;
            float part = (float) this.scrollPos / (float) this.currentMaxScroll;
            scrollbarY = (int) ((float) scrollbarY + (float) (maxScrollbarOffset - scrollbarY) * part);
            if (this.scrolling) {
                u += 6;
            } else if (mouseX >= this.guiLeft + 156 && mouseX < this.guiLeft + 156 + 6 && mouseY >= this.guiTop + scrollbarY && mouseY < this.guiTop + scrollbarY + 27) {
                u += 6;
            }
            graphics.blit(WINDOW, this.guiLeft + 156, this.guiTop + scrollbarY, u, 24, 6, 27);
        }
    }

    private boolean canScroll() {
        return this.origin.isBound() && this.currentMaxScroll > 0;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.scrolling = false;
        return super.m_6348_(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.canScroll()) {
            this.scrolling = false;
            int scrollbarY = 36;
            int maxScrollbarOffset = 141;
            float part = (float) this.scrollPos / (float) this.currentMaxScroll;
            scrollbarY = (int) ((float) scrollbarY + (float) (maxScrollbarOffset - scrollbarY) * part);
            if (mouseX >= (double) (this.guiLeft + 156) && mouseX < (double) (this.guiLeft + 156 + 6) && mouseY >= (double) (this.guiTop + scrollbarY) && mouseY < (double) (this.guiTop + scrollbarY + 27)) {
                this.scrolling = true;
                this.scrollDragStart = scrollbarY;
                this.mouseDragStart = mouseY;
                return true;
            }
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrolling) {
            int delta = (int) (mouseY - this.mouseDragStart);
            int newScrollPos = Math.max(36, Math.min(141, this.scrollDragStart + delta));
            float part = (float) (newScrollPos - 36) / 105.0F;
            this.scrollPos = (int) (part * (float) this.currentMaxScroll);
        }
        return super.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
    }

    private void renderBadgeTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        for (OriginDisplayScreen.RenderedBadge rb : this.renderedBadges) {
            if (mouseX >= rb.x && mouseX < rb.x + 9 && mouseY >= rb.y && mouseY < rb.y + 9 && rb.hasTooltip()) {
                int widthLimit = this.f_96543_ - mouseX - 24;
                ((DrawContextAccessor) graphics).invokeDrawTooltip(this.f_96547_, rb.getTooltipComponents(this.f_96547_, widthLimit), mouseX, mouseY, DefaultTooltipPositioner.INSTANCE);
            }
        }
    }

    protected Component getTitleText() {
        return Component.literal("Origins");
    }

    private void renderOriginWindow(GuiGraphics graphics, int mouseX, int mouseY) {
        RenderSystem.enableBlend();
        this.renderWindowBackground(graphics, 16, 0);
        if (this.origin.isBound()) {
            this.renderOriginContent(graphics, mouseX, mouseY);
        }
        graphics.blit(WINDOW, this.guiLeft, this.guiTop, 0, 0, 176, 182);
        if (this.origin.isBound()) {
            graphics.pose().pushPose();
            graphics.pose().translate(0.0F, 0.0F, 5.0F);
            this.renderOriginName(graphics);
            RenderSystem.setShaderTexture(0, WINDOW);
            this.renderOriginImpact(graphics, mouseX, mouseY);
            graphics.pose().popPose();
            Component title = this.getTitleText();
            graphics.drawCenteredString(this.f_96547_, title.getString(), this.f_96543_ / 2, this.guiTop - 15, 16777215);
        }
        RenderSystem.disableBlend();
    }

    private void renderOriginImpact(GuiGraphics graphics, int mouseX, int mouseY) {
        Impact impact = ((Origin) this.getCurrentOrigin().get()).getImpact();
        int impactValue = impact.getImpactValue();
        int wOffset = impactValue * 8;
        for (int i = 0; i < 3; i++) {
            if (i < impactValue) {
                graphics.blit(WINDOW, this.guiLeft + 128 + i * 10, this.guiTop + 19, 176 + wOffset, 16, 8, 8);
            } else {
                graphics.blit(WINDOW, this.guiLeft + 128 + i * 10, this.guiTop + 19, 176, 16, 8, 8);
            }
        }
        if (mouseX >= this.guiLeft + 128 && mouseX <= this.guiLeft + 158 && mouseY >= this.guiTop + 19 && mouseY <= this.guiTop + 27) {
            Component ttc = Component.translatable("origins.gui.impact.impact").append(": ").append(impact.getTextComponent());
            graphics.renderTooltip(this.f_96547_, ttc, mouseX, mouseY);
        }
    }

    private void renderOriginName(GuiGraphics graphics) {
        FormattedText originName = this.f_96547_.substrByWidth(((Origin) this.getCurrentOrigin().get()).getName(), 140);
        graphics.drawString(this.f_96547_, originName.getString(), this.guiLeft + 39, this.guiTop + 19, 16777215);
        ItemStack is = ((Origin) this.getCurrentOrigin().get()).getIcon();
        graphics.renderItem(is, this.guiLeft + 15, this.guiTop + 15);
    }

    private void renderWindowBackground(GuiGraphics graphics, int offsetYStart, int offsetYEnd) {
        int border = 13;
        int endX = this.guiLeft + 176 - border;
        int endY = this.guiTop + 182 - border;
        for (int x = this.guiLeft; x < endX; x += 16) {
            for (int y = this.guiTop + offsetYStart; y < endY + offsetYEnd; y += 16) {
                graphics.blit(WINDOW, x, y, 176, 0, Math.max(16, endX - x), Math.max(16, endY + offsetYEnd - y));
            }
        }
    }

    @Override
    public boolean mouseScrolled(double x, double y, double z) {
        boolean retValue = super.m_6050_(x, y, z);
        int np = this.scrollPos - (int) z * 4;
        this.scrollPos = np < 0 ? 0 : Math.min(np, this.currentMaxScroll);
        return retValue;
    }

    private void renderOriginContent(GuiGraphics graphics, int mouseX, int mouseY) {
        int textWidth = 128;
        Origin origin = (Origin) this.getCurrentOrigin().get();
        int x = this.guiLeft + 18;
        int y = this.guiTop + 50;
        int startY = y;
        int endY = y - 72 + 182;
        y -= this.scrollPos;
        Component orgDesc = origin.getDescription();
        for (FormattedCharSequence line : this.f_96547_.split(orgDesc, textWidth)) {
            if (y >= startY - 18 && y <= endY + 12) {
                graphics.drawString(this.f_96547_, line, x + 2, y - 6, 13421772, false);
            }
            y += 12;
        }
        if (this.isOriginRandom) {
            for (FormattedCharSequence line : this.f_96547_.split(this.randomOriginText, textWidth)) {
                y += 12;
                if (y >= startY - 24 && y <= endY + 12) {
                    graphics.drawString(this.f_96547_, line, x + 2, y, 13421772, false);
                }
            }
            y += 14;
        } else {
            Registry<ConfiguredPower<?, ?>> powers = ApoliAPI.getPowers();
            for (Holder<ConfiguredPower<?, ?>> holder : origin.getValidPowers().toList()) {
                if (holder.isBound() && !((ConfiguredPower) holder.get()).getData().hidden()) {
                    Optional<ResourceLocation> id = ((Optional) holder.unwrap().map(Optional::of, powers::m_7854_)).map(ResourceKey::m_135782_);
                    if (!id.isEmpty()) {
                        ConfiguredPower<?, ?> p = (ConfiguredPower<?, ?>) holder.get();
                        FormattedCharSequence name = Language.getInstance().getVisualOrder(this.f_96547_.substrByWidth(p.getData().getName().withStyle(ChatFormatting.UNDERLINE), textWidth));
                        Component desc = p.getData().getDescription();
                        List<FormattedCharSequence> drawLines = this.f_96547_.split(desc, textWidth);
                        if (y >= startY - 24 && y <= endY + 12) {
                            graphics.drawString(this.f_96547_, name, x, y, 16777215, false);
                            int tw = this.f_96547_.width(name);
                            Collection<Badge> badges = BadgeManager.getPowerBadges((ResourceLocation) id.get());
                            int xStart = x + tw + 4;
                            int bi = 0;
                            for (Badge badge : badges) {
                                OriginDisplayScreen.RenderedBadge renderedBadge = new OriginDisplayScreen.RenderedBadge(p, badge, xStart + 10 * bi, y - 1);
                                this.renderedBadges.add(renderedBadge);
                                graphics.blit(badge.spriteId(), xStart + 10 * bi, y - 1, 0.0F, 0.0F, 9, 9, 9, 9);
                                bi++;
                            }
                        }
                        for (FormattedCharSequence linex : drawLines) {
                            y += 12;
                            if (y >= startY - 24 && y <= endY + 12) {
                                graphics.drawString(this.f_96547_, linex, x + 2, y, 13421772, false);
                            }
                        }
                        y += 14;
                    }
                }
            }
        }
        y += this.scrollPos;
        this.currentMaxScroll = y - 14 - (this.guiTop + 158);
        if (this.currentMaxScroll < 0) {
            this.currentMaxScroll = 0;
        }
    }

    private class RenderedBadge {

        private final ConfiguredPower<?, ?> powerType;

        private final Badge badge;

        private final int x;

        private final int y;

        public RenderedBadge(ConfiguredPower<?, ?> powerType, Badge badge, int x, int y) {
            this.powerType = powerType;
            this.badge = badge;
            this.x = x;
            this.y = y;
        }

        public boolean hasTooltip() {
            return this.badge.hasTooltip();
        }

        public List<ClientTooltipComponent> getTooltipComponents(Font textRenderer, int widthLimit) {
            return this.badge.getTooltipComponents(this.powerType, widthLimit, OriginDisplayScreen.this.time, textRenderer);
        }
    }
}