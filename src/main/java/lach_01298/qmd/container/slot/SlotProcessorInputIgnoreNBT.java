package lach_01298.qmd.container.slot;

import lach_01298.qmd.item.IItemAmount;
import nc.container.slot.SlotProcessorInput;
import nc.recipe.ProcessorRecipeHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SlotProcessorInputIgnoreNBT extends SlotProcessorInput
{

	public SlotProcessorInputIgnoreNBT(IInventory tile, ProcessorRecipeHandler recipeHandler, int index, int xPosition, int yPosition)
	{
		super(tile, recipeHandler, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		
		ItemStack copy = stack.copy();
		return recipeHandler.isValidItemInput(IItemAmount.cleanNBT(copy));
	}
}
