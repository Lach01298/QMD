package lach_01298.qmd.recipes;

import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.BasicRecipeHandler;

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
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Long.class, Long.MAX_VALUE); 		// max energy

		return fixer.fixed;
	}
	
	
	
}
