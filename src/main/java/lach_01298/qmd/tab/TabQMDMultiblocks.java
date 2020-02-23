package lach_01298.qmd.tab;

import lach_01298.qmd.block.QMDBlocks;
import nc.init.NCItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabQMDMultiblocks extends CreativeTabs
{

	public TabQMDMultiblocks()
	{
		super("qmd.multiblocks");
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(QMDBlocks.linearAcceleratorController);
	}
}
