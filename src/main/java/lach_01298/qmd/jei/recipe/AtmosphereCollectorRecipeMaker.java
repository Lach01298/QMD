package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipes.AtmosphereCollectorRecipes;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidStack;

public class AtmosphereCollectorRecipeMaker
{
	private AtmosphereCollectorRecipeMaker()
	{
	}

	public static List<AtmosphereCollectorRecipe> getRecipes(IJeiHelpers helpers)
	{
		Map<Integer, FluidStack> recipes = AtmosphereCollectorRecipes.recipes;
		List<AtmosphereCollectorRecipe> jeiRecipes = new ArrayList<>();

		for (Entry<Integer, FluidStack> recipe : recipes.entrySet())
		{
			List<FluidStack> outputFluid = new ArrayList<FluidStack>();
			outputFluid.add( recipe.getValue());
			
		
			AtmosphereCollectorRecipe jeiRecipe = new AtmosphereCollectorRecipe(recipe.getKey(),outputFluid);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
