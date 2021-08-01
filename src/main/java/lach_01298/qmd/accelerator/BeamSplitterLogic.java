package lach_01298.qmd.accelerator;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSynchrotronPort;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.container.ContainerBeamSplitterController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.BeamSplitterUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BeamSplitterLogic extends AcceleratorLogic
{

	
	
	public BeamSplitterLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		beam 0 = input particle
		beam 1 =  output particle 
		beam 2 = output particle straight
		tank 0 = input coolant
		tank 1 = output coolant
		*/
	}

	@Override
	public String getID()
	{
		return "beam_splitter";
	}

	// Multiblock Validation
	
	
	@Override
	public boolean isMachineWhole() 
	{
		Accelerator acc = getAccelerator();
		
		if (acc.getExteriorLengthX() != thickness || acc.getExteriorLengthY() != thickness || acc.getExteriorLengthZ() != thickness)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.beam_director.must_be_cube", null);
			return false;
		}
		
		
		if (!(acc.WORLD.getTileEntity(acc.getMiddleCoord()) instanceof TileAcceleratorBeam))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.beam_director.must_be_beam", acc.getMiddleCoord());
			return false;
		}

		if (!acc.isValidDipole(acc.getMiddleCoord(), false) && !acc.isValidDipole(acc.getMiddleCoord(), true))
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.beam_director.must_be_dipole", acc.getMiddleCoord());
			return false;
		}
		
		
	
		//beam ports
		
		for(TileAcceleratorBeamPort port :getPartMap(TileAcceleratorBeamPort.class).values())
		{
			boolean valid = false;
			if(port.getPos().toLong() == acc.getMiddleCoord().up(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().down(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().north(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().south(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().east(2).toLong())
			{
				valid = true;
			}
			else if(port.getPos().toLong() == acc.getMiddleCoord().west(2).toLong())
			{
				valid = true;
			}
			
			
			if(!valid)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.invalid_beam_port", port.getPos());
				return false;
			}
		}
				
			
		int inputs =0;
		int outputs =0;	
		for(TileAcceleratorBeamPort port :getPartMap(TileAcceleratorBeamPort.class).values())
		{	
			
			
			
			port.recalculateExternalDirection(acc.getMinimumCoord(), acc.getMaximumCoord());
			if(port.getExternalFacing() == null)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.something_is_wrong", port.getPos());
				return false;
			}
			
			
			if(!(acc.WORLD.getTileEntity(port.getPos().offset(port.getExternalFacing().getOpposite())) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.beam_port_must_connect", port.getPos().offset(port.getExternalFacing().getOpposite()));
				return false;
			}
			if(port.getIOType() == IOType.INPUT)
			{
				inputs++;
			}
			
			if(port.getIOType() == IOType.OUTPUT)
			{
				outputs++;
			}		
		}
		
		if(inputs != 1 || outputs != 2)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.splitter.must_have_io", null);
			return false;
		}	
		if(containsBlacklistedPart())
		{
			return false;
		}

		return super.isMachineWhole();
	}
	
	// Multiblock Methods
	
	@Override
	public void onAcceleratorFormed()
	{
		 Accelerator acc = getAccelerator();

		 if (!getWorld().isRemote)
		 {
			 acc.beams.get(0).setMinEnergy(0);
			 //beam ports
			for (TileAcceleratorBeamPort port :acc.getPartMap(TileAcceleratorBeamPort.class).values())
			{
				if(port.getIOType() == IOType.INPUT)
				{
					acc.input = port;
				}
				
				if(port.getIOType() == IOType.OUTPUT)
				{
					acc.output = port;
				}
			}	
			
			
			if (acc.isValidDipole(acc.getMiddleCoord(), false) || acc.isValidDipole(acc.getMiddleCoord(), true))
			{
				acc.getDipoleMap().put(acc.getMiddleCoord().toLong(), new DipoleMagnet(acc,acc.getMiddleCoord()));
			}
			
			
			for (DipoleMagnet dipole : acc.getDipoleMap().values())
			{
				for (IAcceleratorComponent componet : dipole.getComponents().values())
				{
					componet.setFunctional(true);
				}

			}
			
			
		}
		 
		 refreshStats();
		 super.onAcceleratorFormed();
		 acc.cooling = (long) (2*(acc.rawHeating+acc.getMaxExternalHeating()));
	}
	
	public void onMachineDisassembled()
	{

		super.onMachineDisassembled();
	}
	
	@Override
	public boolean onUpdateServer()
	{
		getAccelerator().errorCode = Accelerator.errorCode_Nothing;
		getAccelerator().beams.get(0).setParticleStack(null);
		pull();
		
		if (getAccelerator().isAcceleratorOn)
		{
			produceBeam();
		}
		else
		{
			resetBeam();
		}
		
		push();

		return super.onUpdateServer();
	}
	
	@Override
	protected void push()
	{
		if (getAccelerator().input != null && getAccelerator().input.getExternalFacing() != null)
		{
			for (TileAcceleratorBeamPort port :getAccelerator().getPartMap(TileAcceleratorBeamPort.class).values())
			{
				if(port.getExternalFacing() != null)
				{
					if(port.getIOType() == IOType.OUTPUT)
					{
						if(port.getPos().offset(port.getExternalFacing().getOpposite(), thickness - 1).equals(getAccelerator().input.getPos()))
						{
							TileEntity tile = getAccelerator().WORLD.getTileEntity(port.getPos().offset(port.getExternalFacing()));
							if (tile != null)
							{
								if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite()))
								{
									IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite());
									otherStorage.reciveParticle(port.getExternalFacing().getOpposite(), getAccelerator().beams.get(2).getParticleStack());
									
								}
							}	
						}
						else
						{
							TileEntity tile = getAccelerator().WORLD.getTileEntity(port.getPos().offset(port.getExternalFacing()));
							if (tile != null)
							{
								if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite()))
								{
									IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite());
									otherStorage.reciveParticle(port.getExternalFacing().getOpposite(), getAccelerator().beams.get(1).getParticleStack());
									
								}
							}	
						}
					}
				}	
			}	
		}

	}
	
	
	public long getEnergyLoss()
	{
		if(this.getAccelerator().beams.get(0).getParticleStack() != null)
		{
			Particle particle = this.getAccelerator().beams.get(0).getParticleStack().getParticle();
			ParticleStack particleIn = getAccelerator().beams.get(0).getParticleStack();
			return (long)(Math.pow(particle.getCharge(),2)/(6*Math.pow(particle.getMass(),4)*Math.pow(getBeamRadius(),2))*particleIn.getMeanEnergy());
		}
		
		return 0;
	}
	
	public long getMaxEnergy()
	{
		if(this.getAccelerator().beams.get(0).getParticleStack() != null)
		{
			Particle particle = this.getAccelerator().beams.get(0).getParticleStack().getParticle();
			return (long) (Math.pow(particle.getCharge()*getAccelerator().dipoleStrength*getBeamRadius(),2)/(2*particle.getMass())*1000000);
		}
		
		return 0;
	}
	
	public long getAcceleratorMaxEnergy(Particle particle)
	{
		if(particle != null)
		{
			return (long) (Math.pow(particle.getCharge()*getAccelerator().dipoleStrength*getBeamRadius(),2)/(2*particle.getMass())*1000000);
		}
		return 0;
	}
	
	@Override
	public int getBeamLength()
	{
		return getAccelerator().getExteriorLengthX();
	}
	
	@Override
	public double getBeamRadius()
	{
		return QMDConfig.beamDiverterRadius;
	}
	
	

	
	// Recipe Stuff
	
	private void resetBeam()
	{
		getAccelerator().beams.get(1).setParticleStack(null);
		getAccelerator().beams.get(2).setParticleStack(null);
	}


	private void produceBeam()
	{
		
		if(getAccelerator().beams.get(0).getParticleStack() != null)
		{
			ParticleStack particleIn = getAccelerator().beams.get(0).getParticleStack();
			getAccelerator().beams.get(1).setParticleStack(particleIn.copy());
			getAccelerator().beams.get(1).getParticleStack().setAmount(particleIn.getAmount()/2);
			
			getAccelerator().beams.get(2).setParticleStack(particleIn.copy());
			getAccelerator().beams.get(2).getParticleStack().setAmount(particleIn.getAmount()/2);
			
			
			if(particleIn.getMeanEnergy() <= getMaxEnergy())
			{
				ParticleStack particleOut = getAccelerator().beams.get(1).getParticleStack();
				ParticleStack particleStraightOut = getAccelerator().beams.get(2).getParticleStack();
				
				particleOut.addMeanEnergy(-getEnergyLoss());
				particleOut.addFocus(-getBeamLength()*QMDConfig.beamAttenuationRate);
				particleStraightOut.addFocus(-getBeamLength()*QMDConfig.beamAttenuationRate);
				
				if(particleOut.getFocus() <= 0)
				{
					particleOut = null;
					getAccelerator().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
				}
				if(particleStraightOut.getFocus() <= 0)
				{
					particleStraightOut = null;
					getAccelerator().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
				}
			}
		}
		else
		{
			resetBeam();
		}
	}
	
	
	@Override
	protected void pull()
	{
		if (getAccelerator().input != null && getAccelerator().input.getExternalFacing() != null)
		{

			TileEntity tile = getAccelerator().WORLD
					.getTileEntity(getAccelerator().input.getPos().offset(getAccelerator().input.getExternalFacing()));
			if (tile != null)
			{

				if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,getAccelerator().input.getExternalFacing().getOpposite()))
				{
					IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,getAccelerator().input.getExternalFacing().getOpposite());
					ParticleStack stack = otherStorage.extractParticle(getAccelerator().input.getExternalFacing().getOpposite());

					if (stack != null)
					{
						getAccelerator().beams.get(0).setMaxEnergy(getAcceleratorMaxEnergy(stack.getParticle()));

						if (!getAccelerator().beams.get(0).reciveParticle(getAccelerator().input.getExternalFacing(),
								stack))
						{
							if (stack.getMeanEnergy() > getAccelerator().beams.get(0).getMaxEnergy())
							{
								getAccelerator().errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
							}
							else if (stack.getMeanEnergy() < getAccelerator().beams.get(0).getMinEnergy())
							{
								getAccelerator().errorCode = Accelerator.errorCode_InputParticleEnergyToLow;
							}
						}
					}
				}
			}
		}
	}
	
	
	
	
	// Network
	@Override
	public BeamSplitterUpdatePacket getUpdatePacket()
	{
		return new BeamSplitterUpdatePacket(getAccelerator().controller.getTilePos(),
				getAccelerator().isAcceleratorOn, getAccelerator().cooling, getAccelerator().rawHeating,getAccelerator().currentHeating,getAccelerator().maxCoolantIn,getAccelerator().maxCoolantOut,getAccelerator().maxOperatingTemp,
				getAccelerator().requiredEnergy, getAccelerator().efficiency, getAccelerator().acceleratingVoltage,
				getAccelerator().RFCavityNumber, getAccelerator().quadrupoleNumber, getAccelerator().quadrupoleStrength, getAccelerator().dipoleNumber, getAccelerator().dipoleStrength, getAccelerator().errorCode,
				getAccelerator().heatBuffer, getAccelerator().energyStorage, getAccelerator().tanks, getAccelerator().beams);
	}
	
	@Override
	public void onPacket(AcceleratorUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof BeamSplitterUpdatePacket)
		{
			BeamSplitterUpdatePacket packet = (BeamSplitterUpdatePacket) message;
		}
	}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);

	}
	
	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
	}
	
	

	@Override
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) 
	{
		return new ContainerBeamSplitterController(player, getAccelerator().controller);
	}

	public static final List<Pair<Class<? extends IAcceleratorPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileAcceleratorSynchrotronPort.class,
					QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
			Pair.of(TileAcceleratorRFCavity.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_rf_cavity"),
			Pair.of(TileAcceleratorSource.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_source"));

	@Override
	public List<Pair<Class<? extends IAcceleratorPart>, String>> getPartBlacklist()
	{
		return PART_BLACKLIST;
	}
	
}
