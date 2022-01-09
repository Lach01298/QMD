package lach_01298.qmd.recipes;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.BlockTypes.CoolerType1;
import lach_01298.qmd.enums.BlockTypes.CoolerType2;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.enums.BlockTypes.HeaterType;
import lach_01298.qmd.enums.BlockTypes.LampType;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import lach_01298.qmd.enums.MaterialTypes.PartType;
import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.item.QMDArmour;
import lach_01298.qmd.item.QMDItems;
import nc.ModCheck;
import nc.init.NCArmor;
import nc.init.NCBlocks;
import nc.init.NCItems;
import nc.recipe.vanilla.ingredient.BucketIngredient;
import nc.recipe.vanilla.recipe.ShapedEnergyRecipe;
import nc.recipe.vanilla.recipe.ShapedFluidRecipe;
import nc.recipe.vanilla.recipe.ShapelessArmorRadShieldingRecipe;
import nc.recipe.vanilla.recipe.ShapelessFluidRecipe;
import nc.util.NCUtil;
import nc.util.OreDictHelper;
import nc.util.StackHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import vazkii.patchouli.common.item.ItemModBook;

public class QMDCraftingRecipeHandler 
{
	
	public static final String[] FUEL_TYPES = new String[] 
			{
				"",	"Carbide", "Oxide","Nitride","ZA"
			};
	
