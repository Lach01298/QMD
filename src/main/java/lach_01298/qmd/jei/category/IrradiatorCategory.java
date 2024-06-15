package lach_01298.qmd.jei.category;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.recipe.IngredientSorption;
import nclegacy.jei.IJEIHandlerLegacy;

public class IrradiatorCategory extends JEIQMDMachineCategory<QMDRecipeWrapper.Irradiator>
{
	
	public IrradiatorCategory(IGuiHelper guiHelper, IJEIHandlerLegacy handler)
	{
		super(guiHelper, handler, "irradiator", 41, 38, 94, 41);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.Irradiator recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 44 - backPosX, 54 - backPosY);
		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 116 - backPosX, 54 - backPosY);
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		
	}
}
