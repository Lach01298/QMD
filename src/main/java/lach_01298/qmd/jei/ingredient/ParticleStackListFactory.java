package lach_01298.qmd.jei.ingredient;

import lach_01298.qmd.particle.*;

import java.util.*;

public final class ParticleStackListFactory
{
	private ParticleStackListFactory()
	{

	}

	public static List<ParticleStack> create()
	{
		List<ParticleStack> particleStacks = new ArrayList<>();

		Map<String, Particle> registeredParticles = Particles.list;
		for (Particle particle : registeredParticles.values())
		{
			ParticleStack particleStack = new ParticleStack(particle, 0, 0, 0);
			particleStacks.add(particleStack);
		}

		return particleStacks;
	}
}
