package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.BeamDiverterUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Equations;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BeamDiverterLogic extends AcceleratorLogic
{

	// Multiblock logic
	
	public BeamDiverterLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		/*
		beam 0 = input particle
		beam 1 = output particle
		beam 2 = output  particle straight
		tank 0 = input coolant
		tank 1 = output coolant
		*/
	}

	@Override
	public String getID()
	{
		return "beam_diverter";
	}

	// Accelerator methods
	
	@Override
	public int getBeamLength()
	{
		return multiblock.getExteriorLengthX();
	}
	
	@Override
	public double getBeamRadius()
	{
		return QMDConfig.beamDiverterRadius;
	}
	
	public long getEnergyLoss()
	{
		return Equations.cornerEnergyLoss(multiblock.beams.get(0).getParticleStack(),getBeamRadius());
	}
	
	public long getMaxEnergy()
	{
		if(this.multiblock.beams.get(0).getParticleStack() != null)
		{
			Particle particle = this.multiblock.beams.get(0).getParticleStack().getParticle();

			return Equations.ringEnergyMaxEnergyFromDipole(multiblock.dipoleStrength,getBeamRadius(),particle.getCharge(),particle.getMass());
		}
		
		return 0;
	}
	
	public long getAcceleratorMaxEnergy(Particle particle)
	{
		
		if(particle != null)
		{
			
			return Equations.ringEnergyMaxEnergyFromDipole(multiblock.dipoleStrength,getBeamRadius(),particle.getCharge(),particle.getMass());
		}
		
		return 0;
	}
	
	// Multiblock validation
	
	@Override
	public boolean isMachineWhole()
	{
		Accelerator acc = multiblock;
		
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
			
			
			
			port.recalculateOutwardsDirection(acc.getMinimumCoord(), acc.getMaximumCoord());
			if(port.getOutwardFacing() == null)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.something_is_wrong", port.getPos());
				return false;
			}
			
			
			if(!(acc.WORLD.getTileEntity(port.getPos().offset(port.getOutwardFacing().getOpposite())) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.beam_port_must_connect", port.getPos().offset(port.getOutwardFacing().getOpposite()));
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
		
		
		if(inputs != 1 || outputs != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_have_io", null);
			return false;
		}
		if(containsBlacklistedPart())
		{
			return false;
		}

		return super.isMachineWhole();
	}

	public static final List<Pair<Class<? extends IAcceleratorPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileAcceleratorSynchrotronPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
			Pair.of(TileAcceleratorRFCavity.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_rf_cavity"),
			Pair.of(TileAcceleratorIonSource.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_source"),
			Pair.of(TileAcceleratorIonCollector.class,QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_collectors"),
			Pair.of(TileAcceleratorPort.class,QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_ports"));

	@Override
	public List<Pair<Class<? extends IAcceleratorPart>, String>> getPartBlacklist()
	{
		return PART_BLACKLIST;
	}
	
	// Accelerator formation
	
	@Override
	public void onAcceleratorFormed()
	{
		 Accelerator acc = multiblock;

		 if (!getWorld().isRemote)
		 {
			 resetBeams();
			
			 Set<BlockPos> positions = new HashSet<BlockPos>();
			 positions.add(acc.getMiddleCoord().toImmutable());
			 setBeamlineFunctional(positions);
			 formComponents();

			 for(TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values())
			 {
				 if(port.getIOType() == IOType.INPUT)
				 {
					 port.setIONumber(0);
				 }
				 if(port.getIOType() == IOType.OUTPUT)
				 {
					 if(multiblock.WORLD.getTileEntity(port.getPos().offset(port.getOutwardFacing().getOpposite(),getThickness() - 1)) instanceof TileAcceleratorBeamPort)
					 {
						 TileAcceleratorBeamPort oppositePort = (TileAcceleratorBeamPort) multiblock.WORLD.getTileEntity(port.getPos().offset(port.getOutwardFacing().getOpposite(), getThickness() - 1));
						 if(oppositePort.getIOType() == IOType.INPUT)
						 {
							 port.setIONumber(2);
						 }
						 else
						 {
							 port.setIONumber(1);
						 }
					 }
					 else
					 {
						 port.setIONumber(1);
					 }
				 }
			 }
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
		
		if (multiblock.isControllorOn)
		{
			produceBeam();
		}
		else
		{
			resetOutputBeam();
		}
		
		push();
		multiblock.sendMultiblockUpdatePacketToListeners();
		return true;
	}
	
	@Override
	protected void refreshBeams()
	{
		multiblock.beams.get(0).setParticleStack(null);
		pull();
	}
	
	@Override
	protected boolean shouldUseEnergy()
	{
		if (multiblock.beams.get(0).getParticleStack() != null)
		{
			return true;
		}

		return false;
	}
	
	// Beam port IO

	@Override
	protected void pull()
	{
		for(TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.getIOType() == IOType.INPUT)
			{
				if (port.getOutwardFacing() != null)
				{
					EnumFacing face = port.getOutwardFacing();
					TileEntity tile = port.getWorld().getTileEntity(port.getPos().offset(face));
					if(tile != null)
					{
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite()))
						{
							IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,face.getOpposite());
							ParticleStack stack = otherStorage.extractParticle(face.getOpposite());

							if (stack != null)
							{
								if(multiblock.WORLD.getTileEntity(port.getPos().offset(face.getOpposite(),getThickness() - 1)) instanceof TileAcceleratorBeamPort)
								{
									TileAcceleratorBeamPort oppositePort = (TileAcceleratorBeamPort) multiblock.WORLD.getTileEntity(port.getPos().offset(face.getOpposite(), getThickness() - 1));
									if(oppositePort.getIOType() == IOType.OUTPUT)
									{
										multiblock.beams.get(port.getIONumber()).setMaxEnergy(Long.MAX_VALUE);  // for neutral beam pass through
									}
								}
								else
								{
									multiblock.beams.get(port.getIONumber()).setMaxEnergy(getAcceleratorMaxEnergy(stack.getParticle()));
								}

								if (!multiblock.beams.get(port.getIONumber()).reciveParticle(face, stack))
								{
									if (stack.getMeanEnergy() > multiblock.beams.get(port.getIONumber()).getMaxEnergy())
									{
										multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
									}
									else if (stack.getMeanEnergy() < multiblock.beams.get(port.getIONumber()).getMinEnergy())
									{
										multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToLow;
									}
								}
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
		multiblock.beams.get(1).setParticleStack(null);
		multiblock.beams.get(2).setParticleStack(null);
	}

	private void produceBeam()
	{
		
		if(this.multiblock.beams.get(0).getParticleStack() != null)
		{
			ParticleStack stackIn = multiblock.beams.get(0).getParticleStack();
			multiblock.beams.get(1).setParticleStack(stackIn.copy());
			multiblock.beams.get(2).setParticleStack(stackIn.copy());
			
			if(stackIn.getMeanEnergy() <= getMaxEnergy())
			{
				ParticleStack particleOut = multiblock.beams.get(1).getParticleStack();
				ParticleStack particleStraightOut = multiblock.beams.get(2).getParticleStack();
				
				particleOut.addMeanEnergy(-Equations.cornerEnergyLoss(stackIn,getBeamRadius()));
				double focusLoss = -Equations.focusLoss(getBeamLength(), stackIn);
				
				particleOut.addFocus(focusLoss);
				particleStraightOut.addFocus(focusLoss);
				
				if(particleOut.getFocus() <= 0)
				{
					particleOut = null;
					particleStraightOut = null;
					multiblock.errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
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
	public BeamDiverterUpdatePacket getMultiblockUpdatePacket()
	{
		return new BeamDiverterUpdatePacket(multiblock.controller.getTilePos(),
				multiblock.isControllorOn, multiblock.cooling, multiblock.rawHeating,multiblock.currentHeating,multiblock.maxCoolantIn,multiblock.maxCoolantOut,multiblock.maxOperatingTemp,
				multiblock.requiredEnergy, multiblock.efficiency, multiblock.acceleratingVoltage,
				multiblock.RFCavityNumber, multiblock.quadrupoleNumber, multiblock.quadrupoleStrength, multiblock.dipoleNumber, multiblock.dipoleStrength, multiblock.errorCode,
				multiblock.heatBuffer, multiblock.energyStorage, multiblock.tanks, multiblock.beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof BeamDiverterUpdatePacket)
		{
			BeamDiverterUpdatePacket packet = (BeamDiverterUpdatePacket) message;
		}
	}
	
}
