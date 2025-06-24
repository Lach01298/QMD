package lach_01298.qmd.jei.category;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.QMDJEIHelper;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.DecayChamberRecipe;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.recipe.IngredientSorption;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;



public class DecayChamberCategory extends QMDProcessorCategory<DecayChamberRecipe>
{

	public DecayChamberCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "decay_chamber",":textures/gui/jei/decay_chamber.png",new ItemStack(QMDBlocks.decayChamberController), 0, 0, 150, 100);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, DecayChamberRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);

		QMDJEIHelper.RecipeParticleMapper particleMapper = new QMDJEIHelper.RecipeParticleMapper();

		particleMapper.put(IngredientSorption.INPUT, 0, 0, 50, 32);

		particleMapper.put(IngredientSorption.OUTPUT, 0, 1, 83, 9);
		particleMapper.put(IngredientSorption.OUTPUT, 1, 2, 83, 32);
		particleMapper.put(IngredientSorption.OUTPUT, 2, 3, 83, 55);

		particleMapper.apply(recipeLayout.getIngredientsGroup(ParticleType.Particle), ingredients);

	}
	

}
