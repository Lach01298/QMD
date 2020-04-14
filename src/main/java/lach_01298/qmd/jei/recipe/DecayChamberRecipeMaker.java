package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class DecayChamberRecipeMaker
{


	public static List<DecayChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.decay_chamber.getRecipeList();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<DecayChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			ParticleStack inputParticle = recipe.getParticleIngredients().get(0).getStack();
			
			ParticleStack outputParticlePlus = recipe.getParticleProducts().get(0).getStack();
			ParticleStack outputParticleNeutral = recipe.getParticleProducts().get(1).getStack();
			ParticleStack outputParticleMinus = recipe.getParticleProducts().get(2).getStack();
			
			long maxEnergy = recipe.getMaxEnergy();
			double crossSection = recipe.getCrossSection();
			long energyReleased = recipe.getEnergyRelased();
			DecayChamberRecipe jeiRecipe = new DecayChamberRecipe(inputParticle, outputParticlePlus, outputParticleNeutral, outputParticleMinus, maxEnergy, crossSection, energyReleased);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
