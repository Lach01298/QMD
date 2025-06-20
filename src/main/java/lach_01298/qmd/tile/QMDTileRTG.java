package lach_01298.qmd.tile;

import lach_01298.qmd.enums.BlockTypes.RTGType;
import nc.tile.rtg.TileRTG;

public class QMDTileRTG extends TileRTG
{

	public QMDTileRTG(int power, double radiation)
	{
		super();
		this.power = power;
		this.radiation = radiation;
	}

	public static class Strontium extends QMDTileRTG
	{

		public Strontium()
		{
			super(RTGType.STRONTIUM.getPower(), RTGType.STRONTIUM.getRadiation());
		}
	}
}
