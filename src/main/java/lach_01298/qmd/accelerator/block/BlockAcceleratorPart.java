package lach_01298.qmd.accelerator.block;

import lach_01298.qmd.tab.QMDTabs;
import nc.block.multiblock.BlockMultiblockPart;
import net.minecraft.block.material.Material;

public abstract class BlockAcceleratorPart extends BlockMultiblockPart
{

	public BlockAcceleratorPart()
	{
		super(Material.IRON, QMDTabs.MULTIBLOCKS);
		setHardness(2F);
		setResistance(12F);
	}

	public static abstract class Transparent extends BlockMultiblockPart.Transparent
	{

		public Transparent(boolean smartRender)
		{
			super(Material.IRON, QMDTabs.MULTIBLOCKS, smartRender);
			setHardness(2F);
			setResistance(10F);
		}
	}
}