	public static void registerCraftingRecipes() 
	{
		//misc
		addShapedOreRecipe(new ItemStack(QMDItems.canister,4), new Object[] {"TTT", "T T", "TTT", 'T', "ingotTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.beamline,6), new Object[] {"SSS", "   ", "SSS", 'S', "ingotStainlessSteel"});
		tools("ingotTungstenCarbide", QMDItems.sword_tungsten_carbide, QMDItems.pickaxe_tungsten_carbide, QMDItems.shovel_tungsten_carbide, QMDItems.axe_tungsten_carbide,QMDItems.hoe_tungsten_carbide);
		addShapedOreRecipe(new ItemStack(QMDBlocks.fissionReflector, 2, 0), new Object[] {"TTT", "TFT", "TTT", 'T', "ingotTungstenCarbide", 'F', "steelFrame"});
		addShapedOreRecipe(QMDBlocks.oreLeacher, new Object[] {"PRP", "CFC", "PHP", 'P', "plateElite", 'F', "chassis", 'C', NCBlocks.chemical_reactor, 'R', NCBlocks.rock_crusher, 'H', "ingotHardCarbon"});
		addShapedOreRecipe(QMDBlocks.irradiator, new Object[] {"TPT", "PFP", "TPT", 'P', "plateDU", 'F', "chassis", 'T', "ingotTungsten"});
		addShapedOreRecipe(QMDBlocks.rtgStrontium, new Object[] {"AGA", "GSG", "AGA", 'S', "blockStrontium90",'A', "plateAdvanced", 'G', "ingotGraphite"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.dischargeLamp,4,LampType.EMPTY.getID()), new Object[] {"GGG", "GFG", "GRG", 'F', IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.TUNGSTEN_FILAMENT.getID())),'R', "dustRedstone", 'G', "paneGlass"});
		addShapedOreRecipe(QMDBlocks.atmosphereCollector, new Object[] {"ASA", "BMB", "ASA", 'A', "plateAdvanced",'S', "ingotSteel", 'B', Items.BUCKET, 'M', "motor"});
		
		addShapedOreRecipe(new ItemStack(QMDBlocks.dischargeLamp,4,LampType.EMPTY.getID()), new Object[] {"GGG", "GFG", "GRG", 'F', IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.TUNGSTEN_FILAMENT.getID())),'R', "dustRedstone", 'G', "paneGlass"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.dischargeLamp,4,LampType.EMPTY.getID()), new Object[] {"GGG", "GFG", "GRG", 'F', IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.TUNGSTEN_FILAMENT.getID())),'R', "dustRedstone", 'G', "paneGlass"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.fissionShield, 4, 0), new Object[] {"BHB", "HFH", "BHB", 'B', "plateBasic", 'F', "steelFrame", 'H', "ingotHafnium"});
		addShapedOreRecipe(new ItemStack(QMDItems.cell), new Object[] {"WEW", "O O", "WRW", 'W', "wireBSCCO",'O', "ingotOsmiridium", 'R', NCBlocks.rtg_californium, 'E', "processorElite"});
		addShapedOreRecipe(new ItemStack(QMDItems.part,1,PartType.ACCELERATING_BARREL.getID()), new Object[] {"WMW", "BEB", "WMW", 'W', new ItemStack(QMDBlocks.RFCavity,1,RFCavityType.BSCCO.getID()),'M', "magnetNeodymium", 'B', QMDBlocks.beamline, 'E', "processorElite"});
		addShapedOreRecipe(new ItemStack(QMDItems.part,1,PartType.LASER_ASSEMBLY.getID()), new Object[] {"ALA", "SRS", "ALA", 'A', "processorAdvanced",'L', new ItemStack(QMDBlocks.dischargeLamp,1,LampType.ARGON.getID()), 'S', "ingotSilver", 'R', "rodNdYAG"});
		
		addShapedOreRecipe(QMDItems.leptonCannon, new Object[] {"EL ", "OBS", "T  ", 'E', "processorElite",'L', new ItemStack(QMDItems.part,1,PartType.LASER_ASSEMBLY.getID()), 'O', "ingotOsmiridium", 'B', new ItemStack(QMDItems.part,1,PartType.ACCELERATING_BARREL.getID()), 'S', "solenoidCopper", 'T', "ingotSuperAlloy"});
		addShapedOreRecipe(QMDItems.gluonGun, new Object[] {"TT ", "OBB", "EL ", 'E', "processorElite",'L', new ItemStack(QMDItems.part,1,PartType.LASER_ASSEMBLY.getID()), 'O', "ingotOsmiridium", 'B', new ItemStack(QMDItems.part,1,PartType.ACCELERATING_BARREL.getID()), 'T', "ingotSuperAlloy"});
		addShapedOreRecipe(QMDItems.antimatterLauncher, new Object[] {"TT ", "LBB", "EO ", 'E', "processorElite",'L', new ItemStack(QMDItems.part,1,PartType.LASER_ASSEMBLY.getID()), 'O', "ingotOsmiridium", 'B', new ItemStack(QMDItems.part,1,PartType.ACCELERATING_BARREL.getID()), 'T', "ingotSuperAlloy"});
		addShapedOreRecipe(QMDArmour.helm_hev, new Object[] {"SBS", "HAH", "SPS", 'P', "processorElite",'S', "ingotSuperAlloy", 'H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.helm_boron_nitride, 'B', NCItems.lithium_ion_cell});
		addShapedOreRecipe(QMDArmour.chest_hev, new Object[] {"SBS", "HAH", "SPS", 'P', "processorElite",'S', "ingotSuperAlloy", 'H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.chest_boron_nitride, 'B', NCItems.lithium_ion_cell});
		addShapedOreRecipe(QMDArmour.legs_hev, new Object[] {"SBS", "HAH", "SPS", 'P', "processorElite",'S', "ingotSuperAlloy", 'H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.legs_boron_nitride, 'B', NCItems.lithium_ion_cell});
		addShapedOreRecipe(QMDArmour.boots_hev, new Object[] {"SBS", "HAH", "SPS", 'P', "processorElite",'S', "ingotSuperAlloy", 'H', new ItemStack(NCItems.rad_shielding, 1, 2), 'A', NCArmor.boots_boron_nitride, 'B', NCItems.lithium_ion_cell});
		
		
		
		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER,4), new Object[] {"dustCoal","dustSulfur","dustSodiumNitrate","dustSodiumNitrate"});
		addShapelessOreRecipe(new ItemStack(Items.GUNPOWDER,4), new Object[] {"dustCharcoal","dustSulfur","dustSodiumNitrate","dustSodiumNitrate"});
		addShapelessOreRecipe(new ItemStack(QMDItems.luminousPaint,16,LuminousPaintType.GREEN.getID()), new Object[] {"dustZincSulfide","dustRadium","dustCopper"});
		addShapelessOreRecipe(new ItemStack(QMDItems.luminousPaint,16,LuminousPaintType.GREEN.getID()), new Object[] {"dustZincSulfide","dustPromethium147","dustCopper"});
		addShapelessOreRecipe(new ItemStack(QMDItems.luminousPaint,16,LuminousPaintType.BLUE.getID()), new Object[] {"dustZincSulfide","dustRadium","dustSilver"});
		addShapelessOreRecipe(new ItemStack(QMDItems.luminousPaint,16,LuminousPaintType.BLUE.getID()), new Object[] {"dustZincSulfide","dustPromethium147","dustSilver"});
		addShapelessOreRecipe(new ItemStack(QMDItems.luminousPaint,16,LuminousPaintType.ORANGE.getID()), new Object[] {"dustZincSulfide","dustRadium","dustMagnesium"});
		addShapelessOreRecipe(new ItemStack(QMDItems.luminousPaint,16,LuminousPaintType.ORANGE.getID()), new Object[] {"dustZincSulfide","dustPromethium147","dustMagnesium"});
		
		if (ModCheck.patchouliLoaded()) 
		{
			addShapelessOreRecipe(ItemModBook.forBook("qmd:guide"), new Object[] {Items.BOOK, "ingotStainlessSteel"});
			addShapelessOreRecipe(Items.BOOK, new Object[] {ItemModBook.forBook("qmd:guide")});
		}
		
		addShapedOreRecipe(new ItemStack(QMDBlocks.turbineBladeSuperAlloy,4), new Object[] {"SHS", "SHS", "SHS", 'S', "ingotSuperAlloy",'H', "ingotHSLASteel"});
		
		//fuels
		fissionFuelRecipe(QMDItems.pellet_copernicium,0,"Copernicium291","Uranium238");
		fissionFuelRecipe(QMDItems.pellet_copernicium,1,"Copernicium291Carbide","Uranium238Carbide");
		fissionFuelRecipe(QMDItems.fuel_copernicium,1,"Copernicium291Oxide","Uranium238Oxide");
		fissionFuelRecipe(QMDItems.fuel_copernicium,2,"Copernicium291Nitride","Uranium238Nitride");
		fissionFuelRecipe(QMDItems.fuel_copernicium,3,"Copernicium291ZA","Uranium238ZA");
		
		
		
		//Coolers
		addShapedOreRecipe(new ItemStack(QMDItems.part,8,PartType.EMPTY_COOLER.getID()), new Object[] {"STS", "SHS", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'H', "ingotThermoconducting"});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.WATER.getID()), new Object[] {new BucketIngredient("water"),new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.IRON.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotIron", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.REDSTONE.getID()), new Object[] {"III", "ICI", "III", 'I', "dustRedstone", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.QUARTZ.getID()), new Object[] {"III", "ICI", "III", 'I', "gemQuartz", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.OBSIDIAN.getID()), new Object[] {"DID", "ICI", "DID", 'I', "obsidian", 'D', "dustObsidian" ,'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.NETHER_BRICK.getID()), new Object[] {"DID", "ICI", "DID", 'I', Blocks.NETHER_BRICK, 'D', "ingotBrickNether" ,'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.GLOWSTONE.getID()), new Object[] {"III", "ICI", "III", 'I', "dustGlowstone", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.LAPIS.getID()), new Object[] {"III", "ICI", "III", 'I', "gemLapis", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.GOLD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotGold", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.PRISMARINE.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemPrismarine", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.SLIME.getID()), new Object[] {"III", "ICI", "III", 'I', "slimeball", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.END_STONE.getID()), new Object[] {"DID", "ICI", "DID", 'I', "endstone", 'D',"dustEndstone",'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.PURPUR.getID()), new Object[] {"DID", "ICI", "DID", 'I', Blocks.PURPUR_BLOCK, 'D', Items.CHORUS_FRUIT_POPPED,'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.DIAMOND.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemDiamond", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.EMERALD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemEmerald", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.COPPER.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotCopper", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.TIN.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotTin", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.LEAD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotLead", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.BORON.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotBoron", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.LITHIUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotLithium", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.MAGNESIUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotMagnesium", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.MANGANESE.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotManganese", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.ALUMINUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotAluminum", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.SILVER.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotSilver", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.FLUORITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemFluorite", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.VILLIAUMITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemVilliaumite", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.CAROBBIITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemCarobbiite", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.ARSENIC.getID()), new Object[] {"III", "ICI", "III", 'I', "dustArsenic", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.LIQUID_NITROGEN.getID()), new Object[] {new BucketIngredient("liquid_nitrogen"),new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.LIQUID_HELIUM.getID()), new Object[] {new BucketIngredient("liquid_helium"),new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});
		addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.CRYOTHEUM.getID()), new Object[] {new BucketIngredient("cryotheum"),new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID())});

		// Accelerator Controllers
		addShapedOreRecipe(QMDBlocks.linearAcceleratorController, new Object[] {"PEP", "BFB", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'B', "processorBasic", 'F', QMDBlocks.acceleratorCasing});
		addShapedOreRecipe(QMDBlocks.ringAcceleratorController, new Object[] {"PEP", "AFA", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'A', "processorAdvanced", 'F', QMDBlocks.acceleratorCasing});
		addShapedOreRecipe(QMDBlocks.beamDiverterController, new Object[] {"PTP", "AFA", "PTP", 'P', "plateAdvanced", 'T', "ingotTough", 'A', "processorAdvanced", 'F', QMDBlocks.acceleratorCasing});
		addShapedOreRecipe(QMDBlocks.deceleratorController, new Object[] {"PEP", "AFA", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'A', "processorElite", 'F', QMDBlocks.acceleratorCasing});
		addShapedOreRecipe(QMDBlocks.beamSplitterController, new Object[] {"PTP", "AFA", "PTP", 'P', "plateAdvanced", 'T', "ingotSuperAlloy", 'A', "processorAdvanced", 'F', QMDBlocks.acceleratorCasing});		
		
		//Accelerator Parts
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCasing,8), new Object[] {"STS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorComputerPort,1), new Object[] {"STS", "TFT", "STS", 'S', "processorBasic",'T', "ingotGold", 'F', QMDBlocks.acceleratorCasing});
		addShapelessOreRecipe(QMDBlocks.acceleratorCasing, new Object[] {QMDBlocks.acceleratorGlass});
		addShapelessOreRecipe(QMDBlocks.acceleratorGlass, new Object[] {QMDBlocks.acceleratorCasing, "blockGlass"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorBeamPort, 4), new Object[] {"STS", "BFB", "STS", 'S', "ingotStainlessSteel", 'T', "ingotTough", 'B', QMDBlocks.beamline, 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorSynchrotronPort, 4), new Object[] {"STS", "AFA", "STS", 'S', "ingotStainlessSteel", 'T', "ingotTough", 'A', "ingotAluminum", 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorVent,4), new Object[] {"SIS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "servo"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorEnergyPort,4), new Object[] {"SIS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorBeam,3), new Object[] {"SSS", "BBB", "SSS", 'S', "ingotStainlessSteel", 'B', QMDBlocks.beamline});
		addShapedOreRecipe(QMDBlocks.acceleratorSource, new Object[] {"AAA", " CT", "AAA", 'A', "plateAdvanced", 'C', QMDBlocks.acceleratorCasing, 'T', new ItemStack(QMDItems.source,1,SourceType.TUNGSTEN_FILAMENT.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorPort, 4), new Object[] {"SHS", "VFV", "SHS", 'S', "ingotStainlessSteel", 'H', Blocks.HOPPER, 'V', "servo", 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorRedstonePort,1), new Object[] {"STS", "TFT", "STS", 'S', "dustRedstone",'T', "ingotSteel", 'F', QMDBlocks.acceleratorCasing});

		
		
		//Accelerator magnets
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,MagnetType.COPPER.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotCopper"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,MagnetType.MAGNESIUM_DIBORIDE.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotMagnesiumDiboride"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,MagnetType.NIOBIUM_TIN.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,MagnetType.NIOBIUM_TITANIUM.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotNiobiumTitanium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,MagnetType.BSCCO.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "wireBSCCO"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorYoke,4), new Object[] {"IBI", "IBI", "IBI", 'I', "ingotIron",'B', "bioplastic"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorYoke,4), new Object[] {"IBI", "IBI", "IBI", 'I', "ingotIron",'B', "sheetPlastic"});
		
		//Accelerator Cavities
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,4,RFCavityType.COPPER.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotCopper"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,4,RFCavityType.MAGNESIUM_DIBORIDE.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotMagnesiumDiboride"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,4,RFCavityType.NIOBIUM_TIN.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,4,RFCavityType.NIOBIUM_TITANIUM.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotNiobiumTitanium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,4,RFCavityType.BSCCO.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "wireBSCCO"});
		
		//particle chamber controllers
		addShapedOreRecipe(QMDBlocks.targetChamberController, new Object[] {"PTP", "BFB", "PTP", 'P', "plateElite", 'T', "ingotTough", 'B', "processorBasic", 'F', QMDBlocks.particleChamberCasing});
		addShapedOreRecipe(QMDBlocks.decayChamberController, new Object[] {"PTP", "BFB", "PTP", 'P', "plateElite", 'T', "ingotTough", 'B', "processorBasic", 'F', NCBlocks.decay_hastener});
		addShapedOreRecipe(QMDBlocks.beamDumpController, new Object[] {"PTP", "BFB", "PTP", 'P', "plateElite", 'T', "ingotTough", 'B', "processorBasic", 'F', "blockCopper"});
		addShapedOreRecipe(QMDBlocks.collisionChamberController, new Object[] {"PTP", "BFB", "PTP", 'P', "plateElite", 'T', "ingotTough", 'B', "processorElite", 'F', QMDBlocks.particleChamberCasing});
		
		//particle chamber parts
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberCasing,8), new Object[] {"STS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'F', "steelFrame"});
		addShapelessOreRecipe(QMDBlocks.particleChamberCasing, new Object[] {QMDBlocks.particleChamberGlass});
		addShapelessOreRecipe(QMDBlocks.particleChamberGlass, new Object[] {QMDBlocks.particleChamberCasing, "blockGlass"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberPort, 4), new Object[] {"THT", "VFV", "THT", 'T', "ingotTungsten", 'H', Blocks.HOPPER, 'V', "servo", 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberEnergyPort,4), new Object[] {"SIS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'F', "steelFrame", 'I', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberBeam,3), new Object[] {"STS", "BBB", "STS", 'S', "ingotStainlessSteel", 'B', QMDBlocks.beamline, 'T', "ingotTungsten"});
		addShapedOreRecipe(QMDBlocks.particleChamber, new Object[] {"NSN", "NCN", "NSN", 'S', "ingotStainlessSteel", 'C', "chassis", 'N', "ingotNiobiumTin"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberBeamPort, 4), new Object[] {"STS", "BFB", "STS", 'S', "ingotStainlessSteel", 'T', "ingotTungsten", 'B', QMDBlocks.beamline, 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberFluidPort,4), new Object[] {"SIS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'F', "steelFrame", 'I', "servo"});
		
		//sources
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.SODIUM_22.getID())), new Object[] {"BSB", "SSS", "BSB", 'S', "ingotSodium22",'B', "bioplastic"});
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.COBALT_60.getID())), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotCobalt60",'B', "bioplastic"});
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.IRIDIUM_192.getID())), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotIridium192",'B', "bioplastic"});
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.CALCIUM_48.getID())), new Object[] {"BSB", "SSS", "BSB", 'S', "ingotCalcium48",'B', "bioplastic"});
		
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.SODIUM_22.getID())), new Object[] {"BSB", "SSS", "BSB", 'S', "ingotSodium22",'B', "sheetPlastic"});
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.COBALT_60.getID())), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotCobalt60",'B', "sheetPlastic"});
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.IRIDIUM_192.getID())), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotIridium192",'B', "sheetPlastic"});
		addShapedOreRecipe(IItemParticleAmount.fullItem(new ItemStack(QMDItems.source,1,SourceType.CALCIUM_48.getID())), new Object[] {"BSB", "SSS", "BSB", 'S', "ingotCalcium48",'B', "sheetPlastic"});
		
		
		//detectors
		addShapedOreRecipe(new ItemStack(QMDItems.part,4,PartType.DETECTOR_CASING.getID()), new Object[] {"STS", "SBS", "STS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'B', "processorBasic"});
		addShapedOreRecipe(new ItemStack(QMDItems.part,1,PartType.WIRE_CHAMBER_CASING.getID()), new Object[] {"WWW", "ACA", "WWW", 'W',  "wireGoldTungsten", 'A', "processorAdvanced", 'C', new ItemStack(QMDItems.part,1,PartType.DETECTOR_CASING.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,DetectorType.EM_CALORIMETER.getID()), new Object[] {"SSS", "SCS", "SSS", 'S',  new ItemStack(QMDItems.part,1,PartType.SCINTILLATOR_PWO.getID()), 'C', new ItemStack(QMDItems.part,1,PartType.DETECTOR_CASING.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,DetectorType.HADRON_CALORIMETER.getID()), new Object[] {"SSS", "SCS", "SSS", 'S',  new ItemStack(QMDItems.part,1,PartType.SCINTILLATOR_PLASTIC.getID()), 'C', new ItemStack(QMDItems.part,1,PartType.DETECTOR_CASING.getID())});
		addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,DetectorType.SILLICON_TRACKER.getID()), new Object[] {"BAB", "ACA", "BAB", 'B',  "processorBasic", 'A', "processorAdvanced", 'C', new ItemStack(QMDItems.part,1,PartType.DETECTOR_CASING.getID())});
		
		// Containment Controllers
		addShapedOreRecipe(QMDBlocks.exoticContainmentController, new Object[] {"PEP", "BFB", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'B', "processorElite", 'F', QMDBlocks.vacuumChamberCasing});
		addShapedOreRecipe(QMDBlocks.nucleosynthesisChamberController, new Object[] {"PEP", "BFB", "PEP", 'P', "plateElite", 'E', "ingotSuperAlloy", 'B', "processorElite", 'F', QMDBlocks.vacuumChamberCasing});
		
		//Containment Parts
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberCasing,8), new Object[] {"OTO", "SFS", "OTO", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'O', "ingotOsmiridium"});
		addShapelessOreRecipe(QMDBlocks.vacuumChamberCasing, new Object[] {QMDBlocks.vacuumChamberGlass});
		addShapelessOreRecipe(QMDBlocks.vacuumChamberGlass, new Object[] {QMDBlocks.vacuumChamberCasing, "blockGlass"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberBeamPort, 4), new Object[] {"OTO", "BFB", "OTO", 'O', "ingotOsmiridium", 'T', "ingotTough", 'B', QMDBlocks.beamline, 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberVent,4), new Object[] {"OIO", "TFT", "OTO", 'O', "ingotOsmiridium",'T', "ingotTough", 'F', "steelFrame", 'I', "servo"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberEnergyPort,4), new Object[] {"OIO", "TFT", "OIO", 'O', "ingotOsmiridium",'T', "ingotTough", 'F', "steelFrame", 'I', "wireBSCCO"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberCoil,2), new Object[] {"CCC", "OOO", "CCC", 'O', "ingotOsmiridium", 'C', "wireBSCCO"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberPort, 4), new Object[] {"OHO", "VFV", "OHO", 'O', "ingotOsmiridium", 'H', Blocks.HOPPER, 'V', "servo", 'F', "steelFrame"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberLaser), new Object[] {"OEO", "ELL", "OEO", 'O', "ingotOsmiridium", 'L', new ItemStack(QMDItems.part,1,PartType.LASER_ASSEMBLY.getID()), 'E', "plateElite"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeaterVent,4), new Object[] {"OTO", "TFT", "OIO", 'O', "ingotOsmiridium",'T', "ingotTough", 'F', "steelFrame", 'I', "servo"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberRedstonePort,1), new Object[] {"STS", "TFT", "STS", 'S', "dustRedstone",'T', "ingotSteel", 'F', QMDBlocks.vacuumChamberCasing});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberFluidPort,2), new Object[] {"SIS", "TFT", "STS", 'S', "ingotOsmiridium",'T', "ingotTough", 'F', QMDBlocks.vacuumChamberCasing, 'I', "servo"});

		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberBeam,2), new Object[] {"SBS", "B B", "SBS", 'S', "ingotSuperAlloy",'B', "wireBSCCO"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberPlasmaGlass,2), new Object[] {"SBS", "BNB", "SBS", 'S', "ingotSuperAlloy",'B', "wireBSCCO", 'N', "gemBoronNitride"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberPlasmaNozzle,2), new Object[] {"SBS", "BNB", "SBS", 'S', "ingotSuperAlloy",'B', "wireBSCCO", 'N', new ItemStack(QMDItems.part,1,PartType.ACCELERATING_BARREL.getID())});
		
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.IRON.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "ingotIron", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.REDSTONE.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "dustRedstone", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.QUARTZ.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "gemQuartz", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.OBSIDIAN.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "obsidian", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.GLOWSTONE.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "dustGlowstone", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.LAPIS.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "gemLapis", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.GOLD.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "ingotGold", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});
		addShapedOreRecipe(new ItemStack(QMDBlocks.vacuumChamberHeater,1,HeaterType.DIAMOND.getID()), new Object[] {"IOI", "OCO", "IOI", 'I', "gemDiamond", 'C',new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()), 'O', "ingotOsmiridium"});

		
		
		IRecipe recipe = new RecipesArmourDyes();
		recipe.setRegistryName(new ResourceLocation(QMD.MOD_ID, "armour_dye"));
		ForgeRegistries.RECIPES.register(recipe);
		
		
	}
	
	public static void blockCompress(Block blockOut, int metaOut, String itemOutOreName, Object itemIn)
	{
		addShapedOreRecipe(
				OreDictHelper.getPrioritisedCraftingStack(new ItemStack(blockOut, 1, metaOut), itemOutOreName),
				new Object[] { "III", "III", "III", 'I', itemIn });
	}

	public static void blockOpen(Item itemOut, int metaOut, String itemOutOreName, Object itemIn)
	{
		addShapelessOreRecipe(
				OreDictHelper.getPrioritisedCraftingStack(new ItemStack(itemOut, 9, metaOut), itemOutOreName),
				new Object[] { itemIn });
	}

	public static void tools(Object material, Item sword, Item pick, Item shovel, Item axe, Item hoe)
	{
		addShapedOreRecipe(sword, new Object[] { "M", "M", "S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(pick, new Object[] { "MMM", " S ", " S ", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(shovel, new Object[] { "M", "S", "S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(axe, new Object[] { "MM", "MS", " S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(axe, new Object[] { "MM", "SM", "S ", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(hoe, new Object[] { "MM", " S", " S", 'M', material, 'S', "stickWood" });
		addShapedOreRecipe(hoe, new Object[] { "MM", "S ", "S ", 'M', material, 'S', "stickWood" });
	}

	public static void armor(Object material, Item helm, Item chest, Item legs, Item boots)
	{
		addShapedOreRecipe(helm, new Object[] { "MMM", "M M", 'M', material });
		addShapedOreRecipe(chest, new Object[] { "M M", "MMM", "MMM", 'M', material });
		addShapedOreRecipe(legs, new Object[] { "MMM", "M M", "M M", 'M', material });
		addShapedOreRecipe(boots, new Object[] { "M M", "M M", 'M', material });
	}

	
	public static void fissionFuelRecipe(Item fuel, int meta, String fissile, String fertile) {
		fissile = "ingot" + fissile;
		fertile = "ingot" + fertile;
		addShapelessOreRecipe(new ItemStack(fuel, 9, meta), new Object[] {fissile, fertile, fertile, fertile, fertile, fertile, fertile, fertile, fertile});
	}
	
	
	private static final Map<String, Integer> RECIPE_COUNT_MAP = new HashMap<String, Integer>();

	public static void addShapedOreRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedOreRecipe.class, out, inputs);
	}

	public static void addShapedEnergyRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedEnergyRecipe.class, out, inputs);
	}

	public static void addShapedFluidRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapedFluidRecipe.class, out, inputs);
	}

	public static void addShapelessOreRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessOreRecipe.class, out, inputs);
	}

	public static void addShapelessFluidRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessFluidRecipe.class, out, inputs);
	}

	public static void addShapelessArmorUpgradeRecipe(Object out, Object... inputs)
	{
		registerRecipe(ShapelessArmorRadShieldingRecipe.class, out, inputs);
	}

	public static void registerRecipe(Class<? extends IRecipe> clazz, Object out, Object... inputs)
	{
		if (out == null || Lists.newArrayList(inputs).contains(null))
			return;
		ItemStack outStack = StackHelper.fixItemStack(out);
		if (!outStack.isEmpty() && inputs != null)
		{
			String outName = outStack.getTranslationKey();
			if (RECIPE_COUNT_MAP.containsKey(outName))
			{
				int count = RECIPE_COUNT_MAP.get(outName);
				RECIPE_COUNT_MAP.put(outName, count + 1);
				outName = outName + "_" + count;
			}
			else
				RECIPE_COUNT_MAP.put(outName, 1);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID, outName);
			try
			{
				IRecipe recipe = NCUtil.newInstance(clazz, location, outStack, inputs);
				recipe.setRegistryName(location);
				ForgeRegistries.RECIPES.register(recipe);
			}
			catch (Exception e)
			{

			}
		}
	}
}