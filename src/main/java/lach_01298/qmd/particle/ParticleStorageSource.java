package lach_01298.qmd.particle;

import net.minecraft.util.EnumFacing;

public class ParticleStorageSource extends ParticleStorage
{
	public ParticleStorageSource()
	{
		super(null,Integer.MAX_VALUE,Integer.MAX_VALUE);
	
	}

	@Override
	public boolean reciveParticle(EnumFacing side, ParticleStack stack)
	{	
		return false;
	}
	
	@Override
	public boolean canReciveParticle(EnumFacing side, ParticleStack stack)
	{
		return false;
	}
	
	
	@Override
	public ParticleStack extractParticle(EnumFacing side)
	{
		if (canExtractParticle(side))
		{
			ParticleStack stack = this.particleStack;
			return stack;
		}
		return null;
	}

}
