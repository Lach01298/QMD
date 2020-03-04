package lach_01298.qmd.container.slot;

import nc.recipe.ProcessorRecipeHandler;
import nc.tile.inventory.ITileInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDamageFuel extends Slot 
{
	
	protected final ProcessorRecipeHandler recipeHandler;
	
	public SlotDamageFuel(ITileInventory tile, ProcessorRecipeHandler recipeHandler, int index, int xPosition, int yPosition) 
	{
		super(tile.getInventory(), index, xPosition, yPosition);
		this.recipeHandler = recipeHandler;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		if(stack.getCount() != 1)
		{
			return false;
		}
		
		ItemStack item = stack.copy();
		if (item.getItem().isDamageable())
		{
			item.setItemDamage(0);
		}
		return recipeHandler.isValidItemInput(item);
	}
}
