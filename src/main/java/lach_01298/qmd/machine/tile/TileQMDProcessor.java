package lach_01298.qmd.machine.tile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.gui.GUI_ID;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.processor.TileItemFluidProcessor;


public class TileQMDProcessor
{

	public static class TileOreLeacher extends TileItemFluidProcessor
	{
		public TileOreLeacher()
		{

			super("ore_leacher", 1, 3, 3, 0, defaultItemSorptions(1, 3, true), defaultTankCapacities(5000, 3, 0),
					defaultTankSorptions(3, 0), QMDRecipes.ore_leacher_valid_fluids, QMDConfig.processor_time[0],
					QMDConfig.processor_power[0], true, QMDRecipes.ore_leacher, GUI_ID.ORE_LEACHER, 0);
		}
	}
}
