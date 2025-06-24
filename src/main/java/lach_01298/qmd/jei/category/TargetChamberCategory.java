package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.QMDJEIHelper;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.TargetChamberRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;



public class TargetChamberCategory extends QMDProcessorCategory<TargetChamberRecipe>
{
	public TargetChamberCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "target_chamber",":textures/gui/jei/target_chamber.png",new ItemStack(QMDBlocks.targetChamberController), 0,0,150,115);
	}
	


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TargetChamberRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);

		JEIHelper.RecipeItemMapper itemMapper = new JEIHelper.RecipeItemMapper();
		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
		QMDJEIHelper.RecipeParticleMapper particleMapper = new QMDJEIHelper.RecipeParticleMapper();
		
		itemMapper.put(IngredientSorption.INPUT, 0, 0, 43, 26);
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 43, 43, 16, 16);
		particleMapper.put(IngredientSorption.INPUT, 0, 0, 9, 35);

		itemMapper.put(IngredientSorption.OUTPUT, 0, 1, 84, 26);
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 1, 84, 43, 16, 16);
		particleMapper.put(IngredientSorption.OUTPUT, 0, 1, 77, 4);
		particleMapper.put(IngredientSorption.OUTPUT, 1, 2, 120, 35);
		particleMapper.put(IngredientSorption.OUTPUT, 2, 3, 77, 67);

		itemMapper.apply(recipeLayout.getItemStacks(), ingredients);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		particleMapper.apply(recipeLayout.getIngredientsGroup(ParticleType.Particle), ingredients);

	}
	

}
