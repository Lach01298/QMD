package lach_01298.qmd.container.slot;

import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotQMDProcessorInput extends Slot
{

	public final QMDRecipeHandler recipeHandler;
	public final int stackSize;

	public SlotQMDProcessorInput(ITileInventory tile, QMDRecipeHandler recipeHandler, int slotIndex, int xPosition, int yPosition)
	{
		super(tile, slotIndex, xPosition, yPosition);
		this.recipeHandler = recipeHandler;
		stackSize = 64;
	}
	
	public SlotQMDProcessorInput(ITileInventory tile, QMDRecipeHandler recipeHandler, int slotIndex, int xPosition, int yPosition, int stackSize)
	{
		super(tile, slotIndex, xPosition, yPosition);
		this.recipeHandler = recipeHandler;
		this.stackSize = stackSize;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		ItemStack item = stack.copy();
		
		return recipeHandler.isValidItemInput(IItemParticleAmount.cleanNBT(item));
	}

	public int getSlotStackLimit()
	{
		return stackSize;
	}




}
