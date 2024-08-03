package de.keksuccino.fancymenu.customization.background.backgrounds.image;

import de.keksuccino.fancymenu.util.LocalizationUtils;
import de.keksuccino.fancymenu.util.cycle.CommonCycles;
import de.keksuccino.fancymenu.util.file.type.types.ImageFileType;
import de.keksuccino.fancymenu.util.rendering.ui.UIBase;
import de.keksuccino.fancymenu.util.rendering.ui.screen.CellScreen;
import de.keksuccino.fancymenu.util.rendering.ui.screen.resource.ResourceChooserScreen;
import de.keksuccino.fancymenu.util.rendering.ui.tooltip.Tooltip;
import de.keksuccino.fancymenu.util.rendering.ui.widget.button.CycleButton;
import de.keksuccino.fancymenu.util.rendering.ui.widget.button.ExtendedButton;
import de.keksuccino.fancymenu.util.resource.ResourceSupplier;
import de.keksuccino.fancymenu.util.resource.resources.texture.ITexture;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

public class ImageMenuBackgroundConfigScreen extends CellScreen {

    @NotNull
    protected Consumer<ImageMenuBackground> callback;

    @NotNull
    ImageMenuBackground background;

    protected ImageMenuBackgroundConfigScreen(@NotNull ImageMenuBackground background, @NotNull Consumer<ImageMenuBackground> callback) {
        super(Component.translatable("fancymenu.background.image.configure"));
        this.background = background;
        this.callback = callback;
    }

    @Override
    protected void initCells() {
        this.addStartEndSpacerCell();
        this.addWidgetCell(new ExtendedButton(0, 0, 20, 20, Component.translatable("fancymenu.background.image.configure.choose_image.local"), button -> {
            ResourceChooserScreen<ITexture, ImageFileType> s = ResourceChooserScreen.image(null, source -> {
                if (source != null) {
                    this.background.textureSupplier = ResourceSupplier.image(source);
                }
                Minecraft.getInstance().setScreen(this);
            });
            s.setSource(this.background.textureSupplier != null ? this.background.textureSupplier.getSourceWithPrefix() : null, false);
            Minecraft.getInstance().setScreen(s);
        }), true);
        this.addWidgetCell(new ExtendedButton(0, 0, 20, 20, Component.translatable("fancymenu.background.image.type.web.fallback"), var1x -> {
            ResourceChooserScreen<ITexture, ImageFileType> s = ResourceChooserScreen.image(null, source -> {
                if (source != null) {
                    this.background.fallbackTextureSupplier = ResourceSupplier.image(source);
                }
                Minecraft.getInstance().setScreen(this);
            });
            s.setSource(this.background.fallbackTextureSupplier != null ? this.background.fallbackTextureSupplier.getSourceWithPrefix() : null, false);
            Minecraft.getInstance().setScreen(s);
        }).setTooltip(Tooltip.of(LocalizationUtils.splitLocalizedLines("fancymenu.background.image.type.web.fallback.desc"))), true);
        this.addWidgetCell(new ExtendedButton(0, 0, 20, 20, Component.translatable("fancymenu.background.image.type.web.fallback.reset").setStyle(Style.EMPTY.withColor(UIBase.getUIColorTheme().error_text_color.getColorInt())), var1x -> this.background.fallbackTextureSupplier = null), true);
        this.addCellGroupEndSpacerCell();
        CellScreen.WidgetCell repeatButton = this.addCycleButtonCell(CommonCycles.cycleEnabledDisabled("fancymenu.background.image.configure.repeat", this.background.repeat), true, (value, button) -> this.background.repeat = value.getAsBoolean());
        if (repeatButton.widget instanceof CycleButton<?> b) {
            b.setIsActiveSupplier(consumes -> !this.background.slideLeftRight);
        }
        CellScreen.WidgetCell slideButton = this.addCycleButtonCell(CommonCycles.cycleEnabledDisabled("fancymenu.background.image.configure.slide", this.background.slideLeftRight), true, (value, button) -> this.background.slideLeftRight = value.getAsBoolean());
        if (slideButton.widget instanceof CycleButton<?> b) {
            b.setIsActiveSupplier(consumes -> !this.background.repeat);
        }
        this.addStartEndSpacerCell();
    }

    @Override
    protected void init() {
        super.init();
        if (this.doneButton != null) {
            this.doneButton.setTooltipSupplier(consumes -> this.background.textureSupplier == null ? Tooltip.of(LocalizationUtils.splitLocalizedLines("fancymenu.background.image.configure.no_image_chosen")) : null);
        }
    }

    @Override
    public boolean allowDone() {
        return this.background.textureSupplier != null;
    }

    @Override
    protected void onCancel() {
        this.callback.accept(null);
    }

    @Override
    protected void onDone() {
        this.callback.accept(this.background);
    }
}