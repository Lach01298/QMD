package lach_01298.qmd.jei.category;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.IngredientSorption;

public class MassSpectrometerCategory extends JEIQMDMachineCategory<QMDRecipeWrapper.MassSpectrometer> 
{
	
	public MassSpectrometerCategory(IGuiHelper guiHelper, IJEIHandler handler) 
	{
		super(guiHelper, handler, "mass_spectrometer", 45, 13, 111, 93);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.MassSpectrometer recipeWrapper, IIngredients ingredients) 
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.map(IngredientSorption.INPUT, 0, 0, 46 - backPosX, 14 - backPosY);
		
		fluidMapper.map(IngredientSorption.INPUT, 0, 0, 46 - backPosX, 33 - backPosY, 16, 16);

		itemMapper.map(IngredientSorption.OUTPUT, 0, 1, 82 - backPosX, 14 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 1, 2, 101 - backPosX, 14 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 2, 3, 120 - backPosX, 14 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 3, 4, 139 - backPosX, 14 - backPosY);
		
		fluidMapper.map(IngredientSorption.OUTPUT, 0, 1, 82 - backPosX, 33 - backPosY, 16, 16);
		fluidMapper.map(IngredientSorption.OUTPUT, 1, 2, 101 - backPosX, 33 - backPosY, 16, 16);
		fluidMapper.map(IngredientSorption.OUTPUT, 2, 3, 120 - backPosX, 33 - backPosY, 16, 16);
		fluidMapper.map(IngredientSorption.OUTPUT, 3, 4, 139 - backPosX, 33 - backPosY, 16, 16);
		
		itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.mapFluidsTo(recipeLayout.getFluidStacks(), ingredients);
	}
}
