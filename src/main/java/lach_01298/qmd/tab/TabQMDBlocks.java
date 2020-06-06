package lach_01298.qmd.tab;

import lach_01298.qmd.block.QMDBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabQMDBlocks extends CreativeTabs
{

	public TabQMDBlocks()
	{
		super("qmd.blocks");
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(QMDBlocks.oreLeacher);
	}
}
