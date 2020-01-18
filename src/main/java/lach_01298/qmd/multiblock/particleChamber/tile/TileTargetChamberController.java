package lach_01298.qmd.multiblock.particleChamber.tile;


import static nc.block.property.BlockProperties.FACING_ALL;

import javax.annotation.Nonnull;

import lach_01298.qmd.QMD;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.block.BlockLinearAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPart;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipes;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.internal.inventory.InventoryConnection;
import nc.tile.internal.inventory.InventoryTileWrapper;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.inventory.ITileInventory;
import nc.util.BlockPosHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileTargetChamberController extends TileParticleChamberPart implements IParticleChamberController, ITileInventory
{

private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.target_chamber_controller";
	
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	private @Nonnull InventoryTileWrapper invWrapper;
	

	
	public int inventoryStackLimit = 64;
	public QMDRecipeHandler recipe_handler = QMDRecipes.target_chamber;
	
	
	public TileTargetChamberController()
	{
		super(CuboidalPartPositionType.WALL);
		invWrapper = new InventoryTileWrapper(this);
	}

	@Override
	public String getLogicID()
	{
		return	"target_chamber";
	}


	@Override
	public void onMachineAssembled(ParticleChamber controller)
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
	public void updateBlockState(boolean isActive)
	{
		if (getBlockType() instanceof BlockLinearAcceleratorController)
		{
			((BlockLinearAcceleratorController) getBlockType()).setState(isActive, this);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}


	public QMDRecipeHandler getRecipeHandler() {
		return recipe_handler;
	}
	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);
		writeInventory(nbt);
		
		return nbt;
	}
	
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		readInventory(nbt);
	}
	
	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		return inventoryStacks;
	}

	@Override
	public String getName()
	{
		return inventoryName;
	}
	
	@Override
	public InventoryTileWrapper getInventory()
	{
		return invWrapper;
	}
	
	@Override
	public InventoryConnection[] getInventoryConnections()
	{
		return null;
	}

	@Override
	public void setInventoryConnections(InventoryConnection[] connections)
	{
		
	}
	
	@Override
	public ItemOutputSetting getItemOutputSetting(int slot)
	{
		return ItemOutputSetting.DEFAULT;
	}

	@Override
	public void setItemOutputSetting(int slot, ItemOutputSetting setting)
	{
		// TODO Auto-generated method stub
	}
	
	
	
	@Override
	public NBTTagCompound writeInventory(NBTTagCompound nbt)
	{
		for (int i = 0; i < inventoryStacks.size(); i++)
		{
			nbt.setInteger("inventoryStackSize" + i, inventoryStacks.get(i).getCount());
			if (!inventoryStacks.get(i).isEmpty())
			{
				inventoryStacks.get(i).setCount(1);
			}
		}

		ItemStackHelper.saveAllItems(nbt, inventoryStacks);

		for (int i = 0; i < inventoryStacks.size(); i++)
		{
			if (!inventoryStacks.get(i).isEmpty())
			{
				inventoryStacks.get(i).setCount(nbt.getInteger("inventoryStackSize" + i));
			}
		}

		return nbt;
	}

	@Override
	public void readInventory(NBTTagCompound nbt)
	{
		ItemStackHelper.loadAllItems(nbt, inventoryStacks);

		for (int i = 0; i < inventoryStacks.size(); i++)
		{
			if (!inventoryStacks.get(i).isEmpty())
			{
				inventoryStacks.get(i).setCount(nbt.getInteger("inventoryStackSize" + i));
			}
		}
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) 
	{
		if (getRecipeHandler() == null) {
			return ITileInventory.super.isItemValidForSlot(slot, stack);
		}
		if (stack == ItemStack.EMPTY || slot >= getRecipeHandler().itemInputSize) return false;
		return   getRecipeHandler().isValidItemInput(stack);
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) 
	{
		return ITileInventory.super.canInsertItem(slot, stack, side) && (getRecipeHandler() == null || isItemValidForSlot(slot, stack));
	}
	
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
}