package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.QMDJEIHelper;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.BeamDumpRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;



public class BeamDumpCategory extends QMDProcessorCategory<BeamDumpRecipe>
{

	public BeamDumpCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "beam_dump",":textures/gui/beam_dump_controller.png",new ItemStack(QMDBlocks.beamDumpController), 5, 36, 115, 30);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, BeamDumpRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);

		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
		QMDJEIHelper.RecipeParticleMapper particleMapper = new QMDJEIHelper.RecipeParticleMapper();

		particleMapper.put(IngredientSorption.INPUT, 0, 0, 33, 2);
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 0, 76, 1, 16, 16);

		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		particleMapper.apply(recipeLayout.getIngredientsGroup(ParticleType.Particle), ingredients);
	}
	

}
