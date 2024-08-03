package se.mickelus.tetra.gui.stats;

import javax.annotation.ParametersAreNonnullByDefault;
import se.mickelus.tetra.effect.ExecuteEffect;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.effect.LungeEffect;
import se.mickelus.tetra.effect.OverpowerEffect;
import se.mickelus.tetra.effect.PryChargedEffect;
import se.mickelus.tetra.effect.PunctureEffect;
import se.mickelus.tetra.effect.ReapEffect;
import se.mickelus.tetra.effect.SlamEffect;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.bar.GuiStatIndicator;
import se.mickelus.tetra.gui.stats.getter.IStatGetter;
import se.mickelus.tetra.gui.stats.getter.LabelGetterBasic;
import se.mickelus.tetra.gui.stats.getter.StatFormat;
import se.mickelus.tetra.gui.stats.getter.StatGetterAbilityChargeTime;
import se.mickelus.tetra.gui.stats.getter.StatGetterAbilityCooldown;
import se.mickelus.tetra.gui.stats.getter.StatGetterAbilityDamage;
import se.mickelus.tetra.gui.stats.getter.StatGetterCooldown;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectEfficiency;
import se.mickelus.tetra.gui.stats.getter.StatGetterEffectLevel;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterMultiValue;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterNone;
import se.mickelus.tetra.gui.stats.getter.TooltipGetterPercentage;

@ParametersAreNonnullByDefault
public class AbilityStats {

    public static final IStatGetter abilitySpeedGetter = new StatGetterEffectLevel(ItemEffect.abilitySpeed, 1.0);

    public static final IStatGetter abilityDefensiveGetter = new StatGetterEffectLevel(ItemEffect.abilityDefensive, 1.0);

