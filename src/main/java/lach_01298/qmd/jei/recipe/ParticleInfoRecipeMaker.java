package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.particle.*;
import mezz.jei.api.IJeiHelpers;

import java.util.*;
import java.util.Map.Entry;

public class ParticleInfoRecipeMaker
{
	private ParticleInfoRecipeMaker()
	{
	}

	public static List<ParticleInfoRecipe> getRecipes(IJeiHelpers helpers)
	{
		Map<String, Particle> particles = Particles.list;

		List<ParticleInfoRecipe> jeiRecipes = new ArrayList<>();

		for (Particle particle : particles.values())
		{
			ParticleStack p = new ParticleStack(particle);

			List<ParticleStack> c = new ArrayList<ParticleStack>();
			if (particle.hasComponentParticles())
			{
				for (Entry<Particle, Integer> component : particle.getComponentParticles().entrySet())
				{
					c.add(new ParticleStack(component.getKey(), component.getValue()));
				}
			}
			ParticleInfoRecipe jeiRecipe = new ParticleInfoRecipe(p, c);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
