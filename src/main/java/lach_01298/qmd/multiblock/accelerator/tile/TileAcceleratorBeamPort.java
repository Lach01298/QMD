package lach_01298.qmd.multiblock.accelerator.tile;

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
import lach_01298.qmd.multiblock.accelerator.Accelerator;
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

public class TileAcceleratorBeamPort extends TileAcceleratorPart implements IIOType, ITileParticleStorage
{
	
	private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator());
	private EnumTypes.IOType type;
	private EnumTypes.IOType switchType;
	private boolean triggered = false;
	private boolean powered = false;
	
	public TileAcceleratorBeamPort()
	{
		super(CuboidalPartPositionType.WALL);
		this.type = EnumTypes.IOType.DISABLED;
		this.switchType = EnumTypes.IOType.INPUT;
		
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
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, type));
		markDirtyAndNotify();
		getMultiblock().checkIfMachineIsWhole();
	}
	
	public void toggleSetting()
	{
		setIOType(type.getNextIO());
	}
	
	

	public IOType getSwitchSetting()
	{
		
		return switchType;
	}
	
	public void toggleSwitchSetting()
	{
		if (switchType == IOType.INPUT)
		{
			setSwitchType(IOType.OUTPUT);
		}
		else
		{
			setSwitchType(IOType.INPUT);
		}
	}
	
	
	public void switchSetting()
	{
		if(type == switchType)
		{
			setIOType(IOType.DISABLED);
		}
		else
		{
			setIOType(switchType);
		}
		
		
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, type));
		markDirtyAndNotify();
		getMultiblock().checkIfMachineIsWhole();
	}
	
	public void setSwitchType(IOType setting)
	{
		switchType = setting;
				
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, type));
		markDirtyAndNotify();
		getMultiblock().checkIfMachineIsWhole();
	}
	
	
	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);
		nbt.setInteger("setting", type.getID());
		nbt.setInteger("switchSetting", switchType.getID());
		nbt.setBoolean("triggered", triggered);
		return nbt;
	}
		
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		type =EnumTypes.IOType.getTypeFromID(nbt.getInteger("setting"));
		switchType =EnumTypes.IOType.getTypeFromID(nbt.getInteger("switchSetting"));
		triggered = nbt.getBoolean("triggered");
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
				if(type ==EnumTypes.IOType.OUTPUT && getTanks().size() >=2)
				{
					return (T) getTanks().get(1);
				}
				return (T) getTanks().get(0);
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
	
		
		public boolean isTriggered()
		{
			return triggered;
		}
		public void resetTrigger()
		{
			triggered = false;
		}
		public void setTrigger()
		{
			triggered = true;
		}
		
	
	@Override
	public void update()
	{

		if (!world.isRemote)
		{
			if (this.getMultiblock() != null)
			{
				if (getIsRedstonePowered() && !powered)
				{
					setTrigger();
					this.getMultiblock().switchIO();
				}
			}
			powered = getIsRedstonePowered();
		}
	}

}
		
		
		
		

