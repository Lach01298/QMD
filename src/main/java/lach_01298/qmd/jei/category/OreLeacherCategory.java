package lach_01298.qmd.jei.category;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.*;
import nc.recipe.IngredientSorption;
import nclegacy.jei.IJEIHandlerLegacy;

public class OreLeacherCategory extends JEIQMDMachineCategory<QMDRecipeWrapper.OreLeacher>
{
	
	public OreLeacherCategory(IGuiHelper guiHelper, IJEIHandlerLegacy handler)
	{
		super(guiHelper, handler, "ore_leacher", 30, 7, 142, 56);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.OreLeacher recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 36 - backPosX, 11 - backPosY);
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 36 - backPosX, 42 - backPosY, 16, 16);
		fluidMapper.put(IngredientSorption.INPUT, 1, 1, 56 - backPosX, 42 - backPosY, 16, 16);
		fluidMapper.put(IngredientSorption.INPUT, 2, 2, 76 - backPosX, 42 - backPosY, 16, 16);
		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 112 - backPosX, 42 - backPosY);
		itemMapper.put(IngredientSorption.OUTPUT, 1, 2, 132 - backPosX, 42 - backPosY);
		itemMapper.put(IngredientSorption.OUTPUT, 2, 3, 152 - backPosX, 42 - backPosY);
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
	}
}
