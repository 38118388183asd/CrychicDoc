package com.github.alexthe666.citadel.mixin;

import com.github.alexthe666.citadel.server.generation.SurfaceRulesManager;
import java.util.HashMap;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ NoiseBasedChunkGenerator.class })
public class NoiseBasedChunkGeneratorMixin {

    private final Function<SurfaceRules.RuleSource, SurfaceRules.RuleSource> rulesToMerge = Util.memoize(SurfaceRulesManager::mergeOverworldRules);

    private final HashMap<NoiseGeneratorSettings, SurfaceRules.RuleSource> mergedRulesMap = new HashMap();

    @Redirect(method = { "Lnet/minecraft/world/level/levelgen/NoiseBasedChunkGenerator;buildSurface(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/levelgen/WorldGenerationContext;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/biome/BiomeManager;Lnet/minecraft/core/Registry;Lnet/minecraft/world/level/levelgen/blending/Blender;)V" }, remap = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;surfaceRule()Lnet/minecraft/world/level/levelgen/SurfaceRules$RuleSource;"))
    private SurfaceRules.RuleSource citadel_buildSurface_surfaceRuleRedirect(NoiseGeneratorSettings noiseGeneratorSettings) {
        return this.getMergedRulesFor(noiseGeneratorSettings);
    }

    @Redirect(method = { "Lnet/minecraft/world/level/levelgen/NoiseBasedChunkGenerator;applyCarvers(Lnet/minecraft/server/level/WorldGenRegion;JLnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/biome/BiomeManager;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/levelgen/GenerationStep$Carving;)V" }, remap = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/NoiseGeneratorSettings;surfaceRule()Lnet/minecraft/world/level/levelgen/SurfaceRules$RuleSource;"))
    private SurfaceRules.RuleSource citadel_applyCarvers_surfaceRuleRedirect(NoiseGeneratorSettings noiseGeneratorSettings) {
        return this.getMergedRulesFor(noiseGeneratorSettings);
    }

    private SurfaceRules.RuleSource getMergedRulesFor(NoiseGeneratorSettings settings) {
        SurfaceRules.RuleSource merged = (SurfaceRules.RuleSource) this.mergedRulesMap.get(settings);
        if (merged == null) {
            merged = (SurfaceRules.RuleSource) this.rulesToMerge.apply(settings.surfaceRule());
            this.mergedRulesMap.put(settings, merged);
        }
        return merged;
    }
}