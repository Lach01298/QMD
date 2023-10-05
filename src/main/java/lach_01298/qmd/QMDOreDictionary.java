package lach_01298.qmd;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.enums.MaterialTypes.ChemicalDustType;
import lach_01298.qmd.enums.MaterialTypes.DustType;
import lach_01298.qmd.enums.MaterialTypes.DustType2;
import lach_01298.qmd.enums.MaterialTypes.FissionWasteType;
import lach_01298.qmd.enums.MaterialTypes.IngotAlloyType;
import lach_01298.qmd.enums.MaterialTypes.IngotType;
import lach_01298.qmd.enums.MaterialTypes.IngotType2;
import lach_01298.qmd.enums.MaterialTypes.IsotopeType;
import lach_01298.qmd.enums.MaterialTypes.PartType;
import lach_01298.qmd.enums.MaterialTypes.SemiconductorType;
import lach_01298.qmd.enums.MaterialTypes.SpallationWasteType;
import lach_01298.qmd.enums.MaterialTypes.SpallationWasteType2;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.item.QMDItems;
import nc.handler.OreDictHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class QMDOreDictionary
{


	
	
	public static void register()
	{
		OreDictionary.registerOre("ingotTungsten",new ItemStack(QMDItems.ingot, 1, IngotType.TUNGSTEN.getID()));
		OreDictionary.registerOre("ingotNiobium",new ItemStack(QMDItems.ingot, 1, IngotType.NIOBIUM.getID()));
		OreDictionary.registerOre("ingotChromium",new ItemStack(QMDItems.ingot, 1, IngotType.CHROMIUM.getID()));
		OreDictionary.registerOre("ingotTitanium",new ItemStack(QMDItems.ingot, 1, IngotType.TITANIUM.getID()));
		OreDictionary.registerOre("ingotCobalt",new ItemStack(QMDItems.ingot, 1, IngotType.COBALT.getID()));
		OreDictionary.registerOre("ingotNickel",new ItemStack(QMDItems.ingot, 1, IngotType.NICKEL.getID()));
		OreDictionary.registerOre("ingotHafnium",new ItemStack(QMDItems.ingot, 1, IngotType.HAFNIUM.getID()));
		OreDictionary.registerOre("ingotZinc",new ItemStack(QMDItems.ingot, 1, IngotType.ZINC.getID()));
		OreDictionary.registerOre("ingotOsmium",new ItemStack(QMDItems.ingot, 1, IngotType.OSMIUM.getID()));
		OreDictionary.registerOre("ingotIridium",new ItemStack(QMDItems.ingot, 1, IngotType.IRIDIUM.getID()));
		OreDictionary.registerOre("ingotPlatinum",new ItemStack(QMDItems.ingot, 1, IngotType.PLATNIUM.getID()));
		OreDictionary.registerOre("ingotSodium",new ItemStack(QMDItems.ingot, 1, IngotType.SODIUM.getID()));
		OreDictionary.registerOre("ingotPotassium",new ItemStack(QMDItems.ingot, 1, IngotType.POTASSIUM.getID()));
		OreDictionary.registerOre("ingotCalcium",new ItemStack(QMDItems.ingot, 1, IngotType.CALCIUM.getID()));
		OreDictionary.registerOre("ingotStrontium",new ItemStack(QMDItems.ingot, 1, IngotType.STRONTIUM.getID()));
		
		OreDictionary.registerOre("ingotYttrium",new ItemStack(QMDItems.ingot2, 1, IngotType2.YTTRIUM.getID()));
		OreDictionary.registerOre("ingotNeodymium",new ItemStack(QMDItems.ingot2, 1, IngotType2.NEODYMIUM.getID()));
		//OreDictionary.registerOre("ingotMercury",new ItemStack(QMDItems.ingot2, 1, IngotType2.MERCURY.getID()));
		
		
		
		
		OreDictionary.registerOre("dustTungsten",new ItemStack(QMDItems.dust, 1, DustType.TUNGSTEN.getID()));
		OreDictionary.registerOre("dustNiobium",new ItemStack(QMDItems.dust, 1, DustType.NIOBIUM.getID()));
		OreDictionary.registerOre("dustChromium",new ItemStack(QMDItems.dust, 1, DustType.CHROMIUM.getID()));
		OreDictionary.registerOre("dustTitanium",new ItemStack(QMDItems.dust, 1, DustType.TITANIUM.getID()));
		OreDictionary.registerOre("dustCobalt",new ItemStack(QMDItems.dust, 1, DustType.COBALT.getID()));
		OreDictionary.registerOre("dustNickel",new ItemStack(QMDItems.dust, 1, DustType.NICKEL.getID()));
		OreDictionary.registerOre("dustHafnium",new ItemStack(QMDItems.dust, 1, DustType.HAFNIUM.getID()));
		OreDictionary.registerOre("dustZinc",new ItemStack(QMDItems.dust, 1, DustType.ZINC.getID()));
		OreDictionary.registerOre("dustOsmium",new ItemStack(QMDItems.dust, 1, DustType.OSMIUM.getID()));
		OreDictionary.registerOre("dustIridium",new ItemStack(QMDItems.dust, 1, DustType.IRIDIUM.getID()));
		OreDictionary.registerOre("dustPlatinum",new ItemStack(QMDItems.dust, 1, DustType.PLATNIUM.getID()));
		OreDictionary.registerOre("dustSodium",new ItemStack(QMDItems.dust, 1, DustType.SODIUM.getID()));
		OreDictionary.registerOre("dustPotassium",new ItemStack(QMDItems.dust, 1, DustType.POTASSIUM.getID()));
		OreDictionary.registerOre("dustCalcium",new ItemStack(QMDItems.dust, 1, DustType.CALCIUM.getID()));
		OreDictionary.registerOre("dustStrontium",new ItemStack(QMDItems.dust, 1, DustType.STRONTIUM.getID()));
		
		OreDictionary.registerOre("dustYttrium",new ItemStack(QMDItems.dust2, 1, DustType2.YTTRIUM.getID()));
		OreDictionary.registerOre("dustNeodymium",new ItemStack(QMDItems.dust2, 1, DustType2.NEODYMIUM.getID()));
		OreDictionary.registerOre("dustIodine",new ItemStack(QMDItems.dust2, 1, DustType2.IODINE.getID()));
		OreDictionary.registerOre("dustSamarium",new ItemStack(QMDItems.dust2, 1, DustType2.SAMARIUM.getID()));
		OreDictionary.registerOre("dustTerbium",new ItemStack(QMDItems.dust2, 1, DustType2.TERBIUM.getID()));
		OreDictionary.registerOre("dustErbium",new ItemStack(QMDItems.dust2, 1, DustType2.ERBIUM.getID()));
		OreDictionary.registerOre("dustYtterbium",new ItemStack(QMDItems.dust2, 1, DustType2.YTTERBIUM.getID()));

		
		OreDictionary.registerOre("wasteFissionLight",new ItemStack(QMDItems.fissionWaste, 1, FissionWasteType.LIGHT.getID()));
		OreDictionary.registerOre("wasteFissionHeavy",new ItemStack(QMDItems.fissionWaste, 1, FissionWasteType.HEAVY.getID()));
		
		OreDictionary.registerOre("wasteSpallationCalifornium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.CALIFORNIUM.getID()));
		OreDictionary.registerOre("wasteSpallationBerkelium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.BERKELIUM.getID()));
		OreDictionary.registerOre("wasteSpallationCurium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.CURIUM.getID()));
		OreDictionary.registerOre("wasteSpallationAmericium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.AMERICIUM.getID()));
		OreDictionary.registerOre("wasteSpallationPlutonium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.PLUTONIUM.getID()));
		OreDictionary.registerOre("wasteSpallationNeptunium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.NEPTUNIUM.getID()));
		OreDictionary.registerOre("wasteSpallationUranium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.URANIUM.getID()));
		OreDictionary.registerOre("wasteSpallationProtactinium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.PROTACTINIUM.getID()));
		OreDictionary.registerOre("wasteSpallationThorium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.THORIUM.getID()));
		OreDictionary.registerOre("wasteSpallationRadium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.RADIUM.getID()));
		OreDictionary.registerOre("wasteSpallationPolonium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.POLONIUM.getID()));
		OreDictionary.registerOre("wasteSpallationBismuth",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.BISMUTH.getID()));
		OreDictionary.registerOre("wasteSpallationLead",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.LEAD.getID()));
		OreDictionary.registerOre("wasteSpallationGold",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.GOLD.getID()));
		OreDictionary.registerOre("wasteSpallationPlatinum",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.PLATINUM.getID()));
		OreDictionary.registerOre("wasteSpallationIridium",new ItemStack(QMDItems.spallationWaste, 1, SpallationWasteType.IRIDIUM.getID()));
		
		OreDictionary.registerOre("wasteSpallationOsmium",new ItemStack(QMDItems.spallationWaste2, 1, SpallationWasteType2.OSMIUM.getID()));
		OreDictionary.registerOre("wasteSpallationTungsten",new ItemStack(QMDItems.spallationWaste2, 1, SpallationWasteType2.TUNGSTEN.getID()));
		OreDictionary.registerOre("wasteSpallationHafnium",new ItemStack(QMDItems.spallationWaste2, 1, SpallationWasteType2.HAFNIUM.getID()));
		OreDictionary.registerOre("wasteSpallationMercury",new ItemStack(QMDItems.spallationWaste2, 1, SpallationWasteType2.MERCURY.getID()));
	
		
		
		
		OreDictionary.registerOre("ingotTungstenCarbide",new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.TUNGSTEN_CARBIDE.getID()));
		OreDictionary.registerOre("ingotNiobiumTin",new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.NIOBIUM_TIN.getID()));
		OreDictionary.registerOre("ingotStainlessSteel",new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.STAINLESS_STEEL.getID()));
		OreDictionary.registerOre("ingotNiobiumTitanium",new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.NIOBIUM_TITANIUM.getID()));
		OreDictionary.registerOre("ingotOsmiridium",new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.OSMIRIDIUM.getID()));
		OreDictionary.registerOre("ingotNichrome",new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.NICHROME.getID()));
		OreDictionary.registerOre("ingotSuperAlloy",new ItemStack(QMDItems.ingotAlloy, 1, IngotAlloyType.SUPER_ALLOY.getID()));
		
		OreDictionary.registerOre("ingotSodium22",new ItemStack(QMDItems.isotope, 1, IsotopeType.SODIUM_22.getID()));
		OreDictionary.registerOre("ingotBeryllium7",new ItemStack(QMDItems.isotope, 1, IsotopeType.BERYLLIUM_7.getID()));
		OreDictionary.registerOre("ingotMagnesium24",new ItemStack(QMDItems.isotope, 1, IsotopeType.MAGNESIUM_24.getID()));
		OreDictionary.registerOre("ingotMagnesium26",new ItemStack(QMDItems.isotope, 1, IsotopeType.MAGNESIUM_26.getID()));
		OreDictionary.registerOre("ingotUranium234",new ItemStack(QMDItems.isotope, 1, IsotopeType.Uranium_234.getID()));
		OreDictionary.registerOre("dustProtactinium231",new ItemStack(QMDItems.isotope, 1, IsotopeType.PROTACTINIUM_231.getID()));
		OreDictionary.registerOre("ingotCobalt60",new ItemStack(QMDItems.isotope, 1, IsotopeType.COBALT_60.getID()));
		OreDictionary.registerOre("ingotIridium192",new ItemStack(QMDItems.isotope, 1, IsotopeType.IRIDIUM_192.getID()));
		OreDictionary.registerOre("ingotCalcium48",new ItemStack(QMDItems.isotope, 1, IsotopeType.CALCIUM_48.getID()));
		

		OreDictionary.registerOre("bouleSilicon",new ItemStack(QMDItems.semiconductor, 1, SemiconductorType.SILICON_BOULE.getID()));
		OreDictionary.registerOre("siliconWafer",new ItemStack(QMDItems.semiconductor, 1, SemiconductorType.SILICON_WAFER.getID()));
		OreDictionary.registerOre("siliconPDoped",new ItemStack(QMDItems.semiconductor, 1, SemiconductorType.SILICON_P_DOPED.getID()));
		OreDictionary.registerOre("siliconNDoped",new ItemStack(QMDItems.semiconductor, 1, SemiconductorType.SILICON_N_DOPED.getID()));
		OreDictionary.registerOre("processorBasic",new ItemStack(QMDItems.semiconductor, 1, SemiconductorType.BASIC_PROCESSOR.getID()));
		OreDictionary.registerOre("processorAdvanced",new ItemStack(QMDItems.semiconductor, 1, SemiconductorType.ADVANCED_PROCESSOR.getID()));
		OreDictionary.registerOre("processorElite",new ItemStack(QMDItems.semiconductor, 1, SemiconductorType.ELITE_PROCESSOR.getID()));
		
		
		OreDictionary.registerOre("wireBSCCO",new ItemStack(QMDItems.part, 1, PartType.WIRE_BSCCO.getID()));
		OreDictionary.registerOre("wireGoldTungsten",new ItemStack(QMDItems.part, 1, PartType.WIRE_GOLD_TUNGSTEN.getID()));
		OreDictionary.registerOre("rodNdYAG",new ItemStack(QMDItems.part, 1, PartType.ROD_ND_YAG.getID()));
		OreDictionary.registerOre("magnetNeodymium",new ItemStack(QMDItems.part, 1, PartType.MAGNET_ND.getID()));
		
		OreDictionary.registerOre("dustTungstenOxide",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.TUNGSTEN_OXIDE.getID()));
		OreDictionary.registerOre("dustBSCCO",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.BSCCO.getID()));
		OreDictionary.registerOre("dustNiter",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.SODIUM_NITRATE.getID()));
		OreDictionary.registerOre("dustSaltpeter",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.SODIUM_NITRATE.getID()));
		OreDictionary.registerOre("dustSodiumNitrate",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.SODIUM_NITRATE.getID()));
		OreDictionary.registerOre("dustSodiumChloride",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.SODIUM_CHLORIDE.getID()));
		OreDictionary.registerOre("dustSalt",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.SODIUM_CHLORIDE.getID()));
		OreDictionary.registerOre("dustCopperOxide",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.COPPER_OXIDE.getID()));
		OreDictionary.registerOre("dustHafniumOxide",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.HAFNIUM_OXIDE.getID()));
		OreDictionary.registerOre("dustStrontiumChloride",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.STRONTIUM_CHLORIDE.getID()));
		OreDictionary.registerOre("dustZincSulfide",new ItemStack(QMDItems.chemicalDust, 1, ChemicalDustType.ZINC_SULFIDE.getID()));
	
		
		OreDictionary.registerOre("cellAntimatter",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIHYDROGEN.getID())));
		OreDictionary.registerOre("cellAntimatter",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIDEUTERIUM.getID())));
		OreDictionary.registerOre("cellAntimatter",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTITRITIUM.getID())));
		OreDictionary.registerOre("cellAntimatter",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIHELIUM3.getID())));
		OreDictionary.registerOre("cellAntimatter",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIHELIUM.getID())));
	
		OreDictionary.registerOre("cellAntihydrogen",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIHYDROGEN.getID())));
		OreDictionary.registerOre("cellAntideuterium",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIDEUTERIUM.getID())));
		OreDictionary.registerOre("cellAntitritium",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTITRITIUM.getID())));
		OreDictionary.registerOre("cellAntihelium3",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIHELIUM3.getID())));
		OreDictionary.registerOre("cellAntihelium",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.ANTIHELIUM.getID())));
		
		OreDictionary.registerOre("cellPositronium",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.POSITRONIUM.getID())));
		OreDictionary.registerOre("cellMuonium",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.MUONIUM.getID())));
		OreDictionary.registerOre("cellTauonium",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.TAUONIUM.getID())));
		OreDictionary.registerOre("cellGlueballs",IItemParticleAmount.fullItem(new ItemStack(QMDItems.cell, 1, CellType.GLUEBALLS.getID())));
		
		OreDictionary.registerOre("blockStrontium90", new ItemStack(QMDBlocks.strontium90));
		
		OreDictHandler.registerIsotopes(QMDItems.copernicium, "Copernicium", 291);
		OreDictHandler.registerFuels(QMDItems.pellet_copernicium, QMDItems.fuel_copernicium, QMDItems.depleted_fuel_copernicium, "MIX291");
	}

}
