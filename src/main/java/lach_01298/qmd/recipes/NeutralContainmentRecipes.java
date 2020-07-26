package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;

public class NeutralContainmentRecipes extends QMDRecipeHandler
{
	public NeutralContainmentRecipes()
	{
		super("neutral_containment", 0, 0, 2, 0, 1, 0);
		
	}

	@Override
	public void addRecipes()
	{
		
		addRecipe(new ParticleStack(Particles.antiproton,600,0,2),new ParticleStack(Particles.positron,600,0,2),fluidStack("antihydrogen", 1), 1l);
		addRecipe(new ParticleStack(Particles.antideuteron,600,0,2),new ParticleStack(Particles.positron,600,0,2),fluidStack("antideuterium", 1), 1l);
		addRecipe(new ParticleStack(Particles.antitriton,600,0,2),new ParticleStack(Particles.positron,600,0,2),fluidStack("antitritium", 1), 1l);
		addRecipe(new ParticleStack(Particles.antihelion,600,0,2),new ParticleStack(Particles.positron,1200,0,2),fluidStack("antihelium3", 1), 1l);
		addRecipe(new ParticleStack(Particles.antialpha,600,0,2),new ParticleStack(Particles.positron,1200,0,2),fluidStack("antihelium", 1), 1l);
		
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : Long.MAX_VALUE);
		return fixed;
	}
	
	
	
}
