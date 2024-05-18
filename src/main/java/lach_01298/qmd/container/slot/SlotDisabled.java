package lach_01298.qmd.container.slot;

import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.*;

public class SlotDisabled extends Slot
{

	public SlotDisabled(IInventory inventoryIn, int index)
	{
		super(inventoryIn, index, 0, 0);
		
	}

	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isEnabled()
	{
		return false;
	}
}
