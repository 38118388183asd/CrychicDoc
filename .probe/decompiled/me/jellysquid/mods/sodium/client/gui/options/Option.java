package me.jellysquid.mods.sodium.client.gui.options;

import java.util.Collection;
import me.jellysquid.mods.sodium.client.gui.options.control.Control;
import me.jellysquid.mods.sodium.client.gui.options.storage.OptionStorage;
import net.minecraft.network.chat.Component;
import org.embeddedt.embeddium.client.gui.options.OptionIdentifier;
import org.jetbrains.annotations.Nullable;

public interface Option<T> {

    @Nullable
    default OptionIdentifier<T> getId() {
        return null;
    }

    Component getName();

    Component getTooltip();

    OptionImpact getImpact();

    Control<T> getControl();

    T getValue();

    void setValue(T var1);

    void reset();

    OptionStorage<?> getStorage();

    boolean isAvailable();

    boolean hasChanged();

    void applyChanges();

    Collection<OptionFlag> getFlags();
}