package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.QMDJEIHelper;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.NucleosynthesisChamberRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;



public class NucleosynthesisChamberCategory extends QMDProcessorCategory<NucleosynthesisChamberRecipe>
{
	public NucleosynthesisChamberCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "nucleosynthesis_chamber",":textures/gui/nucleosynthesis_chamber_controller.png",new ItemStack(QMDBlocks.nucleosynthesisChamberController), 29, 15, 124, 66);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, NucleosynthesisChamberRecipe recipeWrapper, IIngredients ingredients)
	{

		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
		QMDJEIHelper.RecipeParticleMapper particleMapper = new QMDJEIHelper.RecipeParticleMapper();

		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 25, 3, 16, 16);
		fluidMapper.put(IngredientSorption.INPUT, 1, 1, 25, 27, 16, 16);
		particleMapper.put(IngredientSorption.INPUT, 0, 0, 4, 16);

		fluidMapper.put(IngredientSorption.OUTPUT, 0, 2, 105, 3, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 1, 3, 105, 27, 16, 16);

		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		particleMapper.apply(recipeLayout.getIngredientsGroup(ParticleType.Particle), ingredients);
	}
	

}
