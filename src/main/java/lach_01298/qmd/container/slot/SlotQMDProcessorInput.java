package lach_01298.qmd.container.slot;

import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotQMDProcessorInput extends Slot
{

	public final QMDRecipeHandler recipeHandler;

	public SlotQMDProcessorInput(ITileInventory tile, QMDRecipeHandler recipeHandler, int slotIndex, int xPosition, int yPosition)
	{
		super(tile, slotIndex, xPosition, yPosition);
		this.recipeHandler = recipeHandler;

	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		ItemStack item = stack.copy();
		if (item.getItem().isDamageable())
		{
			item.setItemDamage(0);
		}

		return recipeHandler.isValidItemInput(item);
	}
}
