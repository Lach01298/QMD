package lach_01298.qmd.machine.tile;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lach_01298.qmd.recipes.QMDRecipes;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.processor.TileItemFluidProcessor;


public class TileQMDProcessor
{

	public static class TileOreLeacher extends TileItemFluidProcessor
	{
		public TileOreLeacher()
		{

			super("infuser", 1, 1, 1, 0, defaultItemSorptions(1, 1, true), defaultTankCapacities(16000, 1, 0),
					defaultTankSorptions(1, 0), QMDRecipes..infuser_valid_fluids, 400,
					20, true, QMDRecipes.ore_leacher, 6, 0);
		}
	}
}
