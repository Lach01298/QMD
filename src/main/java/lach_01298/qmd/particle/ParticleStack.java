package lach_01298.qmd.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import nc.tile.internal.fluid.Tank.TankInfo;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class ParticleStack
{

	private Particle particle;
	private int meanEnergy, amount;
	private double energySpread;
	private int luminosity;			//beam stat
	
	public ParticleStack()
	{
		this.particle =null;
		this.meanEnergy = 0;
		this.amount = 0;
		this.energySpread = 0;
		this.luminosity= 0;
		
	}
	
	public ParticleStack(Particle particle, int meanEnergy, int amount, double energySpread, int luminosity)
	{
		this.particle =particle;
		this.meanEnergy = meanEnergy;
		this.amount = amount;
		this.energySpread = energySpread;
		this.luminosity= luminosity;
		
	}
	
	
	public ParticleStack(Particle particle, int meanEnergy, int amount)
	{
		this.particle =particle;
		this.meanEnergy = meanEnergy;
		this.amount = amount;
		this.energySpread = 0;
		this.luminosity= 0;
		
	}

	
	public Particle getParticle()
	{
		return particle;
	}
	

	public int getMeanEnergy()
	{
		return meanEnergy;
	}
	

	public double getEnergySpread()
	{
		return energySpread;
	}
	

	public int getAmount()
	{
		return amount;
	}
	
	public int getLuminosity()
	{
		return luminosity;
	}
	
	
	public void setParticle(Particle newParticle)
	{
		this.particle =newParticle;
	}
	
	public void setMeanEnergy(int newMeanEnergy)
	{
		this.meanEnergy = newMeanEnergy;
	}
	
	public void setEnergySpread(double newEnergySpread)
	{
		this.energySpread = newEnergySpread;
	}
	
	public void setAmount(int newAmount)
	{
		this.amount = newAmount;
	}
	
	
	public void addAmount(int add)
	{
		this.amount += add;
	}
	
	public void removeAmount(int remove)
	{
		this.amount -= remove;
		if(amount < 0)
		{
			amount = 0;
			particle = null;		
		}
	}
	
	public void setLuminosity(int newLuminosity)
	{
		this.luminosity = newLuminosity;
	}
	
	
	public void addLuminosity(int add)
	{
		this.luminosity += add;
	}


	

	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		if(particle == null)
		{
			nbt.setString("particle", "none");
		}
		else
		{
			nbt.setString("particle", particle.getName());
		}
		
		nbt.setInteger("meanEnergy", meanEnergy);
		nbt.setInteger("amount", amount);
		nbt.setDouble("energySpread", energySpread);
		nbt.setInteger("luminosity", luminosity);

		
		return nbt;
	}

	public ParticleStack readFromNBT(NBTTagCompound nbt)
	{

		this.particle = Particles.getParticleFromName(nbt.getString("particle"));
		this.meanEnergy = nbt.getInteger("meanEnergy");
		this.amount = nbt.getInteger("amount");
		this.energySpread = nbt.getDouble("energySpread");
		this.luminosity = nbt.getInteger("luminosity");
		
		return this;
	}

	
	public ParticleStack copy()
	{
		return new ParticleStack(particle, meanEnergy,amount,energySpread,luminosity);
	}

	
	public static ParticleStack loadParticleStackFromNBT(NBTTagCompound nbt)
	{
		if (nbt == null)
		{
			return new ParticleStack();
		}
		if (!nbt.hasKey("particle", Constants.NBT.TAG_STRING))
		{
			return new ParticleStack();
		}

		String particleName = nbt.getString("particle");
		int amount = nbt.getInteger("amount");
		int energy = nbt.getInteger("meanEnergy");
		double spread = nbt.getDouble("energySpread");
		int lum = nbt.getInteger("luminosity");

		ParticleStack beam = new ParticleStack(Particles.getParticleFromName(particleName), energy, amount, spread,lum);

		return beam;
	}
	
	
	public static ParticleStack getParticleStack(String particleName, int meanEnergy, int amount,double spread, int luminosity)
	{
		return new ParticleStack(Particles.getParticleFromName(particleName),meanEnergy,amount,spread, luminosity);
	}

	public boolean isInRange(ParticleStack particleStack)
	{
		if(particleStack != null)
		{
			if(particleStack.getParticle() == particle)
			{
				if(particleStack.getLuminosity() >= luminosity)
				{
					if(particleStack.getMeanEnergy() >= meanEnergy && particleStack.getMeanEnergy() <= meanEnergy *(1+ energySpread))
					{
						return true;
					}
				}
				
			}
		}
		return false;
	}



	
	
	
	
	
	
}
