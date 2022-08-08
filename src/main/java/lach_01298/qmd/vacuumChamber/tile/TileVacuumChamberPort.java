package lach_01298.qmd.vacuumChamber.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMD;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
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

public class TileVacuumChamberPort extends TileVacuumChamberPart implements ITileInventory
{
	
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private IVacuumChamberController controller;
	
	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.containment_port";
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.IN, ItemSorption.OUT));
	
	public TileVacuumChamberPort()
	{
		super(CuboidalPartPositionType.WALL);
	}

	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
	
	@Override
	public void onMachineAssembled(VacuumChamber chamber) 
	{
		if(chamber.controller instanceof IVacuumChamberController)
		{
			controller =  (IVacuumChamberController) chamber.controller;
		}
		super.onMachineAssembled(chamber);
	}
	
	
	@Override
	public void onMachineBroken()
	{
		controller = null;
		super.onMachineBroken();
	}

	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		if(controller == null)
		{
			return inventoryStacks;
		}
		else if(controller instanceof TileExoticContainmentController)
		{
			TileExoticContainmentController tile = (TileExoticContainmentController) controller;
			return tile.getInventoryStacks();
		}	
		return inventoryStacks;
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

}
