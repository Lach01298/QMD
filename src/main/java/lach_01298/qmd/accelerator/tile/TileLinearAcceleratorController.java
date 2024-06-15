package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.TileContainerInfo;
import nc.tile.internal.inventory.*;
import nc.tile.inventory.ITileInventory;
import nc.util.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static nc.block.property.BlockProperties.FACING_ALL;

public class TileLinearAcceleratorController extends TileAcceleratorPart implements IAcceleratorController<TileLinearAcceleratorController>, ITileInventory
{
	protected final TileContainerInfo<TileLinearAcceleratorController> info = TileInfoHandler.getTileContainerInfo("linear_accelerator_controller");

	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.linear_accelerator_controller";
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Arrays.asList(ItemSorption.BOTH,ItemSorption.BOTH));
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	
	public TileLinearAcceleratorController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"linear_accelerator";
	}
	
	@Override
	public TileContainerInfo<TileLinearAcceleratorController> getContainerInfo()
	{
		return info;
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().getFacing() != null)
		{
			getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(FACING_ALL, getPartPosition().getFacing()), 2);
		}
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
	}

	//items
	
	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		return inventoryStacks;
	}

	@Override
	public String getName()
	{
		return Lang.localize("gui."+inventoryName);
	}

	@Override
	public InventoryConnection[] getInventoryConnections()
	{
		return inventoryConnections;
	}

	@Override
	public void setInventoryConnections(InventoryConnection[] connections)
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
		return 1;
	}
	
	
	
	
	
	
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos)
	{
		super.onBlockNeighborChanged(state, world, pos, fromPos);
		if (getMultiblock() != null) getMultiblock().updateActivity();
	}
	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
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
	public NBTTagCompound writeInventory(NBTTagCompound nbt)
	{
		NBTHelper.writeAllItems(nbt, inventoryStacks);
		return nbt;
	}

	@Override
	public void readInventory(NBTTagCompound nbt)
	{
		NBTHelper.readAllItems(nbt, inventoryStacks);
	}
	
	
	
	@Override
	public  boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return QMDRecipes.accelerator_source.isValidItemInput(IItemParticleAmount.cleanNBT(stack));
	}
	
}
