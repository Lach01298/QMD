package lach_01298.qmd.liquefier.tile;

import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.config.QMDConfig;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.hx.HeatExchanger;
import nc.tile.hx.TileHeatExchangerPart;

public class TileLiquefierCompressor extends TileHeatExchangerPart
{

	public final double energyEfficiency;
	public final double heatEfficiency;
	public final String name;

	public TileLiquefierCompressor(double energyEfficiency, double heatEfficiency, String name)
	{
		super(CuboidalPartPositionType.WALL);
		this.energyEfficiency = energyEfficiency;
		this.heatEfficiency = heatEfficiency;
		this.name = name;
	}


	public static class Copper extends TileLiquefierCompressor
	{
		public Copper()
		{
			super(QMDConfig.liquefier_compressor_energy_efficiency[0],QMDConfig.liquefier_compressor_heat_efficiency[0], "copper");
		}
	}
	public static class Neodymium extends TileLiquefierCompressor
	{
		public Neodymium()
		{
			super(QMDConfig.liquefier_compressor_energy_efficiency[1],QMDConfig.liquefier_compressor_heat_efficiency[1], "neodymium");
		}
	}
	public static class SamariumCobalt extends TileLiquefierCompressor
	{
		public SamariumCobalt()
		{
			super(QMDConfig.liquefier_compressor_energy_efficiency[2],QMDConfig.liquefier_compressor_heat_efficiency[2], "samarium_cobalt");
		}
	}


	@Override
	public void onMachineAssembled(HeatExchanger multiblock)
	{
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
	}
}