package lach_01298.qmd.particle;

import lach_01298.qmd.config.QMDConfig;
import net.minecraft.util.EnumFacing;

public class ParticleStorageBeamline extends ParticleStorage
{

	private int length;
	public ParticleStorageBeamline(int length)
	{
		super(null,Integer.MAX_VALUE,Integer.MAX_VALUE);
		this.length = length;
	}
	
	public void setLength(int length)
	{
		this.length = length;	
	}
	
	public int getLength()
	{
		return length;	
	}
	
	public ParticleStack extractParticle(EnumFacing side)
	{
		if (canExtractParticle(side) && particleStack.getFocus() >length*QMDConfig.beamAttenuationRate)
		{	
			ParticleStack stack = this.particleStack;
			this.particleStack = null;
			stack.addFocus(-length*QMDConfig.beamAttenuationRate);
			return stack;
		}
		return null;
	}
	
	@Override
	public boolean reciveParticle(EnumFacing side, ParticleStack stack)
	{
		if(stack != null)
		{
			particleStack = stack.copy();
			return true;
		}
		return false;
	}







}
