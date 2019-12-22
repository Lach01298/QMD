package lach_01298.qmd.multiblock.accelerator.ring;

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
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPort;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
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
	private final int thickness = 5;

	
	public RingAcceleratorLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
	}

	
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
					getPartMap(TileAcceleratorBeam.class).put(beam.getTilePos().toLong(), beam);
				}
			}

			// ports
			for (TileAcceleratorPort port : acc.getPartMap(TileAcceleratorPort.class).values())
			{

			}

			// beam
			for (TileAcceleratorBeam beam :acc.getPartMap(TileAcceleratorBeam.class).values())
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

			getAccelerator().RFCavityNumber = acc.getQuadrupoleMap().size();
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
		 super.onAcceleratorFormed();
	}
	
	
	
	



	public boolean isMachineWhole(Multiblock multiblock) 
	{
		Accelerator acc = getAccelerator();


		// Beam
		for (BlockPos pos : getinteriorAxisPositions())
		{
			if (!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.ring.must_be_beam, ", pos);
				return false;
			}
		}

		// Source and beam port

		// Ports

		if (getPartMap(TileAcceleratorPort.class).size() < 2)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.not_enough_ports", null);
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
	public RingAcceleratorUpdatePacket getUpdatePacket()
	{
		return new RingAcceleratorUpdatePacket(getAccelerator().controller.getTilePos(),
				getAccelerator().isAcceleratorOn, getAccelerator().cooling, getAccelerator().rawHeating,
				getAccelerator().requiredEnergy, getAccelerator().efficiency, getAccelerator().acceleratingVoltage,
				getAccelerator().RFCavityNumber, getAccelerator().quadrupoleNumber, getAccelerator().quadrupoleStrength,
				getAccelerator().heatBuffer, getAccelerator().energyStorage,getAccelerator().beam,dipoleNumber,dipoleStrength);
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
	
	@Override
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) {
		return new ContainerRingAcceleratorController(player, getAccelerator().controller);
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
	
	@Override
	public int getMaximumInteriorLength()
	{
		// TODO Auto-generated method stub
		return QMDConfig.accelerator_ring_max_size;
	}
	
	
}
