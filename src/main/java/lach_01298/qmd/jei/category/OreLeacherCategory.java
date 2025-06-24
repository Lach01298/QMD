package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.OreLeacherRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.*;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

public class OreLeacherCategory extends QMDProcessorCategory<OreLeacherRecipe>
{

	public OreLeacherCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "ore_leacher",":textures/gui/ore_leacher.png", new ItemStack(QMDBlocks.oreLeacher), 30, 7, 142, 56);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, OreLeacherRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);

		RecipeItemMapper itemMapper = new RecipeItemMapper();
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();

		itemMapper.put(IngredientSorption.INPUT, 0, 0, 6, 4);
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 6, 35, 16, 16);
		fluidMapper.put(IngredientSorption.INPUT, 1, 1, 26, 35, 16, 16);
		fluidMapper.put(IngredientSorption.INPUT, 2, 2, 46, 35, 16, 16);

		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 82, 35);
		itemMapper.put(IngredientSorption.OUTPUT, 1, 2, 102, 35);
		itemMapper.put(IngredientSorption.OUTPUT, 2, 3, 122, 35);

		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
	}
}
