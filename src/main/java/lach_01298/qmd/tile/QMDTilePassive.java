package lach_01298.qmd.tile;

import static nc.config.NCConfig.processor_passive_rate;

import nc.recipe.ingredient.FluidIngredient;
import nc.tile.passive.TilePassiveAbstract;
import nc.tile.passive.TilePassive.NitrogenCollectorAbstract;

public class QMDTilePassive
{
	public static class HeliumCollector extends TilePassiveAbstract
	{

		public HeliumCollector()
		{
			super("helium_collector", new FluidIngredient("helium", 1),processor_passive_rate[2] * 1);
		}
	}
	
	public static class NeonCollector extends TilePassiveAbstract
	{

		public NeonCollector()
		{
			super("neon_collector", new FluidIngredient("neon", 1),processor_passive_rate[2] * 1);
		}
	}
	
	public static class ArgonCollector extends TilePassiveAbstract
	{

		public ArgonCollector()
		{
			super("argon_collector", new FluidIngredient("argon", 1),processor_passive_rate[2] * 1);
		}
	}

	
}
