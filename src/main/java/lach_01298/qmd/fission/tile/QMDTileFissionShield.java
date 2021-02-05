package lach_01298.qmd.fission.tile;

import lach_01298.qmd.enums.BlockTypes.NeutronShieldType;
import lach_01298.qmd.fission.block.QMDBlockFissionShield;
import nc.multiblock.fission.tile.TileFissionShield;

public class QMDTileFissionShield extends TileFissionShield
{

	public QMDTileFissionShield(double heatPerFlux, double efficiency)
	{
		super(heatPerFlux, efficiency);
	}

	@Override
	public void setActivity (boolean isActive)
	{
		if (getBlockType() instanceof QMDBlockFissionShield)
		{
			((QMDBlockFissionShield) getBlockType()).setState(isActive, this);
		}
	}
	
	
	public static class Hafnium extends QMDTileFissionShield
	{

		public Hafnium()
		{
			super(NeutronShieldType.HAFNIUM.getHeatPerFlux(), NeutronShieldType.HAFNIUM.getEfficiency());
		}
	}
}
