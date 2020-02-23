package lach_01298.qmd.multiblock.accelerator;

import static lach_01298.qmd.recipes.QMDRecipes.accelerator_cooling;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.sun.jna.platform.win32.WinUser.INPUT;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorOutlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPart;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.config.NCConfig;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.turbine.tile.TileTurbinePart;
import nc.network.PacketHandler;
import nc.recipe.NCRecipes;
import nc.recipe.ProcessorRecipe;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.MaterialHelper;
import nc.util.NCMath;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AcceleratorLogic extends MultiblockLogic<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket> 
{ 

	
	
	
	public static final int thickness = 5;
	private boolean operational = false;
	private double excessCoolantIn =0;
	private double excessCoolantOut =0;
	
	public Random rand = new Random();
	
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
		
		if(!getAccelerator().cold)
		{
			getAccelerator().heatBuffer.setHeatStored(getAccelerator().heatBuffer.getHeatCapacity());
		}
		getAccelerator().cold = true;
		
		getAccelerator().tanks.get(0).setCapacity(Accelerator.BASE_MAX_INPUT * getCapacityMultiplier());
		getAccelerator().tanks.get(1).setCapacity(Accelerator.BASE_MAX_OUTPUT * getCapacityMultiplier());
		
		
		if (!getWorld().isRemote) 
		{
			refreshAccelerator();
			getAccelerator().updateActivity();	
		}
		
		
		
		 //Coolers
		getAccelerator().cooling = 0;
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
			
		 }
	
	}
	
	
	public int getCapacityMultiplier() 
	{
		return getAccelerator().getExteriorVolume();
	}
	
	


	public void refreshAccelerator() 
	{

	}
	
	
	@Override
	public void onMachinePaused() {}
	
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
		
		acc.resetStats();
		if (acc.controller != null)
		{
			acc.controller.updateBlockState(false);
		}
		acc.isAcceleratorOn = false;
		operational = false;
	}

	public boolean isMachineWhole(Multiblock multiblock) 
	{
		multiblock.setLastError("zerocore.api.nc.multiblock.validation.invalid_logic", null);
		return false;
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
	

	
	// Server
	
	public boolean onUpdateServer()
	{
		if (getAccelerator().getTemperature() <= getAccelerator().ambientTemp)
		{
			if (isRedstonePowered())
			{
				if (getAccelerator().energyStorage.extractEnergy(getAccelerator().requiredEnergy,true) == getAccelerator().requiredEnergy)
				{
					getAccelerator().energyStorage.changeEnergyStored(-getAccelerator().requiredEnergy);
					internalHeating();
					if (getAccelerator().getTemperature() < getAccelerator().MAX_OPERATING_TEMP)
					{
						operational = true;
					}
					else
					{	
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
			getAccelerator().maxCoolantIn =   (double)(getAccelerator().cooling* getAccelerator().coolingRecipeInfo.getRecipe().fluidIngredients().get(0).getMaxStackSize(0))/ (double)(getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
			getAccelerator().maxCoolantOut =  (double)(getAccelerator().cooling* getAccelerator().coolingRecipeInfo.getRecipe().fluidProducts().get(0).getMaxStackSize(0))/ (double)(getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
		}
	}

	
	
	protected boolean canProcessFluidInputs() 
	{
		
		if(getAccelerator().coolingRecipeInfo == null)
		{
			return false;
		}
		
		IFluidIngredient fluidProduct = getAccelerator().coolingRecipeInfo.getRecipe().fluidProducts().get(0);
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
			int heatUsed = (int) ((fluidIngredientStackSize/getAccelerator().coolingRecipeInfo.getRecipe().fluidIngredients().get(0).getMaxStackSize(0))*getAccelerator().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
			
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
			IFluidIngredient fluidProduct = getAccelerator().coolingRecipeInfo.getRecipe().fluidProducts().get(0);
			
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
	
	
	public void doMeltdown() 
	{
		
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
	


}
