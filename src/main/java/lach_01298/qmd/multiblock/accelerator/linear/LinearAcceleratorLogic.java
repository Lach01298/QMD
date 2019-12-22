package lach_01298.qmd.multiblock.accelerator.linear;

import java.util.HashSet;
import java.util.Set;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
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
import lach_01298.qmd.multiblock.container.ContainerLinearAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import nc.Global;
import nc.multiblock.Multiblock;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.container.ContainerSaltFissionController;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.FissionReactorLogic;
import nc.multiblock.fission.tile.IFissionController;
import nc.multiblock.network.FissionUpdatePacket;
import nc.multiblock.network.SolidFissionUpdatePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class LinearAcceleratorLogic extends AcceleratorLogic
{

	public LinearAcceleratorLogic(AcceleratorLogic oldLogic) 
	{
		super(oldLogic);
	}

	
	@Override
	public boolean isMachineWhole(Multiblock multiblock)
	{
		Axis axis;
		Accelerator acc = getAccelerator();

		if (acc.getExteriorLengthY() != 5)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.wrong_height, ", null);
			return false;
		}
		
		
		if (acc.getExteriorLengthX() > acc.getExteriorLengthZ())
		{
			axis = Axis.X;
			if(acc.getExteriorLengthX() < QMDConfig.accelerator_linear_min_size)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_short, ", null);
				return false;
			}
			
			
		}
		else
		{
			axis = Axis.Z;
			if(acc.getExteriorLengthZ() < QMDConfig.accelerator_linear_min_size)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_short, ", null);
				return false;
			}
		}

		// Beam
		for (BlockPos pos : getinteriorAxisPositions(axis))
		{
			if (!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_be_beam, ", pos);
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
	
	
	
	
	@Override
	public void onAcceleratorFormed()
	{
		
			Axis axis;

			if (multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ())
			{
				axis = Axis.X;
			}
			else
			{

				axis = Axis.Z;
			}

			// beam
			 Accelerator acc = getAccelerator();
				
			 
			 
			 
			 
			 if (!getWorld().isRemote)
			{

				// beam
				for (BlockPos pos :getinteriorAxisPositions(axis))
				{
					if (acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam)
					{

						TileAcceleratorBeam beam = (TileAcceleratorBeam) getWorld().getTileEntity(pos);
						beam.setFunctional(true);
						getPartMap(TileAcceleratorBeam.class).put(beam.getTilePos().toLong(), beam);
					}
				}

				// ports
				for (TileAcceleratorPort port : acc.getPartMap((TileAcceleratorPort.class)).values())
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
				}

				getAccelerator().RFCavityNumber = acc.getQuadrupoleMap().size();
				getAccelerator().quadrupoleNumber = acc.getQuadrupoleMap().size();


			
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

			}
			 super.onAcceleratorFormed();
	}
	
	
	
	
	public Set<BlockPos> getinteriorAxisPositions(EnumFacing.Axis axis)
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Accelerator acc = getAccelerator();
		
		if (axis == Axis.X)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					acc.getExtremeInteriorCoord(false, false, false).add(0, acc.getInteriorLengthY() / 2, acc.getInteriorLengthZ() / 2),
					acc.getExtremeInteriorCoord(true, false, false).add(0, acc.getInteriorLengthY() / 2, acc.getInteriorLengthZ() / 2)))
			{
				postions.add(pos.toImmutable());
			}
		}

		if (axis == Axis.Z)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					acc.getExtremeInteriorCoord(false, false, false).add(acc.getInteriorLengthX() / 2, acc.getInteriorLengthY() / 2, 0),
					acc.getExtremeInteriorCoord(false, false, true).add(acc.getInteriorLengthX() / 2, acc.getInteriorLengthY() / 2, 0)))
			{
				postions.add(pos.toImmutable());
			}
		}

		return postions;
	}
	
	
	@Override
	public AcceleratorUpdatePacket getUpdatePacket() 
	{

		return new LinearAcceleratorUpdatePacket(getAccelerator().controller.getTilePos(),
				getAccelerator().isAcceleratorOn, getAccelerator().cooling, getAccelerator().rawHeating,
				getAccelerator().requiredEnergy, getAccelerator().efficiency, getAccelerator().acceleratingVoltage,
				getAccelerator().RFCavityNumber, getAccelerator().quadrupoleNumber, getAccelerator().quadrupoleStrength,
				getAccelerator().heatBuffer, getAccelerator().energyStorage,getAccelerator().beam);
	}
	
	
	@Override
	public void writeToNBT(NBTTagCompound data, SyncReason syncReason)
	{
		super.writeToNBT(data, syncReason);
		NBTTagCompound logicTag = new NBTTagCompound();
		//stuff
		data.setTag("linear_accelerator", logicTag);
	}

	@Override
	public void readFromNBT(NBTTagCompound data, SyncReason syncReason)
	{
		super.readFromNBT(data, syncReason);
		if (data.hasKey("linear_accelerator"))
		{
			NBTTagCompound logicTag = data.getCompoundTag("linear_accelerator");
			//stuff
		}
	}

	@Override
	public void onPacket(AcceleratorUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof LinearAcceleratorUpdatePacket)
		{
			LinearAcceleratorUpdatePacket packet = (LinearAcceleratorUpdatePacket) message;

		}
	}
	
	
	@Override
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player)
	{
		return new ContainerLinearAcceleratorController(player, getAccelerator().controller);
	}
	
}
