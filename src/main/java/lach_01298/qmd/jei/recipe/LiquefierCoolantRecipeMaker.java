package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import nc.recipe.BasicRecipe;

import java.util.ArrayList;
import java.util.List;

public class LiquefierCoolantRecipeMaker
{
	private LiquefierCoolantRecipeMaker()
	{
	}

	public static List<LiquefierCoolantRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<BasicRecipe> recipes = QMDRecipes.liquefier_coolant.getRecipeList();
		List<LiquefierCoolantRecipe> jeiRecipes = new ArrayList<>();

		for (BasicRecipe recipe : recipes)
		{
			LiquefierCoolantRecipe jeiRecipe = new LiquefierCoolantRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
