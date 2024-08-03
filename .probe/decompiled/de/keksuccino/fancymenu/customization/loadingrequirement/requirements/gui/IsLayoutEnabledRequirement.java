package de.keksuccino.fancymenu.customization.loadingrequirement.requirements.gui;

import com.google.common.io.Files;
import de.keksuccino.fancymenu.customization.layout.Layout;
import de.keksuccino.fancymenu.customization.layout.LayoutHandler;
import de.keksuccino.fancymenu.customization.loadingrequirement.LoadingRequirement;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import de.keksuccino.fancymenu.util.rendering.ui.screen.texteditor.TextEditorFormattingRule;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.NotNull;

public class IsLayoutEnabledRequirement extends LoadingRequirement {

    public IsLayoutEnabledRequirement() {
        super("fancymenu_visibility_requirement_is_layout_enabled");
    }

    @Override
    public boolean hasValue() {
        return true;
    }

    @Override
    public boolean isRequirementMet(@Nullable String value) {
        if (value != null) {
            for (Layout s : LayoutHandler.getEnabledLayouts()) {
                if (s.layoutFile != null) {
                    return Files.getNameWithoutExtension(s.layoutFile.getName()).equals(value);
                }
            }
        }
        return false;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return I18n.get("fancymenu.helper.visibilityrequirement.is_layout_enabled");
    }

    @Override
    public List<String> getDescription() {
        return Arrays.asList(LocalizationUtils.splitLocalizedStringLines("fancymenu.helper.visibilityrequirement.is_layout_enabled.desc"));
    }

    @Override
    public String getCategory() {
        return I18n.get("fancymenu.editor.loading_requirement.category.gui");
    }

    @Override
    public String getValueDisplayName() {
        return I18n.get("fancymenu.helper.visibilityrequirement.is_layout_enabled.value.desc");
    }

    @Override
    public String getValuePreset() {
        return "my_cool_main_menu_layout";
    }

    @Override
    public List<TextEditorFormattingRule> getValueFormattingRules() {
        return null;
    }
}