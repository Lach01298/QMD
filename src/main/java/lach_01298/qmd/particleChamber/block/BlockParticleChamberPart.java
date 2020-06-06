package lach_01298.qmd.particleChamber.block;

import lach_01298.qmd.tab.QMDTabs;
import nc.multiblock.block.BlockMultiblockPart;
import net.minecraft.block.material.Material;

public abstract class BlockParticleChamberPart extends BlockMultiblockPart
{

	public BlockParticleChamberPart()
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
