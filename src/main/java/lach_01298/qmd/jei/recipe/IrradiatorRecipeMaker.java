package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.ArrayList;
import java.util.List;

public class IrradiatorRecipeMaker
{
	private IrradiatorRecipeMaker()
	{
	}

	public static List<IrradiatorRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.irradiator.getRecipeList();
		List<IrradiatorRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			IrradiatorRecipe jeiRecipe = new IrradiatorRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
