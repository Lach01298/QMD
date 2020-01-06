package lach_01298.qmd.particle;

import net.minecraft.util.EnumFacing;

public class AcceleratorStorage extends ParticleStorage
{
	private double inverseArea;
	private int minExtractionLuminosity;
	public AcceleratorStorage()
	{
		super(null,Integer.MAX_VALUE,Integer.MAX_VALUE);
		this.inverseArea = 1;
		this.minExtractionLuminosity = 0;
	}
	
	public int getLuminosity()
	{
		if(this.particleStack != null) 
		{
			return (int) (this.inverseArea*this.particleStack.getAmount());
		}
		return 0;
	}
	
	
	public void setInverseArea(double inverseArea)
	{
		this.inverseArea = inverseArea;
	}
	
	public double getInverseArea()
	{
		return this.inverseArea;
	}
	
	public void setMaxEnergy(int maxEnergy)
	{
		this.maxEnergy = maxEnergy;
	}
	
	public void setMinEnergy(int minEnergy)
	{
		this.minEnergy = minEnergy;
	}
	
	public void setMinExtractionLuminosity(int min)
	{
		this.minExtractionLuminosity = min;
	}
	
	public int getMinExtractionLuminosity()
	{
		return this.minExtractionLuminosity;
	}
	
	
	
	
	@Override
	public boolean reciveParticle(EnumFacing side, ParticleStack stack)
	{

		this.particleStack = stack;
		return true;
	}
	
	public ParticleStack extractParticle(EnumFacing side)
	{
		if(getLuminosity() >= minExtractionLuminosity)
		{
			return this.particleStack.copy();
		}
		return null;
	}
	
	
	
	
	
	
}
