package lach_01298.qmd.container;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class QMDMachineSlot extends Slot
{

	public final QMDRecipeHandler recipeHandler;

	public QMDMachineSlot(ITileInventory tile, QMDRecipeHandler recipeHandler, int slotIndex, int xPosition,
			int yPosition)
	{
		super(tile.getInventory(), slotIndex, xPosition, yPosition);
		this.recipeHandler = recipeHandler;

	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		ItemStack item = stack.copy();
		item.setItemDamage(0);
		return recipeHandler.isValidItemInput(item);
	}
}
