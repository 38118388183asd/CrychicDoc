package net.minecraft.client.gui.components.tabs;

import java.util.function.Consumer;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.network.chat.Component;

public class GridLayoutTab implements Tab {

    private final Component title;

    protected final GridLayout layout = new GridLayout();

    public GridLayoutTab(Component component0) {
        this.title = component0;
    }

    @Override
    public Component getTabTitle() {
        return this.title;
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumerAbstractWidget0) {
        this.layout.m_264134_(consumerAbstractWidget0);
    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle0) {
        this.layout.arrangeElements();
        FrameLayout.alignInRectangle(this.layout, screenRectangle0, 0.5F, 0.16666667F);
    }
}