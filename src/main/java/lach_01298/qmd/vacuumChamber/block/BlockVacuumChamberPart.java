package lach_01298.qmd.vacuumChamber.block;

import lach_01298.qmd.tab.QMDTabs;
import nc.block.multiblock.BlockMultiblockPart;
import net.minecraft.block.material.Material;

public abstract class BlockVacuumChamberPart extends BlockMultiblockPart
{

	public BlockVacuumChamberPart()
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
