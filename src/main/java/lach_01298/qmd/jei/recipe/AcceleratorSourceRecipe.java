package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IGuiHelper;

public class AcceleratorSourceRecipe extends JEIRecipeWrapper
{

	public AcceleratorSourceRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.accelerator_source, recipe);
	}

}
