package lach_01298.qmd.accelerator;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.IPacketMultiblockLogic;
import nc.multiblock.MultiblockLogic;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import nc.util.MaterialHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static lach_01298.qmd.recipes.QMDRecipes.accelerator_cooling;
import static nc.block.property.BlockProperties.ACTIVE;

public class AcceleratorLogic extends MultiblockLogic<Accelerator, AcceleratorLogic, IAcceleratorPart>
		implements IPacketMultiblockLogic<Accelerator, AcceleratorLogic, IAcceleratorPart, AcceleratorUpdatePacket>
{

	public boolean searchFlag = false;
	public final ObjectSet<TileAcceleratorCooler> coolerCache = new ObjectOpenHashSet<>();
	public final Long2ObjectMap<TileAcceleratorCooler> componentFailCache = new Long2ObjectOpenHashMap<>(), assumedValidCache = new Long2ObjectOpenHashMap<>();
	
	
	protected boolean operational = false;
	private double excessCoolingRecipes =0;
	private double excessHeat =0;
	
	
	
	// Multiblock logic
	
	public AcceleratorLogic(Accelerator accelerator)
	{
		super(accelerator);
	}
	
	public AcceleratorLogic(AcceleratorLogic oldLogic)
	{
		super(oldLogic);
	}
	
	@Override
	public String getID()
	{
		return "";
	}
	
	
	// Multiblock methods
	
	@Override
	public void onMachineAssembled()
	{
		onAcceleratorFormed();
	}
	
	@Override
	public void onMachineRestored()
	{
		onAcceleratorFormed();
	}
	
	@Override
	public void onMachinePaused()
	{
		onAcceleratorBroken();
	}
	
	public void onMachineDisassembled()
	{
		onAcceleratorBroken();
	}
	
	public void onAssimilate(Accelerator assimilated)
	{

		multiblock.heatBuffer.mergeHeatBuffers(assimilated.heatBuffer);
		multiblock.energyStorage.mergeEnergyStorage(assimilated.energyStorage);

		if (multiblock.isAssembled())
		{

			onAcceleratorFormed();
		}
		else
		{
			onAcceleratorBroken();
		}
	}
	
	public void onAssimilated(Accelerator assimilator)
	{
	
	}
	
	// Accelerator methods
	
	public int getBeamLength()
	{
		return 0;
	}
	
	public double getBeamRadius()
	{
		return 0;
	}

	public int getCapacityMultiplier()
	{
		return multiblock.getInteriorVolume();
	}

	public @Nonnull List<Tank> getTanks(List<Tank> backupTanks)
	{
		return multiblock.isAssembled() ? multiblock.tanks : backupTanks;
	}
	
	// Multiblock validation
	
	public boolean isMachineWhole()
	{
		// vents
		boolean inlet = false;
		boolean outlet = false;
		for (TileAcceleratorVent vent : getPartMap(TileAcceleratorVent.class).values())
		{
			if (!vent.getBlockState(vent.getPos()).getValue(ACTIVE).booleanValue())
			{
				inlet = true;
			}
			else
			{
				outlet = true;
			}
		}

		if (!inlet)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.no_inlet", null);
			return false;
		}

		if (!outlet)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.no_outlet", null);
			return false;
		}

		// Energy Ports
		if (getPartMap(TileAcceleratorEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.need_energy_ports", null);
			return false;
		}

		return true;
	}
	
	@Override
	public int getMinimumInteriorLength()
	{
		return 3;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return QMDConfig.accelerator_linear_max_size;
	}
	
	public int getThickness()
	{
		return Accelerator.thickness;
	}

	@Override
	public List<Pair<Class<? extends IAcceleratorPart>, String>> getPartBlacklist()
	{
		return new ArrayList<>();
	}
	
	@Override
	public boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		
		if (MaterialHelper.isReplaceable(world.getBlockState(pos).getMaterial()) || world.getTileEntity(pos) instanceof TileAcceleratorPart) return true;
		else return multiblock.standardLastError(pos);
	}
	
	
	// Accelerator formation
	
	public void onAcceleratorFormed()
	{
		Accelerator acc = multiblock;
		for (IAcceleratorController contr : getPartMap(IAcceleratorController.class).values())
		{
			acc.controller = contr;
		}

		acc.energyStorage.setStorageCapacity(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
		acc.energyStorage.setMaxTransfer(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
		acc.heatBuffer.setHeatCapacity(QMDConfig.accelerator_base_heat_capacity * getCapacityMultiplier());
		acc.ambientTemp = 293;
		acc.tanks.get(0).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * getCapacityMultiplier());
		acc.tanks.get(1).setCapacity(QMDConfig.accelerator_base_output_tank_capacity * getCapacityMultiplier());

		if (!getWorld().isRemote)
		{

			if (acc.isNew)
			{
				// new accelerators start at ambient temperature
				acc.heatBuffer.setHeatStored(acc.ambientTemp * acc.heatBuffer.getHeatCapacity() / acc.MAX_TEMP);
			}
			acc.isNew = false;
			acc.currentHeating = 0;

			acc.updateActivity();

			for (ParticleStorageAccelerator beam : acc.beams)
			{
				beam.setMaxEnergy(Long.MAX_VALUE);
			}

			// Coolers
			acc.cooling = 0;
			acc.maxOperatingTemp = acc.MAX_TEMP;

			componentFailCache.clear();
			do
			{
				assumedValidCache.clear();
				refreshCoolers();
			}
			while (searchFlag);

			for (IAcceleratorComponent part : acc.getPartMap(IAcceleratorComponent.class).values())
			{
				if (part instanceof TileAcceleratorCooler)
				{
					TileAcceleratorCooler cooler = (TileAcceleratorCooler) part;
					if (part.isFunctional())
					{
						acc.cooling += cooler.coolingRate;
					}
				}
				else if (part instanceof TileAcceleratorMagnet || part instanceof TileAcceleratorRFCavity)
				{
					if (part.getMaxOperatingTemp() < acc.maxOperatingTemp)
					{
						acc.maxOperatingTemp = part.getMaxOperatingTemp();
					}
				}
			}
		}
	}
	
	public void setBeamlineFunctional(Set<BlockPos> beamline)
	{
		for (BlockPos pos : beamline)
		{
			if (multiblock.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam)
			{

				TileAcceleratorBeam beam = (TileAcceleratorBeam) getWorld().getTileEntity(pos);
				beam.setFunctional(true);
			}
		}
	}
	
	public void resetBeams()
	{
		for(ParticleStorageAccelerator beam : multiblock.beams)
		{
			beam.setMinEnergy(0);
			beam.setMaxEnergy(Long.MAX_VALUE);
		}
	}
	
	public void formComponents()
	{
		Accelerator acc = multiblock;
		// beam
		for (TileAcceleratorBeam beam : acc.getPartMap(TileAcceleratorBeam.class).values())
		{
			if (beam.isFunctional())
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
					acc.getQuadrupoleMap().put(beam.getPos().toLong(),
							new QuadrupoleMagnet(acc, beam.getPos(), Axis.X));
				}
				else if (acc.isValidQuadrupole(beam.getPos(), Axis.Z))
				{
					acc.getQuadrupoleMap().put(beam.getPos().toLong(),
							new QuadrupoleMagnet(acc, beam.getPos(), Axis.Z));
				}
				else if (acc.isValidDipole(beam.getPos(), false))
				{	
					acc.getDipoleMap().put(beam.getPos().toLong(), new DipoleMagnet(acc, beam.getPos()));
				}
				else if (acc.isValidDipole(beam.getPos(), true))
				{	
					acc.getDipoleMap().put(beam.getPos().toLong(), new DipoleMagnet(acc, beam.getPos()));
				}
				else if (acc.isValidDipole(beam.getPos(), true))
				{
					acc.getDipoleMap().put(beam.getPos().toLong(), new DipoleMagnet(acc, beam.getPos()));
				}
			}
		}

		acc.RFCavityNumber = acc.getRFCavityMap().size();
		acc.quadrupoleNumber = acc.getQuadrupoleMap().size();
		acc.dipoleNumber = acc.getDipoleMap().size();

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

		for (DipoleMagnet dipole : acc.dipoleMap.values())
		{
			for (IAcceleratorComponent componet : dipole.getComponents().values())
			{
				componet.setFunctional(true);
			}

		}

	}
	
	public void refreshStats()
	{
		int energy = 0;
		long heat = 0;
		int parts = 0;
		double efficiency = 0;
		double quadStrength = 0;
		double dipoleStrength = 0;
		int voltage = 0;

		for (DipoleMagnet dipole : multiblock.dipoleMap.values())
		{
			for (IAcceleratorComponent componet : dipole.getComponents().values())
			{
				if (componet instanceof TileAcceleratorMagnet)
				{
					TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) componet;
					dipoleStrength += magnet.strength;
					heat += magnet.heat;
					energy += magnet.basePower;
					parts++;
					efficiency += magnet.efficiency;
					break;
				}
			}
		}

		for (QuadrupoleMagnet quad : multiblock.getQuadrupoleMap().values())
		{
			for (IAcceleratorComponent componet : quad.getComponents().values())
			{
				if (componet instanceof TileAcceleratorMagnet)
				{
					TileAcceleratorMagnet magnet = (TileAcceleratorMagnet) componet;
					quadStrength += magnet.strength;
					heat += magnet.heat;
					energy += magnet.basePower;
					parts++;
					efficiency += magnet.efficiency;
					break;
				}
			}
		}

		for (RFCavity cavity : multiblock.getRFCavityMap().values())
		{
			for (IAcceleratorComponent componet : cavity.getComponents().values())
			{
				if (componet instanceof TileAcceleratorRFCavity)
				{
					TileAcceleratorRFCavity cav = (TileAcceleratorRFCavity) componet;
					voltage += cav.voltage;
					heat += cav.heat;
					energy += cav.basePower;
					parts++;
					efficiency += cav.efficiency;
					break;
				}
			}
		}

		for (TileAcceleratorIonSource source : multiblock.getPartMap(TileAcceleratorIonSource.class).values())
		{
			energy += source.basePower;
		}

		efficiency /= parts;
		multiblock.requiredEnergy = (int) (energy / efficiency);
		multiblock.rawHeating = heat;
		multiblock.dipoleStrength = dipoleStrength;
		multiblock.quadrupoleStrength = quadStrength;
		multiblock.acceleratingVoltage = voltage;
		multiblock.efficiency = efficiency;

	}
	
	private void refreshCoolers()
	{
		searchFlag = false;
		
		if (getPartMap(TileAcceleratorCooler.class).isEmpty())
		{
			return;
		}
		
		for (TileAcceleratorCooler cooler : getParts(TileAcceleratorCooler.class))
		{
			cooler.isSearched = cooler.isInValidPosition = false;
		}
		
		coolerCache.clear();
		
		for (TileAcceleratorCooler cooler : getParts(TileAcceleratorCooler.class))
		{
			if (cooler.isSearchRoot())
			{
				iterateCoolerSearch(cooler, coolerCache);
			}
		}
		
		for (TileAcceleratorCooler cooler : assumedValidCache.values())
		{
			if (!cooler.isInValidPosition)
			{
				componentFailCache.put(cooler.getPos().toLong(), cooler);
				searchFlag = true;
			}
		}
		
	}

	private void iterateCoolerSearch(TileAcceleratorCooler rootCooler, ObjectSet<TileAcceleratorCooler> coolerCache)
	{
		final ObjectSet<TileAcceleratorCooler> searchCache = new ObjectOpenHashSet<>();
		rootCooler.coolerSearch(coolerCache, searchCache, componentFailCache, assumedValidCache);
		
		do
		{
			final Iterator<TileAcceleratorCooler> searchIterator = searchCache.iterator();
			final ObjectSet<TileAcceleratorCooler> searchSubCache = new ObjectOpenHashSet<>();
			while (searchIterator.hasNext())
			{
				TileAcceleratorCooler component = searchIterator.next();
				searchIterator.remove();
				component.coolerSearch(coolerCache, searchSubCache, componentFailCache, assumedValidCache);
			}
			searchCache.addAll(searchSubCache);
		}
		while (!searchCache.isEmpty());
	}
	

	// Accelerator disassembly

	public void onAcceleratorBroken()
	{
		Accelerator acc = multiblock;

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

		for (DipoleMagnet dipole : acc.dipoleMap.values())
		{
			for (IAcceleratorComponent componet : dipole.getComponents().values())
			{
				componet.setFunctional(false);
			}

		}

		acc.getRFCavityMap().clear();
		acc.getQuadrupoleMap().clear();
		acc.dipoleMap.clear();

		for (TileAcceleratorBeam beam : acc.getPartMap(TileAcceleratorBeam.class).values())
		{
			beam.setFunctional(false);
		}

		for (TileAcceleratorCooler cooler : acc.getPartMap(TileAcceleratorCooler.class).values())
		{
			cooler.setFunctional(false);
		}

		for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values())
		{
			port.setRedstoneLevel(0);
		}

		for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values())
		{
			port.setIONumber(0);
		}

		operational = false;
		
		if (!getWorld().isRemote)
		{
			acc.updateActivity();
		}
	}
	
	// Accelerator Operation

	public boolean onUpdateServer()
	{
		multiblock.errorCode = Accelerator.errorCode_Nothing;
		
		multiblock.currentHeating = 0;
		operate();
		
		externalHeating();
		refreshFluidRecipe();
		
		if (canProcessFluidInputs())
		{
			produceFluidProducts();
		}
		updateRedstone();
		multiblock.updateActivity();
		
		return true;
	}
	
	public boolean isAcceleratorOn()
	{
		return operational;
	}
	
	protected void operate()
	{
		if ((isRedstonePowered() && !multiblock.computerControlled) || (multiblock.computerControlled && multiblock.energyPercentage > 0))
		{
			refreshBeams();
			if (shouldUseEnergy())
			{
				if (multiblock.energyStorage.extractEnergy(multiblock.requiredEnergy,
						true) == multiblock.requiredEnergy )
				{
					multiblock.energyStorage.changeEnergyStored(-multiblock.requiredEnergy);
					internalHeating();
				}
				else
				{
					operational = false;
					multiblock.errorCode = Accelerator.errorCode_OutOfPower;
					return;
				}
			}
			if (multiblock.getTemperature() <= multiblock.maxOperatingTemp)
			{
				operational = true;
				return;
			}
			else
			{
				if(operational)
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
	
	protected void refreshBeams()
	{
	
	}
	
	protected boolean shouldUseEnergy()
	{
		return true;
	}
	
	public void quenchMagnets()
	{
		if(QMDConfig.accelerator_explosion)
		{
			List<BlockPos> components = new ArrayList<BlockPos>();
			
			for (TileAcceleratorMagnet magnet : getPartMap(TileAcceleratorMagnet.class).values())
			{
				if(magnet.isToHot())
				{
					components.add(magnet.getPos());
				}
				
			}
			for (TileAcceleratorRFCavity cavity : getPartMap(TileAcceleratorRFCavity.class).values())
			{
				if(cavity.isToHot())
				{
					components.add(cavity.getPos());
				}
			}
			
			if(!components.isEmpty())
			{
				
				int explosions = 1+ rand.nextInt(1+components.size()/10);
				for(int i = 0; i < explosions; i++)
				{
					int j = rand.nextInt(components.size());
					BlockPos component = components.get(j);
					multiblock.WORLD.createExplosion(null, component.getX(), component.getY(), component.getZ(), 6.0f, true);
					components.remove(j);
				}
			}
		}
	}

	// Beam port IO
	protected void push()
	{
		for(TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.getIOType() == IOType.OUTPUT)
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
							otherStorage.reciveParticle(face.getOpposite(), multiblock.beams.get(port.getIONumber()).getParticleStack());
						}
					}
				}
			}
		}
	}



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

							if (!multiblock.beams.get(port.getIONumber()).reciveParticle(face, stack))
							{
								if (stack.getMeanEnergy() > multiblock.beams.get(port.getIONumber()).getMaxEnergy())
								{
									multiblock.errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
								}
								else
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



	public void switchIO()
	{
		for (TileAcceleratorBeamPort port : getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if (port.isTriggered())
			{
				if (port.getSetting() != port.getIOType())
				{
					port.switchMode();
					if (port.getIOType() == IOType.INPUT)
					{
						port.setIONumber(0);
						for (TileAcceleratorBeamPort otherPort : getPartMap(TileAcceleratorBeamPort.class).values())
						{
							if (otherPort.getIOType() == IOType.INPUT && otherPort != port)
							{
								otherPort.setIOType(IOType.DISABLED);
								otherPort.setIONumber(0);
							}
						}
					}
					else if (port.getIOType() == IOType.OUTPUT)
					{
						port.setIONumber(1);
						for (TileAcceleratorBeamPort otherPort : getPartMap(TileAcceleratorBeamPort.class).values())
						{
							if (otherPort.getIOType() == IOType.OUTPUT && otherPort != port)
							{
								otherPort.setIOType(IOType.DISABLED);
								otherPort.setIONumber(0);
							}
						}
					}
					multiblock.checkIfMachineIsWhole();
				}
				port.resetTrigger();
			}
		}
	}


	
	
	// Coolant recipe handling
	
	protected void refreshFluidRecipe()
	{
		multiblock.coolingRecipeInfo = accelerator_cooling.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), multiblock.tanks.subList(0, 1),new ArrayList<ParticleStack>());
		if (multiblock.coolingRecipeInfo != null)
		{
			multiblock.maxCoolantIn =(int) (1000*multiblock.coolingRecipeInfo.recipe.getFluidIngredients().get(0).getMaxStackSize(0)*multiblock.cooling/(double)multiblock.coolingRecipeInfo.recipe.getHeatRequired());
			multiblock.maxCoolantOut = (int) (1000*multiblock.coolingRecipeInfo.recipe.getFluidProducts().get(0).getMaxStackSize(0)*multiblock.cooling/(double)multiblock.coolingRecipeInfo.recipe.getHeatRequired());
		}
	}
	
	protected boolean canProcessFluidInputs()
	{
		
		if(multiblock.coolingRecipeInfo == null)
		{
			return false;
		}
		
		IFluidIngredient fluidInput = multiblock.coolingRecipeInfo.recipe.getFluidIngredients().get(0);
		IFluidIngredient fluidOutput = multiblock.coolingRecipeInfo.recipe.getFluidProducts().get(0);
		Tank outputTank = multiblock.tanks.get(1);
		long maximumHeatChange = multiblock.cooling;
		int recipeHeat = multiblock.coolingRecipeInfo.recipe.getHeatRequired();
		
		if(multiblock.getTemperature() <= multiblock.coolingRecipeInfo.recipe.getInputTemperature())
		{
			return false;
		}
		
		if (fluidOutput.getMaxStackSize(0) <= 0 || fluidOutput.getStack() == null)
			return false;
		
		
		double recipesPerTick = maximumHeatChange/(double)(recipeHeat);
		
		if (!outputTank.isEmpty())
		{
			if (!outputTank.getFluid().isFluidEqual(fluidOutput.getStack()))
			{
				return false;
			}
			if (outputTank.getFluidAmount() +  (recipesPerTick+excessCoolingRecipes) * fluidOutput.getMaxStackSize(0)> outputTank.getCapacity())
			{
				return false;
			}
		}
		
		if (multiblock.heatBuffer.getHeatStored() < recipeHeat)
		{
			return false;
		}

		return true;
	}
	
	private void produceFluidProducts()
	{
		
		IFluidIngredient fluidInput = multiblock.coolingRecipeInfo.recipe.getFluidIngredients().get(0);
		IFluidIngredient fluidOutput = multiblock.coolingRecipeInfo.recipe.getFluidProducts().get(0);
		Tank inputTank = multiblock.tanks.get(0);
		Tank outputTank = multiblock.tanks.get(1);
		long maximumHeatChange = multiblock.cooling;
		int recipeHeat = multiblock.coolingRecipeInfo.recipe.getHeatRequired();
		
		double recipesPerTick = maximumHeatChange/(double)(recipeHeat);
		
		if(recipesPerTick*fluidInput.getMaxStackSize(0) > inputTank.getFluidAmount())
		{
			recipesPerTick = inputTank.getFluidAmount()/(double)fluidInput.getMaxStackSize(0);
		}
		
		if(recipesPerTick * recipeHeat > multiblock.heatBuffer.getHeatStored())
		{
			recipesPerTick = multiblock.heatBuffer.getHeatStored()/(recipeHeat);
		}
		
		
		int recipesThisTick = (int) Math.floor(recipesPerTick);
		excessCoolingRecipes += recipesPerTick - recipesThisTick;

		if(excessCoolingRecipes >= 1)
		{
			recipesThisTick += (int) Math.floor(excessCoolingRecipes);
			excessCoolingRecipes -= Math.floor(excessCoolingRecipes);
		}
		
		
		inputTank.changeFluidAmount(-recipesThisTick*fluidInput.getMaxStackSize(0));
		if (inputTank.getFluidAmount() <= 0) inputTank.setFluidStored(null);
		
		if(outputTank.isEmpty())
		{
			outputTank.changeFluidStored(fluidOutput.getNextStack(0).getFluid(),recipesThisTick*fluidOutput.getMaxStackSize(0));
		}
		else
		{
			outputTank.changeFluidAmount(recipesThisTick*fluidOutput.getMaxStackSize(0));
		}
		
		
		
		double heatChange =recipesThisTick* recipeHeat;
		
		excessHeat += heatChange;
		
		if(excessHeat > 1)
		{
			long thisTickHeatChange = (long) Math.floor(excessHeat);
			excessHeat -= thisTickHeatChange;
			multiblock.heatBuffer.changeHeatStored(-thisTickHeatChange);
		}
		
	}
	
	// Heating
	
	protected void externalHeating()
	{
		multiblock.heatBuffer.addHeat(multiblock.getExternalHeating(),false);
		multiblock.currentHeating +=multiblock.getExternalHeating();
	}

	protected void internalHeating()
	{
		multiblock.heatBuffer.addHeat(multiblock.rawHeating,false);
		multiblock.currentHeating +=multiblock.rawHeating;
	}
	
	// Redstone
	
	protected boolean isRedstonePowered()
	{
		for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values())
		{
			if(!getWorld().getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{
				if(port.checkIsRedstonePowered(getWorld(), port.getPos()))
				{
					return true;
				}
			}
		}
		
		
		if (multiblock.controller != null && multiblock.controller.checkIsRedstonePowered(getWorld(), multiblock.controller.getTilePos())) return true;
		return false;
	}
	
	protected int getRedstoneLevel()
	{
		int level = getWorld().getRedstonePowerFromNeighbors(multiblock.controller.getTilePos());
		
		for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values())
		{
			if(!getWorld().getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{
				if( getWorld().getRedstonePowerFromNeighbors(port.getPos()) > level)
				{
					level = getWorld().getRedstonePowerFromNeighbors(port.getPos());
				}
			}
		}
		return level;
	}
	
	protected void updateRedstone()
	{
		
		for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values())
		{
			if(multiblock.WORLD.getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{
				port.setRedstoneLevel((int) (15 *(multiblock.getTemperature()/(double)multiblock.maxOperatingTemp)));
			}
		}
	}
	
	// Client
	
	public void onUpdateClient()
	{
	
	}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound data, SyncReason syncReason)
	{
		data.setDouble("excessCoolingRecipes", excessCoolingRecipes);
		data.setDouble("excessHeat", excessHeat);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound data, SyncReason syncReason)
	{
		excessCoolingRecipes = data.getDouble("excessCoolingRecipes");
		excessHeat = data.getDouble("excessHeat");
		
	}
	
	// Packets
	
	@Override
	public AcceleratorUpdatePacket getMultiblockUpdatePacket()
	{
		return null;
	}
	
	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
	
	}
	
	public void clearAllMaterial()
	{
		for (Tank tank : multiblock.tanks)
		{
			tank.setFluidStored(null);
		}
	}


}
