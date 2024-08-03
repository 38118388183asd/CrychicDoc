package net.liopyu.entityjs.events;

import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import net.liopyu.entityjs.util.ContextUtils;
import net.liopyu.entityjs.util.EntityJSHelperClass;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import org.jetbrains.annotations.Nullable;

public class AddGoalTargetsEventJS<T extends Mob> extends GoalEventJS<T> {

    public AddGoalTargetsEventJS(T mob, GoalSelector selector) {
        super(mob, selector);
    }

    @Info(value = "Remove a goal from the entity via class reference.\n\nExample of usage:\n=====================================\nlet $PanicGoal = Java.loadClass(\"net.minecraft.world.entity.ai.goal.PanicGoal\")\nbuilder.removeGoal($PanicGoal)\n=====================================\n", params = { @Param(name = "goal", value = "The goal class to remove") })
    public void removeGoal(Class<? extends Goal> goal) {
        this.selector.removeAllGoals(g -> goal == g.getClass());
    }

    @Info(value = "Remove all goals fitting the specified predicate. Returns a boolean\n\nExample of usage:\n=====================================\nlet $PanicGoal = Java.loadClass(\"net.minecraft.world.entity.ai.goal.PanicGoal\")\ne.removeGoals(context => {\n    const { goal, entity } = context\n    return goal.getClass() == $PanicGoal\n})\n=====================================\n", params = { @Param(name = "goalFunction", value = "A function to remove goals with entity & available goals as arguments") })
    public void removeGoals(Function<ContextUtils.GoalContext, Boolean> goalFunction) {
        this.selector.removeAllGoals(g -> {
            ContextUtils.GoalContext context = new ContextUtils.GoalContext(this.getEntity(), g);
            Object remove = EntityJSHelperClass.convertObjectToDesired(goalFunction.apply(context), "boolean");
            if (remove != null) {
                return (Boolean) remove;
            } else {
                ConsoleJS.SERVER.error("[EntityJS]: Failed to remove goals from entity " + this.getEntity().m_7755_() + ": function must return a boolean.");
                return false;
            }
        });
    }

    @Info(value = "Remove all goals.\n\nExample of usage:\n=====================================\nbuilder.removeAllGoals()\n=====================================\n", params = { @Param(name = "goal", value = "The goal to remove") })
    public void removeAllGoals() {
        this.selector.removeAllGoals(goal -> true);
    }

    @Info(value = "Enables the addition of arbitrary goals to an entity\n\nIt is the responsibility of the user to ensure the goal is\ncompatible with the entity\n\nExample of usage:\n=====================================\nbuilder.arbitraryTargetGoal(3, entity -> new $DefendVillageTargetGoal(entity))\n=====================================\n\nNote in the example the entity must be an instance of IronGolem\n", params = { @Param(name = "priority", value = "The priority of the goal"), @Param(name = "goalSupplier", value = "The goal supplier, a function that takes a Mob and returns a Goal") })
    public void arbitraryTargetGoal(int priority, Function<T, Goal> goalSuppler) {
        this.selector.addGoal(priority, (Goal) goalSuppler.apply(this.mob));
    }

    @Info(value = "Adds a `NearestAttackableTargetGoal` to the entity", params = { @Param(name = "priority", value = "The priority of the goal"), @Param(name = "targetClass", value = "The entity class that should be targeted"), @Param(name = "randomInterval", value = "The interval at which the goal amy be 'refreshed'"), @Param(name = "mustSee", value = "If the mob must have line of sight at all times"), @Param(name = "mustReach", value = "If the mob must be able to reach the target to attack"), @Param(name = "targetConditions", value = "The conditions under which the targeted entity will be targeted, may be null") })
    public <E extends LivingEntity> void nearestAttackableTarget(int priority, Class<E> targetClass, int randomInterval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> targetConditions) {
        this.selector.addGoal(priority, new NearestAttackableTargetGoal(this.mob, targetClass, randomInterval, mustSee, mustReach, targetConditions));
    }

    @Info(value = "Adds s `HurtByTargetGoal` to the entity, only applicable to **pathfinder** mobs", params = { @Param(name = "priority", value = "The priority of the goal"), @Param(name = "toIgnoreDamage", value = "The classes that damage should be ignored from"), @Param(name = "alertOthers", value = "If other mobs should be alerted when this mob is damaged"), @Param(name = "toIgnoreAlert", value = "The entity classes that should not be alerted") })
    public void hurtByTarget(int priority, List<Class<?>> toIgnoreDamage, boolean alertOthers, List<Class<?>> toIgnoreAlert) {
        if (this.isPathFinder) {
            HurtByTargetGoal goal = new HurtByTargetGoal((PathfinderMob) this.mob, (Class<?>[]) toIgnoreDamage.toArray(new Class[0]));
            if (alertOthers) {
                goal.setAlertOthers((Class<?>[]) toIgnoreAlert.toArray(new Class[0]));
            }
            this.selector.addGoal(priority, goal);
        }
    }

    @Info(value = "Adds a `NonTameRandomTargetGoal` to the entity, only applicable to **tamable** mobs", params = { @Param(name = "priority", value = "The priority of the goal"), @Param(name = "targetClass", value = "The entity class that should be targeted"), @Param(name = "mustSee", value = "If the mob must have line of sight at all times"), @Param(name = "targetConditions", value = "The conditions under which the targeted entity will be targeted, may be null") })
    public <E extends LivingEntity> void nonTameRandomTarget(int priority, Class<E> targetClass, boolean mustSee, @Nullable Predicate<LivingEntity> targetCondition) {
        if (this.isTamable) {
            this.selector.addGoal(priority, new NonTameRandomTargetGoal((TamableAnimal) this.mob, targetClass, mustSee, targetCondition));
        }
    }

    @Info(value = "Adds a `OwnerHurtByTargetGoal` to the entity, only applicable to **tamable** mobs", params = { @Param(name = "priority", value = "The priority of the goal") })
    public void ownerHurtByTarget(int priority) {
        if (this.isTamable) {
            this.selector.addGoal(priority, new OwnerHurtByTargetGoal((TamableAnimal) this.mob));
        }
    }

    @Info(value = "Adds a `ResetUniversalAngerTargetGoal` to the entity, only applicable to **neutral** mobs", params = { @Param(name = "priority", value = "The priority of the goal"), @Param(name = "alertOthersOfSameType", value = "If other mobs of the same type should be alerted") })
    public <E extends Mob & NeutralMob> void resetUniversalAngerTarget(int priority, boolean alertOthersOfSameType) {
        if (this.isNeutral) {
            this.selector.addGoal(priority, new ResetUniversalAngerTargetGoal(this.mob, alertOthersOfSameType));
        }
    }
}