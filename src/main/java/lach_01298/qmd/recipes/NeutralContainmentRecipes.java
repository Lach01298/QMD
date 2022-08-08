package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;

public class NeutralContainmentRecipes extends QMDRecipeHandler
{
	
	public NeutralContainmentRecipes()
	{
		super("neutral_containment", 0, 0, 2, 0, 1, 0);
		
	}

	@Override
	public void addRecipes()
	{
		//particles per milibucket
		int ppmB = QMDConfig.mole_amount/1000;
		addRecipe(new ParticleStack(Particles.antiproton,ppmB,0,2),new ParticleStack(Particles.positron,ppmB,0,2),fluidStack("antihydrogen", 1), 1l);
		addRecipe(new ParticleStack(Particles.antideuteron,ppmB,0,2),new ParticleStack(Particles.positron,ppmB,0,2),fluidStack("antideuterium", 1), 1l);
		addRecipe(new ParticleStack(Particles.antitriton,ppmB,0,2),new ParticleStack(Particles.positron,ppmB,0,2),fluidStack("antitritium", 1), 1l);
		addRecipe(new ParticleStack(Particles.antihelion,ppmB,0,2),new ParticleStack(Particles.positron,2*ppmB,0,2),fluidStack("antihelium3", 1), 1l);
		addRecipe(new ParticleStack(Particles.antialpha,ppmB,0,2),new ParticleStack(Particles.positron,2*ppmB,0,2),fluidStack("antihelium", 1), 1l);
		addRecipe(new ParticleStack(Particles.electron,ppmB,0,2),new ParticleStack(Particles.positron,ppmB,0,2),fluidStack("positronium", 1), 1l);
		addRecipe(new ParticleStack(Particles.muon,ppmB,0,2),new ParticleStack(Particles.antimuon,ppmB,0,2),fluidStack("muonium", 1), 1l);
		addRecipe(new ParticleStack(Particles.tau,ppmB,0,2),new ParticleStack(Particles.antitau,ppmB,0,2),fluidStack("tauonium", 1), 1l);
		addRecipe(new ParticleStack(Particles.glueball,ppmB,0,1), new EmptyParticleIngredient(),fluidStack("glueballs", 1));
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : Long.MAX_VALUE);
		return fixed;
	}
	
	
	
}
