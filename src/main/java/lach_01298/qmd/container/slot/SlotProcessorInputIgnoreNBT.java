package lach_01298.qmd.container.slot;

import lach_01298.qmd.item.IItemParticleAmount;
import nc.container.slot.SlotProcessorInput;
import nc.recipe.BasicRecipeHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SlotProcessorInputIgnoreNBT extends SlotProcessorInput
{

	public SlotProcessorInputIgnoreNBT(IInventory tile, BasicRecipeHandler recipeHandler, int index, int xPosition, int yPosition)
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
