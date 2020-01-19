package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TargetChamberTargetRecipeMaker
{
	private TargetChamberTargetRecipeMaker()
	{
	}

	public static List<TargetChamberRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.target_chamber.getRecipeList();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<TargetChamberRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			
			ItemStack inputItem = recipe.itemIngredients().get(0).getStack();
			ParticleStack inputParticle = recipe.particleIngredients().get(0).getStack();
			ItemStack outputItem = recipe.itemProducts().get(0).getStack();
			ParticleStack outputParticlePlus = recipe.particleProducts().get(0).getStack();
			ParticleStack outputParticleNeutral = recipe.particleProducts().get(1).getStack();
			ParticleStack outputParticleMinus = recipe.particleProducts().get(2).getStack();
			
			TargetChamberRecipe jeiRecipe = new TargetChamberRecipe(inputItem, inputParticle,outputItem,outputParticlePlus,outputParticleNeutral,outputParticleMinus);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
