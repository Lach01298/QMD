package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.ArrayList;
import java.util.List;

public class IrradiatorFuelRecipeMaker
{
	private IrradiatorFuelRecipeMaker()
	{
	}

	public static List<IrradiatorFuelRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.irradiator_fuel.getRecipeList();
		List<IrradiatorFuelRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			IrradiatorFuelRecipe jeiRecipe = new IrradiatorFuelRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
