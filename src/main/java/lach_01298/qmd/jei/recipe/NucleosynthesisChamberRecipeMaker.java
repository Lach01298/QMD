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

public class NucleosynthesisChamberRecipeMaker
{
	private NucleosynthesisChamberRecipeMaker()
	{
	}

	public static List<NucleosynthesisChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.nucleosynthesis_chamber.getRecipeList();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<NucleosynthesisChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			
			
			 List<List<ParticleStack>> inputParticle = QMDRecipeHelper.getParticleInputLists(recipe.getParticleIngredients());
			 List<List<FluidStack>> inputFluid = QMDRecipeHelper.getFluidInputLists(recipe.getFluidIngredients());
			 List<List<FluidStack>> outputFluid = QMDRecipeHelper.getFluidOutputLists(recipe.getFluidProducts());
			
			 long maxEnergy = (long) recipe.getExtras().get(0);
			 long heat = (long) recipe.getExtras().get(1);
			
			
			NucleosynthesisChamberRecipe jeiRecipe = new NucleosynthesisChamberRecipe(inputParticle, inputFluid, outputFluid, heat, maxEnergy);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
