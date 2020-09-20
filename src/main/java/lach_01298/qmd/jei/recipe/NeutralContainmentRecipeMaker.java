package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraftforge.fluids.FluidStack;

public class NeutralContainmentRecipeMaker
{
	private NeutralContainmentRecipeMaker()
	{
	}

	public static List<NeutralContainmentRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.neutral_containment.getRecipeList();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<NeutralContainmentRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			
			
			List<List<ParticleStack>> inputParticles = QMDRecipeHelper.getParticleInputLists(recipe.getParticleIngredients());
			ParticleStack inputParticle2 = recipe.getParticleIngredients().get(1).getStack();
			
			List<List<FluidStack>> outputFluid = QMDRecipeHelper.getFluidOutputLists(recipe.getFluidProducts());
			
			long maxEnergy = (long) recipe.getMaxEnergy();
			
			NeutralContainmentRecipe jeiRecipe = new NeutralContainmentRecipe(inputParticles, outputFluid, maxEnergy);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
