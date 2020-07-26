package lach_01298.qmd.containment.block;

import lach_01298.qmd.tab.QMDTabs;
import nc.multiblock.block.BlockMultiblockPart;
import net.minecraft.block.material.Material;

public abstract class BlockContainmentPart extends BlockMultiblockPart
{

	public BlockContainmentPart()
	{
		super(Material.IRON, QMDTabs.MULTIBLOCKS);
	}

	public static abstract class Transparent extends BlockMultiblockPart.Transparent
	{

		public Transparent(boolean smartRender)
		{
			super(Material.IRON, QMDTabs.MULTIBLOCKS, smartRender);
		}
	}
}
