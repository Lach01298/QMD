package lach_01298.qmd.recipe;

import static nc.config.NCConfig.ore_dict_raw_material_recipes;

import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.MaterialEnums;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.item.QMDItems;
import nc.enumm.MetaEnums.IngotType;
import nc.init.NCBlocks;
import nc.init.NCItems;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.NCRecipes;
import nc.recipe.RecipeHelper;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.processor.ManufactoryRecipes;
import nc.recipe.vanilla.CraftingRecipeHandler;
import nc.recipe.vanilla.ingredient.BucketIngredient;
import nc.util.FluidRegHelper;
import nc.util.FluidStackHelper;
import nc.util.OreDictHelper;
import nc.util.StringHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class QMDRecipes
{
	private static boolean initialized = false;
	
	
	public static AcceleratorSourceRecipes accelerator_source;
	public static AcceleratorCoolingRecipes accelerator_cooling;
	public static TargetChamberRecipes target_chamber;
	
	
	
	
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) 
	{
		if (initialized) return;
		
		accelerator_source = new AcceleratorSourceRecipes();
		 accelerator_cooling = new AcceleratorCoolingRecipes();
		
		 target_chamber = new TargetChamberRecipes();
		
		 addRecipes();
		 
		 
		initialized = true;
	}

	public static List<List<String>> accelerator_cooling_valid_fluids;
	
	public static void init() 
	{
		accelerator_cooling_valid_fluids = RecipeHelper.validFluids(accelerator_cooling);
		
		
	}
	
	
	public static void refreshRecipeCaches() 
	{
		accelerator_source.refreshCache();
		accelerator_cooling.refreshCache();
		target_chamber.refreshCache();
	}
	
	
	public static void addRecipes()
	{
		// Alloy furnace
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Steel", 5, "Chromium", 1, "StainlessSteel", 6, 1D, 1D);
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Niobium", 3, "Tin", 1, "NiobiumTin", 4, 1D, 1D);
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Tungsten", 1, "Graphite", 1, "TungstenCarbide", 2, 1D, 1D);

		// Fluid Infuser
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_Hydrogen),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("helium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_Helium),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_Deuterium),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID()),fluidStack("liquid_hydrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.BUBBLE_CHAMBER.getID()),1D,1D);
	
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("water", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.WATER.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("liquid_helium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_HELIUM.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("liquid_nitrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_NITROGEN.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("cryotheum", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.CRYOTHEUM.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("enderium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ENDERIUM.getID()),1D,1D);

		
		
	
		// Separator
		
		NCRecipes.separator.addRecipe(AbstractRecipeHandler.oreStackList(Lists.newArrayList("ingotMagnesium", "dustMagnesium"), 9), AbstractRecipeHandler.oreStack("ingotMagnesium24", 8), AbstractRecipeHandler.oreStack("ingotMagnesium26", 1), 6D, 1D);
		
		//Manufactory
		NCRecipes.manufactory.addRecipe("siliconBoule", AbstractRecipeHandler.oreStack("siliconWafer",4), 1D, 1D);
		NCRecipes.manufactory.addRecipe(AbstractRecipeHandler.oreStack("ingotTungsten",2), QMDItems.tungsten_filament, 1D, 1D);
		
		//Melter
		NCRecipes.melter.addRecipe("itemSilicon", fluidStack("silicon", FluidStackHelper.INGOT_VOLUME));
		
		//crystallizer
		NCRecipes.crystallizer.addRecipe(fluidStack("silicon", FluidStackHelper.INGOT_BLOCK_VOLUME), "siliconBoule", 2D, 2D);
		
		//SuperCooler
		NCRecipes.supercooler.addRecipe(fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_hydrogen", 25), 1D, 1D);
		
		
		//crafting
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDItems.canister,4), new Object[] {"TTT", "T T", "TTT", 'T', "ingotTin"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.beamline,6), new Object[] {"SSS", "   ", "SSS", 'S', "ingotStainlessSteel"});
	
		
		CraftingRecipeHandler.addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.WATER.getID()), new Object[] {new BucketIngredient("water"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.IRON.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotIron", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.REDSTONE.getID()), new Object[] {"III", "ICI", "III", 'I', "dustRedstone", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.QUARTZ.getID()), new Object[] {"III", "ICI", "III", 'I', "gemQuartz", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.OBSIDIAN.getID()), new Object[] {"DID", "ICI", "DID", 'I', "obsidian", 'D', "dustObsidian" ,'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.NETHER_BRICK.getID()), new Object[] {"DID", "ICI", "DID", 'I', Blocks.NETHER_BRICK, 'D', "ingotBrickNether" ,'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.GLOWSTONE.getID()), new Object[] {"III", "ICI", "III", 'I', "dustGlowstone", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.LAPIS.getID()), new Object[] {"III", "ICI", "III", 'I', "gemLapis", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.GOLD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotGold", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.PRISMARINE.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemPrismarine", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.SLIME.getID()), new Object[] {"III", "ICI", "III", 'I', "slimeball", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.END_STONE.getID()), new Object[] {"DID", "ICI", "DID", 'I', "endstone", 'D',"dustEndstone",'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.PURPUR.getID()), new Object[] {"DID", "ICI", "DID", 'I', Blocks.PURPUR_BLOCK, 'D', Items.CHORUS_FRUIT_POPPED,'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.DIAMOND.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemDiamond", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.EMERALD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "gemEmerald", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.COPPER.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotCopper", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.TIN.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotTin", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LEAD.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotLead", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.BORON.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotBoron", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LITHIUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotLithium", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.MAGNESIUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotMagnesium", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.MANGANESE.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotManganese", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ALUMINUM.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotAluminum", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.SILVER.getID()), new Object[] {" I ", "ICI", " I ", 'I', "ingotSilver", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.FLUORITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemFluorite", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.VILLIAUMITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemVilliaumite", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.CAROBBIITE.getID()), new Object[] {"III", "ICI", "III", 'I', "gemCarobbiite", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ARSENIC.getID()), new Object[] {"III", "ICI", "III", 'I', "dustArsenic", 'C',new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_NITROGEN.getID()), new Object[] {new BucketIngredient("liquid_nitrogen"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_HELIUM.getID()), new Object[] {new BucketIngredient("liquid_helium"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ENDERIUM.getID()), new Object[] {new BucketIngredient("enderium"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});
		CraftingRecipeHandler.addShapelessOreRecipe(new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.CRYOTHEUM.getID()), new Object[] {new BucketIngredient("cryotheum"),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID())});

		
		CraftingRecipeHandler.addShapedOreRecipe(QMDBlocks.linearAcceleratorController, new Object[] {"PEP", "HFH", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'H', "ingotHardCarbon", 'F', QMDBlocks.acceleratorCasing});
		CraftingRecipeHandler.addShapedOreRecipe(QMDBlocks.ringAcceleratorController, new Object[] {"PEP", "HFN", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'H', new ItemStack(QMDItems.semiconductor,1,MaterialEnums.SemiconductorType.SILICON_P_DOPED.getID()), 'N', new ItemStack(QMDItems.semiconductor,1,MaterialEnums.SemiconductorType.SILICON_N_DOPED.getID()),'F', QMDBlocks.acceleratorCasing});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorCasing,8), new Object[] {"STS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame"});
		CraftingRecipeHandler.addShapelessOreRecipe(QMDBlocks.acceleratorCasing, new Object[] {QMDBlocks.acceleratorGlass});
		CraftingRecipeHandler.addShapelessOreRecipe(QMDBlocks.acceleratorGlass, new Object[] {QMDBlocks.acceleratorCasing, "blockGlass"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorBeamPort, 4), new Object[] {"STS", "BFB", "STS", 'S', "ingotStainlessSteel", 'T', "ingotTough", 'B', QMDBlocks.beamline, 'F', "steelFrame"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorInlet,4), new Object[] {"SIS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "servo"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorOutlet,4), new Object[] {"STS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "servo"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorEnergyPort,4), new Object[] {"SIS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'F', "steelFrame", 'I', "ingotNiobiumTin"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorBeam,3), new Object[] {"SSS", "BBB", "SSS", 'S', "ingotStainlessSteel", 'B', QMDBlocks.beamline});
		CraftingRecipeHandler.addShapedOreRecipe(QMDBlocks.acceleratorSource, new Object[] {"AAA", " CT", "AAA", 'A', "plateAdvanced", 'C', QMDBlocks.acceleratorCasing, 'T', QMDItems.tungsten_filament});

		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,EnumTypes.MagnetType.COPPER.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotCopper"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,EnumTypes.MagnetType.MAGNESIUM_DIBORIDE.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotMagnesiumDiboride"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorMagnet,2,EnumTypes.MagnetType.NIOBIUM_TIN.getID()), new Object[] {"CCC", "STS", "CCC", 'S', "ingotStainlessSteel",'T', "ingotTough", 'C', "ingotNiobiumTin"});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.acceleratorYoke,4), new Object[] {"SBS", "SBS", "SBS", 'S', "ingotStainlessSteel",'B', "bioplastic"});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDItems.part,4,MaterialEnums.PartType.EMTPY_COOLER.getID()), new Object[] {"STS", "SHS", "STS", 'S', "ingotStainlessSteel",'T', "ingotTough", 'H', "ingotThermoconducting"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID()), new Object[] {"STS", "SGS", "STS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'G', "ingotGold"});
		
		
		
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,2,EnumTypes.MagnetType.COPPER.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotCopper"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,2,EnumTypes.MagnetType.MAGNESIUM_DIBORIDE.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotMagnesiumDiboride"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.RFCavity,2,EnumTypes.MagnetType.NIOBIUM_TIN.getID()), new Object[] {"CCC", "S S", "CCC", 'S', "ingotStainlessSteel", 'C', "ingotNiobiumTin"});
		
		CraftingRecipeHandler.addShapedOreRecipe(QMDBlocks.TargetChamberController, new Object[] {"PEP", "HFH", "PEP", 'P', "plateElite", 'E', "ingotExtreme", 'H', "ingotHardCarbon", 'F', QMDBlocks.particleChamberCasing});

		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberCasing,8), new Object[] {"STS", "TFT", "STS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'F', "steelFrame"});
		CraftingRecipeHandler.addShapelessOreRecipe(QMDBlocks.particleChamberCasing, new Object[] {QMDBlocks.particleChamberGlass});
		CraftingRecipeHandler.addShapelessOreRecipe(QMDBlocks.particleChamberGlass, new Object[] {QMDBlocks.particleChamberCasing, "blockGlass"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberPort, 4), new Object[] {"SHS", "VFV", "SHS", 'S', "ingotStainlessSteel", 'H', Blocks.HOPPER, 'V', "servo", 'F', "steelFrame"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberEnergyPort,4), new Object[] {"SIS", "TFT", "SIS", 'S', "ingotStainlessSteel",'T', "ingotTungsten", 'F', "steelFrame", 'I', "ingotNiobiumTin"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberBeam,3), new Object[] {"STS", "BBB", "STS", 'S', "ingotStainlessSteel", 'B', QMDBlocks.beamline, 'T', "ingotTungsten"});
		CraftingRecipeHandler.addShapedOreRecipe(QMDBlocks.particleChamberTarget, new Object[] {"NSN", "NCN", "NSN", 'S', "ingotStainlessSteel", 'C', "chassis", 'N', "ingotNiobiumTin"});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberBeamPort, 4), new Object[] {"STS", "BFB", "STS", 'S', "ingotStainlessSteel", 'T', "ingotTungsten", 'B', QMDBlocks.beamline, 'F', "steelFrame"});

		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDItems.source_sodium_22), new Object[] {"BBB", "BSB", "BBB", 'S', "ingotSodium22",'B', "bioplastic"});
		
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.EM_CALORIMETER.getID()), new Object[] {"SSS", "SCS", "SSS", 'S',  new ItemStack(QMDItems.part,1,MaterialEnums.PartType.SCINTILLATOR_PWO.getID()), 'C', new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.HADRON_CALORIMETER.getID()), new Object[] {"SSS", "SCS", "SSS", 'S',  new ItemStack(QMDItems.part,1,MaterialEnums.PartType.SCINTILLATOR_PLASTIC.getID()), 'C', new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID())});
		CraftingRecipeHandler.addShapedOreRecipe(new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.SILLICON_TRACKER.getID()), new Object[] {"NPN", "PCP", "NPN", 'P',  new ItemStack(QMDItems.semiconductor,1,MaterialEnums.SemiconductorType.SILICON_P_DOPED.getID()), 'N', new ItemStack(QMDItems.semiconductor,1,MaterialEnums.SemiconductorType.SILICON_N_DOPED.getID()), 'C', new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID())});

		
		
		//furnace
		for (int i = 0; i < MaterialEnums.IngotType.values().length; i++) 
		{
			String type = StringHelper.capitalize( MaterialEnums.IngotType.values()[i].getName());
			if (!ore_dict_raw_material_recipes) {
				GameRegistry.addSmelting(new ItemStack(QMDItems.dust, 1, i), OreDictHelper.getPrioritisedCraftingStack(new ItemStack(QMDItems.ingot, 1, i), "ingot" + type), 0F);
			}
			else for (ItemStack dust : OreDictionary.getOres("dust" + type)) {
				GameRegistry.addSmelting(dust, OreDictHelper.getPrioritisedCraftingStack(new ItemStack(QMDItems.ingot, 1, i), "ingot" + type), 0F);
			}
		}
	
		
		
		
		
	}

	public static FluidIngredient fluidStack(String fluidName, int stackSize)
	{
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new FluidIngredient(fluidName, stackSize);
	}

}
