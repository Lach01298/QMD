package lach_01298.qmd.particle;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

/**
 * The base particle storage class
 * 
 *
 */
public class ParticleStorage implements IParticleStorage, IParticleStackHandler
{

	protected ParticleStack particleStack;
	protected TileEntity tile;
	protected long maxEnergy;
	protected long minEnergy;
	protected int capacity;

	
	public ParticleStorage(ParticleStack stack, long maxEnergy, int capacity, long minEnergy)
	{
		this.particleStack = stack;
		this.maxEnergy = maxEnergy;
		this.capacity = capacity;
		this.minEnergy = minEnergy;
	}
	
	public ParticleStorage(ParticleStack stack, long maxEnergy, int capacity)
	{
		this.particleStack = stack;
		this.maxEnergy = maxEnergy;
		this.capacity = capacity;
		this.minEnergy = 0;
	}
	
	public ParticleStorage(ParticleStack stack, long maxEnergy)
	{
		this.particleStack = stack;
		this.maxEnergy = maxEnergy;
		this.capacity = Integer.MAX_VALUE;
		this.minEnergy = 0;
	}
	

	public ParticleStorage readFromNBT(NBTTagCompound nbt)
	{
		if (!nbt.hasKey("Empty"))
		{
			ParticleStack stack = ParticleStack.loadParticleStackFromNBT(nbt);
			setParticleStack(stack);
		}
		else
		{
			setParticleStack(null);
		}
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		if (particleStack != null)
		{
			particleStack.writeToNBT(nbt);
		}
		else
		{
			nbt.setString("Empty", "");
		}
		return nbt;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt, int id)
	{
		writeToNBT(nbt);
		NBTTagCompound tag = new NBTTagCompound();
		nbt.setTag("ParticleStorage" + id, tag);
		return nbt;
	}

	public ParticleStorage readFromNBT(NBTTagCompound nbt, int id)
	{
		if (nbt.hasKey("ParticleStorage" + id)) 
		{
			NBTTagCompound tag = nbt.getCompoundTag("ParticleStorage" + id);
			return readFromNBT(tag);
		}
		return readFromNBT(nbt);
	}
	
	
	public void setParticleStack(ParticleStack stack)
	{
		this.particleStack = stack;

	}	
	
	public void setTileEntity(TileEntity tile)
	{
		this.tile = tile;
	}

	public ParticleStorageInfo getInfo()
	{
		return new ParticleStorageInfo(this);
	}

	@Override
	public boolean reciveParticle(EnumFacing side, ParticleStack stack)
	{
		if(stack != null)
		{
			if(particleStack == null)
			{
				particleStack = stack.copy();
				return true;
			}
			if(particleStack.getParticle() == stack.getParticle())
			{
				if(stack.getMeanEnergy() >= minEnergy && stack.getMeanEnergy() <= maxEnergy)
				{
					if(particleStack.getAmount()+ stack.getAmount() <= capacity)
					{
						
						particleStack.setMeanEnergy((stack.getMeanEnergy() * stack.getAmount() + particleStack.getMeanEnergy() * particleStack.getAmount())/ (stack.getAmount() + particleStack.getAmount()));
						particleStack.addAmount(stack.getAmount());
						return true;
					}
				}
			}
		}
		return false;
	}



	@Override
	public boolean canReciveParticle(EnumFacing side, ParticleStack stack)
	{
		if(stack != null)
		{
			if(particleStack == null)
			{
				return true;
			}
			if(particleStack.getParticle() == stack.getParticle())
			{
				if(stack.getMeanEnergy() > minEnergy && stack.getMeanEnergy() < maxEnergy)
				{
					if(particleStack.getAmount()+ stack.getAmount() <= capacity)
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canExtractParticle(EnumFacing side)
	{
		return particleStack != null;
	}

	@Override
	public ParticleStack getParticleStack()
	{
		return particleStack;
	}

	@Override
	public long getMaxEnergy()
	{
		return maxEnergy;
	}

	@Override
	public long getMinEnergy()
	{
		return minEnergy;
	}
	
	@Override
	public int getCapacity()
	{
		return capacity;
	}
	
	public void setCapacity(int capacity)
	{
		this.capacity = capacity;
	}
	
	public void setMaxEnergy(long maxEnergy)
	{
		this.maxEnergy = maxEnergy;
	}

	public void setMinEnergy(long minEnergy)
	{
		this.minEnergy = minEnergy;
	}


	@Override
	public ParticleStack extractParticle(EnumFacing side)
	{
		if (canExtractParticle(side))
		{
			ParticleStack stack = this.particleStack;
			this.particleStack = null;
			return stack;
		}
		return null;
	}
	
	@Override
	public ParticleStack extractParticle(EnumFacing side, Particle type)
	{
		if (canExtractParticle(side))
		{
			if (particleStack.getParticle() == type)
			{
				return extractParticle(side);
			}
		}
		return null;
	}
	
	@Override
	public ParticleStack extractParticle(EnumFacing side, int amount)
	{
		if(canExtractParticle(side))
		{
			ParticleStack stack = particleStack.copy();
			if(particleStack.getAmount() > amount)
			{
				stack.setAmount(amount);
			}
			else
			{
				stack.setAmount(particleStack.getAmount());
			}
			particleStack.removeAmount(amount);
		}
		return null;
	}
	
	@Override
	public ParticleStack extractParticle(EnumFacing side, Particle type, int Amount)
	{

		if (canExtractParticle(side))
		{
			if (particleStack.getParticle() == type)
			{
				return extractParticle(side, Amount);
			}
		}
		return null;
	}

	
	
	
	
	
	
	
	
}
