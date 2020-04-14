package lach_01298.qmd.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import net.minecraft.util.ResourceLocation;

public class CTAddParticle implements IAction
{

	Particle particle;
	String name;
	
	public CTAddParticle(String name, String textureLocation, double mass, double charge, double spin,
			boolean weakCharged, boolean coloured)
	{	
		this.name = name;

		particle = new Particle(name, new ResourceLocation("contenttweaker","textures/particles/"+textureLocation), mass, charge, spin, weakCharged, coloured);
	}

	@Override
	public void apply()
	{
		Particles.registerParticle(particle);
	}

	@Override
	public String describe() 
	{
		return String.format("Adding particle: %s", name);
	}

	
}
