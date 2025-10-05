package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.LiquefierCoolantRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

public class LiquefierCoolantCategory extends BasicProcessorCategory<LiquefierCoolantRecipe>
{

	public LiquefierCoolantCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "liquefier_coolant",":textures/gui/jei/liquefier_coolant.png",new ItemStack(QMDBlocks.liquefierController), 0, 0, 90, 26);

	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, LiquefierCoolantRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 9, 5, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 1, 65, 1, 24, 24);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		

	}
}
