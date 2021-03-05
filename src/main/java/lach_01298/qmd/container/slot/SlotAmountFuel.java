package lach_01298.qmd.container.slot;

import lach_01298.qmd.item.IItemAmount;
import nc.recipe.ProcessorRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAmountFuel extends Slot
{

	protected final ProcessorRecipeHandler recipeHandler;

	public SlotAmountFuel(ITileInventory tile, ProcessorRecipeHandler recipeHandler, int index, int xPosition,
			int yPosition)
	{
		super(tile, index, xPosition, yPosition);
		this.recipeHandler = recipeHandler;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (stack.getCount() != 1)
		{
			return false;
		}

		if (stack.getItem() instanceof IItemAmount)
		{
			return recipeHandler.isValidItemInput(IItemAmount.cleanNBT(stack));
		}

		return false;
	}

	public int getSlotStackLimit()
	{
		return 1;
	}

}



