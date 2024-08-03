package de.keksuccino.fancymenu.customization.action.actions.variables;

import de.keksuccino.fancymenu.customization.action.Action;
import de.keksuccino.fancymenu.customization.variables.VariableHandler;
import de.keksuccino.fancymenu.util.LocalizationUtils;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ClearVariablesAction extends Action {

    public ClearVariablesAction() {
        super("clear_variables");
    }

    @Override
    public boolean hasValue() {
        return false;
    }

    @Override
    public void execute(String value) {
        VariableHandler.clearVariables();
    }

    @NotNull
    @Override
    public Component getActionDisplayName() {
        return Component.translatable("fancymenu.helper.buttonaction.variables.clearall");
    }

    @NotNull
    @Override
    public Component[] getActionDescription() {
        return LocalizationUtils.splitLocalizedLines("fancymenu.helper.buttonaction.variables.clearall.desc");
    }

    @Override
    public Component getValueDisplayName() {
        return null;
    }

    @Override
    public String getValueExample() {
        return null;
    }
}