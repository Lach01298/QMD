package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.*;

public class DecayChamberRecipeMaker
{


	public static List<DecayChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.decay_chamber.getRecipeList();
		List<DecayChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			DecayChamberRecipe jeiRecipe = new DecayChamberRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
