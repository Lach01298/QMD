package lach_01298.qmd.accelerator.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.item.IItemAmount;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.internal.inventory.InventoryConnection;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.inventory.ITileInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileAcceleratorPort extends TileAcceleratorPart implements ITileInventory
{
	
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(1, ItemStack.EMPTY);
	private TileAcceleratorSource source;
	
	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.accelerator_port";
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.BOTH));
	
	public TileAcceleratorPort()
	{
		super(CuboidalPartPositionType.WALL);
	}

	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
	
	@Override
	public void onMachineAssembled(Accelerator accelerator) 
	{
		super.onMachineAssembled(accelerator);	
	}
	
	public void setSource(LinearAcceleratorLogic logic)
	{
		source = logic.getSource();
	}
	
	
	
	@Override
	public void onMachineBroken()
	{
		source = null;
		super.onMachineBroken();
	}

	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
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
	
	
	// NBT
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);
		writeInventory(nbt);
		writeInventoryConnections(nbt);

		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		readInventory(nbt);
		readInventoryConnections(nbt);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return !getInventoryStacks().isEmpty() && hasInventorySideCapability(side);
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
		return super.getCapability(capability, side);
	}
	
	@Override
	public int getInventoryStackLimit() 
	{
		return 1;
	}
	
	@Override
	public  boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		if(getInventoryStacks().get(0).getCount() > 0)
		{
			return false;
		}
		return QMDRecipes.accelerator_source.isValidItemInput(IItemAmount.cleanNBT(stack));
	}

}
