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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class DeceleratorLogic extends AcceleratorLogic
{
	
	// Multiblock logic
	
	public DeceleratorLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		beam 0 = input particle
		beam 1 = output particle
		tank 0 = input coolant
		tank 1 = output coolant
		*/

	}

	@Override
	public String getID()
	{
		return "decelerator";
	}

	// Accelerator methods
	
	@Override
	public int getBeamLength()
	{
		return 4*(getMultiblock().getInteriorLengthX()-2);
	}
	
	@Override
	public double getBeamRadius()
	{
		return (getMultiblock().getInteriorLengthX()-2)/2d;
	}
	
	public long getAcceleratorMaxEnergy(Particle particle)
	{
		if(particle != null && getMultiblock().acceleratingVoltage > 0)
		{
			return  Equations.ringEnergyMaxEnergyFromDipole(getMultiblock().dipoleStrength, getBeamRadius(), particle.getCharge(), particle.getMass());
		}
		return 0;
	}
	
	// Multiblock Validation
	
	@Override
	public boolean isMachineWhole()
	{
		Accelerator acc = getMultiblock();
		
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
			
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.something_is_wrong", port.getPos());
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
		if(containsBlacklistedPart())
		{
			return false;
		}
		
		return super.isMachineWhole();
	}
	
	public Set<BlockPos> getinteriorAxisPositions()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Accelerator acc = getMultiblock();
		
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
		Accelerator acc = getMultiblock();
		
		postions.add(acc.getExtremeInteriorCoord(false, false, false).add(1, acc.getInteriorLengthY() / 2, (getThickness() - 2) / 2));
		postions.add(acc.getExtremeInteriorCoord(false, false, true).add(1, acc.getInteriorLengthY() / 2, -(getThickness() - 2) / 2));
		postions.add(acc.getExtremeInteriorCoord(true, false, false).add(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, 1));
		postions.add(acc.getExtremeInteriorCoord(true, false, true).add(-(getThickness() - 2) / 2, acc.getInteriorLengthY() / 2, -1));
		
		return postions;
	}
	
	public static final List<Pair<Class<? extends IAcceleratorPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileAcceleratorIonSource.class, QMD.MOD_ID + ".multiblock_validation.accelerator.source"),
			Pair.of(TileAcceleratorSynchrotronPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
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
		Accelerator acc = getMultiblock();

		if (!getWorld().isRemote)
		{
			resetBeams();

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
								getMultiblock().beams.get(0).setMaxEnergy(getAcceleratorMaxEnergy(stack.getParticle()));

								if (!getMultiblock().beams.get(0).reciveParticle(face, stack))
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
		}
	}
	
	// Recipe handling
	
	private void resetOutputBeam()
	{
		getMultiblock().beams.get(1).setParticleStack(null);
	}

	private void produceBeam()
	{
		if(this.getMultiblock().beams.get(0).getParticleStack() != null)
		{
			ParticleStack beam = getMultiblock().beams.get(0).getParticleStack();
			getMultiblock().beams.get(1).setParticleStack(beam.copy());
			Particle particle = beam.getParticle();
	
			ParticleStack beamOut = getMultiblock().beams.get(1).getParticleStack();
			
			double fraction = 1;
			if(getMultiblock().computerControlled)
			{
				fraction = 1 - (getMultiblock().energyPercentage/100d);
			}
			else
			{
				fraction = 1-(getRedstoneLevel()/15d);
			}
			
			long energyTarget = (long)(getAcceleratorMaxEnergy(particle)* fraction);
			
			if(energyTarget > beam.getMeanEnergy())
			{
				beamOut.setMeanEnergy(beam.getMeanEnergy());
			}
			else
			{
				beamOut.setMeanEnergy(energyTarget);
			}
			
			
			beamOut.addFocus(Equations.focusGain(getMultiblock().quadrupoleStrength, beamOut));
			
			if(beamOut.getFocus() <= 0)
			{
				getMultiblock().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
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
	public DeceleratorUpdatePacket getMultiblockUpdatePacket()
	{
		return new DeceleratorUpdatePacket(getMultiblock().controller.getTilePos(),
				getMultiblock().isControllorOn, getMultiblock().cooling, getMultiblock().rawHeating,getMultiblock().currentHeating,getMultiblock().maxCoolantIn,getMultiblock().maxCoolantOut,getMultiblock().maxOperatingTemp,
				getMultiblock().requiredEnergy, getMultiblock().efficiency, getMultiblock().acceleratingVoltage,
				getMultiblock().RFCavityNumber, getMultiblock().quadrupoleNumber, getMultiblock().quadrupoleStrength, getMultiblock().dipoleNumber, getMultiblock().dipoleStrength, getMultiblock().errorCode,
				getMultiblock().heatBuffer, getMultiblock().energyStorage, getMultiblock().tanks, getMultiblock().beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof DeceleratorUpdatePacket)
		{
			DeceleratorUpdatePacket packet = (DeceleratorUpdatePacket) message;
		}
	}

}
