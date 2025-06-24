package lach_01298.qmd.recipes;

import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.recipe.QMDRecipeHandler;

import java.util.*;

public class BeamDumpRecipes extends QMDRecipeHandler
{
	public BeamDumpRecipes()
	{
		super("beam_dump", 0, 0, 1, 0, 1, 0);
		
	}

	@Override
	public void addRecipes()
	{
		//addRecipe(inputParticle(amount,minEnergy,minFocus), outputFluid(amount), maxEnergy)
		
		//particles per milibucket
		int ppmB = QMDConstants.moleAmount/QMDConstants.bucketAmount;
		
		addRecipe(new ParticleStack(Particles.proton,2*ppmB), fluidStack("hydrogen", 1));
		addRecipe(new ParticleStack(Particles.deuteron,2*ppmB), fluidStack("deuterium", 1));
		addRecipe(new ParticleStack(Particles.triton,2*ppmB), fluidStack("tritium", 1));
		addRecipe(new ParticleStack(Particles.helion,ppmB), fluidStack("helium_3", 1));
		addRecipe(new ParticleStack(Particles.alpha,ppmB), fluidStack("helium", 1));
	}


	@Override
	public List fixedExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : Long.MAX_VALUE);
	
		return fixed;
	}
	
	
	
}
