package lach_01298.qmd.multiblock.accelerator.block;

import nc.multiblock.BlockMultiblockPart;
import nc.tab.NCTabs;
import net.minecraft.block.material.Material;

public abstract class BlockAcceleratorPart extends BlockMultiblockPart
{

	public BlockAcceleratorPart()
	{
		super(Material.IRON, NCTabs.MULTIBLOCK);
	}

	public static abstract class Transparent extends BlockMultiblockPart.Transparent
	{

		public Transparent(boolean smartRender)
		{
			super(Material.IRON, NCTabs.MULTIBLOCK, smartRender);
		}
	}
}
