package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.MaterialTypes.CanisterType;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;

public class NeutralContainmentRecipes extends QMDRecipeHandler
{
	public NeutralContainmentRecipes()
	{
		super("neutral_containment", 0, 0, 2, 0, 1, 0);
		
	}

	@Override
	public void addRecipes()
	{
		addRecipe(new ParticleStack(Particles.antiproton,1000,0,2),new ParticleStack(Particles.positron,1000,0,2),fluidStack("antihydrogen", 1), 1l);
		addRecipe(new ParticleStack(Particles.antideuteron,1000,0,2),new ParticleStack(Particles.positron,1000,0,2),fluidStack("antideuterium", 1), 1l);
		addRecipe(new ParticleStack(Particles.antitriton,1000,0,2),new ParticleStack(Particles.positron,1000,0,2),fluidStack("antitritium", 1), 1l);
		addRecipe(new ParticleStack(Particles.antihelion,1000,0,2),new ParticleStack(Particles.positron,2000,0,2),fluidStack("antihelium3", 1), 1l);
		addRecipe(new ParticleStack(Particles.antialpha,1000,0,2),new ParticleStack(Particles.positron,2000,0,2),fluidStack("antihelium", 1), 1l);
		addRecipe(new ParticleStack(Particles.electron,1000,0,2),new ParticleStack(Particles.positron,1000,0,2),fluidStack("positronium", 1), 1l);
		addRecipe(new ParticleStack(Particles.muon,1000,0,2),new ParticleStack(Particles.antimuon,1000,0,2),fluidStack("muonium", 1), 1l);
		addRecipe(new ParticleStack(Particles.tau,1000,0,2),new ParticleStack(Particles.antitau,1000,0,2),fluidStack("tauonium", 1), 1l);
		addRecipe(new ParticleStack(Particles.glueball,1000,0,1), new EmptyParticleIngredient(),fluidStack("glueballs", 1));
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : Long.MAX_VALUE);
		return fixed;
	}
	
	
	
}
