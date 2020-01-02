package lach_01298.qmd.jei;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleBeam;
import lach_01298.qmd.particle.Particles;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public final class ParticleBeamListFactory {
	private ParticleBeamListFactory() {

	}

	public static List<ParticleBeam> create() {
		List<ParticleBeam> particleBeams = new ArrayList<>();

		Map<String, Particle> registeredParticles = Particles.list;
		for (Particle particle : registeredParticles.values()) 
		{
				ParticleBeam fluidStack = new ParticleBeam(particle, 0, 0, 0);
				particleBeams.add(fluidStack);
		}

		return particleBeams;
	}
}