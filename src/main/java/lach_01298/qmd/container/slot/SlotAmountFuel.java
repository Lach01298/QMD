package lach_01298.qmd.container.slot;

import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAmountFuel extends Slot
{

	protected final QMDRecipeHandler recipeHandler;

	public SlotAmountFuel(ITileInventory tile, QMDRecipeHandler recipeHandler, int index, int xPosition,
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

		if (stack.getItem() instanceof IItemParticleAmount)
		{
			return recipeHandler.isValidItemInput(IItemParticleAmount.cleanNBT(stack));
		}

		return false;
	}

	public int getSlotStackLimit()
	{
		return 1;
	}

}



