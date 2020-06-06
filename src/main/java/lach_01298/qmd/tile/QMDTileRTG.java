package lach_01298.qmd.tile;

import lach_01298.qmd.enums.BlockTypes.RTGType;
import nc.multiblock.rtg.tile.TileRTG;

public class QMDTileRTG extends TileRTG
{

	public QMDTileRTG(int power, double radiation)
	{
		super(power, radiation);
	}

	public static class Strontium extends QMDTileRTG
	{

		public Strontium()
		{
			super(RTGType.STRONTIUM.getPower(), RTGType.STRONTIUM.getRadiation());
		}
	}
}
