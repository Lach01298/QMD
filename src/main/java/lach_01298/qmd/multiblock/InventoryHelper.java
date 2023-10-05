package lach_01298.qmd.multiblock;

import nc.tile.ITile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventoryHelper
{

	
	
	
	public static boolean addItem(int slot, ItemStack item, NonNullList<ItemStack> inventoryStacks, ITile tile)
	{
		
		if(item == null)
		{
			return false;
		}
		
		if (inventoryStacks.get(slot) == ItemStack.EMPTY)
		{
			inventoryStacks.set(slot, item);
			return true;
		}
		else if (inventoryStacks.get(slot).isItemEqual(item))
		{
			if (inventoryStacks.get(slot).getCount() + item.getCount() <= item.getMaxStackSize())
			{
				inventoryStacks.get(slot).setCount(inventoryStacks.get(slot).getCount() + item.getCount());
				return true;
			}

		}	
		return false;
	}
	
	public static boolean removeItem(int slot, ItemStack item, NonNullList<ItemStack> inventoryStacks, ITile tile)
	{	
		if (item == null)
		{
			return false;
		}
		if (inventoryStacks.get(slot).isItemEqual(item))
		{

			if (inventoryStacks.get(slot).getCount() - item.getCount() <= 0)
			{
				inventoryStacks.set(slot, ItemStack.EMPTY);
			}
			else
			{
				inventoryStacks.get(slot).setCount(inventoryStacks.get(slot).getCount() - item.getCount());
			}
			tile.markDirtyAndNotify();
			return true;
		}
		return false;
	}
	
	public static void removeItem(int slot, int count, NonNullList<ItemStack> inventoryStacks, ITile tile)
	{	
			if (inventoryStacks.get(slot).getCount() - count <= 0)
			{
				inventoryStacks.set(slot, ItemStack.EMPTY);
			}
			else
			{
				inventoryStacks.get(slot).setCount(inventoryStacks.get(slot).getCount() - count);
			}
	}

	
	
	
}
