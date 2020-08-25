package lach_01298.qmd.item;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface ITickItem
{

	public void updateTick(ItemStack stack, TileEntity tile, long worldTime);
	
	
	
}
