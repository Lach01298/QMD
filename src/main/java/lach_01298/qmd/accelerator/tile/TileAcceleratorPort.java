package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.*;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.util.InventoryStackList;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.fluid.*;
import nc.tile.internal.inventory.*;
import nc.tile.inventory.ITileInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.*;
import java.util.*;

public class TileAcceleratorPort extends TileAcceleratorPart implements ITileInventory, ITileFluid
{
	
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private TileAcceleratorIonSource source;
	private IAcceleratorController controller;
	
	private final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(QMDConfig.accelerator_base_input_tank_capacity * 1000, new ArrayList<>()));
	private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(Lists.newArrayList(TankSorption.NON));
	private @Nonnull FluidTileWrapper[] fluidSides;
	
	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.accelerator_port";
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.NON,ItemSorption.NON));
	
	public TileAcceleratorPort()
	{
		super(CuboidalPartPositionType.WALL);
		
		fluidSides = ITileFluid.getDefaultFluidSides(this);
	}

	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
	
	@Override
	public void onMachineAssembled(Accelerator accelerator)
	{
		if(accelerator.controller instanceof TileMassSpectrometerController)
		{
			controller =  (TileMassSpectrometerController) accelerator.controller;
			
			for (int i = 0; i < 6; i++)
			{
				setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.IN);
				setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.NON);
				setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.IN);
			}

		}
		else if(accelerator.controller instanceof TileLinearAcceleratorController)
		{
			controller =  (TileLinearAcceleratorController) accelerator.controller;
			for (int i = 0; i < 6; i++)
			{
				setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.BOTH);
				setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.BOTH);
				setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.IN);
			}
		}
		else
		{
			for (int i = 0; i < 6; i++)
			{
				setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.NON);
				setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.NON);
				setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.NON);
			}
		}
		
		super.onMachineAssembled(accelerator);
	}
	
	public void setSource(LinearAcceleratorLogic logic)
	{
		source = logic.getSource();
	}
	
	
	
	@Override
	public void onMachineBroken()
	{
		controller = null;
		source = null;
		
		for (int i = 0; i < 6; i++)
		{
			setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.NON);
			setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.NON);
			setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.NON);
		}
		
		super.onMachineBroken();
	}

	// Items
	
	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		if(controller != null )
		{
			if(getLogic() instanceof MassSpectrometerLogic && controller instanceof TileMassSpectrometerController)
			{
				TileMassSpectrometerController massSpec = (TileMassSpectrometerController) controller;
				return new InventoryStackList(massSpec.getInventoryStacks().subList(0,2));
			}
		}
		
		
		return source == null ? inventoryStacks : source.getInventoryStacks();
	}

	@Override
	public String getName()
	{
		return inventoryName;
	}

	@Override
	public @Nonnull InventoryConnection[] getInventoryConnections()
	{
		return inventoryConnections;
	}

	@Override
	public void setInventoryConnections(@Nonnull InventoryConnection[] connections)
	{
		inventoryConnections = connections;
	}


	@Override
	public ItemOutputSetting getItemOutputSetting(int slot)
	{
		return ItemOutputSetting.DEFAULT;
	}

	@Override
	public void setItemOutputSetting(int slot, ItemOutputSetting setting)
	{
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		if(controller instanceof TileMassSpectrometerController)
		{
			return 64;
		}
		
		return 1;
	}
	
	@Override
	public  boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if(controller instanceof TileMassSpectrometerController)
		{
			
			return QMDRecipes.mass_spectrometer.isValidItemInput(stack);
		}
		
		return QMDRecipes.accelerator_source.isValidItemInput(IItemParticleAmount.cleanNBT(stack));
	}
	
	
	
	// Fluids
	
		@Override
		public @Nonnull List<Tank> getTanks()
		{
			if(getMultiblock() != null)
			{
					return getMultiblock().isAssembled() ? getMultiblock().tanks.subList(2, 3) : backupTanks;
			}

			return  backupTanks;
		}
		@Override
		@Nonnull
		public FluidConnection[] getFluidConnections()
		{
			return fluidConnections;
		}
		
		@Override
		public void setFluidConnections(@Nonnull FluidConnection[] connections)
		{
			fluidConnections = connections;
		}

		@Override
		@Nonnull
		public FluidTileWrapper[] getFluidSides()
		{
			return fluidSides;
		}

		@Override
		public GasTileWrapper getGasWrapper()
		{
			return null;
		}


		@Override
		public boolean getInputTanksSeparated()
		{
			return false;
		}

		@Override
		public void setInputTanksSeparated(boolean separated)
		{
		}

		@Override
		public boolean getVoidUnusableFluidInput(int tankNumber)
		{
			return false;
		}

		@Override
		public void setVoidUnusableFluidInput(int tankNumber, boolean voidUnusableFluidInput)
		{
		}

		@Override
		public TankOutputSetting getTankOutputSetting(int tankNumber)
		{
			return TankOutputSetting.DEFAULT;
		}

		@Override
		public void setTankOutputSetting(int tankNumber, TankOutputSetting setting)
		{
		}

		@Override
		public boolean hasConfigurableFluidConnections()
		{
			return true;
		}
	
	
	
	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		writeInventory(nbt);
		writeInventoryConnections(nbt);
		writeTanks(nbt);
		writeFluidConnections(nbt);
		writeTankSettings(nbt);

		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		readInventory(nbt);
		readInventoryConnections(nbt);
		readTanks(nbt);
		readFluidConnections(nbt);
		readTankSettings(nbt);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return !getInventoryStacks().isEmpty() && hasInventorySideCapability(side);
		}
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return !getTanks().isEmpty() && hasFluidSideCapability(side);
		}
		
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (!getInventoryStacks().isEmpty() && hasInventorySideCapability(side))
			{
				return (T) getItemHandler(side);
			}
			return null;

		}
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			if (!getTanks().isEmpty() && hasFluidSideCapability(side))
			{
				return (T) getFluidSide(nonNullSide(side));
			}
			return null;
		}
		
		return super.getCapability(capability, side);
	}
	
	

}
