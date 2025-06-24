package lach_01298.qmd.jei.category;

import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.jei.recipe.CellFillingRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.integration.jei.JEIHelper.RecipeItemMapper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

public class CellFillingCategory extends QMDProcessorCategory<CellFillingRecipe>
{
	
	public CellFillingCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "cell_filling",":textures/gui/jei/cell_filling.png", new ItemStack(QMDItems.cell), 0, 0, 64, 45);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CellFillingRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 3, 3);
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 3, 26, 16, 16);
		
		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 45, 3);
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 1, 45, 26, 16, 16);
		
		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
	}
}
