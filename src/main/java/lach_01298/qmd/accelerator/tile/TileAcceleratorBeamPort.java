package lach_01298.qmd.accelerator.tile;

import static lach_01298.qmd.block.BlockProperties.IO;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.tile.ITileIOType;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileAcceleratorBeamPort extends TileAcceleratorPart implements ITileIOType, ITileParticleStorage, ITickable
{
	
	private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator());
	private EnumTypes.IOType mode;
	private EnumTypes.IOType setting;
	private boolean triggered = false;
	private boolean powered = false;
	
	public TileAcceleratorBeamPort()
	{
		super(CuboidalPartPositionType.WALL);
		this.mode = EnumTypes.IOType.DISABLED;
		this.setting = EnumTypes.IOType.INPUT;
		
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
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, type));
		markDirtyAndNotify(true);
		getMultiblock().checkIfMachineIsWhole();
	}
	
	public void cycleMode()
	{
		setIOType(mode.getNextIO());
	}
	
	

	public IOType getSetting()
	{
		
		return setting;
	}
	
	public void toggleSetting()
	{
		if (setting == IOType.INPUT)
		{
			setSettingType(IOType.OUTPUT);
		}
		else
		{
			setSettingType(IOType.INPUT);
		}
	}
	
	
	public void switchMode()
	{
		if(mode == setting)
		{
			setIOType(IOType.DISABLED);
		}
		else
		{
			setIOType(setting);
		}
		
		
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, mode));
		markDirtyAndNotify(true);
		getMultiblock().checkIfMachineIsWhole();
	}
	
	public void setSettingType(IOType setting)
	{
		this.setting = setting;
				
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, mode));
		markDirtyAndNotify(true);
		getMultiblock().checkIfMachineIsWhole();
	}
	
	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayer player, World world, EnumFacing facing,
			float hitX, float hitY, float hitZ)
	{
		
		if (player.isSneaking())
		{
			toggleSetting();
			
			TextFormatting format;
			switch(getSetting())
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
			
			player.sendMessage(new TextComponentString(Lang.localise("qmd.block.accelerator_port_setting_toggle") + " "
					+ format + Lang.localise("qmd.block.port_mode."+ getSetting().name()) + " "
					+ TextFormatting.WHITE + Lang.localise("qmd.block.port.mode")));
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
			
			player.sendMessage(new TextComponentString(Lang.localise("qmd.block.port_mode_toggle") + " "
					+ format + Lang.localise("qmd.block.port_mode."+ getIOType().name()) + " "
					+ TextFormatting.WHITE + Lang.localise("qmd.block.port.mode")));
		}
		
		return true;
		
	}
	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);
		nbt.setInteger("mode", mode.getID());
		nbt.setInteger("setting", setting.getID());
		nbt.setBoolean("triggered", triggered);
		return nbt;
	}
		
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		mode =EnumTypes.IOType.getTypeFromID(nbt.getInteger("mode"));
		setting =EnumTypes.IOType.getTypeFromID(nbt.getInteger("setting"));
		triggered = nbt.getBoolean("triggered");
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
				if(mode ==EnumTypes.IOType.OUTPUT && getParticleBeams().size() >=2)
				{
					return (T) getParticleBeams().get(1);
				}
				return (T) getParticleBeams().get(0);
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
		
		
		
		

