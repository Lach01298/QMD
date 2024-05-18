package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.*;

public class NeutralContainmentRecipeMaker
{
	private NeutralContainmentRecipeMaker()
	{
	}

	public static List<NeutralContainmentRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.neutral_containment.getRecipeList();
		List<NeutralContainmentRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			NeutralContainmentRecipe jeiRecipe = new NeutralContainmentRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
