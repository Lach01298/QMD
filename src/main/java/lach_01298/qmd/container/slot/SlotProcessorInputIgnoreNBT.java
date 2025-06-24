package lach_01298.qmd.container.slot;

import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotProcessorInputIgnoreNBT extends SlotQMDProcessorInput
{

	public SlotProcessorInputIgnoreNBT(ITileInventory tile, QMDRecipeHandler recipeHandler, int index, int xPosition, int yPosition)
	{
		super(tile, recipeHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		
		ItemStack copy = stack.copy();
		return true;//recipeHandler.isValidItemInput(IItemAmount.cleanNBT(copy));
	}
}
