package lach_01298.qmd.tab;

import lach_01298.qmd.item.QMDItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabQMDItems extends CreativeTabs
{

	public TabQMDItems() {
		super("qmd.items");
	}

	@Override
	public ItemStack createIcon()
	{
		return new ItemStack(QMDItems.source_sodium_22);
	}
}

