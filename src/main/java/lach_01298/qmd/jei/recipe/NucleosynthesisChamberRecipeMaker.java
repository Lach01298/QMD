package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

public class NucleosynthesisChamberRecipeMaker
{
	private NucleosynthesisChamberRecipeMaker()
	{
	}

	public static List<NucleosynthesisChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.nucleosynthesis_chamber.getRecipeList();
		List<NucleosynthesisChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{

			NucleosynthesisChamberRecipe jeiRecipe = new NucleosynthesisChamberRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
