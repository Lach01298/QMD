package lach_01298.qmd.block;

import lach_01298.qmd.tab.QMDTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQMD extends Block
{

	public BlockQMD(Material material)
	{
		super(material);
		this.setCreativeTab(QMDTabs.BLOCKS);
	}

	
}
