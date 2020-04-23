package lach_01298.qmd.enums;

import nc.config.NCConfig;
import nc.multiblock.rtg.tile.TileRTG;
import nc.radiation.RadSources;
import net.minecraft.tileentity.TileEntity;
import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.machine.tile.QMDTileRTG;


public enum QMDRTGType
{
	STRONTIUM(0, QMDRadSources.STRONTIUM_90 / 8D);

	private int id;
	private double radiation;

	private QMDRTGType(int id, double radiation)
	{
		this.id = id;
		this.radiation = radiation;
	}

	public int getPower()
	{
		return QMDConfig.rtg_power[id];
	}

	public double getRadiation()
	{
		return radiation;
	}

	public TileEntity getTile()
	{
		switch (this)
		{
		case STRONTIUM:
			return new QMDTileRTG.Strontium();
		default:
			return null;
		}
	}
}
