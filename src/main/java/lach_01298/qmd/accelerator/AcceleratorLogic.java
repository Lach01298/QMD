package lach_01298.qmd.accelerator;

import static lach_01298.qmd.recipes.QMDRecipes.accelerator_cooling;
import static nc.block.property.BlockProperties.ACTIVE;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.accelerator.tile.TileAcceleratorVent;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.fission.tile.TileFissionIrradiator;
import nc.multiblock.fission.tile.port.TileFissionIrradiatorPort;
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

	
	
	
	public static final int thickness = 5;
	private boolean operational = false;
	private double excessCoolantIn =0;
	private double excessCoolantOut =0;
	
	
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
	
	protected Accelerator getAccelerator() 
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
	
	public void onAcceleratorFormed() 
	{
		for (IAcceleratorController contr : getPartMap(IAcceleratorController.class).values()) 
		{
			 getAccelerator().controller = contr;
		}
		
		getAccelerator().energyStorage.setStorageCapacity(Accelerator.BASE_MAX_ENERGY * getCapacityMultiplier());
		getAccelerator().energyStorage.setMaxTransfer(Accelerator.BASE_MAX_ENERGY * getCapacityMultiplier());
		getAccelerator().heatBuffer.setHeatCapacity(Accelerator.BASE_MAX_HEAT * getCapacityMultiplier());
		getAccelerator().ambientTemp = 273 + (int) (getWorld().getBiome(getAccelerator().getMiddleCoord()).getTemperature(getAccelerator().getMiddleCoord())*20F);
		
		getAccelerator().tanks.get(0).setCapacity(Accelerator.BASE_MAX_INPUT * getCapacityMultiplier());
		getAccelerator().tanks.get(1).setCapacity(Accelerator.BASE_MAX_OUTPUT * getCapacityMultiplier());
		
		if(!getAccelerator().cold)
		{
			getAccelerator().heatBuffer.setHeatStored(getAccelerator().ambientTemp*getAccelerator().heatBuffer.getHeatCapacity()/getAccelerator().MAX_TEMP);
		}
		getAccelerator().cold = true;
		
		
		
		if (!getWorld().isRemote) 
		{
			refreshConnections();
			getAccelerator().updateActivity();	
		}
		
		
		
		 //Coolers
		getAccelerator().cooling = 0;
		getAccelerator().maxOperatingTemp = getAccelerator().MAX_TEMP;
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
	
	
	public boolean isMachineWhole(Multiblock multiblock) 
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
		if (!(assimilated instanceof Accelerator)) return;
		Accelerator assimilatedAccelerator = (Accelerator) assimilated;
		getAccelerator().heatBuffer.mergeHeatBuffers(assimilatedAccelerator.heatBuffer);
	}
	
	public void onAssimilated(Multiblock assimilator) 
	{
		
	}
	
	public void refreshConnections()
	{
		
	}

	public void refreshAccelerator()
	{
		
		
	}
	// Server
	
	public boolean onUpdateServer()
	{

		if (isRedstonePowered())
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
		
	
		getAccelerator().coolingRecipeInfo = accelerator_cooling.getRecipeInfoFromInputs(new ArrayList<ItemStack>(),getAccelerator().tanks.subList(0, 1));
		if(getAccelerator().coolingRecipeInfo != null)
		{
			getAccelerator().maxCoolantIn =   (double)(getAccelerator().cooling* getAccelerator().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getMaxStackSize(0))/ (double)(getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
			getAccelerator().maxCoolantOut =  (double)(getAccelerator().cooling* getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0).getMaxStackSize(0))/ (double)(getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
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
			else if (getAccelerator().tanks.get(1).getFluidAmount() + fluidProduct.getMaxStackSize(0) > getAccelerator().tanks.get(1).getCapacity())
			{
				return false;
			}
			
			else if (getAccelerator().heatBuffer.getHeatStored() < getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB())
			{
				return false;
			}
		}
		return true;
	}
	
	private void produceFluidProducts()
	{
		int fluidIngredientStackSize =(int) getAccelerator().maxCoolantIn;
		excessCoolantIn += getAccelerator().maxCoolantIn - Math.floor(getAccelerator().maxCoolantIn);
		
		int fluidOutputStackSize = (int) getAccelerator().maxCoolantOut;
		excessCoolantOut += getAccelerator().maxCoolantOut - Math.floor(getAccelerator().maxCoolantOut);
		
		
		if(excessCoolantIn >= 1)
		{
			fluidIngredientStackSize += Math.floor(excessCoolantIn);
			excessCoolantIn -= Math.floor(excessCoolantIn);
		
		}
		
		if(excessCoolantOut >= 1)
		{
			fluidOutputStackSize += Math.floor(excessCoolantOut);
			excessCoolantOut -= Math.floor(excessCoolantOut);
		}
		
		if(fluidIngredientStackSize > 0)
		{
			int heatUsed = (int) ((fluidIngredientStackSize/getAccelerator().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getMaxStackSize(0))*getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
			
			double recipeRatio =getAccelerator().tanks.get(0).getFluidAmount()/fluidIngredientStackSize;
			
			if(recipeRatio >(getAccelerator().tanks.get(1).getCapacity()-getAccelerator().tanks.get(1).getFluidAmount()/fluidOutputStackSize))
			{
				 recipeRatio =(getAccelerator().tanks.get(1).getCapacity()-getAccelerator().tanks.get(1).getFluidAmount())/fluidOutputStackSize;
			}
			if(recipeRatio > getAccelerator().heatBuffer.getHeatStored()/heatUsed)
			{
				 recipeRatio =getAccelerator().heatBuffer.getHeatStored()/heatUsed;
			}
			
			if(recipeRatio > 1)
			{
				recipeRatio = 1;
			}
			IFluidIngredient fluidProduct = getAccelerator().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
			
			if (getAccelerator().tanks.get(1).isEmpty())
			{
				getAccelerator().tanks.get(1).setFluidStored(fluidProduct.getNextStack(0));
				getAccelerator().tanks.get(1).setFluidAmount((int) (fluidOutputStackSize * recipeRatio));
			}
			else
			{
				getAccelerator().tanks.get(1).changeFluidAmount((int) (fluidOutputStackSize * recipeRatio));
			}
			
			getAccelerator().tanks.get(0).changeFluidAmount(-(int)(fluidIngredientStackSize*recipeRatio));
			getAccelerator().heatBuffer.changeHeatStored(-(int)(heatUsed*recipeRatio));
			if (getAccelerator().tanks.get(0).getFluidAmount() <= 0) getAccelerator().tanks.get(0).setFluidStored(null);
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
		
	}

	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
	
		
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
	public boolean isBlockGoodForInterior(World world, int x, int y, int z, Multiblock multiblock)
	{
		BlockPos pos = new BlockPos(x, y, z);
		if (MaterialHelper.isReplaceable(world.getBlockState(pos).getMaterial()) || world.getTileEntity(pos) instanceof TileAcceleratorPart) return true;
		else return getAccelerator().standardLastError(x, y, z, multiblock);
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
