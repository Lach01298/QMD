package lach_01298.qmd.vacuumChamber;

import static lach_01298.qmd.recipes.QMDRecipes.accelerator_cooling;
import static nc.block.property.BlockProperties.ACTIVE;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.tuple.Pair;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.particle.IParticleStackHandler;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberComponent;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberController;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberPart;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeamPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberEnergyPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPart;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberRedstonePort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberVent;
import nc.multiblock.IPacketMultiblockLogic;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VacuumChamberLogic extends MultiblockLogic<VacuumChamber, VacuumChamberLogic,IVacuumChamberPart>
		implements IPacketMultiblockLogic<VacuumChamber, VacuumChamberLogic,IVacuumChamberPart,VacuumChamberUpdatePacket>
{ 

	public static final int maxSize = 7;
	public static final int minSize = 5;
	
	
	private int excessCoolant =0; // in mirco buckets
	protected boolean operational = false;
	
	public VacuumChamberLogic(VacuumChamber multiblock)
	{
		super(multiblock);
	
	}
	
	public VacuumChamberLogic(VacuumChamberLogic oldLogic) 
	{
		super(oldLogic);
	}

	@Override
	public String getID() 
	{
		return "";
	}

	protected VacuumChamber getMultiblock() 
	{
		return multiblock;
	}
	
	// Multiblock Size Limits
	
	@Override
	public int getMinimumInteriorLength()
	{
		return minSize;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return maxSize;
	}

	// Multiblock Methods
	
	@Override
	public void onMachineAssembled()
	{
		onVacuumChamberFormed();
	}

	@Override
	public void onMachineRestored()
	{
		onVacuumChamberFormed();
	}
	
	public void onVacuumChamberFormed()
	{
		for (IVacuumChamberController contr : getPartMap(IVacuumChamberController.class).values()) 
		{
			 getMultiblock().controller = contr;
		}
		
		getMultiblock().energyStorage.setStorageCapacity(QMDConfig.vacuum_chamber_base_energy_capacity * getCapacityMultiplier());
		getMultiblock().energyStorage.setMaxTransfer(QMDConfig.vacuum_chamber_base_energy_capacity * getCapacityMultiplier());
		getMultiblock().heatBuffer.setHeatCapacity(QMDConfig.accelerator_base_heat_capacity * getCapacityMultiplier());
		getMultiblock().ambientTemp = 273 + (int) (getWorld().getBiome(getMultiblock().getMiddleCoord()).getTemperature(getMultiblock().getMiddleCoord())*20F);
		getMultiblock().tanks.get(0).setCapacity(QMDConfig.accelerator_base_input_tank_capacity * getCapacityMultiplier());
		getMultiblock().tanks.get(1).setCapacity(QMDConfig.accelerator_base_output_tank_capacity * getCapacityMultiplier());
		
		if(!getMultiblock().cold)
		{
			getMultiblock().heatBuffer.setHeatStored(getMultiblock().ambientTemp*getMultiblock().heatBuffer.getHeatCapacity()/getMultiblock().MAX_TEMP);
		}
		getMultiblock().cold = true;
		getMultiblock().currentHeating = 0;
		
		if (!getWorld().isRemote)
		{

			int energy = 0;
			long heat = 0;
			int maxTemp = Accelerator.MAX_TEMP;
			for (IVacuumChamberComponent part : getMultiblock().getPartMap(IVacuumChamberComponent.class).values())
			{
				heat += part.getHeating();
				energy += part.getPower();
				if (part.getMaxOperatingTemp() < maxTemp)
				{
					maxTemp = part.getMaxOperatingTemp();
				}
			}

			getMultiblock().requiredEnergy = energy;
			getMultiblock().heating = heat;
			getMultiblock().maxOperatingTemp = maxTemp;

			getMultiblock().updateActivity();
		}

	}

	

	
	public int getCapacityMultiplier() 
	{
		return getMultiblock().getExteriorVolume();
	}
	
	
	@Override
	public void onMachinePaused()
	{
		onContainmentBroken();
	}

	@Override
	public void onMachineDisassembled()
	{	
		for (TileVacuumChamberRedstonePort port : getPartMap(TileVacuumChamberRedstonePort.class).values())
		{
			port.setRedstoneLevel(0);	
		}
		
		operational = false;
		onContainmentBroken();
	}
	
	public void onContainmentBroken()
	{
		if (getMultiblock().controller != null)
		{
			getMultiblock().controller.setActivity(false);
		}
	}

	@Override
	public boolean isMachineWhole()
	{
		// vents
		boolean inlet = false;
		boolean outlet = false;
		for (TileVacuumChamberVent vent : getPartMap(TileVacuumChamberVent.class).values())
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
		if (getPartMap(TileVacuumChamberEnergyPort.class).size() < 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.need_energy_ports", null);
			return false;
		}

		return true;
	}
	
	@Override
	public List<Pair<Class<? extends IVacuumChamberPart>, String>> getPartBlacklist()
	{
		return new ArrayList<>();
	}
	
	public void onAssimilate(VacuumChamber assimilated)
	{	
		if (assimilated instanceof VacuumChamber)
		{
			VacuumChamber assimilatedAccelerator = (VacuumChamber) assimilated;
			getMultiblock().heatBuffer.mergeHeatBuffers(assimilatedAccelerator.heatBuffer);
			getMultiblock().energyStorage.mergeEnergyStorage(assimilatedAccelerator.energyStorage);
		}
		
		if (getMultiblock().isAssembled()) {
			
			onVacuumChamberFormed();
		}
		else 
		{
			onContainmentBroken();
		}
	}

	public void onAssimilated(VacuumChamber assimilator)
	{	
	}

	// Server
	
	public boolean onUpdateServer()
	{

		externalHeating();
		refreshFluidRecipe();
		if (canProcessFluidInputs())
		{	
			produceFluidProducts();
		}
		
		
		return true;
	}
	
	protected void pull()
	{
		for(TileVacuumChamberBeamPort port : getPartMap(TileVacuumChamberBeamPort.class).values())
		{
		
			if(port.getIOType() == IOType.INPUT)
			{
				for(EnumFacing face : EnumFacing.HORIZONTALS)
				{
					TileEntity tile = port.getWorld().getTileEntity(port.getPos().offset(face));
					if(tile != null)
					{
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite()))
						{
							IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,face.getOpposite());
							getMultiblock().beams.get(port.getIONumber()).setParticleStack(otherStorage.extractParticle(face.getOpposite()));
						}
					}
				}
			}
		}
		
	}
	
	protected void push()
	{
		for(TileVacuumChamberBeamPort port : getPartMap(TileVacuumChamberBeamPort.class).values())
		{
		
			if(port.getIOType() == IOType.OUTPUT)
			{
				for(EnumFacing face : EnumFacing.HORIZONTALS)
				{
					TileEntity tile = port.getWorld().getTileEntity(port.getPos().offset(face));
					if(tile != null)
					{
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face.getOpposite()))
						{
							IParticleStackHandler otherStorage = tile.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY,face.getOpposite());
							otherStorage.reciveParticle(face.getOpposite(), getMultiblock().beams.get(port.getIONumber()).getParticleStack());
						}
					}
				}
			}
		}
	}
	
	public @Nonnull List<Tank> getVentTanks(List<Tank> backupTanks)
	{
		return getMultiblock().isAssembled() ? getMultiblock().tanks.subList(0, 2) : backupTanks;
	}
	
	

	protected void externalHeating()
	{
		getMultiblock().heatBuffer.addHeat(getMultiblock().getExternalHeating(),false);
		getMultiblock().currentHeating +=getMultiblock().getExternalHeating();
	}
	
	protected void internalHeating()
	{
		getMultiblock().heatBuffer.addHeat(getMultiblock().heating,false);
		getMultiblock().currentHeating +=getMultiblock().heating;
	}
	
	public boolean isMultiblockOn()
	{
		return operational;
	}
	
	// Recipes
	
	protected void refreshFluidRecipe() 
	{
		getMultiblock().coolingRecipeInfo = accelerator_cooling.getRecipeInfoFromInputs(new ArrayList<ItemStack>(),getMultiblock().tanks.subList(0, 1));
		if(getMultiblock().coolingRecipeInfo != null)
		{
			getMultiblock().maxCoolantIn = 1000 / getMultiblock().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB() * (int) (2*getMultiblock().heating * getMultiblock().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getMaxStackSize(0));
			getMultiblock().maxCoolantOut = 1000 / getMultiblock().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB() * (int) (2*getMultiblock().heating * getMultiblock().coolingRecipeInfo.getRecipe().getFluidProducts().get(0).getMaxStackSize(0));
		}	
	}
	
	protected boolean canProcessFluidInputs() 
	{
		
		if(getMultiblock().coolingRecipeInfo == null)
		{
			return false;
		}
		
		if(getMultiblock().getTemperature() <= getMultiblock().coolingRecipeInfo.getRecipe().getFluidIngredients().get(0).getStack().getFluid().getTemperature())
		{
			return false;
		}
		
		IFluidIngredient fluidProduct = getMultiblock().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		if (fluidProduct.getMaxStackSize(0) <= 0 || fluidProduct.getStack() == null)
			return false;

		if (!getMultiblock().tanks.get(1).isEmpty())
		{
			if (!getMultiblock().tanks.get(1).getFluid().isFluidEqual(fluidProduct.getStack()))
			{
				return false;
			}
			else if (getMultiblock().tanks.get(1).getFluidAmount() + (getMultiblock().maxCoolantIn/1000 +1)*fluidProduct.getNextStack(0).amount > getMultiblock().tanks.get(1).getCapacity())			
			{
				return false;
			}
			
			else if (getMultiblock().heatBuffer.getHeatStored() < 1)
			{
				return false;
			}
		}
		return true;
	}
	
	
	private void produceFluidProducts()
	{
		int uBConsumed = getMultiblock().maxCoolantIn;
		
		if(uBConsumed > getMultiblock().tanks.get(0).getFluidAmount() *1000)
		{
			uBConsumed = getMultiblock().tanks.get(0).getFluidAmount() *1000;
		}
		if(uBConsumed > getMultiblock().heatBuffer.getHeatStored())
		{
			uBConsumed = (int) getMultiblock().heatBuffer.getHeatStored();
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
		
		
		getMultiblock().tanks.get(0).changeFluidAmount(-mBConsumed);
		if (getMultiblock().tanks.get(0).getFluidAmount() <= 0) getMultiblock().tanks.get(0).setFluidStored(null);
		
		getMultiblock().heatBuffer.changeHeatStored(-mBConsumed*getMultiblock().coolingRecipeInfo.getRecipe().getFissionHeatingHeatPerInputMB());
		
		
		IFluidIngredient fluidProduct = getMultiblock().coolingRecipeInfo.getRecipe().getFluidProducts().get(0);
		int producedCoolant = mBConsumed* fluidProduct.getNextStack(0).amount;
		if (getMultiblock().tanks.get(1).isEmpty())
		{
			getMultiblock().tanks.get(1).changeFluidStored(fluidProduct.getNextStack(0).getFluid(),producedCoolant);
		}
		else
		{
			getMultiblock().tanks.get(1).changeFluidAmount(producedCoolant);	
		}
			
	}
	
	
	protected boolean isRedstonePowered() 
	{
		for (TileVacuumChamberRedstonePort port : getPartMap(TileVacuumChamberRedstonePort.class).values())
		{
			if(!getWorld().getBlockState(port.getPos()).getValue(ACTIVE).booleanValue())
			{
				if(port.checkIsRedstonePowered(getWorld(), port.getPos()))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected int getRedstoneLevel()
	{
		int level = 0;
		
		for (TileVacuumChamberRedstonePort port : getPartMap(TileVacuumChamberRedstonePort.class).values())
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
	
	}
	
	
	
	
	// Client
	
	public void onUpdateClient()
	{
	
	}

	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound data, SyncReason syncReason)
	{
		data.setInteger("excessCoolant", excessCoolant);
	}

	@Override
	public void readFromLogicTag(NBTTagCompound data, SyncReason syncReason)
	{
		excessCoolant = data.getInteger("excessCoolant");
	}

	// Packets
	
	@Override
	public VacuumChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return null;
	}

	@Override
	public void onMultiblockUpdatePacket(VacuumChamberUpdatePacket message)
	{
		
	}

	public ContainmentRenderPacket getRenderPacket() 
	{
		return null;
	}
	
	public void onRenderPacket(ContainmentRenderPacket message) 
	{
		
	}
	
	
	
	
	public void clearAllMaterial()
	{
		for (Tank tank : getMultiblock().tanks)
		{
			tank.setFluidStored(null);
		}
	}
	
	
	
	// Multiblock Validators
	
	@Override
	public boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		
		if (MaterialHelper.isReplaceable(world.getBlockState(pos).getMaterial()) || world.getTileEntity(pos) instanceof TileVacuumChamberPart) return true;
		else return getMultiblock().standardLastError(pos);
	}
	
	
	
	
	/*public ContainerMultiblockController<VacuumChamber, IVacuumChamberController> getContainer(EntityPlayer player)
	{
		return null;
	}*/

	
	
	

	
	
	
	
	
	

	

	

	

}
