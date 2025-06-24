package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.QMDJEIHelper;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.AcceleratorSourceRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;



public class AcceleratorSourceCategory extends QMDProcessorCategory<AcceleratorSourceRecipe>
{

	public AcceleratorSourceCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "accelerator_source",":textures/gui/jei/ion_source.png",new ItemStack(QMDBlocks.acceleratorSource), 7, 3, 72, 37);

		
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AcceleratorSourceRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);

		JEIHelper.RecipeItemMapper itemMapper = new JEIHelper.RecipeItemMapper();
		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
		QMDJEIHelper.RecipeParticleMapper particleMapper = new QMDJEIHelper.RecipeParticleMapper();


		itemMapper.put(IngredientSorption.INPUT, 0, 0, 2, 2);
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 2, 19, 16, 16);

		particleMapper.put(IngredientSorption.OUTPUT, 0, 0, 55, 12);

		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		particleMapper.apply(recipeLayout.getIngredientsGroup(ParticleType.Particle), ingredients);
	}
	

}
