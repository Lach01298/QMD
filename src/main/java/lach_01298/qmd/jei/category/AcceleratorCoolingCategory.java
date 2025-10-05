package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.AcceleratorCoolingRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

public class AcceleratorCoolingCategory extends QMDProcessorCategory<AcceleratorCoolingRecipe>
{
	
	public AcceleratorCoolingCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "accelerator_cooling",":textures/gui/jei/accelerator_cooling.png",new ItemStack(QMDBlocks.linearAcceleratorController), 0, 0, 90, 26);

	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AcceleratorCoolingRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 9, 5, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 1, 65, 1, 24, 24);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		

	}
}
