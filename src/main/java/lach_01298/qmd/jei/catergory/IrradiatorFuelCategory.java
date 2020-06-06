package lach_01298.qmd.jei.catergory;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.IJEIHandler;
import nc.integration.jei.JEIMethods.RecipeItemMapper;
import nc.recipe.IngredientSorption;

public class IrradiatorFuelCategory extends JEICategoryQMDProcessor<QMDRecipeWrapper.IrradiatorFuel> 
{
	
	public IrradiatorFuelCategory(IGuiHelper guiHelper, IJEIHandler handler) 
	{
		super(guiHelper, handler, "irradiator_fuel", 62, 5, 52, 52);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.IrradiatorFuel recipeWrapper, IIngredients ingredients) 
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		itemMapper.map(IngredientSorption.INPUT, 0, 0, 80 - backPosX, 21 - backPosY);
		
		itemMapper.mapItemsTo(recipeLayout.getItemStacks(), ingredients);
		
	}
}
