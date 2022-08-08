package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

public class TargetChamberRecipeMaker
{
	private TargetChamberRecipeMaker()
	{
	}

	public static List<TargetChamberRecipe> getRecipes(IJeiHelpers helpers)
	{	
		List<QMDRecipe> recipes = QMDRecipes.target_chamber.getRecipeList();
		List<TargetChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			TargetChamberRecipe jeiRecipe = new TargetChamberRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
