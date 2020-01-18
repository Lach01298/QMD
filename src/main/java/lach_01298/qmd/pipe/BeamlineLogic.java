package lach_01298.qmd.pipe;

import static lach_01298.qmd.block.BlockProperties.AXIS_HORIZONTAL;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.Util;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.io.IIOType;
import lach_01298.qmd.particle.AcceleratorStorage;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.tile.TileBeamline;
import nc.multiblock.ITileMultiblockPart;
import nc.multiblock.Multiblock;
import nc.multiblock.TileBeefBase.SyncReason;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing.Axis;

public class BeamlineLogic extends PipeLogic
{

	public final AcceleratorStorage storage = new AcceleratorStorage();
	public Axis axis;
	
	public BeamlineLogic(PipeLogic oldLogic)
	{
		super(oldLogic);
		
		if(getWorld().getBlockState(multiblock.controller.getTilePos()).getValue(AXIS_HORIZONTAL) != null)
		{
			this.axis = getWorld().getBlockState(multiblock.controller.getTilePos()).getValue(AXIS_HORIZONTAL);	
		}
		else
		{
			this.axis =Axis.X;
		}
		
	}
	
	@Override
	public boolean isMachineWhole(Multiblock multiblock)
	{
		return true;
	}
	
	@Override
	public String getID() 
	{
		return "beamline";
	}
	

	@Override
	public boolean onUpdateServer() 
	{
		storage.setParticleStack(null);
		input();
		output();
		return true;
	}
	
	
	
	
	
	
	
	
	public void input()
	{
		if (getPipe().WORLD.getTileEntity(getPipe().getMinimumCoord().offset(Util.getAxisFacing(axis, false))) != null)
		{
			TileEntity tile = getPipe().WORLD.getTileEntity(getPipe().getMinimumCoord().offset(Util.getAxisFacing(axis, false)));
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

		if (getPipe().WORLD.getTileEntity(getPipe().getMaximumCoord().offset(Util.getAxisFacing(axis, true))) != null)
		{
			TileEntity tile = getPipe().WORLD.getTileEntity(getPipe().getMaximumCoord().offset(Util.getAxisFacing(axis, true)));
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
		ParticleStack output = null;
		if (storage.getParticleStack() != null)
		{
			output = storage.getParticleStack().copy();
			output.addLuminosity(-getPipe().length() * QMDConfig.beamAttenuationRate);

			if (output.getLuminosity() <= 0)
			{
				output =null;
			}
		}
		 
		
		
		
		if (getPipe().WORLD.getTileEntity(getPipe().getMinimumCoord().offset(Util.getAxisFacing(axis, false))) != null)
		{
			TileEntity tile = getPipe().WORLD.getTileEntity(getPipe().getMinimumCoord().offset(Util.getAxisFacing(axis, false)));
			if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, false)))
			{
				IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, false));

				if (tile instanceof IIOType)
				{
					IIOType tileIO = (IIOType) tile;
					if (tileIO.getIOType() == EnumTypes.IOType.INPUT)
					{
						
						otherStorage.reciveParticle(Util.getAxisFacing(axis, false), output);
					}

				}
			}
		}

		if (getPipe().WORLD.getTileEntity(getPipe().getMaximumCoord().offset(Util.getAxisFacing(axis, true))) != null)
		{
			TileEntity tile = getPipe().WORLD.getTileEntity(getPipe().getMaximumCoord().offset(Util.getAxisFacing(axis, true)));
			if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,Util.getAxisFacing(axis, true)))
			{
				IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, Util.getAxisFacing(axis, true));

				if (tile instanceof IIOType)
				{
					IIOType tileIO = (IIOType) tile;
					if (tileIO.getIOType() == EnumTypes.IOType.INPUT)
					{
						otherStorage.reciveParticle(Util.getAxisFacing(axis, true), output);
					}

				}
			}
		}
	}
	
	
	
	
	
	
	
	@Override
	public void onBlockAdded(ITileMultiblockPart newPart)
	{
		if(newPart instanceof TileBeamline)
		{
			TileBeamline beamline = (TileBeamline) newPart;
			if(getPipe().WORLD.getBlockState(beamline.getPos()).getValue(AXIS_HORIZONTAL) == axis)
			{
				if(beamline.getPos() == getPipe().getMinimumCoord().offset(Util.getAxisFacing(axis, false)) || beamline.getPos() == getPipe().getMaximumCoord().offset(Util.getAxisFacing(axis, true)))
				{
					getPipe().onPartAdded(newPart);
				}
			}
		}
	}
	
	
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		storage.writeToNBT(logicTag);		
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		storage.readFromNBT(logicTag);
	}
	
	
	
}
