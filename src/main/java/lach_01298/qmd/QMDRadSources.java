package lach_01298.qmd;

import lach_01298.qmd.item.QMDItems;
import nc.radiation.RadSources;

public class QMDRadSources
{
	
	
	public static final double SODIUM_22 = 0.384;
	public static final double PROMETIUM_147 = 0.381;
	public static final double BERYLLIUM_7 = 6.89;
	public static final double STRONTIUM_90 = 0.0347;
	public static final double VANADIUM_50 = Math.pow(6.67, -18);
	public static final double LEAD_210 = 0.0448;
	public static final double URANIUM_234 = 0.00000407;
	public static final double PROTACTINIUM_231 = 0.0000305;
	public static final double COBALT_60 = 0.190;
	
	public static void init() 
	{
		RadSources.putMaterial(BERYLLIUM_7, "Beryllium7");
		RadSources.putMaterial(SODIUM_22, "Sodium22");
		RadSources.putMaterial(PROMETIUM_147, "Promethium147");
		RadSources.putMaterial(STRONTIUM_90, "Strontium90");
		RadSources.putMaterial(VANADIUM_50, "Vanadium50");
		RadSources.putMaterial(LEAD_210, "Lead210");
		RadSources.putMaterial(URANIUM_234, "Uranium234");
		RadSources.putMaterial(PROTACTINIUM_231, "Protactinium231");
		
		RadSources.put(SODIUM_22, QMDItems.source_sodium_22);
	}
	
	
	
}
