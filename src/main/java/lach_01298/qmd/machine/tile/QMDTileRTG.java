package lach_01298.qmd.machine.tile;

import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.config.QMDConfig;
import nc.config.NCConfig;
import nc.radiation.RadSources;
import nc.tile.generator.TilePassiveGenerator;
import nc.tile.generator.TileRTG;

public class QMDTileRTG extends TilePassiveGenerator
{
	public static class Strontium extends QMDTileRTG 
	{

		public Strontium() {
			super(QMDConfig.rtg_power[0], QMDRadSources.STRONTIUM_90/8D);
		}
	}
	
	
	
	public QMDTileRTG(int power, double radiation) 
	{
		super(power);
		getRadiationSource().setRadiationLevel(radiation);
	}
	
	@Override
	public int getGenerated() 
	{
		return power;
	}
	
	@Override
	public boolean shouldSaveRadiation() 
	{
		return false;
	}
}
