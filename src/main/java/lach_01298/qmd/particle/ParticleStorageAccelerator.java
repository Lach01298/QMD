package lach_01298.qmd.particle;

import net.minecraft.util.EnumFacing;

public class ParticleStorageAccelerator extends ParticleStorage
{
	public ParticleStorageAccelerator()
	{
		super(null,Integer.MAX_VALUE,Integer.MAX_VALUE);
	
	}
	
	public void setMaxEnergy(long maxEnergy)
	{
		this.maxEnergy = maxEnergy;
	}
	
	public void setMinEnergy(int minEnergy)
	{
		this.minEnergy = minEnergy;
	}
	
	
	@Override
	public boolean reciveParticle(EnumFacing side, ParticleStack stack)
	{
		if(stack != null)
		{
			if(stack.getMeanEnergy() >= minEnergy && stack.getMeanEnergy() <= maxEnergy)
			{
				this.particleStack = stack;
				return true;
			}
			return false;
		}
		this.particleStack = stack;
		return true;
	}
	
	
	
	
	
	
	
}
