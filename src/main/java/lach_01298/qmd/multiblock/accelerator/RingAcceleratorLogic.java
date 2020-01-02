package lach_01298.qmd.multiblock.accelerator;

import java.util.HashSet;
import java.util.Set;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.ToroidalMultiblock;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.DipoleMagnet;
import lach_01298.qmd.multiblock.accelerator.QuadrupoleMagnet;
import lach_01298.qmd.multiblock.accelerator.RFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorInlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorOutlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import lach_01298.qmd.particle.ParticleBeam;
import lach_01298.qmd.recipe.IParticleIngredient;
import nc.Global;
import nc.multiblock.Multiblock;
import nc.multiblock.TileBeefBase.SyncReason;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class RingAcceleratorLogic extends AcceleratorLogic
{

	public int dipoleNumber =0;
	public double dipoleStrength =0;
	
	protected final Long2ObjectMap<DipoleMagnet> dipoleMap = new Long2ObjectOpenHashMap<>();

	
	public RingAcceleratorLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
	}


	// Multiblock Validation
	
	
	
	public boolean isMachineWhole(Multiblock multiblock) 
	{
		Accelerator acc = getAccelerator();
		
		if (acc.getExteriorLengthY() != thickness)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.wrong_height, ", null);
			return false;
		}

		
		if (acc.getExteriorLengthX() != acc.getExteriorLengthZ())
		{
			
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_square, ", null);
				return false;
		}
		
		if(acc.getExteriorLengthX() < QMDConfig.accelerator_ring_min_size)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.to_short, ", null);
			return false;
		}
		
		if(acc.getExteriorLengthX() > QMDConfig.accelerator_ring_max_size)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.to_long, ", null);
			return false;
		}
		
		
		// Beam
		for (BlockPos pos : getinteriorAxisPositions())
		{
			if (!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_beam, ", pos);
				return false;
			}
		}
		
		
		//Dipoles in conners check
		
		for (BlockPos pos : getinteriorAxisConners())
		{
			if (!acc.isValidDipole(pos, true))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_dipole_in_conner, ", pos);
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
		return super.onUpdateServer();
	}

	
	
	private void produceBeam()
	{
//		IParticleIngredient particleIngredient = recipeInfo.getRecipe().particleProducts().get(0);
//		ParticleBeam beam = particleIngredient.getStack();
//		getAccelerator().beam.setParticle(beam.getParticle());
//		
//	
//		int maxEnergyFromRadiation = (int) ((3* getAccelerator().acceleratingVoltage * Math.pow(beam.getParticle().getMass(),4)*(getMaximumInteriorLength()-2)/2) / beam.getParticle().getCharge());
//		int maxEnergyFromMagnet = (int) Math.sqrt(Math.pow(beam.getParticle().getCharge()*dipoleStrength*((getMaximumInteriorLength()-2)/2),2) +Math.pow(beam.getParticle().getMass(),2));
//		if(maxEnergyFromRadiation < maxEnergyFromMagnet)
//		{
//			getAccelerator().beam.setMeanEnergy(maxEnergyFromRadiation*1000);
//		}
//		else
//		{
//			getAccelerator().beam.setMeanEnergy(maxEnergyFromMagnet*1000);
//		}
//		
//		
//		getAccelerator().beam.setLuminosity((int) (beam.getLuminosity()*getAccelerator().quadrupoleStrength));
		
	}
	
	
	
	
	
	private void refreshStats()
	{
		dipoleStrength = 0;
		int energy = 0;
		int heat = 0;
		double efficiency =1;
		double quadStrength =0;
		double voltage = 0;
		for(TileAcceleratorMagnet magnet :getAccelerator().getPartMap(TileAcceleratorMagnet.class).values())
		{
			heat += magnet.heat;
			energy += magnet.basePower;
			efficiency *= Math.pow(magnet.efficiency,1/4d);
		}
		for(TileAcceleratorRFCavity cavity :getAccelerator().getPartMap(TileAcceleratorRFCavity.class).values())
		{
			heat += cavity.heat;
			energy += cavity.basePower;
			efficiency *= Math.pow(cavity.efficiency,1/8d);
			
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
		
		
		getAccelerator().requiredEnergy =  (int) (energy*(2-efficiency));
		getAccelerator().rawHeating = heat;
		getAccelerator().quadrupoleStrength = quadStrength;
		getAccelerator().efficiency = efficiency;
		getAccelerator().acceleratingVoltage=(int) voltage;
		
	}

	
	
	// Network
	@Override
	public RingAcceleratorUpdatePacket getUpdatePacket()
	{
		return new RingAcceleratorUpdatePacket(getAccelerator().controller.getTilePos(),
				getAccelerator().isAcceleratorOn, getAccelerator().cooling, getAccelerator().rawHeating, getAccelerator().maxCoolantIn,getAccelerator().maxCoolantOut,
				getAccelerator().requiredEnergy, getAccelerator().efficiency, getAccelerator().acceleratingVoltage,
				getAccelerator().RFCavityNumber, getAccelerator().quadrupoleNumber, getAccelerator().quadrupoleStrength,
				getAccelerator().heatBuffer, getAccelerator().energyStorage,getAccelerator().beam,getAccelerator().tanks,dipoleNumber,dipoleStrength);
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

		}
	}
	
	// NBT
	
	@Override
	public void writeToNBT(NBTTagCompound data, SyncReason syncReason) {
		super.writeToNBT(data, syncReason);
		NBTTagCompound logicTag = new NBTTagCompound();
	
		logicTag.setInteger("dipoleNumber", dipoleNumber);
		logicTag.setDouble("dipoleStrength", dipoleStrength);
	
		data.setTag("ring_accelerator", logicTag);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data, SyncReason syncReason) {
		super.readFromNBT(data, syncReason);
		if (data.hasKey("ring_accelerator")) {
			NBTTagCompound logicTag = data.getCompoundTag("ring_accelerator");
			dipoleNumber = logicTag.getInteger("dipoleNumber");
			dipoleStrength = logicTag.getDouble("dipoleStrength");
			
		}
	}
	
	
	
	
	
	
	@Override
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) {
		return new ContainerRingAcceleratorController(player, getAccelerator().controller);
	}
	
	

	
	
	
	
	
	
	
	
	

	
	
}
