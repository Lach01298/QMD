package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.ArrayList;
import java.util.List;

public class AcceleratorCoolingRecipeMaker
{
	private AcceleratorCoolingRecipeMaker()
	{
	}

	public static List<AcceleratorCoolingRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.accelerator_cooling.getRecipeList();
		List<AcceleratorCoolingRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			AcceleratorCoolingRecipe jeiRecipe = new AcceleratorCoolingRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
