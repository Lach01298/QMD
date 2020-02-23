package lach_01298.qmd.tab;

import nc.config.NCConfig;
import nc.tab.TabMachine;
import nc.tab.TabMaterial;
import nc.tab.TabMisc;
import nc.tab.TabMultiblock;
import nc.tab.TabRadiation;
import net.minecraft.creativetab.CreativeTabs;

public class QMDTabs
{
	public static final CreativeTabs ITEMS = new TabQMDItems();
	public static final CreativeTabs MULTIBLOCKS = new TabQMDMultiblocks();
	public static final CreativeTabs BLOCKS = new TabQMDBlocks();
}
