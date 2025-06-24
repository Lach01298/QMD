package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.QMDJEIHelper;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.NeutralContainmentRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;



public class NeutralContainmentCategory extends QMDProcessorCategory<NeutralContainmentRecipe>
{
	public NeutralContainmentCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "neutral_containment",":textures/gui/neutral_containment_controller.png",new ItemStack(QMDBlocks.exoticContainmentController), 24, 9, 126, 47);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, NeutralContainmentRecipe recipeWrapper, IIngredients ingredients)
	{
		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
		QMDJEIHelper.RecipeParticleMapper particleMapper = new QMDJEIHelper.RecipeParticleMapper();

		particleMapper.put(IngredientSorption.INPUT, 0, 0, 4, 20);
		particleMapper.put(IngredientSorption.INPUT, 1, 1, 108, 20);

		fluidMapper.put(IngredientSorption.OUTPUT, 0, 0, 47, 11, 32, 32);

		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		particleMapper.apply(recipeLayout.getIngredientsGroup(ParticleType.Particle), ingredients);
	}
}
