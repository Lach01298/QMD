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
	private int amount;
	private long meanEnergy;
	private double focus;			//Basically inverse area of the beam
	
	public ParticleStack()
	{
		this.particle =null;
		this.amount = 0;
		this.meanEnergy = 0;
		this.focus= 0;
		
	}
	
	
	public ParticleStack(Particle particle, int amount, long meanEnergy, double focus)
	{
		this.particle =particle;
		this.amount = amount;
		this.meanEnergy = meanEnergy;
		this.focus= focus;
		
	}
	
	public ParticleStack(Particle particle, int amount, long meanEnergy)
	{
		this.particle =particle;
		this.meanEnergy = meanEnergy;
		this.amount = amount;
		this.focus= 0;
		
	}
	
	public ParticleStack(Particle particle, int amount)
	{
		this.particle =particle;
		this.amount = amount;
		this.meanEnergy = 0;
		this.focus= 0;
		
	}
	public ParticleStack(Particle particle)
	{
		this.particle =particle;
		this.amount = 1;
		this.meanEnergy = 0;
		this.focus= 0;
		
	}

	
	public Particle getParticle()
	{
		return particle;
	}
	

	public long getMeanEnergy()
	{
		return meanEnergy;
	}
	

	public int getAmount()
	{
		return amount;
	}
	
	public double getFocus()
	{
		return focus;
	}
	
	
	public void setParticle(Particle newParticle)
	{
		this.particle =newParticle;
	}
	
	public void setMeanEnergy(long newMeanEnergy)
	{
		this.meanEnergy = newMeanEnergy;
	}
	
	public void addMeanEnergy(long add)
	{
		this.meanEnergy += add;
	}
	
	
	public void setAmount(int newAmount)
	{
		this.amount = newAmount;
	}
	
	
	public void addAmount(long add)
	{
		this.amount += add;
	}
	
	public void removeAmount(long remove)
	{
		this.amount -= remove;
		if(amount < 0)
		{
			amount = 0;
			particle = null;		
		}
	}
	
	public void setFocus(double newFocus)
	{
		this.focus = newFocus;
	}
	
	
	public void addFocus(double add)
	{
		this.focus += add;
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
		
		nbt.setInteger("amount", amount);
		nbt.setLong("meanEnergy", meanEnergy);
		nbt.setDouble("focus", focus);

		
		return nbt;
	}

	public ParticleStack readFromNBT(NBTTagCompound nbt)
	{

		this.particle = Particles.getParticleFromName(nbt.getString("particle"));
		this.amount = nbt.getInteger("amount");
		this.meanEnergy = nbt.getLong("meanEnergy");
		this.focus = nbt.getDouble("focus");
		
		return this;
	}

	
	public ParticleStack copy()
	{
		return new ParticleStack(particle,amount,meanEnergy,focus);
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
		long energy = nbt.getLong("meanEnergy");
		double focus = nbt.getDouble("focus");

		ParticleStack beam = new ParticleStack(Particles.getParticleFromName(particleName), amount, energy,focus);

		return beam;
	}
	
	
	public static ParticleStack getParticleStack(String particleName, int amount, long meanEnergy, double focus)
	{
		return new ParticleStack(Particles.getParticleFromName(particleName),amount, meanEnergy, focus);
	}

	public boolean matchesType(ParticleStack particleStack)
	{
		if(particleStack != null)
		{
			if(particleStack.getParticle() == particle)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isInRange(ParticleStack particleStack, long maxEnergy)
	{
		if(particleStack != null)
		{
			if(particleStack.getParticle() == particle)
			{
				if(particleStack.getFocus() >= focus)
				{
					if(particleStack.getMeanEnergy() >= meanEnergy && particleStack.getMeanEnergy() <= maxEnergy)
					{
						return true;
					}
				}
				
			}
		}
		return false;
	}
	
}
