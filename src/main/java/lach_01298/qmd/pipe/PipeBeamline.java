package lach_01298.qmd.pipe;

import javax.annotation.Nonnull;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.Util;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.io.IIOType;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.particle.AcceleratorStorage;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.tile.TileBeamline;
import nc.multiblock.ITileMultiblockPart;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.ILogicMultiblock.PartSuperMap;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.util.SuperMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.world.World;
import static lach_01298.qmd.block.BlockProperties.AXIS_HORIZONTAL;

public class PipeBeamline extends PipeMultiblock<PipeBeamlineUpdatePacket> implements ILogicMultiblock<MultiblockLogic<PipeBeamline,ITileMultiblockPart<PipeBeamline>,PipeBeamlineUpdatePacket>,ITileMultiblockPart<PipeBeamline>>
{
	
	public static final ObjectSet<Class<? extends TileBeamline>> PART_CLASSES = new ObjectOpenHashSet<>();
	protected final SuperMap<Long, ITileMultiblockPart<PipeBeamline>, Long2ObjectMap<? extends ITileMultiblockPart<PipeBeamline>>> partSuperMap = new PartSuperMap<>();

	public final AcceleratorStorage storage = new AcceleratorStorage();
	public Axis axis;
	
	private int updateCount = 0;
	private int updateRate = 2;
	
	public PipeBeamline(World world, Axis axis)
	{
		super(world);
		for (Class<? extends TileBeamline> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
		this.axis = axis;
	}

	

	@Override
	public void onAttachedPartWithMultiblockData(ITileMultiblockPart part, NBTTagCompound data)
	{
		syncDataFrom(data, SyncReason.FullSync);	
	}

	@Override
	protected void onBlockAdded(ITileMultiblockPart newPart)
	{
		if(newPart instanceof TileBeamline)
		{
			TileBeamline beamline = (TileBeamline) newPart;
			if(WORLD.getBlockState(beamline.getPos()).getValue(AXIS_HORIZONTAL) == axis)
			{
				if(beamline.getPos() == getMinimumCoord().offset(Util.getAxisFacing(axis, false)) || beamline.getPos() == getMaximumCoord().offset(Util.getAxisFacing(axis, true)))
				{
					onPartAdded(newPart);
				}
			}
		}
	}

	@Override
	protected void onBlockRemoved(ITileMultiblockPart oldPart)
	{
		onPartRemoved(oldPart);
		
	}

	@Override
	protected void onMachineAssembled()
	{
	}

	@Override
	protected void onMachineRestored()
	{
	}

	@Override
	protected void onMachinePaused()
	{
	}

	@Override
	protected void onMachineDisassembled()
	{	
	}

	

	@Override
	protected void onAssimilate(Multiblock assimilated)
	{
	}

	@Override
	protected void onAssimilated(Multiblock assimilator)
	{
	}

	@Override
	protected boolean updateServer()
	{
		updateCount--;
		if (updateCount <= 0)
		{
			storage.setParticleStack(null);
			input();
			output();
			updateCount = updateRate;
		}
		return true;
	}
	
	
	public void input()
	{
		if (WORLD.getTileEntity(getMinimumCoord().offset(Util.getAxisFacing(axis, false))) != null)
		{
			TileEntity tile = WORLD.getTileEntity(getMinimumCoord().offset(Util.getAxisFacing(axis, false)));
			if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, false)))
			{
				IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, false));

				if (tile instanceof IIOType)
				{
					IIOType tileIO = (IIOType) tile;
					if (tileIO.getIOType() == EnumTypes.IOType.OUTPUT)
					{
						storage.setParticleStack(otherStorage.extractParticle(Util.getAxisFacing(axis, false)));
					}
				}
			}
		}

		if (WORLD.getTileEntity(getMaximumCoord().offset(Util.getAxisFacing(axis, true))) != null)
		{
			TileEntity tile = WORLD.getTileEntity(getMaximumCoord().offset(Util.getAxisFacing(axis, true)));
			if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, true)))
			{
				IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, Util.getAxisFacing(axis, true));

				if (tile instanceof IIOType)
				{
					IIOType tileIO = (IIOType) tile;
					if (tileIO.getIOType() == EnumTypes.IOType.OUTPUT)
					{
						storage.setParticleStack(otherStorage.extractParticle(Util.getAxisFacing(axis, true)));
					}
				}
			}
		}
	}
	
	public void output()
	{
		if (WORLD.getTileEntity(getMinimumCoord().offset(Util.getAxisFacing(axis, false))) != null)
		{
			TileEntity tile = WORLD.getTileEntity(getMinimumCoord().offset(Util.getAxisFacing(axis, false)));
			if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, false)))
			{
				IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, false));

				if (tile instanceof IIOType)
				{
					IIOType tileIO = (IIOType) tile;
					if (tileIO.getIOType() == EnumTypes.IOType.INPUT)
					{
						otherStorage.reciveParticle(Util.getAxisFacing(axis, false), storage.getParticleStack());
					}

				}
			}
		}

		if (WORLD.getTileEntity(getMaximumCoord().offset(Util.getAxisFacing(axis, true))) != null)
		{
			TileEntity tile = WORLD.getTileEntity(getMaximumCoord().offset(Util.getAxisFacing(axis, true)));
			if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, true)))
			{
				IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, Util.getAxisFacing(axis, true));

				if (tile instanceof IIOType)
				{
					IIOType tileIO = (IIOType) tile;
					if (tileIO.getIOType() == EnumTypes.IOType.INPUT)
					{
						otherStorage.reciveParticle(Util.getAxisFacing(axis, true), storage.getParticleStack());
					}

				}
			}
		}
	}
	
	
	

	@Override
	protected void updateClient()
	{	
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, Multiblock multiblock)
	{
		return true;
	}

	@Override
	protected void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		storage.readFromNBT(data);
	}

	@Override
	protected void syncDataTo(NBTTagCompound data, SyncReason syncReason)
	{
		storage.writeToNBT(data);	
	}

	@Override
	protected PipeBeamlineUpdatePacket getUpdatePacket()
	{
		
		return null;
	}

	@Override
	public void onPacket(PipeBeamlineUpdatePacket message)
	{
		
	}





	@Override
	public SuperMap<Long, ITileMultiblockPart<PipeBeamline>, Long2ObjectMap<? extends ITileMultiblockPart<PipeBeamline>>> getPartSuperMap()
	{
		return partSuperMap;
	}



	@Override
	public MultiblockLogic<PipeBeamline, ITileMultiblockPart<PipeBeamline>, PipeBeamlineUpdatePacket> getLogic()
	{
		return null;
	}

}
