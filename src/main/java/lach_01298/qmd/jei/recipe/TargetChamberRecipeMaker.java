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
import lach_01298.qmd.recipe.QMDRecipeHelper;

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
			
			List<List<ItemStack>> inputItems = QMDRecipeHelper.getItemInputLists(recipe.getItemIngredients());
			List<List<ParticleStack>> inputParticles = QMDRecipeHelper.getParticleInputLists(recipe.getParticleIngredients());
			List<List<ItemStack>> outputItems = QMDRecipeHelper.getItemOutputLists(recipe.getItemProducts());
			List<List<ParticleStack>> outputParticles = QMDRecipeHelper.getParticleOutputLists(recipe.getParticleProducts());
			
			
			long maxEnergy = (long) recipe.getMaxEnergy();
			double crossSection = (double) recipe.getCrossSection();
			long energyReleased = (long) recipe.getEnergyRelased();
			
			TargetChamberRecipe jeiRecipe = new TargetChamberRecipe(inputItems, inputParticles, outputItems, outputParticles, maxEnergy, crossSection, energyReleased);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
