package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IGuiHelper;
import net.minecraft.util.ResourceLocation;

public class CellFillingRecipe extends JEIRecipeWrapper
{
	public CellFillingRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.cell_filling, recipe);
	}
}
