package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public class AcceleratorSourceRecipeMaker
{
	private AcceleratorSourceRecipeMaker()
	{
	}

	public static List<AcceleratorSourceRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.accelerator_source.getRecipeList();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<AcceleratorSourceRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			List<List<ItemStack>> input = QMDRecipeHelper.getItemInputLists(recipe.getItemIngredients());
			List<List<ParticleStack>> output = QMDRecipeHelper.getParticleOutputLists(recipe.getParticleProducts());
			AcceleratorSourceRecipe jeiRecipe = new AcceleratorSourceRecipe(input, output);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
