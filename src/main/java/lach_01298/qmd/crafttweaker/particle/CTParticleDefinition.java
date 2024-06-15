package lach_01298.qmd.crafttweaker.particle;

import lach_01298.qmd.particle.Particle;
import nc.util.Lang;

public class CTParticleDefinition implements IParticleDefinition
{

    private final Particle particle;

    public CTParticleDefinition(Particle particle)
    {
        this.particle = particle;
    }



    @Override
    public String getName() {
        return particle.getName();
    }

    @Override
    public String getDisplayName()
    {
        return Lang.localize(particle.getUnlocalizedName());
    }


	@Override
	public double getMass()
	{
		return particle.getMass();
	}


	@Override
	public double getCharge()
	{
		return particle.getCharge();
	}


	@Override
	public double getSpin()
	{
		return particle.getSpin();
	}
}
