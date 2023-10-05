package lach_01298.qmd.accelerator;

import static lach_01298.qmd.recipes.QMDRecipes.accelerator_cooling;
import static nc.block.property.BlockProperties.ACTIVE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRedstonePort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorVent;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.IPacketMultiblockLogic;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.MaterialHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AcceleratorLogic extends MultiblockLogic<Accelerator, AcceleratorLogic, IAcceleratorPart>
		implements IPacketMultiblockLogic<Accelerator, AcceleratorLogic, IAcceleratorPart, AcceleratorUpdatePacket>
{ 

	public boolean searchFlag = false;
	public final ObjectSet<TileAcceleratorCooler> coolerCache = new ObjectOpenHashSet<>();
	public final Long2ObjectMap<TileAcceleratorCooler> componentFailCache = new Long2ObjectOpenHashMap<>(), assumedValidCache = new Long2ObjectOpenHashMap<>();
	
	
	protected boolean operational = false;
	private double excessCoolingRecipes =0;
	private double excessHeat =0;
	
	
	
	
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
	
	public Accelerator getMultiblock()
	{
		return multiblock;
	}

	public void onResetStats() {}
	
	// Multiblock Size Limits
	
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
	
	
	// Multiblock Methods
	
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
	
	public int getBeamLength() 
	{
		return 0;
	}
	
	public double getBeamRadius() 
	{
		return 0;
	}
	

	public void onAcceleratorFormed() 
	{
		for (IAcceleratorController contr : getPartMap(IAcceleratorController.class).values()) 
		{
			 getMultiblock().controller = contr;
		}
	
		getMultiblock().energyStorage.setStorageCapacity(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
		getMultiblock().energyStorage.setMaxTransfer(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
		getMultiblock().heatBuffer.setHeatCapacity(QMDConfig.accelerator_base_heat_capacity * getCapacityMultiplier());
		getMultiblock().ambientTemp = 273 + (int) (getWorld().getBiome(getMultiblock().getMiddleCoord()).getTemperature(getMultiblock().getMiddleCoord())*20F);
		getMultiblock().tanks.get(0).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * getCapacityMultiplier());
		getMultiblock().tanks.get(1).setCapacity(QMDConfig.accelerator_base_output_tank_capacity * getCapacityMultiplier());
		
		if (!getWorld().isRemote) 
		{
			
			
			if(getMultiblock().isNew)
			{
				// new accelerators start at ambient temperature
				getMultiblock().heatBuffer.setHeatStored(getMultiblock().ambientTemp*getMultiblock().heatBuffer.getHeatCapacity()/getMultiblock().MAX_TEMP);
			}
			getMultiblock().isNew = false;
			getMultiblock().currentHeating = 0;

			getMultiblock().updateActivity();	
			
			
			for(ParticleStorageAccelerator beam:  getMultiblock().beams)
			{
				beam.setMaxEnergy(Long.MAX_VALUE);
			}
			
			
			//Coolers
			getMultiblock().cooling = 0;
			getMultiblock().maxOperatingTemp = getMultiblock().MAX_TEMP;
			 
			
			
			componentFailCache.clear();
			do {
				assumedValidCache.clear();
				refreshCoolers();
			}
			while (searchFlag);
			
						
			for (IAcceleratorComponent part :getMultiblock().getPartMap(IAcceleratorComponent.class).values())
			 {
				 if(part instanceof TileAcceleratorCooler)
				 { 
					TileAcceleratorCooler cooler = (TileAcceleratorCooler) part;
					if(part.isFunctional())
					{
						getMultiblock().cooling += cooler.coolingRate;
					}
				 }
				 else if(part instanceof TileAcceleratorMagnet || part instanceof TileAcceleratorRFCavity)
				 {
					 if(part.getMaxOperatingTemp() < getMultiblock().maxOperatingTemp)
					 {
						 getMultiblock().maxOperatingTemp = part.getMaxOperatingTemp();
					 }
				 }
			 }
		}
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
	

	public int getCapacityMultiplier() 
	{
		return getMultiblock().getInteriorVolume();
	}

	@Override
	public void onMachinePaused() 
	{
		onAcceleratorBroken();
	}
	
	public void onMachineDisassembled()
	{
	 Accelerator acc = getMultiblock();
		 
	 	
	 	
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
		
		for (TileAcceleratorBeam beam :acc.getPartMap(TileAcceleratorBeam.class).values())
		{
			beam.setFunctional(false);
		}
		
		for (TileAcceleratorCooler cooler :acc.getPartMap(TileAcceleratorCooler.class).values())
		{
			cooler.setFunctional(false);
		}
		
		for (TileAcceleratorRedstonePort port : getPartMap(TileAcceleratorRedstonePort.class).values())
		{
			port.setRedstoneLevel(0);	
		}
		
		
		operational = false;
		onAcceleratorBroken();
		
	}

	public void onAcceleratorBroken()
	{
		if (!getWorld().isRemote)
		{
			getMultiblock().updateActivity();
		}
	}
	
	
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
	public List<Pair<Class<? extends IAcceleratorPart>, String>> getPartBlacklist()
	{
		return new ArrayList<>();
	}
	
	
	public void onAssimilate(Accelerator assimilated) 
	{	
		if (assimilated instanceof Accelerator)
		{
			Accelerator assimilatedAccelerator = (Accelerator) assimilated;
			getMultiblock().heatBuffer.mergeHeatBuffers(assimilatedAccelerator.heatBuffer);
			getMultiblock().energyStorage.mergeEnergyStorage(assimilatedAccelerator.energyStorage);
		}
		
		if (getMultiblock().isAssembled()) {
			
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
	
	
	public void refreshStats()
	{
		int energy = 0;
		long heat = 0;
		int parts= 0;
		double efficiency =0;
		double quadStrength =0;
		double dipoleStrength =0;
		int voltage = 0;
		
		for (DipoleMagnet dipole : getMultiblock().dipoleMap.values())
		{
			for (IAcceleratorComponent componet : dipole.getComponents().values())
			{
				if(componet instanceof TileAcceleratorMagnet)
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
		
		for (QuadrupoleMagnet quad : getMultiblock().getQuadrupoleMap().values())
		{
			for (IAcceleratorComponent componet : quad.getComponents().values())
			{
				if(componet instanceof TileAcceleratorMagnet)
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
		
		for (RFCavity cavity : getMultiblock().getRFCavityMap().values())
		{
			for (IAcceleratorComponent componet : cavity.getComponents().values())
			{
				if(componet instanceof TileAcceleratorRFCavity)
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
		
		for (TileAcceleratorIonSource source : getMultiblock().getPartMap(TileAcceleratorIonSource.class).values())
		{		
			energy += source.basePower;
		}
		
		efficiency /= parts;
		getMultiblock().requiredEnergy =  (int) (energy/efficiency);
		getMultiblock().rawHeating = heat;
		getMultiblock().dipoleStrength = dipoleStrength;
		getMultiblock().quadrupoleStrength = quadStrength;
		getMultiblock().efficiency = efficiency;
		getMultiblock().acceleratingVoltage= voltage;
		
		
	}
	

	// Server
	
	public boolean onUpdateServer()
	{
		getMultiblock().errorCode = Accelerator.errorCode_Nothing;
		
		getMultiblock().currentHeating = 0;
		operate();
		
		externalHeating();
		refreshFluidRecipe();
		
		if (canProcessFluidInputs())
		{
			produceFluidProducts();
		}
		updateRedstone();
		getMultiblock().updateActivity();
		
		return true;
	}
	
	protected void refreshBeams()
	{
		
	}
	
	
	protected void operate()
	{
		if ((isRedstonePowered() && !getMultiblock().computerControlled) || (getMultiblock().computerControlled && getMultiblock().energyPercentage > 0))
		{
			refreshBeams();			
			if (shouldUseEnergy())
			{
				if (getMultiblock().energyStorage.extractEnergy(getMultiblock().requiredEnergy,
						true) == getMultiblock().requiredEnergy )
				{
					getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
					internalHeating();
				}
				else
				{
					operational = false;
					getMultiblock().errorCode = Accelerator.errorCode_OutOfPower;
					return;
				}
			}
			if (getMultiblock().getTemperature() <= getMultiblock().maxOperatingTemp)
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
				getMultiblock().errorCode = Accelerator.errorCode_ToHot;
				return;
				
			}	
		}
		else
		{
			operational = false;
			return;
		}
	}
	
	protected boolean shouldUseEnergy()
	{
		return true;
	}
	


	protected void refreshFluidRecipe()
	{
		getMultiblock().coolingRecipeInfo = accelerator_cooling.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), getMultiblock().tanks.subList(0, 1));
		if (getMultiblock().coolingRecipeInfo != null)
		{
			getMultiblock().maxCoolantIn =(int) (getMultiblock().cooling/(double)getMultiblock().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB()*1000);
			getMultiblock().maxCoolantOut = (int) (getMultiblock().coolingRecipeInfo.getRecipe().getFluidProducts().get(0).getMaxStackSize(0)*getMultiblock().cooling/(double)(getMultiblock().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB()*getMultiblock().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getMaxStackSize(0))*1000);
		}
	}
	
	
	protected boolean canProcessFluidInputs() 
	{
		
		if(getMultiblock().coolingRecipeInfo == null)
		{
			return false;
		}
		
		IFluidIngredient fluidInput = getMultiblock().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0);
		IFluidIngredient fluidOutput = getMultiblock().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		Tank outputTank = getMultiblock().tanks.get(1);
		long maximumHeatChange = getMultiblock().cooling;
		int heatPerMB = getMultiblock().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB();
		
		if(getMultiblock().getTemperature() <= fluidInput.getStack().getFluid().getTemperature())
		{
			return false;
		}
		
		if (fluidOutput.getMaxStackSize(0) <= 0 || fluidOutput.getStack() == null)
			return false;
		
		
		double recipesPerTick = maximumHeatChange/(double)(fluidInput.getMaxStackSize(0)*heatPerMB);
		
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
		
		if (getMultiblock().heatBuffer.getHeatStored() < fluidInput.getMaxStackSize(0)*heatPerMB)
		{
			return false;
		}
		
		return true;
	}
	
	private void produceFluidProducts()
	{
		
		IFluidIngredient fluidInput = getMultiblock().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0);
		IFluidIngredient fluidOutput = getMultiblock().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		Tank inputTank = getMultiblock().tanks.get(0);
		Tank outputTank = getMultiblock().tanks.get(1);
		long maximumHeatChange = getMultiblock().cooling;
		int heatPerMB = getMultiblock().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB();
		
		double recipesPerTick = maximumHeatChange/(double)(fluidInput.getMaxStackSize(0)*heatPerMB);
		
		if(recipesPerTick*fluidInput.getMaxStackSize(0) > inputTank.getFluidAmount())
		{
			recipesPerTick = inputTank.getFluidAmount()/(double)fluidInput.getMaxStackSize(0);
		}
		
		if(recipesPerTick * fluidInput.getMaxStackSize(0) * heatPerMB > getMultiblock().heatBuffer.getHeatStored())
		{
			recipesPerTick = getMultiblock().heatBuffer.getHeatStored()/(fluidInput.getMaxStackSize(0) * heatPerMB);
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
		
		
		
		double heatChange =recipesThisTick*fluidInput.getMaxStackSize(0)* heatPerMB;
		
		excessHeat += heatChange;
		
		if(excessHeat > 1)
		{
			long thisTickHeatChange = (long) Math.floor(excessHeat);
			excessHeat -= thisTickHeatChange;
			getMultiblock().heatBuffer.changeHeatStored(-thisTickHeatChange);
		}
		
	}
	

	
	
	
	
	protected void externalHeating()
	{
		getMultiblock().heatBuffer.addHeat(getMultiblock().getExternalHeating(),false);
		getMultiblock().currentHeating +=getMultiblock().getExternalHeating();
	}

	protected void internalHeating()
	{
		getMultiblock().heatBuffer.addHeat(getMultiblock().rawHeating,false);
		getMultiblock().currentHeating +=getMultiblock().rawHeating;
	}
	

	public boolean isAcceleratorOn() 
	{
		return operational;
	}
	
	
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
		
		
		if (getMultiblock().controller != null && getMultiblock().controller.checkIsRedstonePowered(getWorld(), getMultiblock().controller.getTilePos())) return true;
		return false;
	}
	
	protected int getRedstoneLevel()
	{
		int level = getWorld().getRedstonePowerFromNeighbors(getMultiblock().controller.getTilePos());
		
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
			if(getMultiblock().WORLD.getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{		
				port.setRedstoneLevel((int) (15 *(getMultiblock().getTemperature()/(double)getMultiblock().maxOperatingTemp)));	
			}
		}
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
	
	/*public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) 
	{
		return null;
	}*/
	
	public void clearAllMaterial()
	{
		for (Tank tank : getMultiblock().tanks)
		{
			tank.setFluidStored(null);
		}
	}


	
	
	@Override
	public boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		
		if (MaterialHelper.isReplaceable(world.getBlockState(pos).getMaterial()) || world.getTileEntity(pos) instanceof TileAcceleratorPart) return true;
		else return getMultiblock().standardLastError(pos);
	}

	
	protected void push()
	{
		if(getMultiblock().output != null && getMultiblock().output.getExternalFacing() != null)
		{
			TileEntity tile = getMultiblock().WORLD.getTileEntity(getMultiblock().output.getPos().offset(getMultiblock().output.getExternalFacing()));
			if (tile != null)
			{
				if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getMultiblock().output.getExternalFacing().getOpposite()))
				{
					IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getMultiblock().output.getExternalFacing().getOpposite());
					otherStorage.reciveParticle(getMultiblock().output.getExternalFacing().getOpposite(), getMultiblock().beams.get(1).getParticleStack());
				}
			}
		}
	}
	
	protected void pull()
	{
		if (getMultiblock().input != null && getMultiblock().input.getExternalFacing() != null)
		{
			
				TileEntity tile = getMultiblock().WORLD.getTileEntity(getMultiblock().input.getPos().offset(getMultiblock().input.getExternalFacing()));
				if (tile != null)
				{

					if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,
							getMultiblock().input.getExternalFacing().getOpposite()))
					{
						IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getMultiblock().input.getExternalFacing().getOpposite());
						ParticleStack stack = otherStorage.extractParticle(getMultiblock().input.getExternalFacing().getOpposite());
						if (!getMultiblock().beams.get(0).reciveParticle(getMultiblock().input.getExternalFacing(), stack))
						{
							if (stack.getMeanEnergy() > getMultiblock().beams.get(0).getMaxEnergy())
							{
								
								getMultiblock().errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
							}
							else
							{
								getMultiblock().errorCode = Accelerator.errorCode_InputParticleEnergyToLow;

							}
						}
					}
				}
			
		}
	}

	

	
	public @Nonnull List<Tank> getTanks(List<Tank> backupTanks)
	{
		return getMultiblock().isAssembled() ? getMultiblock().tanks : backupTanks;
	}

}
