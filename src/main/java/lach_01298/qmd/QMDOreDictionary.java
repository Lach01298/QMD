package lach_01298.qmd;

import lach_01298.qmd.item.QMDItems;
import nc.init.NCBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class QMDOreDictionary
{

	public static void register()
	{
		OreDictionary.registerOre("ingotTungsten",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.TUNGSTEN.getID()));
		OreDictionary.registerOre("ingotNiobium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.NIOBIUM.getID()));
		OreDictionary.registerOre("ingotChromium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.CHROMIUM.getID()));

		OreDictionary.registerOre("dustTungsten",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.TUNGSTEN.getID()));
		OreDictionary.registerOre("dustNiobium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.NIOBIUM.getID()));
		OreDictionary.registerOre("dustChromium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.CHROMIUM.getID()));

		OreDictionary.registerOre("ingotTungstenCarbide",new ItemStack(QMDItems.ingotAlloy, 1, MaterialEnums.IngotAlloyType.TUNGSTEN_CARBIDE.getID()));
		OreDictionary.registerOre("ingotNiobiumTin",new ItemStack(QMDItems.ingotAlloy, 1, MaterialEnums.IngotAlloyType.NIOBIUM_TIN.getID()));
		OreDictionary.registerOre("ingotStainlessSteel",new ItemStack(QMDItems.ingotAlloy, 1, MaterialEnums.IngotAlloyType.STAINLESS_STEEL.getID()));

		OreDictionary.registerOre("ingotSodium22",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.SODIUM_22.getID()));
		OreDictionary.registerOre("ingotPromethium147",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.PROMETHIUM_147.getID()));
		OreDictionary.registerOre("ingotBeryllium7",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.BERYLLIUM_7.getID()));
		OreDictionary.registerOre("ingotMagnesium24",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.MAGNESIUM_24.getID()));
		OreDictionary.registerOre("ingotMagnesium26",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.MAGNESIUM_26.getID()));
	}

}
