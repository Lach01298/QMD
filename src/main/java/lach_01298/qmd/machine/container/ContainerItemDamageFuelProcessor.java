package lach_01298.qmd.machine.container;

import lach_01298.qmd.machine.tile.TileItemDamageFuelProcessor;
import nc.container.ContainerTile;
import nc.init.NCItems;
import nc.recipe.ProcessorRecipeHandler;
import nc.tile.processor.TileItemProcessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerItemDamageFuelProcessor<PROCESSOR extends TileItemDamageFuelProcessor> extends ContainerTile<PROCESSOR> 
{
	
	protected final TileItemDamageFuelProcessor tile;
	protected final ProcessorRecipeHandler recipeHandler;
	protected final ProcessorRecipeHandler fuelHandler;
	

	
	public ContainerItemDamageFuelProcessor(EntityPlayer player, PROCESSOR tileEntity, ProcessorRecipeHandler recipeHandler,ProcessorRecipeHandler fuelHandler) 
	{
		super(tileEntity);
		tile = tileEntity;
		this.recipeHandler = recipeHandler;
		this.fuelHandler = fuelHandler;
		
		tileEntity.beginUpdatingPlayer(player);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) 
	{
		return tile.isUsableByPlayer(player);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer player) 
	{
		super.onContainerClosed(player);
		tile.stopUpdatingPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) 
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		int invStart = tile.itemInputSize +tile.itemFuelSize+ tile.itemOutputSize;
		
		int invEnd = tile.itemInputSize +tile.itemFuelSize + tile.itemOutputSize + 36;
		if (slot != null && slot.getHasStack()) 
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index >= tile.itemInputSize && index < invStart) 
			{
				if (!mergeItemStack(itemstack1, invStart, invEnd, false)) 
				{
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= invStart) 
			{
				
				
				if (recipeHandler.isValidItemInput(itemstack1)) 
				{
					if (!mergeItemStack(itemstack1, 0, tile.itemInputSize, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
				if (fuelHandler.isValidItemInput(itemstack1)) 
				{
					if (!mergeItemStack(itemstack1, 0, tile.itemFuelSize, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= invStart && index < invEnd - 9) 
				{
					if (!mergeItemStack(itemstack1, invEnd - 9, invEnd, false)) 
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= invEnd - 9 && index < invEnd && !mergeItemStack(itemstack1, invStart, invEnd - 9, false)) 
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!mergeItemStack(itemstack1, invStart, invEnd, false)) 
			{
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}
			else {
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) 
			{
				return ItemStack.EMPTY;
			}
			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}
}
