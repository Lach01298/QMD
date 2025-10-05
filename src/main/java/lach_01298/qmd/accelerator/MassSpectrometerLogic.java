package lach_01298.qmd.accelerator;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.InventoryHelper;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.MassSpectrometerUpdatePacket;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lach_01298.qmd.recipes.QMDRecipes.mass_spectrometer;
import static nc.block.property.BlockProperties.FACING_ALL;

public class MassSpectrometerLogic extends AcceleratorLogic
{

	
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	public static final int diameter = 7;
	
	public double workDone = 0;
	public double recipeWork = 100;
	public double speed = 1;
	
	// Multiblock logic
	
	public MassSpectrometerLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
		
		/*
		tank 0 = input coolant
		tank 1 = output coolant
		tank 2 = input fluid
		tank 3 = output fluid 1
		tank 4 = output fluid 2
		tank 5 = output fluid 3
		tank 6 = output fluid 4
		*
		*/
		
		//on the rare occasion of changing the multiblock to a different type with the tank full
		if(!(oldLogic instanceof MassSpectrometerLogic || oldLogic.getID().equals("")))
		{
			multiblock.tanks.get(2).setFluidStored(null);
			multiblock.tanks.get(3).setFluidStored(null);
			multiblock.tanks.get(4).setFluidStored(null);
			multiblock.tanks.get(5).setFluidStored(null);
			multiblock.tanks.get(6).setFluidStored(null);
			
		}
	}

	
	@Override
	public String getID()
	{
		return "mass_spectrometer";
	}
	
	// Accelerator methods
	
	@Override
	public int getBeamLength()
	{
		return 13;
	}

	@Override
	public double getBeamRadius()
	{
		return 2.5;
	}
	
	// Multiblock validation
	
	@Override
	public boolean isMachineWhole()
	{
		Axis axis;
		Accelerator acc = multiblock;
		
		// check size
		if (acc.getExteriorLengthY() != diameter)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.wrong_height", null);
			return false;
		}
		
		
		if (acc.getExteriorLengthX() != diameter && acc.getExteriorLengthZ() != diameter)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.wrong_width", null);
			return false;
		}
		
		
		int length = diameter-2;
		if(acc.getExteriorLengthX() == acc.getExteriorLengthZ())
		{
			axis = null;
		}
		else if(acc.getExteriorLengthX() == diameter)
		{
			axis = Axis.Z;
			length = acc.getInteriorLengthZ();
		}
		else
		{
			axis = Axis.X;
			length = acc.getInteriorLengthX();
		}
		
		if(length % 2 == 0)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_odd_length", null);
			return false;
		}
		
		//check ion sources and collectors
		
		
		int sourceAmount = length / 2;
		
		if (getPartMap(TileAcceleratorIonSource.class).size() != sourceAmount)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.need_ion_source_amount",null ,sourceAmount);
			return false;
		}
		
		
		// figure out axis if the multiblock is a cube
		if(axis == null)
		{
			TileAcceleratorIonSource source = getPartMap(TileAcceleratorIonSource.class).values().iterator().next();
			EnumFacing normal = getWallNormal(source.getPos());
			if(normal == null)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos", source.getPos());
				return false;
			}
			axis = normal.getAxis() == Axis.X ? Axis.Z : Axis.X;
		}
		
		boolean[] layerHasSource = new boolean[sourceAmount];
		Arrays.fill(layerHasSource, false);
		
		
		
		
		for(TileAcceleratorIonSource source : getPartMap(TileAcceleratorIonSource.class).values())
		{
			BlockPos sourcePos = source.getPos();
			boolean validSource = false;
			for(String validSourceName : QMDConfig.mass_spectrometer_valid_sources)
			{
				if(validSourceName.equals(source.name))
				{
					validSource = true;
				}
			}
			
			if(!validSource)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_type", sourcePos);
				return false;
			}
			

			if(sourcePos.getY() != acc.getMinInteriorY())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos", sourcePos);
				return false;
			}
			
			if(getWallNormal(source.getPos()) == null)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos", source.getPos());
				return false;
			}
			
			if(acc.WORLD.getBlockState(sourcePos).getValue(FACING_ALL) != getWallNormal(sourcePos).getOpposite())
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_facing", sourcePos);
				return false;
			}
			int offset = axis == Axis.X ? sourcePos.getX() - acc.getMinInteriorX() : sourcePos.getZ() - acc.getMinInteriorZ();
			
			if(offset % 2 != 1 || offset/2 >= layerHasSource.length || offset < 0)
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_wrong_pos", sourcePos);
				return false;
			}
			
			if(layerHasSource[offset/2])
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_source_in_layer_already", sourcePos);
				return false;
			}
			else
			{
				layerHasSource[offset/2] = true;
			}
			
			for(int i = 1; i <= 4; i++)
			{
				if(!(acc.WORLD.getTileEntity(sourcePos.up(i)) instanceof TileAcceleratorIonCollector))
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_ion_collector", sourcePos.up(i));
					return false;
				}
				if(acc.WORLD.getBlockState(sourcePos.up(i)).getValue(FACING_ALL) != getWallNormal(sourcePos).getOpposite())
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.ion_collector_wrong_facing", sourcePos.up(i));
					return false;
				}
			}
		}
	
		if (getPartMap(TileAcceleratorIonCollector.class).size() != sourceAmount*4)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.need_ion_collector_amount", null);
			return false;
		}
		

		
		// check interior
		Class magnetType = null;
		
		for(int layerNumber = 0; layerNumber < length; layerNumber++)
		{
			Iterable<MutableBlockPos> layer = acc.getInteriorPlane(EnumFacing.getFacingFromAxis(AxisDirection.NEGATIVE, axis),layerNumber,0,0,0,0);
		
			if(layerNumber % 2 == 0)
			{
				for(BlockPos pos : layer)
				{
					if(axis == Axis.X)
					{
						if(pos.getY() > acc.getMinInteriorY() && pos.getY() < acc.getMaxInteriorY() && pos.getZ() > acc.getMinInteriorZ() && pos.getZ() < acc.getMaxInteriorZ())
						{
							if(!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorYoke))
							{
								multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_yoke", pos);
								return false;
							}
						}
						else
						{
							if(!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorMagnet))
							{
								multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_magnet", pos);
								return false;
							}
							else
							{
								if(magnetType == null)
								{
									TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) acc.WORLD.getTileEntity(pos);
									boolean validMagnet = false;
									for(String validSourceName : QMDConfig.mass_spectrometer_valid_magnets)
									{
										if(validSourceName.equals(magnet.name))
										{
											validMagnet = true;
											break;
										}
									}
									if(!validMagnet)
									{
										multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.magnet_wrong_type", pos);
										return false;
									}
									magnetType = acc.WORLD.getTileEntity(pos).getClass();
								}
								else if (!magnetType.isInstance(acc.WORLD.getTileEntity(pos)))
								{
									multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_one_magnet_type", pos);
									return false;
								}
							}
						}
						
					}
					else
					{
						if(pos.getY() > acc.getMinInteriorY() && pos.getY() < acc.getMaxInteriorY() && pos.getX() > acc.getMinInteriorX() && pos.getX() < acc.getMaxInteriorX())
						{
							if(!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorYoke))
							{
								multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_yoke", pos);
								return false;
							}
						}
						else
						{
							if(!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorMagnet))
							{
								multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_magnet", pos);
								return false;
							}
							else
							{
								if(magnetType == null)
								{
									TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) acc.WORLD.getTileEntity(pos);
									boolean validMagnet = false;
									for(String validSourceName : QMDConfig.mass_spectrometer_valid_magnets)
									{
										if(validSourceName.equals(magnet.name))
										{
											validMagnet = true;
											break;
										}
									}
									if(!validMagnet)
									{
										multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.magnet_wrong_type", pos);
										return false;
									}
									magnetType = acc.WORLD.getTileEntity(pos).getClass();
								}
								else if (!magnetType.isInstance(acc.WORLD.getTileEntity(pos)))
								{
									multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_one_magnet_type", pos);
									return false;
								}
							}
						}
					}
				}
			}
			else
			{
				for(BlockPos pos : layer)
				{
					if(!(acc.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam))
					{
						multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.accelerator.mass_spectrometer.must_be_beam", pos);
						return false;
					}
				}
			}
		
			
		}
		
		return super.isMachineWhole();
	}
	
	public EnumFacing getWallNormal(BlockPos pos)
	{
		Accelerator acc = multiblock;
		if(pos.getY() == acc.getMaxY())
		{
			return EnumFacing.UP;
		}
		else if(pos.getY() == acc.getMinY())
		{
			return EnumFacing.DOWN;
		}
		else if(pos.getX() == acc.getMinX())
		{
			return EnumFacing.WEST;
		}
		else if(pos.getX() == acc.getMaxX())
		{
			return EnumFacing.EAST;
		}
		else if(pos.getZ() == acc.getMaxZ())
		{
			return EnumFacing.SOUTH;
		}
		else if(pos.getZ() == acc.getMinZ())
		{
			return EnumFacing.NORTH;
		}
		return null;
	}
	
	@Override
	public int getThickness()
	{
		return -1;
	}
	
	public static final List<Pair<Class<? extends IAcceleratorPart>, String>> PART_BLACKLIST = Lists.newArrayList(
			Pair.of(TileAcceleratorBeamPort.class,QMD.MOD_ID + ".multiblock_validation.accelerator.no_beam_ports"),
			Pair.of(TileAcceleratorSynchrotronPort.class,QMD.MOD_ID + ".multiblock_validation.accelerator.no_synch_ports"),
			Pair.of(TileAcceleratorRFCavity.class, QMD.MOD_ID + ".multiblock_validation.accelerator.no_rf_cavity"));

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
		
		acc.tanks.get(2).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * 1000);
		acc.tanks.get(2).setAllowedFluids(QMDRecipes.mass_spectrometer.validFluids.get(0));
		acc.tanks.get(3).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * 1000);
		acc.tanks.get(4).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * 1000);
		acc.tanks.get(5).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * 1000);
		acc.tanks.get(6).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * 1000);
		
		
		if (!getWorld().isRemote)
		{
			resetBeams();
			speed = 0;
			for (TileAcceleratorIonSource source : getPartMap(TileAcceleratorIonSource.class).values())
			{
				BlockPos sourcePos = source.getPos();
				source.setIONumber(2);
				speed += source.outputParticleMultiplier/2d;
				
				for (int i = 1; i <= 4; i++)
				{
					if(acc.WORLD.getTileEntity(sourcePos.up(i)) instanceof TileAcceleratorIonCollector)
					{
						TileAcceleratorIonCollector collector = (TileAcceleratorIonCollector) multiblock.WORLD.getTileEntity(sourcePos.up(i));
						collector.setIONumber(i+2);
					}
				}
			}
		}
		
		refreshStats();
		super.onAcceleratorFormed();
		
		acc.cooling = (long) (2*(acc.rawHeating+acc.getMaxExternalHeating()));
	}
	
	public void refreshStats()
	{
		Accelerator acc = multiblock;
		
		int energy = 0;
		long heat = 0;
		for (TileAcceleratorMagnet magnet : multiblock.getPartMap(TileAcceleratorMagnet.class).values())
		{
			energy += magnet.basePower/16;
			heat += magnet.heat/16;
		}
		for (TileAcceleratorIonSource source : multiblock.getPartMap(TileAcceleratorIonSource.class).values())
		{
			energy += source.basePower;
		}
		
		acc.requiredEnergy = energy;
		acc.rawHeating = heat;
		acc.dipoleStrength = 0;
		acc.quadrupoleStrength = 0;
		acc.efficiency = 1;
		acc.acceleratingVoltage = 0;
	}
	
	// Accelerator disassembly
	
	public void onAcceleratorBroken()
	{
		for (TileAcceleratorIonSource source : getPartMap(TileAcceleratorIonSource.class).values())
		{
			source.setIONumber(0);
		}
		
		for (TileAcceleratorIonCollector collector : getPartMap(TileAcceleratorIonCollector.class).values())
		{
			collector.setIONumber(0);
		}
		
		
		super.onAcceleratorBroken();
	}
	
	// Accelerator Operation
	
	@Override
	public boolean onUpdateServer()
	{
		super.onUpdateServer();
		if (multiblock.isControllorOn)
		{
			refreshRecipe();
	
			if (recipeInfo != null)
			{
				
				if (canProduceProduct())
				{
					
					if (multiblock.energyStorage.extractEnergy(multiblock.requiredEnergy,
							true) == multiblock.requiredEnergy)
					{
						internalHeating();
	
						multiblock.energyStorage.changeEnergyStored(-multiblock.requiredEnergy);
						workDone += speed;
						produceProduct();
					}
				}
	
			}
			else
			{
				workDone = 0;
			}
		}
		else
		{
			workDone = 0;
		}
		multiblock.sendMultiblockUpdatePacketToListeners();
		return true;
	}
	
	protected void operate()
	{
		if ((isRedstonePowered() && !multiblock.computerControlled) || (multiblock.computerControlled && multiblock.energyPercentage > 0))
		{
			if (multiblock.getTemperature() <= multiblock.maxOperatingTemp)
			{
				operational = true;
				return;
			}
			else
			{
				if (operational)
				{
					quenchMagnets();
				}
				operational = false;
				multiblock.errorCode = Accelerator.errorCode_ToHot;
				return;
			}
		}
		else
		{
			operational = false;
			return;
		}

	}
	
	// Recipe handling
	
	private boolean canProduceProduct()
	{
		TileMassSpectrometerController inv = (TileMassSpectrometerController) multiblock.controller;
		List<IItemIngredient> productItems = recipeInfo.recipe.getItemProducts();
		List<IFluidIngredient> productFluids = recipeInfo.recipe.getFluidProducts();

		for(int i = 0; i < productItems.size(); i++)
		{
			
			ItemStack stack = productItems.get(i).getStack();
			if(stack != null)
			{
				// some strange safety measure
				if(inv.getInventoryStacks().get(i+2).getCount() <= 0)
				{
					inv.getInventoryStacks().set(i+2, ItemStack.EMPTY);
				}
				
				
				if (!inv.getInventoryStacks().get(i+2).isItemEqual(stack) && inv.getInventoryStacks().get(i+2) != ItemStack.EMPTY)
				{
					return false;
				}
				
				if (inv.getInventoryStacks().get(i+2).getCount() + stack.getCount() > stack.getMaxStackSize())
				{
					return false;
				}
			}
		}
		
		for(int i = 0; i < productFluids.size(); i++)
		{
			FluidStack stack = productFluids.get(i).getStack();
			if(stack != null)
			{
				if(multiblock.tanks.get(i+3).fill(stack, false) != stack.amount)
				{
					return false;
				}
			}
		}

		return true;
	}

	private void produceProduct()
	{
		recipeWork =  recipeInfo.recipe.getBaseProcessTime(QMDConfig.processor_time[2]);
		
		while(workDone >= recipeWork && canProduceProduct())
		{
			
			TileMassSpectrometerController inv = (TileMassSpectrometerController) multiblock.controller;

			List<IItemIngredient> productItems = recipeInfo.recipe.getItemProducts();
			for (int i = 0; i < productItems.size(); i++)
			{
				ItemStack productItem = productItems.get(i).getStack();
				
				if (productItem == null)
				{
					productItem = ItemStack.EMPTY;
				}
				else
				{
					productItem.setCount(productItems.get(i).getNextStackSize(0));
				}

				InventoryHelper.addItem(i + 2, productItem, inv.getInventoryStacks(), inv);

			}

			InventoryHelper.removeItem(0, recipeInfo.recipe.getItemIngredients().get(0).getMaxStackSize(0), inv.getInventoryStacks(), inv);
			
			List<IFluidIngredient> productFluids = recipeInfo.recipe.getFluidProducts();
			for (int i = 0; i < productFluids.size(); i++)
			{

				FluidStack productFluid = productFluids.get(i).getStack();
				if (productFluid != null)
				{
					productFluid.amount = productFluids.get(i).getNextStackSize(0);
					multiblock.tanks.get(i+3).fill(productFluid, true);
				}

			}

			FluidStack ingredientFluid = recipeInfo.recipe.getFluidIngredients().get(0).getStack();
			if (ingredientFluid != null)
			{
				multiblock.tanks.get(2).drain(ingredientFluid, true);
			}

			workDone = Math.max(0, workDone - recipeWork);
		}
	}

	protected void refreshRecipe()
	{
		TileMassSpectrometerController cont = (TileMassSpectrometerController) multiblock.controller;
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack item = cont.getInventoryStacks().get(0).copy();
		items.add(item);
		ArrayList<Tank> tanks = new ArrayList<Tank>();
		tanks.add(multiblock.tanks.get(2));
		
		recipeInfo = mass_spectrometer.getRecipeInfoFromInputs(items, tanks, new ArrayList<ParticleStack>());
	}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		logicTag.setDouble("workDone", workDone);
		logicTag.setDouble("recipeWork", recipeWork);
		logicTag.setDouble("speed", speed);
		
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);
		workDone=logicTag.getDouble("workDone");
		recipeWork=logicTag.getDouble("recipeWork");
		speed=logicTag.getDouble("speed");
	}
	
	// Packets
	
	@Override
	public AcceleratorUpdatePacket getMultiblockUpdatePacket()
	{

		return new MassSpectrometerUpdatePacket(multiblock.controller.getTilePos(),
				multiblock.isControllorOn, multiblock.cooling, multiblock.rawHeating,multiblock.currentHeating,multiblock.maxCoolantIn,multiblock.maxCoolantOut,multiblock.maxOperatingTemp,
				multiblock.requiredEnergy, multiblock.efficiency, multiblock.acceleratingVoltage,
				multiblock.RFCavityNumber, multiblock.quadrupoleNumber, multiblock.quadrupoleStrength, multiblock.dipoleNumber, multiblock.dipoleStrength, multiblock.errorCode,
				multiblock.heatBuffer, multiblock.energyStorage, multiblock.tanks, multiblock.beams,workDone,recipeWork, speed);
	}
	
	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
		super.onMultiblockUpdatePacket(message);
		if (message instanceof MassSpectrometerUpdatePacket)
		{
			MassSpectrometerUpdatePacket packet = (MassSpectrometerUpdatePacket) message;
			this.workDone = packet.workDone;
			this.recipeWork = packet.recipeWork;
			this.speed = packet.speed;
		}
	}
	
}
