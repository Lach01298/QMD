package lach_01298.qmd.tile;

import lach_01298.qmd.enums.BlockTypes.RTGType;
import nc.tile.rtg.TileRTG;

public class QMDTileRTG extends TileRTG
{

	public QMDTileRTG(String rtgType, long power, double radiation)
	{
		super(rtgType, power, radiation);
	}

	public static class Strontium extends QMDTileRTG
	{

		public Strontium()
		{
			super(RTGType.STRONTIUM.name(),RTGType.STRONTIUM.getPower(), RTGType.STRONTIUM.getRadiation());
		}
	}
}
