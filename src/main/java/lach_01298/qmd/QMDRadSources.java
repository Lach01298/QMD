package lach_01298.qmd;

import lach_01298.qmd.item.QMDItems;
import nc.radiation.RadSources;

public class QMDRadSources
{
	
	
	public static final double SODIUM_22 = 0.384D;
	public static final double PROMETIUM_147 = 0.381D;
	public static final double BERYLLIUM_7 = 6.89D;
	
	
	public static void init() 
	{
		RadSources.putMaterial(BERYLLIUM_7, "Beryllium7");
		RadSources.putMaterial(SODIUM_22, "Sodium22");
		RadSources.putMaterial(PROMETIUM_147, "Promethium147");
		
		RadSources.put(SODIUM_22, QMDItems.source_sodium_22);
	}
	
	
	
}
