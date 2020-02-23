package lach_01298.qmd.jei.catergory;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.IJEIHandler;
import nc.integration.jei.JEICategoryProcessor;
import nc.integration.jei.JEIRecipeWrapper;
import nc.integration.jei.JEIMethods.RecipeFluidMapper;
import nc.integration.jei.JEIMethods.RecipeItemMapper;
import nc.recipe.IngredientSorption;

public class OreLeacherCategory extends JEICategoryQMDProcessor<QMDRecipeWrapper.OreLeacher> 
{
	
	public OreLeacherCategory(IGuiHelper guiHelper, IJEIHandler handler) 
	{
		super(guiHelper, handler, "ore_leacher", 30, 7, 142, 56);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.OreLeacher recipeWrapper, IIngredients ingredients) 
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.map(IngredientSorption.INPUT, 0, 0, 36 - backPosX, 11 - backPosY);
		fluidMapper.map(IngredientSorption.INPUT, 0, 0, 36 - backPosX, 42 - backPosY, 16, 16);
		fluidMapper.map(IngredientSorption.INPUT, 1, 1, 56 - backPosX, 42 - backPosY, 16, 16);
		fluidMapper.map(IngredientSorption.INPUT, 2, 2, 76 - backPosX, 42 - backPosY, 16, 16);
		itemMapper.map(IngredientSorption.OUTPUT, 0, 1, 112 - backPosX, 42 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 1, 2, 132 - backPosX, 42 - backPosY);
		itemMapper.map(IngredientSorption.OUTPUT, 2, 3, 152 - backPosX, 42 - backPosY);
		itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.mapFluidsTo(recipeLayout.getFluidStacks(), ingredients);
	}
}
