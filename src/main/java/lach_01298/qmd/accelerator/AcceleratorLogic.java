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
import lach_01298.qmd.accelerator.tile.TileAcceleratorVent;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.MaterialHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AcceleratorLogic extends MultiblockLogic<Accelerator, AcceleratorLogic, IAcceleratorPart, AcceleratorUpdatePacket> 
{ 

	public boolean searchFlag = false;
	public final ObjectSet<TileAcceleratorCooler> coolerCache = new ObjectOpenHashSet<>();
	public final Long2ObjectMap<TileAcceleratorCooler> componentFailCache = new Long2ObjectOpenHashMap<>(), assumedValidCache = new Long2ObjectOpenHashMap<>();
	
	public static final int thickness = 5;
	private boolean operational = false;
	private int excessCoolant =0; // in mirco buckets
	
	
	
	
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
		if (!getWorld().isRemote) 
		{
			getAccelerator().energyStorage.setStorageCapacity(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
			getAccelerator().energyStorage.setMaxTransfer(QMDConfig.accelerator_base_energy_capacity * getCapacityMultiplier());
			getAccelerator().heatBuffer.setHeatCapacity(QMDConfig.accelerator_base_heat_capacity * getCapacityMultiplier());
			getAccelerator().ambientTemp = 273 + (int) (getWorld().getBiome(getAccelerator().getMiddleCoord()).getTemperature(getAccelerator().getMiddleCoord())*20F);
			
			getAccelerator().tanks.get(0).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * getCapacityMultiplier());
			getAccelerator().tanks.get(1).setCapacity(QMDConfig.accelerator_base_output_tank_capacity * getCapacityMultiplier());
			
			if(!getAccelerator().cold)
			{
				getAccelerator().heatBuffer.setHeatStored(getAccelerator().ambientTemp*getAccelerator().heatBuffer.getHeatCapacity()/getAccelerator().MAX_TEMP);
			}
			getAccelerator().cold = true;

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
	
	
	public void onAssimilate(Multiblock assimilated) 
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
	
	public void onAssimilated(Multiblock assimilator) 
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
		
		if ((isRedstonePowered() && !getAccelerator().computerControlled) || (getAccelerator().computerControlled && getAccelerator().energyPercentage > 0))
		{
			if (getAccelerator().energyStorage.extractEnergy(getAccelerator().requiredEnergy,
					true) == getAccelerator().requiredEnergy)
			{
				getAccelerator().energyStorage.changeEnergyStored(-getAccelerator().requiredEnergy);
				internalHeating();
				if (getAccelerator().getTemperature() <= getAccelerator().maxOperatingTemp)
				{
					operational = true;
				}
				else
				{
					if(operational)
					{
						quenchMagnets(); 
					}
					operational = false;
					getAccelerator().errorCode = Accelerator.errorCode_ToHot;
					
				}

			}
			else
			{
				operational = false;
				getAccelerator().errorCode = Accelerator.errorCode_OutOfPower;
			}
		}
		else
		{
			operational = false;
		}
		
		externalHeating();
		refreshFluidRecipe();
		if (canProcessFluidInputs())
		{
			produceFluidProducts();
		}
		getAccelerator().sendUpdateToListeningPlayers();
		return true;
	}
	


	protected void refreshFluidRecipe()
	{
		getAccelerator().coolingRecipeInfo = accelerator_cooling.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), getAccelerator().tanks.subList(0, 1));
		if (getAccelerator().coolingRecipeInfo != null)
		{
			getAccelerator().maxCoolantIn = 1000 / getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB() * (int) (getAccelerator().cooling * getAccelerator().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getMaxStackSize(0));
			getAccelerator().maxCoolantOut = 1000 / getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB() * (int) (getAccelerator().cooling * getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0).getMaxStackSize(0));
		}
	}
	
	
	protected boolean canProcessFluidInputs() 
	{
		
		if(getAccelerator().coolingRecipeInfo == null)
		{
			return false;
		}
		
		if(getAccelerator().getTemperature() <= getAccelerator().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().getFluid().getTemperature())
		{
			return false;
		}
		
		IFluidIngredient fluidProduct = getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		if (fluidProduct.getMaxStackSize(0) <= 0 || fluidProduct.getStack() == null)
			return false;

		if (!getAccelerator().tanks.get(1).isEmpty())
		{
			if (!getAccelerator().tanks.get(1).getFluid().isFluidEqual(fluidProduct.getStack()))
			{
				return false;
			}
			else if (getAccelerator().tanks.get(1).getFluidAmount() + (getAccelerator().maxCoolantIn/1000 +1)*fluidProduct.getNextStack(0).amount > getAccelerator().tanks.get(1).getCapacity())
			{
				return false;
			}
			
			else if (getAccelerator().heatBuffer.getHeatStored() < 1)
			{
				return false;
			}
		}
		return true;
	}
	
	private void produceFluidProducts()
	{
		int uBConsumed = getAccelerator().maxCoolantIn;
		
		if(uBConsumed > getAccelerator().tanks.get(0).getFluidAmount() *1000)
		{
			uBConsumed = getAccelerator().tanks.get(0).getFluidAmount() *1000;
		}
		if(uBConsumed > getAccelerator().heatBuffer.getHeatStored())
		{
			uBConsumed = (int) getAccelerator().heatBuffer.getHeatStored();
		}
		
		
		int mBConsumed =0;
		if(uBConsumed%1000 != 0)
		{
			mBConsumed = (uBConsumed + (1000-(uBConsumed%1000)))/1000;
			excessCoolant += (1000-(uBConsumed%1000));
		}
		else
		{
			mBConsumed = uBConsumed/1000;
		}
		
		if(excessCoolant > 1000)
		{
			mBConsumed -= excessCoolant/1000;
			excessCoolant = excessCoolant%1000;
		}
		
		
		getAccelerator().tanks.get(0).changeFluidAmount(-mBConsumed);
		if (getAccelerator().tanks.get(0).getFluidAmount() <= 0) getAccelerator().tanks.get(0).setFluidStored(null);
		
		getAccelerator().heatBuffer.changeHeatStored(-mBConsumed*getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
		
		
		IFluidIngredient fluidProduct = getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		int producedCoolant = mBConsumed* fluidProduct.getNextStack(0).amount;
		if (getAccelerator().tanks.get(1).isEmpty())
		{
			getAccelerator().tanks.get(1).changeFluidStored(fluidProduct.getNextStack(0).getFluid(),producedCoolant);
		}
		else
		{
			getAccelerator().tanks.get(1).changeFluidAmount(producedCoolant);	
		}
		
	}
	

	
	
	
	
	private void externalHeating()
	{
		getAccelerator().heatBuffer.addHeat(getAccelerator().getExternalHeating(),false);
	}

	private void internalHeating()
	{
		getAccelerator().heatBuffer.addHeat(getAccelerator().rawHeating,false);
	}

	public boolean isAcceleratorOn() 
	{
		return operational;
	}
	
	
	protected boolean isRedstonePowered() 
	{
		if (getAccelerator().controller != null && getAccelerator().controller.checkIsRedstonePowered(getWorld(), getAccelerator().controller.getTilePos())) return true;
		return false;
	}
	
	
	public void quenchMagnets() 
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
	

	
	// Client
	
	public void onUpdateClient() 
	{
		
	}
	
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		logicTag.setInteger("excessCoolant", excessCoolant);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		excessCoolant = logicTag.getInteger("excessCoolant");
		
	}
	
	// Packets
	
	@Override
	public AcceleratorUpdatePacket getUpdatePacket() 
	{
		return null;
	}
	
	@Override
	public void onPacket(AcceleratorUpdatePacket message) 
	{
		
	}
	
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) 
	{
		return null;
	}
	
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
