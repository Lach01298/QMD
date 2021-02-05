package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;

public class DecayChamberRecipeMaker
{


	public static List<DecayChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.decay_chamber.getRecipeList();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<DecayChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			List<List<ParticleStack>> inputParticles =  QMDRecipeHelper.getParticleInputLists(recipe.getParticleIngredients());
			
			List<List<ParticleStack>> outputParticles = QMDRecipeHelper.getParticleOutputLists(recipe.getParticleProducts());
			
			
			long maxEnergy = recipe.getMaxEnergy();
			double crossSection = recipe.getCrossSection();
			long energyReleased = recipe.getEnergyRelased();
			DecayChamberRecipe jeiRecipe = new DecayChamberRecipe(inputParticles, outputParticles, maxEnergy, crossSection, energyReleased);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
