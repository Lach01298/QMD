package lach_01298.qmd.liquefier.tile;

import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.hx.HeatExchanger;
import nc.multiblock.hx.HeatExchangerTubeNetwork;
import nc.tile.hx.TileHeatExchangerPart;

import javax.annotation.Nullable;

public class TileLiquefierNozzle extends TileHeatExchangerPart
{
	public @Nullable HeatExchangerTubeNetwork network;

	public TileLiquefierNozzle()
	{
		super(CuboidalPartPositionType.INTERIOR);
	}

	@Override
	public void onMachineAssembled(HeatExchanger multiblock)
	{
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
	}


}