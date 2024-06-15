package lach_01298.qmd.particleChamber.tile;

import com.google.common.collect.Lists;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.particleChamber.CollisionChamberLogic;
import lach_01298.qmd.tile.*;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.*;
import java.util.List;

import static lach_01298.qmd.block.BlockProperties.IO;

public class TileParticleChamberBeamPort extends TileParticleChamberPart implements ITileIOType, ITileIONumber, ITileParticleStorage
{
	
	private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator());
	private EnumTypes.IOType mode;
	private int IONumber;
	
	public TileParticleChamberBeamPort()
	{
		super(CuboidalPartPositionType.WALL);
		this.mode = EnumTypes.IOType.INPUT;
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
		
		return mode;
	}

	@Override
	public void setIOType(IOType type)
	{
		this.mode = type;
	}
	
	public void cycleMode()
	{
		setIOType(mode.getNextIO());
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, mode));
		markDirtyAndNotify();
		getMultiblock().checkIfMachineIsWhole();
	}
	
	public boolean toggleSetting()
	{
		if(isMultiblockAssembled())
		{
			return getMultiblock().toggleSetting(this.pos, IONumber);
		}

		return false;
	}
	
	
	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayerMP player, World world, EnumFacing facing,
	                              float hitX, float hitY, float hitZ)
	{
		
		if (player.isSneaking())
		{
			if(toggleSetting())
			{
				int inputNumberOffset = 0;
				if (getMultiblock().getLogic() instanceof CollisionChamberLogic)
				{
					inputNumberOffset = 1;
				}
				
				
				player.sendMessage(new TextComponentString(Lang.localize("qmd.block.particle_chamber_port_setting_toggle") + " "
						+ TextFormatting.LIGHT_PURPLE +" " + (getIONumber()-inputNumberOffset)));
			}
			else
			{
				return false;
			}
			
		}
		else
		{
			cycleMode();
			
			TextFormatting format;
			switch(getIOType())
			{
			case INPUT:
				format = TextFormatting.DARK_AQUA;
				break;
			case OUTPUT:
				format = TextFormatting.RED;
				break;
			default:
				format = TextFormatting.GRAY;
				break;
			}
			
			player.sendMessage(new TextComponentString(Lang.localize("qmd.block.port_mode_toggle") + " "
					+ format + Lang.localize("qmd.block.port_mode."+ getIOType().name()) + " "
					+ TextFormatting.WHITE + Lang.localize("qmd.block.port.mode")));
		}
		
		return true;
		
	}
	
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		nbt.setInteger("mode", mode.getID());
		nbt.setInteger("IONumber", IONumber);
		return nbt;
	}
	
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		mode =EnumTypes.IOType.getTypeFromID(nbt.getInteger("mode"));
		IONumber = nbt.getInteger("IONumber");
	}


	
	// Capability

		@Override
		public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
		{
			if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
			{
				return mode != EnumTypes.IOType.DISABLED;
			}
			return super.hasCapability(capability, side);
		}

		@Override
		public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
		{
			if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
			{
				if (!getParticleBeams().isEmpty())
				{
					return (T) getParticleBeams().get(IONumber);
				}
				return null;
			}

			return super.getCapability(capability, side);
		}

		@Override
		public List<? extends ParticleStorage> getParticleBeams()
		{
			if (!isMultiblockAssembled())
				return backupTanks;
			return getMultiblock().beams;
		}
	
	public void setIONumber(int number)
	{
		if(number >= 0)
		{
			IONumber= number;
		}
	}
	
	public int getIONumber()
	{
		return	IONumber;
	}
	
}
