package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipe.ingredient.ParticleIngredient;

public class BeamDumpRecipes extends QMDRecipeHandler
{
	public BeamDumpRecipes()
	{
		super("decay_chamber", 0, 0, 1, 0, 1, 0);
		
	}

	@Override
	public void addRecipes()
	{
		//addRecipe(inputParticle(amount,minEnergy,minFocus), outputFluid(amount), maxEnergy)
		
		addRecipe(new ParticleStack(Particles.proton,600), fluidStack("hydrogen", 1));
		addRecipe(new ParticleStack(Particles.deuteron,600), fluidStack("deuterium", 1));
		addRecipe(new ParticleStack(Particles.alpha,600), fluidStack("helium", 1));
		addRecipe(new ParticleStack(Particles.triton,600), fluidStack("tritium", 1));
		addRecipe(new ParticleStack(Particles.helion,600), fluidStack("helium3", 1));
	}


	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : Long.MAX_VALUE);
	
		return fixed;
	}
	
	
	
}
