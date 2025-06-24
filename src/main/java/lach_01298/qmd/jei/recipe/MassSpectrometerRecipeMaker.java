package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.ArrayList;
import java.util.List;

public class MassSpectrometerRecipeMaker
{
	private MassSpectrometerRecipeMaker()
	{
	}

	public static List<MassSpectrometerRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.mass_spectrometer.getRecipeList();
		List<MassSpectrometerRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			MassSpectrometerRecipe jeiRecipe = new MassSpectrometerRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
