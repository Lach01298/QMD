package lach_01298.qmd.crafttweaker;

import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import lach_01298.qmd.particle.ParticleStack;

public class CTAddComponentParticle implements IAction
{

	ParticleStack parentParticle;
	ParticleStack particle;
	
	public CTAddComponentParticle(IIngredient parentParticle, IIngredient particle)
	{
		this.parentParticle = ((ParticleStack) parentParticle.getInternal());
		this.particle =((ParticleStack) particle.getInternal());

	}

	@Override
	public void apply()
	{
		parentParticle.getParticle().addComponentParticle(particle.getParticle(), particle.getAmount());
	}

	@Override
	public String describe()
	{
		return String.format("Adding compent %s to particle: %s", particle.getParticle().getName(), parentParticle.getParticle().getName());
	}

}
