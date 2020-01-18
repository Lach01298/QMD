package lach_01298.qmd.jei.ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

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
			ParticleStack particleStack = new ParticleStack(particle, 0, 0,0,0);
			particleStacks.add(particleStack);
		}

		return particleStacks;
	}
}