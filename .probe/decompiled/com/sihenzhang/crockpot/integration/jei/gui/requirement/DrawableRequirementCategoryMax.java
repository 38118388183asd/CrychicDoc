package com.sihenzhang.crockpot.integration.jei.gui.requirement;

import com.sihenzhang.crockpot.base.FoodCategory;
import com.sihenzhang.crockpot.recipe.FoodValuesDefinition;
import com.sihenzhang.crockpot.recipe.cooking.requirement.RequirementCategoryMax;
import com.sihenzhang.crockpot.util.MathUtils;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class DrawableRequirementCategoryMax extends AbstractDrawableRequirement<RequirementCategoryMax> {

    public DrawableRequirementCategoryMax(RequirementCategoryMax requirement) {
        super(requirement, MathUtils.fuzzyIsZero(requirement.getMax()) ? Component.translatable("integration.crockpot.jei.crock_pot_cooking.requirement.no") : Component.translatable("integration.crockpot.jei.crock_pot_cooking.requirement.le", requirement.getMax()));
    }

    @Override
    public int getWidth() {
        return 23 + Minecraft.getInstance().font.width(this.description);
    }

    @Override
    public int getHeight() {
        return 22;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, int xOffset, int yOffset) {
        super.draw(guiGraphics, xOffset, yOffset);
        guiGraphics.drawString(Minecraft.getInstance().font, this.description, MathUtils.fuzzyIsZero(this.requirement.getMax()) ? xOffset + 3 : xOffset + 20, yOffset + 7, 0, false);
    }

    @Override
    public List<ItemStack> getInvisibleInputs() {
        return MathUtils.fuzzyIsZero(this.requirement.getMax()) ? List.of() : List.copyOf(FoodValuesDefinition.getMatchedItems(this.requirement.getCategory(), Minecraft.getInstance().level));
    }

    @Override
    public List<AbstractDrawableRequirement.GuiItemStacksInfo> getGuiItemStacksInfos(int xOffset, int yOffset) {
        return List.of(new AbstractDrawableRequirement.GuiItemStacksInfo(List.of(FoodCategory.getItemStack(this.requirement.getCategory())), MathUtils.fuzzyIsZero(this.requirement.getMax()) ? xOffset + this.getWidth() - 19 : xOffset + 3, yOffset + 3, MathUtils.fuzzyIsZero(this.requirement.getMax())));
    }
}