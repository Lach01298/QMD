package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

public class CollisionChamberRecipeMaker
{


	public static List<CollisionChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.collision_chamber.getRecipeList();
		List<CollisionChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			CollisionChamberRecipe jeiRecipe = new CollisionChamberRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
