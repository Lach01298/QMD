package lach_01298.qmd.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;

public class CTAddAntiParticle implements IAction
{

	Particle antiParticle;
	Particle particle;
	
	
	
	public CTAddAntiParticle(IIngredient particle, IIngredient antiParticle)
	{
		this.antiParticle = ((ParticleStack) antiParticle.getInternal()).getParticle();
		this.particle =((ParticleStack) particle.getInternal()).getParticle();
	}

	@Override
	public void apply()
	{
		particle.setAntiParticle(antiParticle);

	}

	@Override
	public String describe()
	{
		return String.format("Adding antiparticle %s to particle: %s", antiParticle.getName(), particle.getName());
	}

}