    public static final IStatGetter abilityDefEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityDefensive, 1.0);

    public static final IStatGetter abilityOverchargeGetter = new StatGetterEffectLevel(ItemEffect.abilityOvercharge, 1.0);

    public static final IStatGetter abilityOverchargeEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityOvercharge, 1.0);

    public static final IStatGetter abilityMomentumGetter = new StatGetterEffectLevel(ItemEffect.abilityMomentum, 1.0);

    public static final IStatGetter abilityMomentumEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityMomentum, 1.0);

    public static final IStatGetter abilityComboGetter = new StatGetterEffectLevel(ItemEffect.abilityCombo, 1.0);

    public static final IStatGetter abilityComboEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityCombo, 1.0);

    public static final IStatGetter abilityRevengeGetter = new StatGetterEffectLevel(ItemEffect.abilityRevenge, 1.0);

    public static final IStatGetter abilityRevengeEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityRevenge, 1.0);

    public static final IStatGetter abilityOverextendGetter = new StatGetterEffectLevel(ItemEffect.abilityOverextend, 1.0);

    public static final IStatGetter abilityOverextendEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityOverextend, 1.0);

    public static final IStatGetter abilityExhilarationGetter = new StatGetterEffectLevel(ItemEffect.abilityExhilaration, 1.0);

    public static final IStatGetter abilityExhilarationEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityExhilaration, 1.0);

    public static final IStatGetter abilityEchoGetter = new StatGetterEffectLevel(ItemEffect.abilityEcho, 1.0);

    public static final IStatGetter abilityEchoEffGetter = new StatGetterEffectEfficiency(ItemEffect.abilityEcho, 1.0);

    public static final IStatGetter lungeGetter = new StatGetterEffectLevel(ItemEffect.lunge, 1.0);

    public static final IStatGetter executeGetter = new StatGetterEffectLevel(ItemEffect.execute, 0.1);

    public static final IStatGetter slamGetter = new StatGetterEffectLevel(ItemEffect.slam, 1.0);

    public static final IStatGetter slamEntityGetter = new StatGetterEffectLevel(ItemEffect.slam, 1.5);

    public static final IStatGetter punctureGetter = new StatGetterEffectLevel(ItemEffect.puncture, 5.0);

    public static final IStatGetter pryGetter = new StatGetterEffectLevel(ItemEffect.pry, 1.0);

    public static final GuiStatBar pry = new GuiStatBar(0, 0, 59, "tetra.stats.pry_armor", 0.0, 10.0, false, pryGetter, LabelGetterBasic.integerLabel, new TooltipGetterMultiValue("tetra.stats.pry_armor.tooltip", StatsHelper.withStats(new StatGetterAbilityDamage(0.0, 0.5), pryGetter, new StatGetterEffectEfficiency(ItemEffect.pry, 1.0), new StatGetterCooldown(2.0, 3.0)), StatsHelper.withFormat(StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.noDecimal, StatFormat.oneDecimal))).setIndicators(new GuiStatIndicator(0, 0, "tetra.stats.ability_speed_bonus", 8, abilitySpeedGetter, new TooltipGetterPercentage("tetra.stats.pry_speed_bonus.tooltip", abilitySpeedGetter)), new GuiStatIndicator(0, 0, "tetra.stats.ability_defensive", 9, abilityDefensiveGetter, new TooltipGetterMultiValue("tetra.stats.pry_defensive.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityDefensive, 4.0), abilityDefEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overcharge", 10, abilityOverchargeGetter, new TooltipGetterMultiValue("tetra.stats.pry_overcharge.tooltip", StatsHelper.withStats(abilityOverchargeGetter, StatsHelper.multiply(abilityOverchargeGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityOverchargeEffGetter, new StatGetterAbilityChargeTime(PryChargedEffect.instance)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_momentum", 11, abilityMomentumGetter, new TooltipGetterMultiValue("tetra.stats.pry_momentum.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityMomentum, 0.05)), StatsHelper.withFormat(StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_combo", 12, abilityComboGetter, new TooltipGetterMultiValue("tetra.stats.pry_combo.tooltip", StatsHelper.withStats(abilityComboGetter, StatsHelper.multiply(abilityComboGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityComboEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_revenge", 13, abilityRevengeGetter, new TooltipGetterMultiValue("tetra.stats.pry_revenge.tooltip", StatsHelper.withStats(abilityRevengeGetter, StatsHelper.multiply(abilityRevengeGetter, new StatGetterAbilityDamage(0.0, 0.01))), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overextend", 14, abilityOverextendGetter, new TooltipGetterNone("tetra.stats.pry_overextend.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_exhilaration", 15, abilityExhilarationGetter, new TooltipGetterMultiValue("tetra.stats.pry_exhilaration.tooltip", StatsHelper.withStats(abilityExhilarationGetter, StatsHelper.multiply(abilityExhilarationGetter, new StatGetterAbilityDamage(0.0, 0.01))), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_echo", 16, abilityEchoGetter, new TooltipGetterNone("tetra.stats.pry_echo.tooltip")));

    public static final IStatGetter overpowerGetter = new StatGetterEffectLevel(ItemEffect.overpower, 1.0);

    public static final IStatGetter reapGetter = new StatGetterEffectLevel(ItemEffect.reap, 1.0);

    public static final GuiStatBar reap = new GuiStatBar(0, 0, 59, "tetra.stats.reap", 0.0, 200.0, false, reapGetter, LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue("tetra.stats.reap.tooltip", StatsHelper.withStats(reapGetter, StatsHelper.multiply(reapGetter, new StatGetterAbilityDamage(0.0, 0.01)), new StatGetterEffectEfficiency(ItemEffect.reap, 1.0), new StatGetterAbilityChargeTime(ReapEffect.instance), new StatGetterAbilityCooldown(ReapEffect.instance)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))).setIndicators(new GuiStatIndicator(0, 0, "tetra.stats.ability_speed_bonus", 8, abilitySpeedGetter, new TooltipGetterMultiValue("tetra.stats.reap_speed_bonus.tooltip", StatsHelper.withStats(abilitySpeedGetter, new StatGetterEffectEfficiency(ItemEffect.abilitySpeed, 1.0)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_defensive", 9, abilityDefensiveGetter, new TooltipGetterMultiValue("tetra.stats.reap_defensive.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityDefensive, 0.05), new StatGetterEffectLevel(ItemEffect.abilityDefensive, 0.1), abilityDefEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.noDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overcharge", 10, abilityOverchargeGetter, new TooltipGetterMultiValue("tetra.stats.reap_overcharge.tooltip", StatsHelper.withStats(abilityOverchargeGetter, StatsHelper.multiply(abilityOverchargeGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityOverchargeEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_momentum", 11, abilityMomentumGetter, new TooltipGetterMultiValue("tetra.stats.reap_momentum.tooltip", StatsHelper.withStats(abilityMomentumEffGetter, abilityMomentumGetter), StatsHelper.withFormat(StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_combo", 12, abilityComboGetter, new TooltipGetterMultiValue("tetra.stats.reap_combo.tooltip", StatsHelper.withStats(abilityComboGetter, StatsHelper.multiply(abilityComboGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityComboEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_revenge", 13, abilityRevengeGetter, new TooltipGetterMultiValue("tetra.stats.reap_revenge.tooltip", StatsHelper.withStats(abilityRevengeGetter, StatsHelper.multiply(abilityRevengeGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityRevengeEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overextend", 14, abilityOverextendGetter, new TooltipGetterMultiValue("tetra.stats.reap_overextend.tooltip", StatsHelper.withStats(abilityOverextendGetter), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_exhilaration", 15, abilityExhilarationGetter, new TooltipGetterMultiValue("tetra.stats.reap_exhilaration.tooltip", StatsHelper.withStats(abilityExhilarationEffGetter, abilityExhilarationGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_echo", 16, abilityEchoGetter, new TooltipGetterMultiValue("tetra.stats.reap_echo.tooltip", StatsHelper.withStats(abilityEchoGetter), StatsHelper.withFormat(StatFormat.noDecimal))));

    public static GuiStatIndicator abilitySpeedIndicator = new GuiStatIndicator(0, 0, "tetra.stats.ability_speed_bonus", 8, abilitySpeedGetter, new TooltipGetterPercentage("tetra.stats.ability_speed_bonus.tooltip", abilitySpeedGetter));

    public static final GuiStatBar lunge = new GuiStatBar(0, 0, 59, "tetra.stats.lunge", 0.0, 200.0, false, lungeGetter, LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue("tetra.stats.lunge.tooltip", StatsHelper.withStats(lungeGetter, StatsHelper.multiply(lungeGetter, new StatGetterAbilityDamage(0.0, 0.01)), new StatGetterAbilityChargeTime(LungeEffect.instance), new StatGetterAbilityCooldown(LungeEffect.instance)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))).setIndicators(abilitySpeedIndicator, new GuiStatIndicator(0, 0, "tetra.stats.ability_defensive", 9, abilityDefensiveGetter, new TooltipGetterNone("tetra.stats.lunge_defensive.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_overcharge", 10, abilityOverchargeGetter, new TooltipGetterMultiValue("tetra.stats.lunge_overcharge.tooltip", StatsHelper.withStats(abilityOverchargeGetter, abilityOverchargeEffGetter, StatsHelper.multiply(abilityOverchargeEffGetter, new StatGetterAbilityDamage(0.0, 0.01))), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_momentum", 11, abilityMomentumGetter, new TooltipGetterMultiValue("tetra.stats.lunge_momentum.tooltip", StatsHelper.withStats(abilityMomentumGetter, StatsHelper.multiply(abilityMomentumGetter, abilityMomentumEffGetter)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_combo", 12, abilityComboGetter, new TooltipGetterMultiValue("tetra.stats.lunge_combo.tooltip", StatsHelper.withStats(abilityComboGetter, abilityComboEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_revenge", 13, abilityRevengeGetter, new TooltipGetterNone("tetra.stats.lunge_revenge.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_overextend", 14, abilityOverextendGetter, new TooltipGetterMultiValue("tetra.stats.lunge_overextend.tooltip", StatsHelper.withStats(abilityOverextendGetter, StatsHelper.multiply(abilityOverextendGetter, new StatGetterAbilityDamage(0.0, 0.01))), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_exhilaration", 15, abilityExhilarationGetter, new TooltipGetterMultiValue("tetra.stats.lunge_exhilaration.tooltip", StatsHelper.withStats(abilityExhilarationGetter, StatsHelper.multiply(abilityExhilarationGetter, new StatGetterAbilityDamage(0.0, 0.01))), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_echo", 16, abilityEchoGetter, new TooltipGetterMultiValue("tetra.stats.lunge_echo.tooltip", StatsHelper.withStats(abilityEchoGetter), StatsHelper.withFormat(StatFormat.noDecimal))));

    public static final GuiStatBar execute = new GuiStatBar(0, 0, 59, "tetra.stats.execute", 0.0, 5.0, false, executeGetter, LabelGetterBasic.percentageLabelDecimal, new TooltipGetterMultiValue("tetra.stats.execute.tooltip", StatsHelper.withStats(new StatGetterAbilityDamage(), executeGetter, new StatGetterEffectEfficiency(ItemEffect.execute, 1.0), new StatGetterAbilityChargeTime(ExecuteEffect.instance), new StatGetterAbilityCooldown(ExecuteEffect.instance)), StatsHelper.withFormat(StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))).setIndicators(abilitySpeedIndicator, new GuiStatIndicator(0, 0, "tetra.stats.ability_defensive", 9, abilityDefensiveGetter, new TooltipGetterMultiValue("tetra.stats.execute_defensive.tooltip", StatsHelper.withStats(abilityDefensiveGetter, StatsHelper.multiply(abilityDefensiveGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityDefEffGetter, StatsHelper.multiply(abilityDefEffGetter, new StatGetterAbilityDamage(0.0, 0.01))), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overcharge", 10, abilityOverchargeGetter, new TooltipGetterMultiValue("tetra.stats.execute_overcharge.tooltip", StatsHelper.withStats(abilityOverchargeGetter), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_momentum", 11, abilityMomentumGetter, new TooltipGetterMultiValue("tetra.stats.execute_momentum.tooltip", StatsHelper.withStats(abilityMomentumGetter), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_combo", 12, abilityComboGetter, new TooltipGetterMultiValue("tetra.stats.execute_combo.tooltip", StatsHelper.withStats(abilityComboGetter), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.execute_revenge", 13, abilityRevengeGetter, new TooltipGetterMultiValue("tetra.stats.execute_revenge.tooltip", StatsHelper.withStats(abilityRevengeGetter), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overextend", 14, abilityOverextendGetter, new TooltipGetterMultiValue("tetra.stats.execute_overextend.tooltip", StatsHelper.withStats(abilityOverextendGetter), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_exhilaration", 15, abilityExhilarationGetter, new TooltipGetterMultiValue("tetra.stats.execute_exhilaration.tooltip", StatsHelper.withStats(abilityExhilarationGetter, abilityExhilarationEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_echo", 16, abilityEchoGetter, new TooltipGetterNone("tetra.stats.execute_echo.tooltip")));

    public static final GuiStatBar slam = new GuiStatBar(0, 0, 59, "tetra.stats.slam", 0.0, 200.0, false, slamGetter, LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue("tetra.stats.slam.tooltip", StatsHelper.withStats(slamGetter, StatsHelper.multiply(slamGetter, new StatGetterAbilityDamage(0.0, 0.01)), slamEntityGetter, StatsHelper.multiply(slamEntityGetter, new StatGetterAbilityDamage(0.0, 0.01)), new StatGetterAbilityChargeTime(SlamEffect.instance), new StatGetterAbilityCooldown(SlamEffect.instance)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))).setIndicators(abilitySpeedIndicator, new GuiStatIndicator(0, 0, "tetra.stats.ability_defensive", 9, abilityDefensiveGetter, new TooltipGetterMultiValue("tetra.stats.slam_defensive.tooltip", StatsHelper.withStats(new StatGetterAbilityDamage(0.0, 0.3), new StatGetterEffectLevel(ItemEffect.abilityDefensive, 0.05), abilityDefEffGetter), StatsHelper.withFormat(StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overcharge", 10, abilityOverchargeGetter, new TooltipGetterMultiValue("tetra.stats.slam_overcharge.tooltip", StatsHelper.withStats(abilityOverchargeGetter, StatsHelper.multiply(abilityOverchargeGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityOverchargeEffGetter, abilityOverchargeGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_momentum", 11, abilityMomentumGetter, new TooltipGetterMultiValue("tetra.stats.slam_momentum.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityMomentum, 0.05), new StatGetterEffectLevel(ItemEffect.abilityMomentum, 0.03333)), StatsHelper.withFormat(StatFormat.oneDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_combo", 12, abilityComboGetter, new TooltipGetterMultiValue("tetra.stats.slam_combo.tooltip", StatsHelper.withStats(abilityComboGetter), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_revenge", 13, abilityRevengeGetter, new TooltipGetterMultiValue("tetra.stats.slam_revenge.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityRevenge, 0.05)), StatsHelper.withFormat(StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overextend", 14, abilityOverextendGetter, new TooltipGetterMultiValue("tetra.stats.slam_overextend.tooltip", StatsHelper.withStats(abilityOverextendGetter, StatsHelper.multiply(abilityOverextendGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityOverextendEffGetter, abilityOverextendGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_exhilaration", 15, abilityExhilarationGetter, new TooltipGetterMultiValue("tetra.stats.slam_exhilaration.tooltip", StatsHelper.withStats(abilityExhilarationGetter, StatsHelper.multiply(abilityExhilarationGetter, new StatGetterAbilityDamage(0.0, 0.01)), new StatGetterEffectEfficiency(ItemEffect.abilityExhilaration, 20.0)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_echo", 16, abilityEchoGetter, new TooltipGetterNone("tetra.stats.slam_echo.tooltip")));

    public static final GuiStatBar puncture = new GuiStatBar(0, 0, 59, "tetra.stats.puncture", 0.0, 200.0, false, punctureGetter, LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue("tetra.stats.puncture.tooltip", StatsHelper.withStats(new StatGetterAbilityDamage(0.0, 1.0), punctureGetter, new StatGetterEffectEfficiency(ItemEffect.puncture, 1.0), new StatGetterAbilityChargeTime(PunctureEffect.instance), new StatGetterAbilityCooldown(PunctureEffect.instance)), StatsHelper.withFormat(StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))).setIndicators(abilitySpeedIndicator, new GuiStatIndicator(0, 0, "tetra.stats.ability_defensive", 9, abilityDefensiveGetter, new TooltipGetterMultiValue("tetra.stats.puncture_defensive.tooltip", StatsHelper.withStats(new StatGetterAbilityDamage(0.0, 0.3), new StatGetterEffectLevel(ItemEffect.abilityDefensive, 15.0), abilityDefEffGetter), StatsHelper.withFormat(StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overcharge", 10, abilityOverchargeGetter, new TooltipGetterMultiValue("tetra.stats.puncture_overcharge.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityOvercharge, 5.0), new StatGetterEffectEfficiency(ItemEffect.abilityOvercharge, 0.5)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_momentum", 11, abilityMomentumGetter, new TooltipGetterNone("tetra.stats.puncture_momentum.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_combo", 12, abilityComboGetter, new TooltipGetterMultiValue("tetra.stats.puncture_combo.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityCombo, 0.05)), StatsHelper.withFormat(StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.puncture_revenge", 13, abilityRevengeGetter, new TooltipGetterNone("tetra.stats.puncture_revenge.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_overextend", 14, abilityOverextendGetter, new TooltipGetterMultiValue("tetra.stats.puncture_overextend.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityOverextend, 5.0), abilityOverextendEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_exhilaration", 15, abilityExhilarationGetter, new TooltipGetterMultiValue("tetra.stats.puncture_exhilaration.tooltip", StatsHelper.withStats(new StatGetterEffectLevel(ItemEffect.abilityExhilaration, 0.05)), StatsHelper.withFormat(StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_echo", 16, abilityEchoGetter, new TooltipGetterNone("tetra.stats.puncture_echo.tooltip")));

    public static final GuiStatBar overpower = new GuiStatBar(0, 0, 59, "tetra.stats.overpower", 0.0, 300.0, false, overpowerGetter, LabelGetterBasic.percentageLabel, new TooltipGetterMultiValue("tetra.stats.overpower.tooltip", StatsHelper.withStats(overpowerGetter, StatsHelper.multiply(overpowerGetter, new StatGetterAbilityDamage(0.0, 0.01)), new StatGetterEffectEfficiency(ItemEffect.overpower, 1.0), new StatGetterAbilityChargeTime(OverpowerEffect.instance), new StatGetterAbilityCooldown(OverpowerEffect.instance)), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.oneDecimal))).setIndicators(abilitySpeedIndicator, new GuiStatIndicator(0, 0, "tetra.stats.ability_defensive", 9, abilityDefensiveGetter, new TooltipGetterMultiValue("tetra.stats.overpower_defensive.tooltip", StatsHelper.withStats(abilityDefensiveGetter, StatsHelper.multiply(abilityDefensiveGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityDefEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overcharge", 10, abilityOverchargeGetter, new TooltipGetterMultiValue("tetra.stats.overpower_overcharge.tooltip", StatsHelper.withStats(abilityOverchargeGetter, StatsHelper.multiply(abilityOverchargeGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityOverchargeEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_momentum", 11, abilityMomentumGetter, new TooltipGetterNone("tetra.stats.overpower_momentum.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_combo", 12, abilityComboGetter, new TooltipGetterMultiValue("tetra.stats.overpower_combo.tooltip", StatsHelper.withStats(abilityComboGetter, StatsHelper.multiply(abilityComboGetter, new StatGetterAbilityDamage(0.0, 0.01)), abilityComboEffGetter), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal, StatFormat.noDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_revenge", 13, abilityRevengeGetter, new TooltipGetterMultiValue("tetra.stats.overpower_revenge.tooltip", StatsHelper.withStats(abilityRevengeGetter, StatsHelper.multiply(abilityRevengeGetter, new StatGetterAbilityDamage(0.0, 0.01))), StatsHelper.withFormat(StatFormat.noDecimal, StatFormat.oneDecimal))), new GuiStatIndicator(0, 0, "tetra.stats.ability_overextend", 14, abilityOverextendGetter, new TooltipGetterNone("tetra.stats.overpower_overextend.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_exhilaration", 15, abilityExhilarationGetter, new TooltipGetterNone("tetra.stats.overpower_exhilaration.tooltip")), new GuiStatIndicator(0, 0, "tetra.stats.ability_echo", 16, abilityEchoGetter, new TooltipGetterMultiValue("tetra.stats.overpower_echo.tooltip", StatsHelper.withStats(StatsHelper.sum(new StatGetterAbilityChargeTime(OverpowerEffect.instance), new StatGetterAbilityCooldown(OverpowerEffect.instance), new StatGetterEffectLevel(ItemEffect.abilityEcho, 0.05))), StatsHelper.withFormat(StatFormat.noDecimal))));
}