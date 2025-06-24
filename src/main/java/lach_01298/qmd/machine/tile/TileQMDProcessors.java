package lach_01298.qmd.machine.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.gui.GUI_ID;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.capability.radiation.resistance.IRadiationResistance;
import nc.config.NCConfig;
import nc.util.Lazy;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.BiFunction;

public class TileQMDProcessors
{
	public static class TileOreLeacher extends TileQMDProcessor
	{
		public TileOreLeacher()
		{
			super("ore_leacher", 1, 3, 3, 0, defaultItemSorptions(1, 3, true), defaultTankCapacities(16000, 3, 0),
					defaultTankSorptions(3, 0), QMDRecipes.ore_leacher.validFluids, QMDConfig.processor_time[0],
					QMDConfig.processor_power[0], true, getCapacityFunction(ore_leacher_max_base_stats.get()),
					QMDRecipes.ore_leacher, GUI_ID.ORE_LEACHER, 0, 0);
		}
	}
	
	public static class TileIrradiator extends TileItemAmountFuelProcessor
	{
		public TileIrradiator()
		{
			super("irradiator", 1, 1, 1, defaultItemSorptions(1,1,1), QMDConfig.processor_time[1],
					QMDConfig.irradiator_fuel_usage, true, QMDRecipes.irradiator, QMDRecipes.irradiator_fuel, GUI_ID.IRRADIATOR, 0);
			
			IRadiationResistance resistance = this.getCapability(IRadiationResistance.CAPABILITY_RADIATION_RESISTANCE, null);
			if (resistance != null) {
				resistance.setBaseRadResistance(QMDConfig.irradiator_rad_res);
			}
		}
	}
	
	public static final Lazy<Pair<Double, Double>> ore_leacher_max_base_stats = new Lazy<>(() ->
			getMaxBaseStats(QMDRecipes.ore_leacher, QMDConfig.processor_time[0], QMDConfig.processor_power[0]));
	
	public static Pair<Double, Double> getMaxBaseStats(QMDRecipeHandler handler, double defaultTime, double defaultPower) {
		double maxBaseTime = 1D;
		double maxBasePower = 0D;
		for (QMDRecipe recipe : handler.getRecipeList()) {
			if (recipe != null) {
				maxBaseTime = Math.max(maxBaseTime, recipe.getBaseProcessTime(NCConfig.processor_time_multiplier * defaultTime));
				maxBasePower = Math.max(maxBasePower, recipe.getBaseProcessPower(NCConfig.processor_power_multiplier * defaultPower));
			}
		}
		return Pair.of(maxBaseTime, maxBasePower);
	}
	
	public static BiFunction<Double, Double, Long> getCapacityFunction(Pair<Double, Double> maxBaseStats) {
		return (x, y) -> (long) (Math.ceil(maxBaseStats.getLeft() / x) * Math.ceil(maxBaseStats.getRight() * y));
	}
}
