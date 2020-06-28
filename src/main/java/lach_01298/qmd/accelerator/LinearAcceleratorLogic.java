package lach_01298.qmd.accelerator;

import static lach_01298.qmd.recipes.QMDRecipes.accelerator_source;
import static nc.block.property.BlockProperties.FACING_ALL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.container.ContainerLinearAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.tile.internal.fluid.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class LinearAcceleratorLogic extends AcceleratorLogic
{

	
	protected TileAcceleratorSource source;
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	private int tick = 0;
	
	public LinearAcceleratorLogic(AcceleratorLogic oldLogic) 
	{
		super(oldLogic);
	}

	
	@Override
	public String getID() 
	{
		return "linear_accelerator";
	}
	
	
	
	// Multiblock Validation
	
	@Override
	public boolean isMachineWhole(Multiblock multiblock)
	{
		Axis axis;
		Accelerator acc = getAccelerator();

		if (acc.getExteriorLengthY() != thickness)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.wrong_height", null);
			return false;
		}
		
		
		if (acc.getExteriorLengthX() > acc.getExteriorLengthZ())
		{
			axis = Axis.X;
			if(acc.getExteriorLengthX() < QMDConfig.accelerator_linear_min_size)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_short", null);
				return false;
			}
			if(acc.getExteriorLengthZ() != thickness)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.must_be_5_wide", null);
				return false;
			}
			
		}
		else
		{
			axis = Axis.Z;
			if(acc.getExteriorLengthZ() < QMDConfig.accelerator_linear_min_size)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_short", null);
				return false;
			}
			if(acc.getExteriorLengthX() != thickness)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.must_be_5_wide", null);
				return false;
			}
		}

		// Beam
		for (BlockPos pos : getinteriorAxisPositions(axis))
		{
			if (!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_be_beam", pos);
				return false;
			}
		}

		// Source and beam port
		if(axis == Axis.X)
		{
			BlockPos end1 =acc.getExtremeCoord(false, false, false).add(0, thickness / 2,thickness / 2);
			BlockPos end2 =acc.getExtremeCoord(true, false, false).add(0, thickness / 2,thickness / 2);
			
			if(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorBeamPort && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorBeamPort)
			{
				List<TileAcceleratorBeamPort> ports = new ArrayList<TileAcceleratorBeamPort>();
				ports.add((TileAcceleratorBeamPort) acc.WORLD.getTileEntity(end1));
				ports.add((TileAcceleratorBeamPort) acc.WORLD.getTileEntity(end2));
				
				int inputs =0;
				int outputs =0;
				for(TileAcceleratorBeamPort port : ports)
				{
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
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_have_io", null);
					return false;
				}
			}
			else
			{
				if (!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorSource && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorBeamPort) && 
						!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorBeamPort && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorSource))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.have_source_and_beam_port", null);
					return false;
				}
				if (acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorSource)
				{
					if(acc.WORLD.getBlockState(end1).getValue(FACING_ALL) !=  EnumFacing.EAST)
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in", end1);
						return false;
					}
				}
				else
				{
					if(acc.WORLD.getBlockState(end2).getValue(FACING_ALL) !=  EnumFacing.WEST)
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in", end2);
						return false;
					}
				}
			}
	
		}
		if(axis == Axis.Z)
		{
			BlockPos end1 =acc.getExtremeCoord(false, false, false).add(thickness / 2, thickness / 2,0);
			BlockPos end2 =acc.getExtremeCoord(false, false, true).add(thickness / 2, thickness / 2,0);
			
			if(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorBeamPort && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorBeamPort)
			{
				List<TileAcceleratorBeamPort> ports = new ArrayList<TileAcceleratorBeamPort>();
				ports.add((TileAcceleratorBeamPort) acc.WORLD.getTileEntity(end1));
				ports.add((TileAcceleratorBeamPort) acc.WORLD.getTileEntity(end2));
				
				int inputs =0;
				int outputs =0;
				for(TileAcceleratorBeamPort port : ports)
				{
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
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_have_io", null);
					return false;
				}	
			}
			else
			{
				if (!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorSource && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorBeamPort) && 
						!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorBeamPort && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorSource))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.have_source_and_beam_port",  null);
					return false;
				}
				if (acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorSource)
				{
					if(acc.WORLD.getBlockState(end1).getValue(FACING_ALL) !=  EnumFacing.SOUTH)
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in", end1);
						return false;
					}
				}
				else
				{
					if(acc.WORLD.getBlockState(end2).getValue(FACING_ALL) !=  EnumFacing.NORTH)
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.source_must_face_in", end2);
						return false;
					}
				}
			}
			
			
			
			
		}
		
		int sources = 0;
		for (TileAcceleratorSource port : getPartMap(TileAcceleratorSource.class).values())
		{
			sources++;
		}
		if (sources > 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.only_one_source", null);
			return false;
		}
		int ports = 0;
		for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values())
		{
			ports++;
		}
		if (ports > 2 - sources)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.to_many_beam_ports", null);
			return false;
		}
		
		return super.isMachineWhole(multiblock);
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
	
	
	
	// Multiblock Methods
	
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
						
					}
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
					}
				
					
				}

				acc.RFCavityNumber = acc.getRFCavityMap().size();
				acc.quadrupoleNumber = acc.getQuadrupoleMap().size();


			
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
				
				//source
				for (TileAcceleratorSource source :acc.getPartMap(TileAcceleratorSource.class).values())
				{
					this.source = source;
				}
			}

			 refreshStats();
			 super.onAcceleratorFormed();
	}
	
	
	public void onMachineDisassembled()
	{
		 source = null;
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
			if(source != null)
			{
				refreshRecipe();
				if (recipeInfo != null)
				{
					produceSourceBeam();
				}
				else
				{
					resetBeam();
				}
			}
			else
			{
				produceBeam();
			}	
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
		int energy = 0;
		int heat = 0;
		int parts= 0;
		double efficiency =0;
		double strength =0;
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
					strength += magnet.strength/4;
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
		efficiency /= parts;
		
		getAccelerator().requiredEnergy =  (int) (energy/efficiency);
		getAccelerator().rawHeating = heat;
		getAccelerator().quadrupoleStrength = strength;
		getAccelerator().efficiency = efficiency;
		getAccelerator().acceleratingVoltage=(int) voltage;				
	}
	
	
	// Recipe Stuff
	
	private void resetBeam()
	{
		getAccelerator().beams.get(1).setParticleStack(null);
	}


	private void produceSourceBeam()
	{
		IParticleIngredient particleIngredient = recipeInfo.getRecipe().getParticleProducts().get(0);
		getAccelerator().beams.get(1).setParticleStack(particleIngredient.getStack());
		ParticleStack particle = getAccelerator().beams.get(1).getParticleStack();
		particle.addMeanEnergy((long) (getAccelerator().acceleratingVoltage*Math.abs(getAccelerator().beams.get(1).getParticleStack().getParticle().getCharge())*getWorld().getRedstonePowerFromNeighbors(getAccelerator().controller.getTilePos())/15d));
		particle.addFocus(((getAccelerator().quadrupoleStrength))-getLength()*QMDConfig.beamAttenuationRate);
		if(particle.getFocus() <= 0)
		{
			getAccelerator().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
		}
		
		useItemDurability();
	}

	private void produceBeam()
	{
		
		ParticleStack inputBeam = getAccelerator().beams.get(0).getParticleStack();
		
		getAccelerator().beams.get(1).setParticleStack(inputBeam);
		ParticleStack outputBeam = getAccelerator().beams.get(1).getParticleStack();
		if(outputBeam != null)
		{
			outputBeam.addFocus(((getAccelerator().quadrupoleStrength))-getLength()*QMDConfig.beamAttenuationRate);
			outputBeam.addMeanEnergy((long) (getAccelerator().acceleratingVoltage*Math.abs(getAccelerator().beams.get(1).getParticleStack().getParticle().getCharge())*getWorld().getRedstonePowerFromNeighbors(getAccelerator().controller.getTilePos())/15d));
			if(outputBeam.getFocus() <= 0)
			{
				outputBeam = null;
				getAccelerator().errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
			}
		}
		
		
	}

	private void useItemDurability()
	{
		if(tick >= 20)
		{
			if(source.getInventoryStacks().get(0).attemptDamageItem(1, rand, null))
			{
				source.getInventoryStacks().set(0, ItemStack.EMPTY);
			}
			tick = 0;
		}
		else
		{
			tick++;
		}	
	}


	protected void refreshRecipe() 
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack item =source.getInventoryStacks().get(0).copy();
		item.setItemDamage(0);
		items.add(item);
		recipeInfo = accelerator_source.getRecipeInfoFromInputs(items, new ArrayList<Tank>(), new ArrayList<ParticleStack>());
	}
	
	
	


	
	
	// Network
	
	@Override
	public AcceleratorUpdatePacket getUpdatePacket() 
	{

		return new LinearAcceleratorUpdatePacket(getAccelerator().controller.getTilePos(),
				getAccelerator().isAcceleratorOn, getAccelerator().cooling, getAccelerator().rawHeating,getAccelerator().maxCoolantIn,getAccelerator().maxCoolantOut,getAccelerator().maxOperatingTemp,
				getAccelerator().requiredEnergy, getAccelerator().efficiency, getAccelerator().acceleratingVoltage,
				getAccelerator().RFCavityNumber, getAccelerator().quadrupoleNumber, getAccelerator().quadrupoleStrength, getAccelerator().dipoleNumber, getAccelerator().dipoleStrength, getAccelerator().errorCode,
				getAccelerator().heatBuffer, getAccelerator().energyStorage, getAccelerator().tanks, getAccelerator().beams);
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
		return new ContainerLinearAcceleratorController(player, getAccelerator().controller);
	}
	
	
	public int getLength()
	{
		return getAccelerator().getExteriorLengthX() > getAccelerator().getExteriorLengthZ() ?getAccelerator().getExteriorLengthX() : getAccelerator().getExteriorLengthZ();
	}
	
	
	
	
}
