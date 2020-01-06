package lach_01298.qmd.multiblock.accelerator.tile;

import static lach_01298.qmd.block.BlockProperties.IO;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.EnumTypes.IOType;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.EnumTypes.IOType;
import lach_01298.qmd.io.IIOType;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.particle.AcceleratorStorage;
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
	
	private final @Nonnull List<AcceleratorStorage> backupTanks = Lists.newArrayList(new AcceleratorStorage());
	private EnumTypes.IOType type;
	
	
	public TileAcceleratorBeamPort()
	{
		super(CuboidalPartPositionType.WALL);
		this.type = EnumTypes.IOType.DEFAULT;
		
		
	}

	boolean isFunctional()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
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
	}
	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);
		nbt.setInteger("setting", type.getID());
		return nbt;
	}
		
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		type =EnumTypes.IOType.getTypeFromID(nbt.getInteger("setting"));
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
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
	
	
}
