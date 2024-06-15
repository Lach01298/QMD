package lach_01298.qmd.jei.category;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.recipe.IngredientSorption;
import nclegacy.jei.IJEIHandlerLegacy;

public class IrradiatorFuelCategory extends JEIQMDMachineCategory<QMDRecipeWrapper.IrradiatorFuel>
{
	
	public IrradiatorFuelCategory(IGuiHelper guiHelper, IJEIHandlerLegacy handler)
	{
		super(guiHelper, handler, "irradiator_fuel", 62, 5, 52, 52);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.IrradiatorFuel recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 80 - backPosX, 21 - backPosY);
		
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		
	}
}
