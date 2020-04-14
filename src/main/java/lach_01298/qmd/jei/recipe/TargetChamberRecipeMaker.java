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

public class TargetChamberRecipeMaker
{
	private TargetChamberRecipeMaker()
	{
	}

	public static List<TargetChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.target_chamber.getRecipeList();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<TargetChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			
			ItemStack inputItem = recipe.getItemIngredients().get(0).getStack();
			ParticleStack inputParticle = recipe.getParticleIngredients().get(0).getStack();
			ItemStack outputItem = recipe.getItemProducts().get(0).getStack();
			ParticleStack outputParticlePlus = recipe.getParticleProducts().get(0).getStack();
			ParticleStack outputParticleNeutral = recipe.getParticleProducts().get(1).getStack();
			ParticleStack outputParticleMinus = recipe.getParticleProducts().get(2).getStack();
			
			long maxEnergy = (long) recipe.getMaxEnergy();
			double crossSection = (double) recipe.getCrossSection();
			long energyReleased = (long) recipe.getEnergyRelased();
			
			TargetChamberRecipe jeiRecipe = new TargetChamberRecipe(inputItem, inputParticle, outputItem, outputParticlePlus, outputParticleNeutral, outputParticleMinus, maxEnergy, crossSection, energyReleased);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
