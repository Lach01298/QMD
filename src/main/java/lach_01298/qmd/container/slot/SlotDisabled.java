package lach_01298.qmd.container.slot;

import lach_01298.qmd.item.IItemParticleAmount;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
