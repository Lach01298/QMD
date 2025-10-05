package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
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

public class RingAcceleratorLogic extends AcceleratorLogic
{

	// Multiblock logic
	
	public RingAcceleratorLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		beam 0 = input particle
		beam 1 = output particle
		beam 2 = synchrotron output particle
		tank 0 = input coolant
		tank 1 = output coolant
		*/

	}

	@Override
	public String getID()
	{
		return "ring_accelerator";
	}


	// Accelerator methods
	
	@Override
	public int getBeamLength()
	{
		return 4*(multiblock.getInteriorLengthX()-2);
	}
	
	@Override
	public double getBeamRadius()
	{
		return (multiblock.getInteriorLengthX()-2)/2d;
	}
	
	public long getAcceleratorMaxEnergy(Particle particle)
	{
		if(particle != null && multiblock.acceleratingVoltage > 0)
		{
			return  Math.min(Equations.ringEnergyMaxEnergyFromDipole(multiblock.dipoleStrength, getBeamRadius(), particle.getCharge(), particle.getMass()), Equations.ringEnergyMaxEnergyFromRadiation(multiblock.acceleratingVoltage, getBeamRadius(), particle.getCharge(), particle.getMass()));
		}
		return 0;
	}
	
	// Multiblock validation
	
	@Override
	public boolean isMachineWhole()
	{
		Accelerator acc = multiblock;
		
		if (acc.getExteriorLengthY() != getThickness())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.wrong_height", null);
			return false;
		}

		
		if (acc.getExteriorLengthX() != acc.getExteriorLengthZ())
		{
			
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_square", null);
				return false;
		}
		
		if(acc.getExteriorLengthX() < QMDConfig.accelerator_ring_min_size)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.to_short", null);
			return false;
		}
		
		if(acc.getExteriorLengthX() > QMDConfig.accelerator_ring_max_size)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.to_long", null);
			return false;
		}
		
		
		// Beam
		for (BlockPos pos : getinteriorAxisPositions())
		{
			if (!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_beam", pos);
				return false;
			}
		}
		
		
		//Dipoles in conners check
		
		for (BlockPos pos : getinteriorAxisConners())
		{
			if (!acc.isValidDipole(pos, false))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole_in_conner", pos);
				return false;
			}
		}
		
		
		//beam ports
		
		int inputs =0;
		int outputs =0;
		
		for(TileAcceleratorBeamPort port :getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.getIOType() == IOType.INPUT)
			{
				inputs++;
			}
			
			if(port.getIOType() == IOType.OUTPUT)
			{
				outputs++;
			}
			
			if(port.getPos().getY() != acc.getMiddleY())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_inline_with_beam", port.getPos());
				return false;
			}
			
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
			if(!acc.isValidDipole(port.getPos().offset(port.getOutwardFacing().getOpposite(),2),false))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole", port.getPos().offset(port.getOutwardFacing().getOpposite(),2));
				return false;
			}
			
			
		}
		if(inputs != 1 || outputs != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_have_io", null);
			return false;
		}
		int synchrotronPorts =0;
		for(TileAcceleratorSynchrotronPort port :getPartMap(TileAcceleratorSynchrotronPort.class).values())
		{
			synchrotronPorts++;
			
			if(port.getPos().getY() != acc.getMiddleY())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_inline_with_beam", port.getPos());
				return false;
			}
			
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
			if(!acc.isValidDipole(port.getPos().offset(port.getOutwardFacing().getOpposite(),2),false))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole", port.getPos().offset(port.getOutwardFacing().getOpposite(),2));
				return false;
			}
		}
		if(synchrotronPorts > 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.to_many_synchrotron_ports", null);
			return false;
		}
		if(containsBlacklistedPart())
		{
			return false;
		}

		return super.isMachineWhole();
	}
	
	public Set<BlockPos> getinteriorAxisPositions()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Accelerator acc = multiblock;
		
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(false, false, false).add(1, acc.getInteriorLengthY() / 2, (getThickness() - 2) / 2),
				acc.getExtremeInteriorCoord(true, false, false).add(-1, acc.getInteriorLengthY() / 2, (getThickness()  - 2) / 2)))
		{
			postions.add(pos.toImmutable());
		}
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(false, false, true).add(1, acc.getInteriorLengthY() / 2, -(getThickness() - 2) / 2),
				acc.getExtremeInteriorCoord(true, false, true).add(-1, acc.getInteriorLengthY() / 2, -(getThickness() - 2) / 2)))
		{
			postions.add(pos.toImmutable());
		}
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(false, false, false).add((getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, 1),
				acc.getExtremeInteriorCoord(false, false, true).add((getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, -1)))
		{
			postions.add(pos.toImmutable());
		}
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(true, false, false).add(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, 1),
				acc.getExtremeInteriorCoord(true, false, true).add(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, -1)))
		{
			postions.add(pos.toImmutable());
		}

		return postions;
	}
	
	public Set<BlockPos> getinteriorAxisConners()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Accelerator acc = multiblock;
		
		postions.add(acc.getExtremeInteriorCoord(false, false, false).add(1, acc.getInteriorLengthY() / 2, (getThickness() - 2) / 2));
		postions.add(acc.getExtremeInteriorCoord(false, false, true).add(1, acc.getInteriorLengthY() / 2, -(getThickness() - 2) / 2));
		postions.add(acc.getExtremeInteriorCoord(true, false, false).add(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, 1));
		postions.add(acc.getExtremeInteriorCoord(true, false, true).add(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, -1));
		
		return postions;
	}
	
	public static final List<Pair<Class<? extends IAcceleratorPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileAcceleratorIonSource.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_source"),
			Pair.of(TileAcceleratorIonCollector.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_collectors"),
			Pair.of(TileAcceleratorPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_ports"));

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
			acc.beams.get(0).setMinEnergy(QMDConfig.minimium_accelerator_ring_input_particle_energy);

			setBeamlineFunctional(getinteriorAxisPositions());
			formComponents();

			for(TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values())
			{
				if(port.getIOType() == IOType.INPUT)
				{
					port.setIONumber(0);
				}
				if(port.getIOType() == IOType.OUTPUT)
				{
					port.setIONumber(1);
				}
			}

		}

		refreshStats();
		super.onAcceleratorFormed();
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
	protected void push()
	{
		for(TileAcceleratorSynchrotronPort port :multiblock.getPartMap(TileAcceleratorSynchrotronPort.class).values())
		{
			if(port != null && port.getOutwardFacing() != null)
			{
				TileEntity tile = multiblock.WORLD.getTileEntity(port.getPos().offset(port.getOutwardFacing()));
				if (tile != null)
				{
					if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getOutwardFacing().getOpposite()))
					{
						IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, port.getOutwardFacing().getOpposite());
						otherStorage.reciveParticle(port.getOutwardFacing().getOpposite(), multiblock.beams.get(2).getParticleStack());
					}
				}
			}
		}
		super.push();
	}
	
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
								multiblock.beams.get(0).setMaxEnergy(getAcceleratorMaxEnergy(stack.getParticle()));

								if (!multiblock.beams.get(0).reciveParticle(face, stack))
								{
									if (stack.getMeanEnergy() > multiblock.beams.get(0).getMaxEnergy())
									{
										multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
									}
									else if (stack.getMeanEnergy() < multiblock.beams.get(0).getMinEnergy())
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
			ParticleStack beam = multiblock.beams.get(0).getParticleStack();
			multiblock.beams.get(1).setParticleStack(beam.copy());
			
			Particle particle = beam.getParticle();
	
			ParticleStack beamOut = multiblock.beams.get(1).getParticleStack();
			
			
			if(multiblock.computerControlled)
			{
				beamOut.setMeanEnergy((long)(getAcceleratorMaxEnergy(particle)*(multiblock.energyPercentage/100d)));
			}
			else
			{
				beamOut.setMeanEnergy((long)(getAcceleratorMaxEnergy(particle)*(getRedstoneLevel()/15d)));
			}
			beamOut.addFocus(Equations.focusGain(multiblock.quadrupoleStrength, beamOut) - Equations.focusLoss(getBeamLength(), beamOut));
			
			if(beamOut.getFocus() <= 0)
			{
				multiblock.errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
			}
			
			long synchrotronEnergy = Equations.synchrotronRadiationEnergy(getBeamRadius(),beamOut);
			multiblock.beams.get(2).setParticleStack(new ParticleStack(Particles.photon,beamOut.getAmount(),synchrotronEnergy,beamOut.getFocus()));
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
	public RingAcceleratorUpdatePacket getMultiblockUpdatePacket()
	{
		return new RingAcceleratorUpdatePacket(multiblock.controller.getTilePos(),
				multiblock.isControllorOn, multiblock.cooling, multiblock.rawHeating,multiblock.currentHeating,multiblock.maxCoolantIn,multiblock.maxCoolantOut,multiblock.maxOperatingTemp,
				multiblock.requiredEnergy, multiblock.efficiency, multiblock.acceleratingVoltage,
				multiblock.RFCavityNumber, multiblock.quadrupoleNumber, multiblock.quadrupoleStrength, multiblock.dipoleNumber, multiblock.dipoleStrength, multiblock.errorCode,
				multiblock.heatBuffer, multiblock.energyStorage, multiblock.tanks, multiblock.beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof RingAcceleratorUpdatePacket)
		{
			RingAcceleratorUpdatePacket packet = (RingAcceleratorUpdatePacket) message;
		}
	}

}
