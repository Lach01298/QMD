package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.MassSpectrometerRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

public class MassSpectrometerCategory extends QMDProcessorCategory<MassSpectrometerRecipe>
{
	
	public MassSpectrometerCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "mass_spectrometer",":textures/gui/mass_spectrometer_controller.png", new ItemStack(QMDBlocks.massSpectrometerController), 45, 13, 111, 93);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MassSpectrometerRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 1, 1);
		
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 1, 20, 16, 16);

		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 37, 1);
		itemMapper.put(IngredientSorption.OUTPUT, 1, 2, 56, 1);
		itemMapper.put(IngredientSorption.OUTPUT, 2, 3, 75, 1);
		itemMapper.put(IngredientSorption.OUTPUT, 3, 4, 94, 1);
		
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 1, 37, 20, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 1, 2, 56, 20, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 2, 3, 75, 20, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 3, 4, 94, 20, 16, 16);
		
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
	}
}
