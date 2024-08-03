package me.srrapero720.embeddiumplus.foundation.embeddium.pages;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpact;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlValueFormatter;
import me.jellysquid.mods.sodium.client.gui.options.control.SliderControl;
import me.jellysquid.mods.sodium.client.gui.options.control.TickBoxControl;
import me.srrapero720.embeddiumplus.EmbyConfig;
import me.srrapero720.embeddiumplus.foundation.embeddium.EmbPlusOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.embeddedt.embeddium.client.gui.options.OptionIdentifier;

public class EntityCullingPage extends OptionPage {

    public static final OptionIdentifier<Void> ID = OptionIdentifier.create(new ResourceLocation("embeddiumplus", "culling"));

    public EntityCullingPage() {
        super(ID, Component.translatable("embeddium.plus.options.culling.page"), create());
    }

    private static ImmutableList<OptionGroup> create() {
        List<OptionGroup> groups = new ArrayList();
        OptionImpl<?, Boolean> enableDistanceChecks = OptionImpl.createBuilder(boolean.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.entity.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.entity.desc")).setControl(TickBoxControl::new).setBinding((options, value) -> {
            EmbyConfig.entityDistanceCulling.set(value);
            EmbyConfig.entityDistanceCullingCache = value;
        }, options -> EmbyConfig.entityDistanceCullingCache).setImpact(OptionImpact.HIGH).build();
        OptionImpl<?, Integer> maxEntityDistance = OptionImpl.createBuilder(int.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.entity.distance.horizontal.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.entity.distance.horizontal.desc")).setControl(option -> new SliderControl(option, 16, 192, 8, ControlValueFormatter.biomeBlend())).setBinding((options, value) -> {
            int result = value * value;
            EmbyConfig.entityCullingDistanceX.set(Integer.valueOf(result));
            EmbyConfig.entityCullingDistanceXCache = result;
        }, options -> Math.toIntExact(Math.round(Math.sqrt((double) EmbyConfig.entityCullingDistanceXCache)))).setImpact(OptionImpact.HIGH).build();
        OptionImpl<?, Integer> maxEntityDistanceVertical = OptionImpl.createBuilder(int.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.entity.distance.vertical.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.entity.distance.vertical.desc")).setControl(option -> new SliderControl(option, 16, 64, 4, ControlValueFormatter.biomeBlend())).setBinding((options, value) -> {
            EmbyConfig.entityCullingDistanceY.set(value);
            EmbyConfig.entityCullingDistanceYCache = value;
        }, options -> EmbyConfig.entityCullingDistanceYCache).setImpact(OptionImpact.HIGH).build();
        groups.add(OptionGroup.createBuilder().add(enableDistanceChecks).add(maxEntityDistance).add(maxEntityDistanceVertical).build());
        OptionImpl<?, Boolean> monsterDistanceChecks = OptionImpl.createBuilder(boolean.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.monster.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.monster.desc")).setControl(TickBoxControl::new).setBinding((options, value) -> {
            EmbyConfig.monsterDistanceCulling.set(value);
            EmbyConfig.monsterDistanceCullingCache = value;
        }, options -> EmbyConfig.monsterDistanceCullingCache).setImpact(OptionImpact.HIGH).build();
        OptionImpl<?, Integer> maxMonsterDistance = OptionImpl.createBuilder(int.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.monster.distance.horizontal.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.monster.distance.horizontal.desc")).setControl(option -> new SliderControl(option, 16, 192, 8, ControlValueFormatter.biomeBlend())).setBinding((options, value) -> {
            int result = value * value;
            EmbyConfig.monsterCullingDistanceX.set(Integer.valueOf(result));
            EmbyConfig.monsterCullingDistanceXCache = result;
        }, options -> Math.toIntExact(Math.round(Math.sqrt((double) EmbyConfig.monsterCullingDistanceXCache)))).setImpact(OptionImpact.HIGH).build();
        OptionImpl<?, Integer> maxMonsterDistanceVertical = OptionImpl.createBuilder(int.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.monster.distance.vertical.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.monster.distance.vertical.desc")).setControl(option -> new SliderControl(option, 16, 64, 4, ControlValueFormatter.biomeBlend())).setBinding((options, value) -> {
            EmbyConfig.monsterCullingDistanceY.set(value);
            EmbyConfig.monsterCullingDistanceYCache = value;
        }, options -> EmbyConfig.monsterCullingDistanceYCache).setImpact(OptionImpact.HIGH).build();
        groups.add(OptionGroup.createBuilder().add(monsterDistanceChecks).add(maxMonsterDistance).add(maxMonsterDistanceVertical).build());
        OptionImpl<?, Boolean> enableTileDistanceChecks = OptionImpl.createBuilder(boolean.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.tiles.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.tiles.desc")).setControl(TickBoxControl::new).setBinding((options, value) -> {
            EmbyConfig.tileEntityDistanceCulling.set(value);
            EmbyConfig.tileEntityDistanceCullingCache = value;
        }, options -> EmbyConfig.tileEntityDistanceCullingCache).setImpact(OptionImpact.HIGH).build();
        OptionImpl<?, Integer> maxTileEntityDistance = OptionImpl.createBuilder(int.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.tile.distance.horizontal.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.tile.distance.horizontal.desc")).setControl(option -> new SliderControl(option, 16, 256, 8, ControlValueFormatter.biomeBlend())).setBinding((options, value) -> {
            int result = value * value;
            EmbyConfig.tileEntityCullingDistanceX.set(Integer.valueOf(result));
            EmbyConfig.tileEntityCullingDistanceXCache = result;
        }, options -> Math.toIntExact(Math.round(Math.sqrt((double) EmbyConfig.tileEntityCullingDistanceXCache)))).setImpact(OptionImpact.HIGH).build();
        OptionImpl<?, Integer> maxTileEntityDistanceVertical = OptionImpl.createBuilder(int.class, EmbPlusOptions.STORAGE).setName(Component.translatable("embeddium.plus.options.culling.tile.distance.vertical.title")).setTooltip(Component.translatable("embeddium.plus.options.culling.tile.distance.vertical.desc")).setControl(option -> new SliderControl(option, 16, 64, 4, ControlValueFormatter.biomeBlend())).setBinding((options, value) -> {
            EmbyConfig.tileEntityCullingDistanceY.set(value);
            EmbyConfig.tileEntityCullingDistanceYCache = value;
        }, options -> EmbyConfig.tileEntityCullingDistanceYCache).setImpact(OptionImpact.HIGH).build();
        groups.add(OptionGroup.createBuilder().add(enableTileDistanceChecks).add(maxTileEntityDistance).add(maxTileEntityDistanceVertical).build());
        return ImmutableList.copyOf(groups);
    }
}