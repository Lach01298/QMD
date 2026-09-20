package lach_01298.qmd.block;

import lach_01298.qmd.tab.QMDTabs;
import nc.block.multiblock.BlockMultiblockPart;
import net.minecraft.block.material.Material;

public abstract class BlockCustomMultiblockPart extends BlockMultiblockPart
{
	public BlockCustomMultiblockPart() {
		super(Material.IRON, QMDTabs.MULTIBLOCKS);
	}

	public abstract static class Transparent extends BlockMultiblockPart.Transparent {
		public Transparent(boolean smartRender) {
			super(Material.IRON, QMDTabs.MULTIBLOCKS, smartRender);
		}
	}
}
