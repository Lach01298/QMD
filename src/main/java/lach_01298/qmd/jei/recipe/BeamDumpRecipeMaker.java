package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.*;

public class BeamDumpRecipeMaker
{
	private BeamDumpRecipeMaker()
	{
	}

	public static List<BeamDumpRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.beam_dump.getRecipeList();
		List<BeamDumpRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{

			BeamDumpRecipe jeiRecipe = new BeamDumpRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
