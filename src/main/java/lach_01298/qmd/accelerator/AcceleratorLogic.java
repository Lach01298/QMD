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
	
	public static final int thickness = 5;
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
	
	public Accelerator getAccelerator()
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
			 getAccelerator().controller = contr;
		}
	
		getAccelerator().energyStorage.setStorageCapacity(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
		getAccelerator().energyStorage.setMaxTransfer(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
		getAccelerator().heatBuffer.setHeatCapacity(QMDConfig.accelerator_base_heat_capacity * getCapacityMultiplier());
		getAccelerator().ambientTemp = 273 + (int) (getWorld().getBiome(getAccelerator().getMiddleCoord()).getTemperature(getAccelerator().getMiddleCoord())*20F);
		getAccelerator().tanks.get(0).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * getCapacityMultiplier());
		getAccelerator().tanks.get(1).setCapacity(QMDConfig.accelerator_base_output_tank_capacity * getCapacityMultiplier());
		
		if (!getWorld().isRemote) 
		{
			
			
			if(getAccelerator().isNew)
			{
				// new accelerators start at ambient temperature
				getAccelerator().heatBuffer.setHeatStored(getAccelerator().ambientTemp*getAccelerator().heatBuffer.getHeatCapacity()/getAccelerator().MAX_TEMP);
			}
			getAccelerator().isNew = false;
			getAccelerator().currentHeating = 0;

			getAccelerator().updateActivity();	
			
			
			for(ParticleStorageAccelerator beam:  getAccelerator().beams)
			{
				beam.setMaxEnergy(Long.MAX_VALUE);
			}
			
			
			//Coolers
			getAccelerator().cooling = 0;
			getAccelerator().maxOperatingTemp = getAccelerator().MAX_TEMP;
			 
			
			
			componentFailCache.clear();
			do {
				assumedValidCache.clear();
				refreshCoolers();
			}
			while (searchFlag);
			
						
			for (IAcceleratorComponent part :getAccelerator().getPartMap(IAcceleratorComponent.class).values())
			 {
				 if(part instanceof TileAcceleratorCooler)
				 { 
					TileAcceleratorCooler cooler = (TileAcceleratorCooler) part;
					if(part.isFunctional())
					{
						getAccelerator().cooling += cooler.coolingRate;
					}
				 }
				 else if(part instanceof TileAcceleratorMagnet ||part instanceof TileAcceleratorRFCavity)
				 {
					 if(part.getMaxOperatingTemp() < getAccelerator().maxOperatingTemp)
					 {
						 getAccelerator().maxOperatingTemp = part.getMaxOperatingTemp();
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
		return getAccelerator().getInteriorVolume();
	}

	@Override
	public void onMachinePaused() 
	{
		onAcceleratorBroken();
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
			getAccelerator().updateActivity();
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
			getAccelerator().heatBuffer.mergeHeatBuffers(assimilatedAccelerator.heatBuffer);
			getAccelerator().energyStorage.mergeEnergyStorage(assimilatedAccelerator.energyStorage);
		}
		
		if (getAccelerator().isAssembled()) {
			
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
		
		for (DipoleMagnet dipole : getAccelerator().dipoleMap.values())
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
		
		for (QuadrupoleMagnet quad : getAccelerator().getQuadrupoleMap().values())
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
		
		for (RFCavity cavity : getAccelerator().getRFCavityMap().values())
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
		
		
		
		efficiency /= parts;
		getAccelerator().requiredEnergy =  (int) (energy/efficiency);
		getAccelerator().rawHeating = heat;
		getAccelerator().dipoleStrength = dipoleStrength;
		getAccelerator().quadrupoleStrength = quadStrength;
		getAccelerator().efficiency = efficiency;
		getAccelerator().acceleratingVoltage= voltage;
		
		
	}
	

	// Server
	
	public boolean onUpdateServer()
	{
		getAccelerator().errorCode = Accelerator.errorCode_Nothing;
		
		getAccelerator().currentHeating = 0;
		operate();
		
		externalHeating();
		refreshFluidRecipe();
		
		if (canProcessFluidInputs())
		{
			produceFluidProducts();
		}
		updateRedstone();
		getAccelerator().updateActivity();
		
		return true;
	}
	
	protected void refreshBeams()
	{
		
	}
	
	
	private void operate()
	{
		if ((isRedstonePowered() && !getAccelerator().computerControlled) || (getAccelerator().computerControlled && getAccelerator().energyPercentage > 0))
		{
			refreshBeams();			
			if (shouldUseEnergy())
			{
				if (getAccelerator().energyStorage.extractEnergy(getAccelerator().requiredEnergy,
						true) == getAccelerator().requiredEnergy )
				{
					getAccelerator().energyStorage.changeEnergyStored(-getAccelerator().requiredEnergy);
					internalHeating();
				}
				else
				{
					operational = false;
					getAccelerator().errorCode = Accelerator.errorCode_OutOfPower;
					return;
				}
			}
			if (getAccelerator().getTemperature() <= getAccelerator().maxOperatingTemp)
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
				getAccelerator().errorCode = Accelerator.errorCode_ToHot;
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
		getAccelerator().coolingRecipeInfo = accelerator_cooling.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), getAccelerator().tanks.subList(0, 1));
		if (getAccelerator().coolingRecipeInfo != null)
		{
			getAccelerator().maxCoolantIn =(int) (getAccelerator().cooling/(double)getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB()*1000);
			getAccelerator().maxCoolantOut = (int) (getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0).getMaxStackSize(0)*getAccelerator().cooling/(double)(getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB()*getAccelerator().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getMaxStackSize(0))*1000);
		}
	}
	
	
	protected boolean canProcessFluidInputs() 
	{
		
		if(getAccelerator().coolingRecipeInfo == null)
		{
			return false;
		}
		
		IFluidIngredient fluidInput = getAccelerator().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0);
		IFluidIngredient fluidOutput = getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		Tank outputTank = getAccelerator().tanks.get(1);
		long maximumHeatChange = getAccelerator().cooling;
		int heatPerMB = getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB();
		
		if(getAccelerator().getTemperature() <= fluidInput.getStack().getFluid().getTemperature())
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
		
		if (getAccelerator().heatBuffer.getHeatStored() < fluidInput.getMaxStackSize(0)*heatPerMB)
		{
			return false;
		}
		
		return true;
	}
	
	private void produceFluidProducts()
	{
		
		IFluidIngredient fluidInput = getAccelerator().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0);
		IFluidIngredient fluidOutput = getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		Tank inputTank = getAccelerator().tanks.get(0);
		Tank outputTank = getAccelerator().tanks.get(1);
		long maximumHeatChange = getAccelerator().cooling;
		int heatPerMB = getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB();
		
		double recipesPerTick = maximumHeatChange/(double)(fluidInput.getMaxStackSize(0)*heatPerMB);
		
		if(recipesPerTick*fluidInput.getMaxStackSize(0) > inputTank.getFluidAmount())
		{
			recipesPerTick = inputTank.getFluidAmount()/(double)fluidInput.getMaxStackSize(0);
		}
		
		if(recipesPerTick * fluidInput.getMaxStackSize(0) * heatPerMB > getAccelerator().heatBuffer.getHeatStored())
		{
			recipesPerTick = getAccelerator().heatBuffer.getHeatStored()/(fluidInput.getMaxStackSize(0) * heatPerMB);
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
			getAccelerator().heatBuffer.changeHeatStored(-thisTickHeatChange);
		}
		
	}
	

	
	
	
	
	private void externalHeating()
	{
		getAccelerator().heatBuffer.addHeat(getAccelerator().getExternalHeating(),false);
		getAccelerator().currentHeating +=getAccelerator().getExternalHeating();
	}

	private void internalHeating()
	{
		getAccelerator().heatBuffer.addHeat(getAccelerator().rawHeating,false);
		getAccelerator().currentHeating +=getAccelerator().rawHeating;
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
		
		
		if (getAccelerator().controller != null && getAccelerator().controller.checkIsRedstonePowered(getWorld(), getAccelerator().controller.getTilePos())) return true;
		return false;
	}
	
	protected int getRedstoneLevel()
	{
		int level = getWorld().getRedstonePowerFromNeighbors(getAccelerator().controller.getTilePos());
		
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
			if(getAccelerator().WORLD.getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{		
				port.setRedstoneLevel((int) (15 *(getAccelerator().getTemperature()/(double)getAccelerator().maxOperatingTemp)));	
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
		for (Tank tank : getAccelerator().tanks)
		{
			tank.setFluidStored(null);
		}
	}


	
	
	@Override
	public boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		
		if (MaterialHelper.isReplaceable(world.getBlockState(pos).getMaterial()) || world.getTileEntity(pos) instanceof TileAcceleratorPart) return true;
		else return getAccelerator().standardLastError(pos);
	}

	
	protected void push()
	{
		if(getAccelerator().output != null && getAccelerator().output.getExternalFacing() != null)
		{
			TileEntity tile = getAccelerator().WORLD.getTileEntity(getAccelerator().output.getPos().offset(getAccelerator().output.getExternalFacing()));
			if (tile != null)
			{
				if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getAccelerator().output.getExternalFacing().getOpposite()))
				{
					IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getAccelerator().output.getExternalFacing().getOpposite());
					otherStorage.reciveParticle(getAccelerator().output.getExternalFacing().getOpposite(), getAccelerator().beams.get(1).getParticleStack());
				}
			}
		}
	}
	
	protected void pull()
	{
		if (getAccelerator().input != null && getAccelerator().input.getExternalFacing() != null)
		{
			
				TileEntity tile = getAccelerator().WORLD.getTileEntity(getAccelerator().input.getPos().offset(getAccelerator().input.getExternalFacing()));
				if (tile != null)
				{

					if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,
							getAccelerator().input.getExternalFacing().getOpposite()))
					{
						IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, getAccelerator().input.getExternalFacing().getOpposite());
						ParticleStack stack = otherStorage.extractParticle(getAccelerator().input.getExternalFacing().getOpposite());
						if (!getAccelerator().beams.get(0).reciveParticle(getAccelerator().input.getExternalFacing(), stack))
						{
							if (stack.getMeanEnergy() > getAccelerator().beams.get(0).getMaxEnergy())
							{
								
								getAccelerator().errorCode = Accelerator.errorCode_InputParticleEnergyToHigh;
							}
							else
							{
								getAccelerator().errorCode = Accelerator.errorCode_InputParticleEnergyToLow;

							}
						}
					}
				}
			
		}
	}

	

	
	public @Nonnull List<Tank> getVentTanks(List<Tank> backupTanks)
	{
		return getAccelerator().isAssembled() ? getAccelerator().tanks : backupTanks;
	}

}
