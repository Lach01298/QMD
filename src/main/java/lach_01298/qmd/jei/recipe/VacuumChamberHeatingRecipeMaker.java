package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;

import java.util.ArrayList;
import java.util.List;

public class VacuumChamberHeatingRecipeMaker
{
	private VacuumChamberHeatingRecipeMaker()
	{
	}

	public static List<VacuumChamberHeatingRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.vacuum_chamber_heating.getRecipeList();
		List<VacuumChamberHeatingRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			VacuumChamberHeatingRecipe jeiRecipe = new VacuumChamberHeatingRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
