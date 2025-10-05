package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.util.Equations;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static lach_01298.qmd.recipes.QMDRecipes.accelerator_source;
import static nc.block.property.BlockProperties.FACING_ALL;

public class LinearAcceleratorLogic extends AcceleratorLogic
{

	protected TileAcceleratorIonSource source;
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	// Multiblock logic
	
	public LinearAcceleratorLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		beam 0 = input particle
		beam 1 = output particle
		tank 0 = input coolant
		tank 1 = output coolant
		tank 2 = input fluid
		*/
		
		
		
		//on the rare occasion of changing the multiblock to a different type with the tank full
		if(!(oldLogic instanceof LinearAcceleratorLogic || oldLogic.getID().equals("")))
		{
			multiblock.tanks.get(2).setFluidStored(null);
		}
	}

	@Override
	public String getID()
	{
		return "linear_accelerator";
	}
	
	// Accelerator methods
	
	@Override
	public int getBeamLength()
	{
		return multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ() ?multiblock.getExteriorLengthX() : multiblock.getExteriorLengthZ();
	}
	
	public TileAcceleratorIonSource getSource()
	{
		return source;
	}
	
	// Multiblock validation
	
	@Override
	public boolean isMachineWhole()
	{
		Axis axis;
		Accelerator acc = multiblock;

		if (acc.getExteriorLengthY() != getThickness())
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
			if(acc.getExteriorLengthZ() != getThickness())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_be_5_wide", null);
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
			if(acc.getExteriorLengthX() != getThickness())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.must_be_5_wide", null);
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
			BlockPos end1 =acc.getExtremeCoord(false, false, false).add(0, getThickness() / 2,getThickness() / 2);
			BlockPos end2 =acc.getExtremeCoord(true, false, false).add(0, getThickness() / 2,getThickness() / 2);
			
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
				if (!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorIonSource && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorBeamPort) &&
						!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorBeamPort && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorIonSource))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.have_source_and_beam_port", null);
					return false;
				}
				if (acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorIonSource)
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
			BlockPos end1 =acc.getExtremeCoord(false, false, false).add(getThickness() / 2, getThickness() / 2,0);
			BlockPos end2 =acc.getExtremeCoord(false, false, true).add(getThickness() / 2, getThickness() / 2,0);
			
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
				if (!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorIonSource && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorBeamPort) &&
						!(acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorBeamPort && acc.WORLD.getTileEntity(end2) instanceof TileAcceleratorIonSource))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.linear.have_source_and_beam_port",  null);
					return false;
				}
				if (acc.WORLD.getTileEntity(end1) instanceof TileAcceleratorIonSource)
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
		for (TileAcceleratorIonSource port : getPartMap(TileAcceleratorIonSource.class).values())
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
		
		if(containsBlacklistedPart())
		{
			return false;
		}
		
		return super.isMachineWhole();
	}
	
	public Set<BlockPos> getinteriorAxisPositions(EnumFacing.Axis axis)
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Accelerator acc = multiblock;
		
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
	
	public static final List<Pair<Class<? extends IAcceleratorPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileAcceleratorYoke.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_yokes"),
			Pair.of(TileAcceleratorSynchrotronPort.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
			Pair.of(TileAcceleratorIonCollector.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_ion_collectors"));

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
			
			Axis axis;

			if (multiblock.getExteriorLengthX() > multiblock.getExteriorLengthZ())
			{
				axis = Axis.X;
			}
			else
			{

				axis = Axis.Z;
			}
			
			setBeamlineFunctional(getinteriorAxisPositions(axis));
			formComponents();
	

			// source
			for (TileAcceleratorIonSource source : acc.getPartMap(TileAcceleratorIonSource.class).values())
			{
				this.source = source;
			}

			if (source != null)
			{
				source.setIONumber(2);
			}

			multiblock.tanks.get(2).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * 1000);
			multiblock.tanks.get(2).setAllowedFluids(QMDRecipes.accelerator_source.validFluids.get(0));

			// source ports
			for (TileAcceleratorPort port : acc.getPartMap(TileAcceleratorPort.class).values())
			{
				port.setSource(this);
			}

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
	
	// Accelerator disassembly
	
	public void onAcceleratorBroken()
	{
		if(source != null)
		{
			source.setIONumber(0);
		}
		source = null;
		super.onAcceleratorBroken();
	}
	
	// Accelerator Operation
	
	@Override
	public boolean onUpdateServer()
	{
		super.onUpdateServer();
		
		
		if (multiblock.isControllorOn)
		{
			if(source != null)
			{
				//refreshRecipe(); called in shouldUseEnergy
				
				if (recipeInfo != null)
				{
					
					produceSourceBeam();
				}
				else
				{
					resetOutputBeam();
				}
			}
			else
			{
				produceBeam();
			}
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
		multiblock.beams.get(1).setParticleStack(null);
		pull();
	}
	
	@Override
	protected boolean shouldUseEnergy()
	{
		if (source != null)
		{
			refreshRecipe();
			if (recipeInfo != null)
			{
				return true;
			}
		}
		else if(multiblock.beams.get(0).getParticleStack() != null)
		{
			return true;
		}
		
		return false;
	}
	
	
	// Recipe handling
	
	private void resetOutputBeam()
	{
		multiblock.beams.get(1).setParticleStack(null);
	}

	private void produceSourceBeam()
	{
		IParticleIngredient particleProduct = recipeInfo.recipe.getParticleProducts().get(0);
			
		if (particleProduct.getStack() != null)
		{
			ParticleStack outputStack = particleProduct.getStack();
			
			// amount setting
			int outputAmount = outputStack.getAmount() * source.outputParticleMultiplier;
			if(!source.getInventoryStacks().get(0).isEmpty())
			{
				if (source.getInventoryStacks().get(0).getItem() instanceof IItemParticleAmount)
				{
					IItemParticleAmount item = (IItemParticleAmount) source.getInventoryStacks().get(0).getItem();
					if (item.getAmountStored(source.getInventoryStacks().get(0)) < outputAmount)
					{
						outputAmount = item.getAmountStored(source.getInventoryStacks().get(0));
					}
					
					source.getInventoryStacks().set(0, item.use(source.getInventoryStacks().get(0), outputAmount));
					
					// switch slot items
					if((item.isEmptyItem(source.getInventoryStacks().get(0)) && !source.getInventoryStacks().get(1).isEmpty()))
					{
						ItemStack stack = source.getInventoryStacks().get(1).copy();
						source.getInventoryStacks().set(1,source.getInventoryStacks().get(0).copy());
						source.getInventoryStacks().set(0,stack);
					}
				}
			}
			else if (!source.getTanks().get(0).isEmpty())
			{
				FluidStack fluidStack = recipeInfo.recipe.getFluidIngredients().get(0).getStack();
				
				Tank tank = source.getTanks().get(0);
				int mBtoDrain = fluidStack.amount * source.outputParticleMultiplier;
				
				FluidStack mBDrained = tank.drain(mBtoDrain, true);
				outputAmount *= mBDrained.amount/mBtoDrain;
			}
			
			// energy setting
			if (multiblock.computerControlled)
			{
				outputStack.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage, outputStack) * (multiblock.energyPercentage / 100d)));
			}
			else
			{
				outputStack.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage, outputStack) * getRedstoneLevel() / 15d));
			}
			outputStack.setAmount(outputAmount);
			
			// focus setting
			outputStack.addFocus(source.outputFocus);
			outputStack.addFocus(Equations.focusGain(multiblock.quadrupoleStrength, outputStack) - Equations.focusLoss(getBeamLength(), outputStack));
			if (outputStack.getFocus() <= 0)
			{
				multiblock.errorCode = Accelerator.errorCode_NotEnoughQuadrupoles;
			}
				
			multiblock.beams.get(1).setParticleStack(outputStack);
		}
		
	}

	private void produceBeam()
	{
		ParticleStack inputBeam = multiblock.beams.get(0).getParticleStack();
		
		if(inputBeam != null)
		{
			multiblock.beams.get(1).setParticleStack(inputBeam.copy());
			ParticleStack outputBeam = multiblock.beams.get(1).getParticleStack();
			if(outputBeam != null)
			{
				outputBeam.addFocus(Equations.focusGain(multiblock.quadrupoleStrength, outputBeam) - Equations.focusLoss( getBeamLength(), outputBeam));
				
				if(multiblock.computerControlled)
				{
					outputBeam.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage,outputBeam) * (multiblock.energyPercentage/100d)));
				}
				else
				{
					outputBeam.addMeanEnergy((long) (Equations.linacEnergyGain(multiblock.acceleratingVoltage,outputBeam)*getRedstoneLevel()/15d));
				}
				
				
				
				if(outputBeam.getFocus() <= 0)
				{
					outputBeam = null;
					multiblock.errorCode=Accelerator.errorCode_NotEnoughQuadrupoles;
				}
			}
		}
	}

	protected void refreshRecipe()
	{
		// switch slot items
		if(source.getInventoryStacks().get(0).isEmpty() && !source.getInventoryStacks().get(1).isEmpty())
		{
			ItemStack stack = source.getInventoryStacks().get(1).copy();
			source.getInventoryStacks().set(1,ItemStack.EMPTY);
			source.getInventoryStacks().set(0,stack);
		}
		
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack item = IItemParticleAmount.cleanNBT(source.getInventoryStacks().get(0));
		items.add(item);
		ArrayList<Tank> tanks = new ArrayList<Tank>();
		tanks.add(source.getTanks().get(0));
		recipeInfo = accelerator_source.getRecipeInfoFromInputs(items, tanks, new ArrayList<ParticleStack>());
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

	// Packets
	
	@Override
	public AcceleratorUpdatePacket getMultiblockUpdatePacket()
	{

		return new LinearAcceleratorUpdatePacket(multiblock.controller.getTilePos(),
				multiblock.isControllorOn, multiblock.cooling, multiblock.rawHeating,multiblock.currentHeating,multiblock.maxCoolantIn,multiblock.maxCoolantOut,multiblock.maxOperatingTemp,
				multiblock.requiredEnergy, multiblock.efficiency, multiblock.acceleratingVoltage,
				multiblock.RFCavityNumber, multiblock.quadrupoleNumber, multiblock.quadrupoleStrength, multiblock.dipoleNumber, multiblock.dipoleStrength, multiblock.errorCode,
				multiblock.heatBuffer, multiblock.energyStorage, multiblock.tanks, multiblock.beams);
	}
	
	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof LinearAcceleratorUpdatePacket)
		{
			LinearAcceleratorUpdatePacket packet = (LinearAcceleratorUpdatePacket) message;

		}
	}

}
