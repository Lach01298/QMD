package lach_01298.qmd;

import lach_01298.qmd.enums.MaterialEnums;
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
		OreDictionary.registerOre("ingotTitanium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.TITANIUM.getID()));
		OreDictionary.registerOre("ingotCobalt",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.COBALT.getID()));
		OreDictionary.registerOre("ingotNickel",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.NICKEL.getID()));
		OreDictionary.registerOre("ingotHafnium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.HAFNIUM.getID()));
		OreDictionary.registerOre("ingotZinc",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.ZINC.getID()));
		OreDictionary.registerOre("ingotOsmium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.OSMIUM.getID()));
		OreDictionary.registerOre("ingotIridium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.IRIDIUM.getID()));
		OreDictionary.registerOre("ingotPlatinum",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.PLATNIUM.getID()));
		OreDictionary.registerOre("ingotSodium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.SODIUM.getID()));
		OreDictionary.registerOre("ingotPotassium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.POTASSIUM.getID()));
		OreDictionary.registerOre("ingotCalcium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.CALCIUM.getID()));
		OreDictionary.registerOre("ingotStrontium",new ItemStack(QMDItems.ingot, 1, MaterialEnums.IngotType.STRONTIUM.getID()));
		
		
		OreDictionary.registerOre("dustTungsten",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.TUNGSTEN.getID()));
		OreDictionary.registerOre("dustNiobium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.NIOBIUM.getID()));
		OreDictionary.registerOre("dustChromium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.CHROMIUM.getID()));
		OreDictionary.registerOre("dustTitanium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.TITANIUM.getID()));
		OreDictionary.registerOre("dustCobalt",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.COBALT.getID()));
		OreDictionary.registerOre("dustNickel",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.NICKEL.getID()));
		OreDictionary.registerOre("dustHafnium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.HAFNIUM.getID()));
		OreDictionary.registerOre("dustZinc",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.ZINC.getID()));
		OreDictionary.registerOre("dustOsmium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.OSMIUM.getID()));
		OreDictionary.registerOre("dustIridium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.IRIDIUM.getID()));
		OreDictionary.registerOre("dustPlatinum",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.PLATNIUM.getID()));
		OreDictionary.registerOre("dustSodium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.SODIUM.getID()));
		OreDictionary.registerOre("dustPotassium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.POTASSIUM.getID()));
		OreDictionary.registerOre("dustCalcium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.CALCIUM.getID()));
		OreDictionary.registerOre("dustStrontium",new ItemStack(QMDItems.dust, 1, MaterialEnums.DustType.STRONTIUM.getID()));
		
		OreDictionary.registerOre("ingotTungstenCarbide",new ItemStack(QMDItems.ingotAlloy, 1, MaterialEnums.IngotAlloyType.TUNGSTEN_CARBIDE.getID()));
		OreDictionary.registerOre("ingotNiobiumTin",new ItemStack(QMDItems.ingotAlloy, 1, MaterialEnums.IngotAlloyType.NIOBIUM_TIN.getID()));
		OreDictionary.registerOre("ingotStainlessSteel",new ItemStack(QMDItems.ingotAlloy, 1, MaterialEnums.IngotAlloyType.STAINLESS_STEEL.getID()));

		OreDictionary.registerOre("ingotSodium22",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.SODIUM_22.getID()));
		OreDictionary.registerOre("ingotPromethium147",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.PROMETHIUM_147.getID()));
		OreDictionary.registerOre("ingotBeryllium7",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.BERYLLIUM_7.getID()));
		OreDictionary.registerOre("ingotMagnesium24",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.MAGNESIUM_24.getID()));
		OreDictionary.registerOre("ingotMagnesium26",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.MAGNESIUM_26.getID()));
		OreDictionary.registerOre("ingotStrontium90",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.STRONTIUM_90.getID()));
		OreDictionary.registerOre("ingotLead210",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.LEAD_210.getID()));
		OreDictionary.registerOre("ingotVanadium50",new ItemStack(QMDItems.isotope, 1, MaterialEnums.IsotopeType.VANADIUM_50.getID()));

		
		
		OreDictionary.registerOre("siliconBoule",new ItemStack(QMDItems.semiconductor, 1, MaterialEnums.SemiconductorType.SILICON_BOULE.getID()));
		OreDictionary.registerOre("siliconWafer",new ItemStack(QMDItems.semiconductor, 1, MaterialEnums.SemiconductorType.SILICON_WAFER.getID()));
		
		
		OreDictionary.registerOre("wireBscco",new ItemStack(QMDItems.part, 1, MaterialEnums.PartType.WIRE_BSCCO.getID()));
		
		OreDictionary.registerOre("dustTungstenOxide",new ItemStack(QMDItems.chemicalDust, 1, MaterialEnums.ChemicalDustType.TUNGSTEN_OXIDE.getID()));
		OreDictionary.registerOre("dustBscco",new ItemStack(QMDItems.chemicalDust, 1, MaterialEnums.ChemicalDustType.BSCCO.getID()));
		OreDictionary.registerOre("dustNiter",new ItemStack(QMDItems.chemicalDust, 1, MaterialEnums.ChemicalDustType.SODIUM_NITRATE.getID()));
		OreDictionary.registerOre("dustSodiumNitrate",new ItemStack(QMDItems.chemicalDust, 1, MaterialEnums.ChemicalDustType.SODIUM_NITRATE.getID()));
		OreDictionary.registerOre("dustSodiumChloride",new ItemStack(QMDItems.chemicalDust, 1, MaterialEnums.ChemicalDustType.SODIUM_CHLORIDE.getID()));
		OreDictionary.registerOre("dustCopperOxide",new ItemStack(QMDItems.chemicalDust, 1, MaterialEnums.ChemicalDustType.COPPER_OXIDE.getID()));
		
	}

}
