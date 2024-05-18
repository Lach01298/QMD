package lach_01298.qmd.particleChamber.tile;

import com.google.common.collect.Lists;
import lach_01298.qmd.QMD;
import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.internal.inventory.*;
import nc.tile.inventory.ITileInventory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.*;

public class TileParticleChamberPort extends TileParticleChamberPart implements ITileInventory
{
	
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private TileTargetChamberController controller;
	
	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.particle_chamber_port";
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Lists.newArrayList(ItemSorption.IN, ItemSorption.OUT));
	
	public TileParticleChamberPort()
	{
		super(CuboidalPartPositionType.WALL);
	}

	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}
	
	@Override
	public void onMachineAssembled(ParticleChamber chamber)
	{
		if(chamber.controller instanceof TileTargetChamberController)
		{
			controller =  (TileTargetChamberController) chamber.controller;
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
		return controller == null ? inventoryStacks : controller.getInventoryStacks();
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
