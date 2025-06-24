package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.IrradiatorFuelRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

public class IrradiatorFuelCategory extends QMDProcessorCategory<IrradiatorFuelRecipe>
{
	
	public IrradiatorFuelCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "irradiator_fuel",":textures/gui/irradiator.png", new ItemStack(QMDBlocks.irradiator), 62, 5, 52, 52);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IrradiatorFuelRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 18, 16);
		
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		
	}
}
