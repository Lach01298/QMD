package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.ingredient.WorldIngredient;
import lach_01298.qmd.recipes.LiquidCollectorRecipes;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class LiquidCollectorRecipeMaker
{
	private LiquidCollectorRecipeMaker()
	{
	}

	public static List<LiquidCollectorRecipe> getRecipes(IJeiHelpers helpers)
	{
		Map<WorldIngredient, FluidStack> recipes = LiquidCollectorRecipes.recipes;
		List<LiquidCollectorRecipe> jeiRecipes = new ArrayList<>();

		for (Entry<WorldIngredient, FluidStack> recipe : recipes.entrySet())
		{
			List<FluidStack> outputFluid = new ArrayList<FluidStack>();
			outputFluid.add(recipe.getValue());

			LiquidCollectorRecipe jeiRecipe = new LiquidCollectorRecipe(recipe.getKey(),outputFluid);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
