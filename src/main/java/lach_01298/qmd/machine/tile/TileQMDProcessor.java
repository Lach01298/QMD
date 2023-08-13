package lach_01298.qmd.machine.tile;


import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.gui.GUI_ID;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.capability.radiation.resistance.IRadiationResistance;
import nc.tile.processor.TileItemFluidProcessor;


public class TileQMDProcessor
{

	public static class TileOreLeacher extends TileItemFluidProcessor
	{
		public TileOreLeacher()
		{

			super("ore_leacher", 1, 3, 3, 0, defaultItemSorptions(1, 3, true), defaultTankCapacities(16000, 3, 0),
					defaultTankSorptions(3, 0), QMDRecipes.ore_leacher_valid_fluids, QMDConfig.processor_time[0],
					QMDConfig.processor_power[0], true, QMDRecipes.ore_leacher, GUI_ID.ORE_LEACHER, 0, 0);
		}
	}
	public static class TileIrradiator extends TileItemAmountFuelProcessor
	{
		public TileIrradiator()
		{
			super("irradiator", 1, 1, 1, defaultItemSorptions(1,1,1), QMDConfig.processor_time[1], QMDConfig.irradiator_fuel_usage,
					true, QMDRecipes.irradiator, QMDRecipes.irradiator_fuel, GUI_ID.IRRADIATOR, 0);
			
			IRadiationResistance resistance = this.getCapability(IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE, null);
			resistance.setBaseRadResistance(QMDConfig.irradiator_rad_res);
		}
	}
}
