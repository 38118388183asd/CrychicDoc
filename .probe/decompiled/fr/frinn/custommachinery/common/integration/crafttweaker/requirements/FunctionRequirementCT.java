package fr.frinn.custommachinery.common.integration.crafttweaker.requirements;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import fr.frinn.custommachinery.api.crafting.CraftingResult;
import fr.frinn.custommachinery.api.integration.crafttweaker.RecipeCTBuilder;
import fr.frinn.custommachinery.common.integration.crafttweaker.function.CTFunction;
import fr.frinn.custommachinery.common.integration.crafttweaker.function.Context;
import fr.frinn.custommachinery.common.requirement.FunctionRequirement;
import java.util.function.Function;
import org.openzen.zencode.java.ZenCodeType.Method;
import org.openzen.zencode.java.ZenCodeType.Name;

@ZenRegister
@Name("mods.custommachinery.requirement.Function")
public interface FunctionRequirementCT<T> extends RecipeCTBuilder<T> {

    @Method
    default T requireFunctionToStart(Function<Context, CraftingResult> function) {
        return this.addRequirement(new FunctionRequirement(FunctionRequirement.Phase.CHECK, new CTFunction(function), CraftTweakerAPI.getLogger("custommachinery")::throwing));
    }

    @Method
    default T requireFunctionOnStart(Function<Context, CraftingResult> function) {
        return this.addRequirement(new FunctionRequirement(FunctionRequirement.Phase.START, new CTFunction(function), CraftTweakerAPI.getLogger("custommachinery")::throwing));
    }

    @Method
    default T requireFunctionEachTick(Function<Context, CraftingResult> function) {
        return this.addRequirement(new FunctionRequirement(FunctionRequirement.Phase.TICK, new CTFunction(function), CraftTweakerAPI.getLogger("custommachinery")::throwing));
    }

    @Method
    default T requireFunctionOnEnd(Function<Context, CraftingResult> function) {
        return this.addRequirement(new FunctionRequirement(FunctionRequirement.Phase.END, new CTFunction(function), CraftTweakerAPI.getLogger("custommachinery")::throwing));
    }
}