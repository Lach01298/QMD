package lach_01298.qmd.multiblock.accelerator;

import java.util.HashSet;
import java.util.Set;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.ToroidalMultiblock;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.DipoleMagnet;
import lach_01298.qmd.multiblock.accelerator.QuadrupoleMagnet;
import lach_01298.qmd.multiblock.accelerator.RFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorInlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorOutlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.Global;
import nc.multiblock.Multiblock;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.container.ContainerSaltFissionController;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.salt.tile.TileSaltFissionHeater;
import nc.multiblock.fission.salt.tile.TileSaltFissionVessel;
import nc.multiblock.fission.tile.IFissionController;
import nc.multiblock.network.FissionUpdatePacket;
import nc.multiblock.network.SolidFissionUpdatePacket;
import nc.util.NCMath;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class DeceleratorLogic extends AcceleratorLogic
{

	public int dipoleNumber =0;
	public double dipoleStrength =0;
	
	
	
	protected final Long2ObjectMap<DipoleMagnet> dipoleMap = new Long2ObjectOpenHashMap<>();

	
	public DeceleratorLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		getAccelerator().beams.add(new ParticleStorageAccelerator());
		getAccelerator().beams.get(0).setMinEnergy(QMDConfig.minimium_accelerator_ring_input_particle_energy);
	}

	@Override
	public String getID()
	{
		return "decelerator";
	}

	// Multiblock Validation
	
	
	
	public boolean isMachineWhole(Multiblock multiblock) 
	{
		Accelerator acc = getAccelerator();
		
		if (acc.getExteriorLengthY() != thickness)
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
			if (!acc.isValidDipole(pos, true))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole_in_conner", pos);
				return false;
			}
		}
		

		// inlet

		if (getPartMap(TileAcceleratorInlet.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.no_inlet", null);
			return false;
		}

		// outlet
		if (getPartMap(TileAcceleratorOutlet.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.no_outlet", null);
			return false;
		}

		// Energy Ports
		if (getPartMap(TileAcceleratorEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.need_energy_ports", null);
			return false;
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
			
			port.recalculateExternalDirection(acc.getMinimumCoord(), acc.getMaximumCoord());
			if(port.getExternalFacing() == null)
			{
			
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.something_is_wrong", port.getPos());
				return false;
			}
			
			
			
			if(!(acc.WORLD.getTileEntity(port.getPos().offset(port.getExternalFacing().getOpposite())) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.beam_port_must_connect", port.getPos().offset(port.getExternalFacing().getOpposite()));
				return false;
			}
			if(!acc.isValidDipole(port.getPos().offset(port.getExternalFacing().getOpposite(),2),false))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole", port.getPos().offset(port.getExternalFacing().getOpposite(),2));
				return false;
			}
			
			
		}
		if(inputs != 1 || outputs != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_have_io", null);
			return false;
		}	
		
		

		return true;
	}
	
	public Set<BlockPos> getinteriorAxisPositions()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Accelerator acc = getAccelerator();
		
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(false, false, false).add(1, acc.getInteriorLengthY() / 2, (thickness - 2) / 2),
				acc.getExtremeInteriorCoord(true, false, false).add(-1, acc.getInteriorLengthY() / 2, (thickness  - 2) / 2)))
		{
			postions.add(pos.toImmutable());
		}
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(false, false, true).add(1, acc.getInteriorLengthY() / 2, -(thickness - 2) / 2),
				acc.getExtremeInteriorCoord(true, false, true).add(-1, acc.getInteriorLengthY() / 2, -(thickness - 2) / 2)))
		{
			postions.add(pos.toImmutable());
		}
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(false, false, false).add((thickness - 2) / 2, acc.getInteriorLengthY() / 2, 1),
				acc.getExtremeInteriorCoord(false, false, true).add((thickness - 2) / 2, acc.getInteriorLengthY() / 2, -1)))
		{
			postions.add(pos.toImmutable());
		}
		for (BlockPos pos : BlockPos.getAllInBoxMutable(
				acc.getExtremeInteriorCoord(true, false, false).add(-(thickness - 2) / 2, acc.getInteriorLengthY() / 2, 1),
				acc.getExtremeInteriorCoord(true, false, true).add(-(thickness - 2) / 2, acc.getInteriorLengthY() / 2, -1)))
		{
			postions.add(pos.toImmutable());
		}

		return postions;
	}
	
	public Set<BlockPos> getinteriorAxisConners()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Accelerator acc = getAccelerator();
		
		postions.add(acc.getExtremeInteriorCoord(false, false, false).add(1, acc.getInteriorLengthY() / 2, (thickness - 2) / 2));
		postions.add(acc.getExtremeInteriorCoord(false, false, true).add(1, acc.getInteriorLengthY() / 2, -(thickness - 2) / 2));
		postions.add(acc.getExtremeInteriorCoord(true, false, false).add(-(thickness - 2) / 2, acc.getInteriorLengthY() / 2, 1));
		postions.add(acc.getExtremeInteriorCoord(true, false, true).add(-(thickness - 2) / 2, acc.getInteriorLengthY() / 2, -1));
				
		return postions;
	}
	
	
	
	
	// Multiblock Methods
	
	@Override
	public void onAcceleratorFormed()
	{
		 Accelerator acc = getAccelerator();

		 if (!getWorld().isRemote)
		{

			// beam
			for (BlockPos pos : getinteriorAxisPositions())
			{
				if (acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam)
				{

					TileAcceleratorBeam beam = (TileAcceleratorBeam) getWorld().getTileEntity(pos);
					beam.setFunctional(true);
				}
			}

			// ports
			for (TileAcceleratorOutlet port : acc.getPartMap(TileAcceleratorOutlet.class).values())
			{

			}

			// beam
			for (TileAcceleratorBeam beam :acc.getPartMap(TileAcceleratorBeam.class).values())
			{
				if(beam.isFunctional())
				{
					if (acc.isValidRFCavity(beam.getPos(), Axis.X))
					{
						acc.getRFCavityMap().put(beam.getPos().toLong(), new RFCavity(acc, beam.getPos(), Axis.X));
					}
					else if (acc.isValidRFCavity(beam.getPos(), Axis.Z))
					{
						acc.getRFCavityMap().put(beam.getPos().toLong(), new RFCavity(acc, beam.getPos(), Axis.Z));
					}
					else if (acc.isValidQuadrupole(beam.getPos(), Axis.X))
					{
						acc.getQuadrupoleMap().put(beam.getPos().toLong(), new QuadrupoleMagnet(acc, beam.getPos(), Axis.X));
					}
					else if (acc.isValidQuadrupole(beam.getPos(), Axis.Z))
					{
						acc.getQuadrupoleMap().put(beam.getPos().toLong(), new QuadrupoleMagnet(acc, beam.getPos(), Axis.Z));
					}
					else if (acc.isValidDipole(beam.getPos(), false))
					{
						dipoleMap.put(beam.getPos().toLong(), new DipoleMagnet(acc, beam.getPos()));
					}
					else if (acc.isValidDipole(beam.getPos(), true))
					{
						dipoleMap.put(beam.getPos().toLong(), new DipoleMagnet(acc, beam.getPos()));
					}
				}
			}

			getAccelerator().RFCavityNumber = acc.getRFCavityMap().size();
			getAccelerator().quadrupoleNumber = acc.getQuadrupoleMap().size();
			dipoleNumber = dipoleMap.size();

		
			for (RFCavity cavity : acc.getRFCavityMap().values())
			{
				for (IAcceleratorComponent componet : cavity.getComponents().values())
				{
					componet.setFunctional(true);
				}

			}
			
			
			for (QuadrupoleMagnet quad : acc.getQuadrupoleMap().values())
			{
				for (IAcceleratorComponent componet : quad.getComponents().values())
				{
					componet.setFunctional(true);
				}

			}

			for (DipoleMagnet dipole : dipoleMap.values())
			{
				for (IAcceleratorComponent componet : dipole.getComponents().values())
				{
					componet.setFunctional(true);
				}

			}
		
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
		}
 
		 refreshStats();
		 super.onAcceleratorFormed();
	}
	
	public void onMachineDisassembled()
	{
		 Accelerator acc = getAccelerator();
		 
		 for (RFCavity cavity : acc.getRFCavityMap().values())
		{
			for (IAcceleratorComponent componet : cavity.getComponents().values())
			{
				componet.setFunctional(false);
			}

		}
		
		
		for (QuadrupoleMagnet quad : acc.getQuadrupoleMap().values())
		{
			for (IAcceleratorComponent componet : quad.getComponents().values())
			{
				componet.setFunctional(false);
			}

		}
		
		for (DipoleMagnet dipole : dipoleMap.values())
		{
			for (IAcceleratorComponent componet : dipole.getComponents().values())
			{
				componet.setFunctional(false);
			}

		}
		
		
		acc.getRFCavityMap().clear();
		acc.getQuadrupoleMap().clear();
		dipoleMap.clear();
		
		for (TileAcceleratorBeam beam :acc.getPartMap(TileAcceleratorBeam.class).values())
		{
			beam.setFunctional(false);
		}
		
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

	
	private void refreshStats()
	{
		dipoleStrength = 0;
		int energy = 0;
		int heat = 0;
		int parts= 0;
		double efficiency =0;
		double quadStrength =0;
		double voltage = 0;
		for(TileAcceleratorMagnet magnet :getAccelerator().getPartMap(TileAcceleratorMagnet.class).values())
		{
			heat += magnet.heat;
			energy += magnet.basePower;
			parts++;
			efficiency += magnet.efficiency;
		}
		for(TileAcceleratorRFCavity cavity :getAccelerator().getPartMap(TileAcceleratorRFCavity.class).values())
		{
			heat += cavity.heat;
			energy += cavity.basePower;
			parts++;
			efficiency += cavity.efficiency;
			
		}
		
		for (QuadrupoleMagnet quad : getAccelerator().getQuadrupoleMap().values())
		{
			for (IAcceleratorComponent componet : quad.getComponents().values())
			{
				if(componet instanceof TileAcceleratorMagnet)
				{
					TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) componet;
					quadStrength += magnet.strength/4;
				}
			}
		}
		
		for (RFCavity cavity : getAccelerator().getRFCavityMap().values())
		{
			for (IAcceleratorComponent componet : cavity.getComponents().values())
			{
				if(componet instanceof TileAcceleratorRFCavity)
				{
					TileAcceleratorRFCavity cav = (TileAcceleratorRFCavity) componet;
					voltage += cav.voltage/8d;
				}
			}
		}
		
		for (DipoleMagnet dipole : dipoleMap.values())
		{
			for (IAcceleratorComponent componet : dipole.getComponents().values())
			{
				if(componet instanceof TileAcceleratorMagnet)
				{
					TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) componet;
					dipoleStrength += magnet.strength/2;
				}
			}
		}
		
		efficiency /= parts;
		getAccelerator().requiredEnergy =  (int) (energy/efficiency);
		getAccelerator().rawHeating = heat;
		getAccelerator().quadrupoleStrength = quadStrength;
		getAccelerator().efficiency = efficiency;
		getAccelerator().acceleratingVoltage=(int) voltage;
		
	}

	
	// Recipe Stuff
	
	private void resetBeam()
	{
		getAccelerator().beams.get(1).setParticleStack(null);
	}


	private void produceBeam()
	{
		if(this.getAccelerator().beams.get(0).getParticleStack() != null)
		{
			getAccelerator().beams.get(1).setParticleStack(this.getAccelerator().beams.get(0).getParticleStack());
			ParticleStack particleIn = getAccelerator().beams.get(0).getParticleStack();
			Particle particle = this.getAccelerator().beams.get(0).getParticleStack().getParticle();
			
			long maxEnergyFromFeild = 0;
			if(getAccelerator().acceleratingVoltage > 0)
			{
				maxEnergyFromFeild = (long) (Math.pow(particle.getCharge()*dipoleStrength*getRadius(),2)/(2*particle.getMass())*1000000);
			}
			
			
			long maxEnergyFromRadiation =  (long)(particle.getMass()*Math.pow((300*getAccelerator().acceleratingVoltage/1000d*getRadius())/Math.abs(particle.getCharge()), 1/4d)*1000000);
				
				
			ParticleStack particleOut = getAccelerator().beams.get(1).getParticleStack();
			
			
			if(maxEnergyFromRadiation >= maxEnergyFromFeild)
			{
				particleOut.setMeanEnergy((long)(maxEnergyFromFeild*(getWorld().getRedstonePowerFromNeighbors(getAccelerator().controller.getTilePos())/15d)));
			}
			else
			{
				particleOut.setMeanEnergy((long)(maxEnergyFromRadiation*(getWorld().getRedstonePowerFromNeighbors(getAccelerator().controller.getTilePos())/15d)));
			}
			
			particleOut.addFocus((int) (particleIn.getAmount()*(getAccelerator().quadrupoleStrength))-getLength()*QMDConfig.beamAttenuationRate);
			if(particleOut.getFocus() <= 0)
			{
				particleOut = null;
				getAccelerator().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
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
		if (getAccelerator().input != null)
		{
			for (EnumFacing face : EnumFacing.VALUES)
			{
				TileEntity tile = getAccelerator().WORLD.getTileEntity(getAccelerator().input.getPos().offset(face));
				if (tile != null)
				{

					if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,
							face.getOpposite()))
					{
						IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite());
						ParticleStack stack = otherStorage.extractParticle(face.getOpposite());
						
						if(stack != null)
						{
							Particle particle = stack.getParticle();
							int maxEnergyFromFeild = 0;
							if(getAccelerator().acceleratingVoltage > 0)
							{
								maxEnergyFromFeild = (int) (Math.pow(particle.getCharge()*dipoleStrength*getRadius(),2)/(2*particle.getMass())*1000000);
							}
							int maxEnergyFromRadiation =  (int)(particle.getMass()*Math.pow((300*getAccelerator().acceleratingVoltage*getRadius())/Math.abs(particle.getCharge()), 1/4d)*1000000);
						
							if(maxEnergyFromRadiation >= maxEnergyFromFeild)
							{
								getAccelerator().beams.get(0).setMaxEnergy(maxEnergyFromFeild);
							}
							else
							{
								getAccelerator().beams.get(0).setMaxEnergy(maxEnergyFromRadiation);
							}
							
						}

						if (!getAccelerator().beams.get(0).reciveParticle(face, stack))
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
	public RingAcceleratorUpdatePacket getUpdatePacket()
	{
		return null;
	}
	
	@Override
	public void onPacket(AcceleratorUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof RingAcceleratorUpdatePacket)
		{
			RingAcceleratorUpdatePacket packet = (RingAcceleratorUpdatePacket) message;
			dipoleNumber = packet.dipoleNumber;
			dipoleStrength = packet.dipoleStrength;
			getAccelerator().beams = packet.beams;

		}
	}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		

		logicTag.setInteger("dipoleNumber", dipoleNumber);
		logicTag.setDouble("dipoleStrength", dipoleStrength);
	}
	
	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);

		dipoleNumber = logicTag.getInteger("dipoleNumber");
		dipoleStrength = logicTag.getDouble("dipoleStrength");
	}
	
	
	
	
	public double getRadius()
	{
		return (getAccelerator().getInteriorLengthX()-2)/2d;
	}
	
	public int getLength()
	{
		return 4*getAccelerator().getInteriorLengthX()-12;
	}
	
	
	
	@Override
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) 
	{
		return new ContainerRingAcceleratorController(player, getAccelerator().controller);
	}
	
	
}
