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
import net.minecraftforge.fluids.FluidStack;

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
			List<List<ItemStack>> inputItem = QMDRecipeHelper.getItemInputLists(recipe.getItemIngredients());
			List<List<FluidStack>> inputFluid = QMDRecipeHelper.getFluidInputLists(recipe.getFluidIngredients());
			List<List<ParticleStack>> output = QMDRecipeHelper.getParticleOutputLists(recipe.getParticleProducts());
			AcceleratorSourceRecipe jeiRecipe = new AcceleratorSourceRecipe(inputItem,inputFluid, output);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
