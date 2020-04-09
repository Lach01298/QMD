package lach_01298.qmd.multiblock.particleChamber.tile;

import static lach_01298.qmd.block.BlockProperties.IO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.io.IIOType;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import nc.tile.internal.fluid.Tank;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileParticleChamberBeamPort extends TileParticleChamberPart implements IIOType, ITileParticleStorage
{
	
	private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator());
	private EnumTypes.IOType type;
	private int IONumber;
	
	public TileParticleChamberBeamPort()
	{
		super(CuboidalPartPositionType.WALL);
		this.type = EnumTypes.IOType.INPUT;
		this.IONumber= 0;
		
	}



	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) 
	{
		return oldState.getBlock() != newSate.getBlock();
	}
	
	@Override
	public IOType getIOType()
	{
		
		return type;
	}

	@Override
	public void setIOType(IOType type)
	{
		this.type = type;
	}
	
	public void toggleSetting()
	{
		setIOType(type.getNextIO());
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, type));
		markDirtyAndNotify();
		getMultiblock().checkIfMachineIsWhole();
	}
	
	public boolean switchOutputs()
	{
		if(IONumber == 0 || IONumber == 2)
		{
			return false;
		}
		if(isMultiblockAssembled())
		{
			return getMultiblock().switchOutputs(this.pos);
		}
		return false;
		
	}
	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);
		nbt.setInteger("setting", type.getID());
		nbt.setInteger("IONumber", IONumber);
		return nbt;
	}
		
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		type =EnumTypes.IOType.getTypeFromID(nbt.getInteger("setting"));
		IONumber = nbt.getInteger("IONumber");
	}


		
	// Capability

		@Override
		public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
		{
			if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
			{
				return type != EnumTypes.IOType.DISABLED;
			}
			return super.hasCapability(capability, side);
		}

		@Override
		public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
		{
			if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
			{
				if (!getTanks().isEmpty())
				{
					return (T) getTanks().get(IONumber);
				}
				return null;
			}

			return super.getCapability(capability, side);
		}

		@Override
		public List<? extends ParticleStorage> getTanks()
		{
			if (!isMultiblockAssembled())
				return backupTanks;
			return getMultiblock().beams;
		}
	
	public void setIONumber(int number)
	{
		if(number >= 0 && number <= 3)
		{
			IONumber= number;
		}
	}
	
	public int getIONumber()
	{
		return	IONumber;
	}
	
}
