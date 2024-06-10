package lach_01298.qmd.jei.category;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.*;
import nc.recipe.IngredientSorption;
import nclegacy.jei.IJEIHandlerLegacy;

public class MassSpectrometerCategory extends JEIQMDMachineCategory<QMDRecipeWrapper.MassSpectrometer>
{
	
	public MassSpectrometerCategory(IGuiHelper guiHelper, IJEIHandlerLegacy handler)
	{
		super(guiHelper, handler, "mass_spectrometer", 45, 13, 111, 93);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.MassSpectrometer recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 46 - backPosX, 14 - backPosY);
		
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 46 - backPosX, 33 - backPosY, 16, 16);

		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 82 - backPosX, 14 - backPosY);
		itemMapper.put(IngredientSorption.OUTPUT, 1, 2, 101 - backPosX, 14 - backPosY);
		itemMapper.put(IngredientSorption.OUTPUT, 2, 3, 120 - backPosX, 14 - backPosY);
		itemMapper.put(IngredientSorption.OUTPUT, 3, 4, 139 - backPosX, 14 - backPosY);
		
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 1, 82 - backPosX, 33 - backPosY, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 1, 2, 101 - backPosX, 33 - backPosY, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 2, 3, 120 - backPosX, 33 - backPosY, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 3, 4, 139 - backPosX, 33 - backPosY, 16, 16);
		
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
	}
}
