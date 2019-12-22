package lach_01298.qmd;

import lach_01298.qmd.item.QMDItems;
import nc.init.NCBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class QMDOreDictionary
{

	public static void register()
	{
		OreDictionary.registerOre("ingotTungsten", new ItemStack(QMDItems.ingot, MaterialEnums.IngotType.TUNGSTEN.getID(), 0));
		OreDictionary.registerOre("ingotNiobium", new ItemStack(QMDItems.ingot, MaterialEnums.IngotType.NIOBIUM.getID(), 0));
		
		OreDictionary.registerOre("dustTungsten", new ItemStack(QMDItems.ingot, MaterialEnums.DustType.TUNGSTEN.getID(), 0));
		OreDictionary.registerOre("dustNiobium", new ItemStack(QMDItems.ingot, MaterialEnums.DustType.NIOBIUM.getID(), 0));
		
		
		OreDictionary.registerOre("ingotTungstenCarbide", new ItemStack(QMDItems.ingotAlloy, MaterialEnums.IngotAlloyType.TUNGSTEN_CARBIDE.getID(), 0));
		OreDictionary.registerOre("ingotNiobiumTin", new ItemStack(QMDItems.ingotAlloy, MaterialEnums.IngotAlloyType.NIOBIUM_TIN.getID(), 0));
		
		
		
	}

}
