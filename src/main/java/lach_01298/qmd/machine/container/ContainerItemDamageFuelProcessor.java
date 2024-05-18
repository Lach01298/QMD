package lach_01298.qmd.machine.container;

import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.machine.tile.TileItemAmountFuelProcessor;
import nc.recipe.BasicRecipeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class ContainerItemDamageFuelProcessor<PROCESSOR extends TileItemAmountFuelProcessor>
		extends ContainerTile<PROCESSOR>
{

	protected final TileItemAmountFuelProcessor tile;
	protected final BasicRecipeHandler recipeHandler;
	protected final BasicRecipeHandler fuelHandler;

	public ContainerItemDamageFuelProcessor(EntityPlayer player, PROCESSOR tileEntity, BasicRecipeHandler recipeHandler,
			BasicRecipeHandler fuelHandler)
	{
		super(tileEntity);
		tile = tileEntity;
		this.recipeHandler = recipeHandler;
		this.fuelHandler = fuelHandler;

		tileEntity.addTileUpdatePacketListener(player);
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
		tile.removeTileUpdatePacketListener(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);

		int invStart = tile.itemInputSize + tile.itemFuelSize + tile.itemOutputSize;

		int invEnd = tile.itemInputSize + tile.itemFuelSize + tile.itemOutputSize + 36;
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

				if (fuelHandler.isValidItemInput(IItemParticleAmount.cleanNBT(itemstack1)))
				{

					if (!mergeItemStack(itemstack1, tile.itemInputSize, tile.itemInputSize + tile.itemFuelSize, false))
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
				else if (index >= invEnd - 9 && index < invEnd
						&& !mergeItemStack(itemstack1, invStart, invEnd - 9, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!mergeItemStack(itemstack1, invStart, invEnd, false))
			{
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
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

	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
	{
		boolean flag = false;
		int i = startIndex;

		if (reverseDirection)
		{
			i = endIndex - 1;
		}

		if (stack.isStackable())
		{
			while (!stack.isEmpty())
			{
				if (reverseDirection)
				{
					if (i < startIndex)
					{
						break;
					}
				}
				else if (i >= endIndex)
				{
					break;
				}

				Slot slot = this.inventorySlots.get(i);
				ItemStack itemstack = slot.getStack();

				if (!itemstack.isEmpty() && itemstack.getItem() == stack.getItem()
						&& stack.getMetadata() == itemstack.getMetadata()
						&& ItemStack.areItemStackTagsEqual(stack, itemstack))
				{
					int j = itemstack.getCount() + stack.getCount();
					int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());

					if (j <= maxSize)
					{
						stack.setCount(0);
						itemstack.setCount(j);
						slot.onSlotChanged();
						flag = true;
					}
					else if (itemstack.getCount() < maxSize)
					{
						stack.shrink(maxSize - itemstack.getCount());
						itemstack.setCount(maxSize);
						slot.onSlotChanged();
						flag = true;
					}
				}

				if (reverseDirection)
				{
					--i;
				}
				else
				{
					++i;
				}
			}
		}

		if (!stack.isEmpty())
		{
			if (reverseDirection)
			{
				i = endIndex - 1;
			}
			else
			{
				i = startIndex;
			}

			while (true)
			{
				if (reverseDirection)
				{
					if (i < startIndex)
					{
						break;
					}
				}
				else if (i >= endIndex)
				{
					break;
				}

				Slot slot1 = this.inventorySlots.get(i);
				ItemStack itemstack1 = slot1.getStack();

				if (itemstack1.isEmpty() && slot1.isItemValid(stack))
				{
					if (stack.getCount() > slot1.getSlotStackLimit())
					{
						slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
					}
					else
					{
						slot1.putStack(stack.splitStack(stack.getCount()));
					}

					slot1.onSlotChanged();
					flag = true;
					break;
				}

				if (reverseDirection)
				{
					--i;
				}
				else
				{
					++i;
				}
			}
		}

		return flag;
	}

}
