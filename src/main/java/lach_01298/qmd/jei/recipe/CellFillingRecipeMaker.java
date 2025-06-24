package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.ArrayList;
import java.util.List;

public class CellFillingRecipeMaker
{
	private CellFillingRecipeMaker()
	{
	}

	public static List<CellFillingRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.cell_filling.getRecipeList();
		List<CellFillingRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			CellFillingRecipe jeiRecipe = new CellFillingRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
