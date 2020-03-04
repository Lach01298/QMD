package lach_01298.qmd;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.item.QMDItems;
import nc.radiation.RadSources;
import net.minecraft.item.ItemStack;

public class QMDRadSources
{
	
	
	public static final double SODIUM_22 = 0.384;
	public static final double PROMETHIUM_147 = 0.381;
	public static final double BERYLLIUM_7 = 6.89;
	public static final double STRONTIUM_90 = 0.0347;
	public static final double LEAD_210 = 0.0448;
	public static final double URANIUM_234 = 0.00000407;
	public static final double PROTACTINIUM_231 = 0.0000305;
	public static final double COBALT_60 = 0.190;
	public static final double IRIDIUM_192 = 4.94;
	
	public static void init() 
	{
		RadSources.putMaterial(BERYLLIUM_7, "Beryllium7");
		RadSources.putMaterial(SODIUM_22, "Sodium22");
		RadSources.putMaterial(PROMETHIUM_147, "Promethium147");
		RadSources.putMaterial(STRONTIUM_90, "Strontium90");
		RadSources.putMaterial(LEAD_210, "Lead210");
		RadSources.putMaterial(URANIUM_234, "Uranium234");
		RadSources.putMaterial(PROTACTINIUM_231, "Protactinium231");
		RadSources.putMaterial(COBALT_60, "Cobalt60");
		RadSources.putMaterial(IRIDIUM_192, "Iridium192");
		
		RadSources.put(SODIUM_22/2D, QMDItems.source_sodium_22);
		RadSources.put(COBALT_60/2D, QMDItems.source_cobalt_60);
		RadSources.put(IRIDIUM_192/2D, QMDItems.source_iridium_192);
		
		RadSources.put(STRONTIUM_90/4D, QMDBlocks.rtgStrontium);
		
		
		RadSources.addToFoodMaps(new ItemStack(QMDItems.flesh), 0, 3.0);
	}
	
	
	
}
