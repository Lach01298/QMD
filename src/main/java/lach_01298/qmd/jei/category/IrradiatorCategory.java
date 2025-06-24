package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.IrradiatorRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

public class IrradiatorCategory extends QMDProcessorCategory<IrradiatorRecipe>
{
	
	public IrradiatorCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "irradiator",":textures/gui/irradiator.png", new ItemStack(QMDBlocks.irradiator), 41, 38, 94, 41);

	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IrradiatorRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 3, 16);
		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 75, 16);
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		
	}
}
