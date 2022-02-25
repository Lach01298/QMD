package lach_01298.qmd.jei.category;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.IngredientSorption;

public class CellFillingCategory extends JEIQMDMachineCategory<QMDRecipeWrapper.CellFilling> 
{
	
	public CellFillingCategory(IGuiHelper guiHelper, IJEIHandler handler) 
	{
		super(guiHelper, handler, "cell_filling", 0, 0, 64, 45);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.CellFilling recipeWrapper, IIngredients ingredients) 
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.map(IngredientSorption.INPUT, 0, 0, 3 - backPosX, 3 - backPosY);
		fluidMapper.map(IngredientSorption.INPUT, 0, 0, 3 - backPosX, 26 - backPosY, 16, 16);
		
		itemMapper.map(IngredientSorption.OUTPUT, 0, 1, 45 - backPosX, 3 - backPosY);
		fluidMapper.map(IngredientSorption.OUTPUT, 0, 1, 45 - backPosX, 26 - backPosY, 16, 16);
		
		itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.mapFluidsTo(recipeLayout.getFluidStacks(), ingredients);
	}
}
