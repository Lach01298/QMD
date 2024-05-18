package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.*;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.util.Equations;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class BeamSplitterLogic extends AcceleratorLogic
{

	// Multiblock logic
	
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

	// Accelerator methods
	
	@Override
	public int getBeamLength()
	{
		return getMultiblock().getExteriorLengthX();
	}
	
	@Override
	public double getBeamRadius()
	{
		return QMDConfig.beamDiverterRadius;
	}
	
	public long getEnergyLoss()
	{
		return Equations.cornerEnergyLoss(getMultiblock().beams.get(0).getParticleStack(),getBeamRadius());
	}
	
	public long getMaxEnergy()
	{
		if(this.getMultiblock().beams.get(0).getParticleStack() != null)
		{
			Particle particle = this.getMultiblock().beams.get(0).getParticleStack().getParticle();

			return Equations.ringEnergyMaxEnergyFromDipole(getMultiblock().dipoleStrength,getBeamRadius(),particle.getCharge(),particle.getMass());
		}
		
		return 0;
	}
	
	public long getAcceleratorMaxEnergy(Particle particle)
	{
		if(particle != null)
		{
			return Equations.ringEnergyMaxEnergyFromDipole(getMultiblock().dipoleStrength,getBeamRadius(),particle.getCharge(),particle.getMass());
		}
		return 0;
	}
	
	// Multiblock validation
	
	@Override
	public boolean isMachineWhole()
	{
		Accelerator acc = getMultiblock();
		
		if (acc.getExteriorLengthX() != getThickness() || acc.getExteriorLengthY() != getThickness() || acc.getExteriorLengthZ() != getThickness())
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
	
	public static final List<Pair<Class<? extends IAcceleratorPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileAcceleratorSynchrotronPort.class,
					QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
			Pair.of(TileAcceleratorRFCavity.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_rf_cavity"),
			Pair.of(TileAcceleratorIonSource.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_source"));

	@Override
	public List<Pair<Class<? extends IAcceleratorPart>, String>> getPartBlacklist()
	{
		return PART_BLACKLIST;
	}
	
	// Accelerator formation
	
	@Override
	public void onAcceleratorFormed()
	{
		 Accelerator acc = getMultiblock();

		 if (!getWorld().isRemote)
		 {
			 resetBeams();
			
			 Set<BlockPos> postions = new HashSet<BlockPos>();
			 postions.add(acc.getMiddleCoord().toImmutable());
			 setBeamlineFunctional(postions);
			 formComponents();
		}
		 
		 refreshStats();
		 super.onAcceleratorFormed();
		 acc.cooling = (long) (2*(acc.rawHeating+acc.getMaxExternalHeating()));
	}
	
	// Accelerator Operation
	
	@Override
	public boolean onUpdateServer()
	{
		super.onUpdateServer();
		
		if (getMultiblock().isControllorOn)
		{
			produceBeam();
		}
		else
		{
			resetOutputBeam();
		}
		
		push();
		getMultiblock().sendMultiblockUpdatePacketToListeners();
		return true;
	}
	
	@Override
	protected void refreshBeams()
	{
		getMultiblock().beams.get(0).setParticleStack(null);
		pull();
	}
	
	@Override
	protected boolean shouldUseEnergy()
	{
		if (getMultiblock().beams.get(0).getParticleStack() != null)
		{
			return true;
		}

		return false;
	}
	
	// Beam port IO
	
	@Override
	protected void push()
	{
		if (getMultiblock().input != null && getMultiblock().input.getExternalFacing() != null)
		{
			for (TileAcceleratorBeamPort port :getMultiblock().getPartMap(TileAcceleratorBeamPort.class).values())
			{
				if(port.getExternalFacing() != null)
				{
					if(port.getIOType() == IOType.OUTPUT)
					{
						if(port.getPos().offset(port.getExternalFacing().getOpposite(), getThickness() - 1).equals(getMultiblock().input.getPos()))
						{
							TileEntity tile = getMultiblock().WORLD.getTileEntity(port.getPos().offset(port.getExternalFacing()));
							if (tile != null)
							{
								if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite()))
								{
									IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite());
									otherStorage.reciveParticle(port.getExternalFacing().getOpposite(), getMultiblock().beams.get(2).getParticleStack());
									
								}
							}
						}
						else
						{
							TileEntity tile = getMultiblock().WORLD.getTileEntity(port.getPos().offset(port.getExternalFacing()));
							if (tile != null)
							{
								if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite()))
								{
									IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getExternalFacing().getOpposite());
									otherStorage.reciveParticle(port.getExternalFacing().getOpposite(), getMultiblock().beams.get(1).getParticleStack());
									
								}
							}
						}
					}
				}
			}
		}

	}
	
	@Override
	protected void pull()
	{
		if (getMultiblock().input != null && getMultiblock().input.getExternalFacing() != null)
		{

			TileEntity tile = getMultiblock().WORLD
					.getTileEntity(getMultiblock().input.getPos().offset(getMultiblock().input.getExternalFacing()));
			if (tile != null)
			{

				if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,getMultiblock().input.getExternalFacing().getOpposite()))
				{
					IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,getMultiblock().input.getExternalFacing().getOpposite());
					ParticleStack stack = otherStorage.extractParticle(getMultiblock().input.getExternalFacing().getOpposite());

					if (stack != null)
					{
						getMultiblock().beams.get(0).setMaxEnergy(getAcceleratorMaxEnergy(stack.getParticle()));

						if (!getMultiblock().beams.get(0).reciveParticle(getMultiblock().input.getExternalFacing(),
								stack))
						{
							if (stack.getMeanEnergy() > getMultiblock().beams.get(0).getMaxEnergy())
							{
								getMultiblock().errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
							}
							else if (stack.getMeanEnergy() < getMultiblock().beams.get(0).getMinEnergy())
							{
								getMultiblock().errorCode = Accelerator.errorCode_InputParticleEnergyToLow;
							}
						}
					}
				}
			}
		}
	}
	
	// Recipe handling
	
	private void resetOutputBeam()
	{
		getMultiblock().beams.get(1).setParticleStack(null);
		getMultiblock().beams.get(2).setParticleStack(null);
	}

	private void produceBeam()
	{
		
		if(getMultiblock().beams.get(0).getParticleStack() != null)
		{
			ParticleStack stackIn = getMultiblock().beams.get(0).getParticleStack();
			getMultiblock().beams.get(1).setParticleStack(stackIn.copy());
			getMultiblock().beams.get(1).getParticleStack().setAmount(stackIn.getAmount()/2);
			
			getMultiblock().beams.get(2).setParticleStack(stackIn.copy());
			getMultiblock().beams.get(2).getParticleStack().setAmount(stackIn.getAmount()/2);
			
			
			if(stackIn.getMeanEnergy() <= getMaxEnergy())
			{
				ParticleStack particleOut = getMultiblock().beams.get(1).getParticleStack();
				ParticleStack particleStraightOut = getMultiblock().beams.get(2).getParticleStack();
				
				particleOut.addMeanEnergy(-Equations.cornerEnergyLoss(stackIn,getBeamRadius()));
				particleOut.addFocus(-Equations.focusLoss( getBeamLength(), stackIn));
				particleStraightOut.addFocus(-Equations.focusLoss(getBeamLength(), stackIn));
				
				if(particleOut.getFocus() <= 0)
				{
					particleOut = null;
					getMultiblock().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
				}
				if(particleStraightOut.getFocus() <= 0)
				{
					particleStraightOut = null;
					getMultiblock().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
				}
			}
		}
		else
		{
			resetOutputBeam();
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
	
	// Network
	@Override
	public BeamSplitterUpdatePacket getMultiblockUpdatePacket()
	{
		return new BeamSplitterUpdatePacket(getMultiblock().controller.getTilePos(),
				getMultiblock().isControllorOn, getMultiblock().cooling, getMultiblock().rawHeating,getMultiblock().currentHeating,getMultiblock().maxCoolantIn,getMultiblock().maxCoolantOut,getMultiblock().maxOperatingTemp,
				getMultiblock().requiredEnergy, getMultiblock().efficiency, getMultiblock().acceleratingVoltage,
				getMultiblock().RFCavityNumber, getMultiblock().quadrupoleNumber, getMultiblock().quadrupoleStrength, getMultiblock().dipoleNumber, getMultiblock().dipoleStrength, getMultiblock().errorCode,
				getMultiblock().heatBuffer, getMultiblock().energyStorage, getMultiblock().tanks, getMultiblock().beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof BeamSplitterUpdatePacket)
		{
			BeamSplitterUpdatePacket packet = (BeamSplitterUpdatePacket) message;
		}
	}
	
}
