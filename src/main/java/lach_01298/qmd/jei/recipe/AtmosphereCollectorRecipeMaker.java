package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipes.AtmosphereCollectorRecipes;
import mezz.jei.api.IJeiHelpers;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.Map.Entry;

public class AtmosphereCollectorRecipeMaker
{
	private AtmosphereCollectorRecipeMaker()
	{
	}

	public static List<AtmosphereCollectorRecipe> getRecipes(IJeiHelpers helpers)
	{
		Map<Integer, FluidStack> recipes = AtmosphereCollectorRecipes.recipes;
		List<AtmosphereCollectorRecipe> jeiRecipes = new ArrayList<>();

		for (Entry<Integer, FluidStack> recipe : recipes.entrySet())
		{
			List<FluidStack> outputFluid = new ArrayList<FluidStack>();
			outputFluid.add( recipe.getValue());
			
		
			AtmosphereCollectorRecipe jeiRecipe = new AtmosphereCollectorRecipe(recipe.getKey(),outputFluid);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
