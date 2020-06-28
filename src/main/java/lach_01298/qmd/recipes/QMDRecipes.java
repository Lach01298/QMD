package lach_01298.qmd.recipes;

import static nc.config.NCConfig.ore_dict_raw_material_recipes;
import static nc.config.NCConfig.processor_passive_rate;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.BlockTypes.CoolerType1;
import lach_01298.qmd.enums.BlockTypes.CoolerType2;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.enums.BlockTypes.LampType;
import lach_01298.qmd.enums.BlockTypes.NeutronReflectorType;
import lach_01298.qmd.enums.MaterialTypes.IngotType;
import lach_01298.qmd.enums.MaterialTypes.IngotType2;
import lach_01298.qmd.enums.MaterialTypes.PartType;
import lach_01298.qmd.enums.MaterialTypes.SemiconductorType;
import lach_01298.qmd.item.QMDItems;
import nc.enumm.MetaEnums;
import nc.init.NCBlocks;
import nc.init.NCItems;
import nc.radiation.RadSources;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.NCRecipes;
import nc.recipe.RecipeHelper;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.recipe.ingredient.ItemIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.FluidRegHelper;
import nc.util.FluidStackHelper;
import nc.util.OreDictHelper;
import nc.util.StringHelper;
import nc.util.UnitHelper;
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
	public static DecayChamberRecipes decay_chamber;
	public static OreLeacherRecipes ore_leacher;
	public static IrradiatorRecipes irradiator;
	public static IrradiatorFuel irradiator_fuel;
	
	
	
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		if (initialized)
			return;

		accelerator_source = new AcceleratorSourceRecipes();
		accelerator_cooling = new AcceleratorCoolingRecipes();

		target_chamber = new TargetChamberRecipes();
		decay_chamber = new DecayChamberRecipes();
		
		ore_leacher = new OreLeacherRecipes();
		irradiator = new IrradiatorRecipes();
		irradiator_fuel = new IrradiatorFuel();
		
		addRecipes();

		initialized = true;
	}

	public static List<List<String>> accelerator_cooling_valid_fluids;
	public static List<List<String>> ore_leacher_valid_fluids;
	
	public static void init() 
	{
		accelerator_cooling_valid_fluids = RecipeHelper.validFluids(accelerator_cooling);
		ore_leacher_valid_fluids = RecipeHelper.validFluids(ore_leacher);	
	}
	

	public static void refreshRecipeCaches() 
	{
		accelerator_source.refreshCache();
		accelerator_cooling.refreshCache();
		target_chamber.refreshCache();
		decay_chamber.refreshCache();
		
		ore_leacher.refreshCache();
		irradiator.refreshCache();
		irradiator_fuel.refreshCache();
	}
	
	
	public static void addRecipes()
	{
		// Alloy furnace
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Steel", 5, "Chromium", 1, "StainlessSteel", 6, 1D, 1D);
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Niobium", 3, "Tin", 1, "NiobiumTin", 4, 1D, 1D);
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Niobium", 1, "Titanium", 1, "NiobiumTitanium", 2, 1D, 1D);
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Tungsten", 1, "Graphite", 1, "TungstenCarbide", 2, 2D, 2D);

		
		// Fluid Infuser
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_hydrogen),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("helium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_helium),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_deuterium),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("diborane", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_diborane),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,PartType.DETECTOR_CASING.getID()),fluidStack("liquid_hydrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.particleChamberDetector,1,DetectorType.BUBBLE_CHAMBER.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,PartType.WIRE_CHAMBER_CASING.getID()),fluidStack("argon", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.particleChamberDetector,1,DetectorType.WIRE_CHAMBER.getID()),1D,1D);
		
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()),fluidStack("water", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler1,1,CoolerType1.WATER.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()),fluidStack("liquid_helium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.LIQUID_HELIUM.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()),fluidStack("liquid_nitrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.LIQUID_NITROGEN.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()),fluidStack("cryotheum", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.CRYOTHEUM.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,PartType.EMPTY_COOLER.getID()),fluidStack("enderium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,CoolerType2.ENDERIUM.getID()),1D,1D);
		
		NCRecipes.infuser.addRecipe(new ItemStack(QMDBlocks.dischargeLamp,1,LampType.EMPTY.getID()),fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME/4),new ItemStack(QMDBlocks.dischargeLamp,1,LampType.HYDROGEN.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDBlocks.dischargeLamp,1,LampType.EMPTY.getID()),fluidStack("helium", FluidStackHelper.BUCKET_VOLUME/4),new ItemStack(QMDBlocks.dischargeLamp,1,LampType.HELIUM.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDBlocks.dischargeLamp,1,LampType.EMPTY.getID()),fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/4),new ItemStack(QMDBlocks.dischargeLamp,1,LampType.NITROGEN.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDBlocks.dischargeLamp,1,LampType.EMPTY.getID()),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/4),new ItemStack(QMDBlocks.dischargeLamp,1,LampType.OXYGEN.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDBlocks.dischargeLamp,1,LampType.EMPTY.getID()),fluidStack("neon", FluidStackHelper.BUCKET_VOLUME/4),new ItemStack(QMDBlocks.dischargeLamp,1,LampType.NEON.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDBlocks.dischargeLamp,1,LampType.EMPTY.getID()),fluidStack("argon", FluidStackHelper.BUCKET_VOLUME/4),new ItemStack(QMDBlocks.dischargeLamp,1,LampType.ARGON.getID()),1D,1D);
		
		
		
		
		NCRecipes.infuser.addOxidizingRecipe("dustCopper",FluidStackHelper.BUCKET_VOLUME);
		NCRecipes.infuser.addOxidizingRecipe("dustTungsten",FluidStackHelper.BUCKET_VOLUME);
		NCRecipes.infuser.addOxidizingRecipe("dustHafnium",FluidStackHelper.BUCKET_VOLUME);
		
		
		// Fluid Enricher		
		NCRecipes.enricher.addRecipe("dustTungstenOxide",fluidStack("sodium_hydroxide_solution", FluidStackHelper.GEM_VOLUME*2),fluidStack("sodium_tungstate_solution", FluidStackHelper.GEM_VOLUME), 1D, 1D);	
		NCRecipes.enricher.addRecipe("dustLead",fluidStack("nitric_acid", FluidStackHelper.GEM_VOLUME*2),fluidStack("lead_nitrate_solution", FluidStackHelper.GEM_VOLUME), 1D, 1D);		
		NCRecipes.enricher.addRecipe("ingotYttrium",fluidStack("alumina", 240),fluidStack("yag", 48), 2D, 2D);	
		NCRecipes.enricher.addRecipe("ingotNeodymium",fluidStack("yag", FluidStackHelper.INGOT_BLOCK_VOLUME),fluidStack("nd_yag", FluidStackHelper.INGOT_BLOCK_VOLUME), 2D, 2D);	
			
		NCRecipes.enricher.addRecipe("dustSalt",fluidStack("water", FluidStackHelper.BUCKET_VOLUME),fluidStack("sodium_chloride_solution", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);		
		
		
		// Chemical reactor
		NCRecipes.chemical_reactor.addRecipe(fluidStack("sodium_tungstate_solution", FluidStackHelper.GEM_VOLUME), fluidStack("lead_nitrate_solution", FluidStackHelper.GEM_VOLUME), fluidStack("lead_tungstate_solution", FluidStackHelper.GEM_VOLUME),fluidStack("sodium_nitrate_solution", FluidStackHelper.GEM_VOLUME), 1D, 0.5D);
		
		
		NCRecipes.chemical_reactor.addRecipe(fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME), fluidStack("chlorine", FluidStackHelper.BUCKET_VOLUME), fluidStack("hydrochloric_acid", FluidStackHelper.BUCKET_VOLUME),new EmptyFluidIngredient(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("liquidhydrogenchloride", FluidStackHelper.BUCKET_VOLUME), fluidStack("water", FluidStackHelper.BUCKET_VOLUME), fluidStack("hydrochloric_acid", FluidStackHelper.BUCKET_VOLUME),new EmptyFluidIngredient(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME*2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME*3), fluidStack("water", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitric_acid", FluidStackHelper.BUCKET_VOLUME*2),fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);
		
		
		// Separator
		NCRecipes.separator.addRecipe(AbstractRecipeHandler.oreStackList(Lists.newArrayList("ingotMagnesium", "dustMagnesium"), 9), AbstractRecipeHandler.oreStack("ingotMagnesium24", 8), AbstractRecipeHandler.oreStack("ingotMagnesium26", 1), 6D, 1D);
		
		
		//Electrolyzer
		NCRecipes.electrolyzer.addRecipe(fluidStack("sodium_chloride_solution", FluidStackHelper.BUCKET_VOLUME), fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*2), fluidStack("chlorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient(), 1D, 1D);
		
		
		// Manufactory
		NCRecipes.manufactory.addRecipe("bouleSilicon", AbstractRecipeHandler.oreStack("siliconWafer",4), 1D, 1D);
		NCRecipes.manufactory.addRecipe(AbstractRecipeHandler.oreStack("ingotTungsten",2), QMDItems.tungsten_filament, 1D, 1D);
		
		
		// Melter
		NCRecipes.melter.addRecipe("itemSilicon", fluidStack("silicon", FluidStackHelper.INGOT_VOLUME));
		
		
		// Crystallizer
		NCRecipes.crystallizer.addRecipe(fluidStack("silicon", FluidStackHelper.INGOT_BLOCK_VOLUME), "bouleSilicon", 2D, 2D);
		NCRecipes.crystallizer.addRecipe(fluidStack("lead_tungstate_solution", FluidStackHelper.GEM_VOLUME), new ItemStack(QMDItems.part,1,PartType.SCINTILLATOR_PWO.getID()), 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("sodium_nitrate_solution", FluidStackHelper.GEM_VOLUME), "dustSodiumNitrate", 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("sodium_chloride_solution", FluidStackHelper.GEM_VOLUME), "dustSodiumChloride", 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("nd_yag", FluidStackHelper.INGOT_VOLUME), "rodNdYAG", 2D, 2D);
		NCRecipes.crystallizer.addRecipe(fluidStack("water", FluidStackHelper.BUCKET_VOLUME*10), "dustSodiumChloride", 1D, 4D);	//TODO temporary recipe
		
		
		// SuperCooler
		List<IItemIngredient> emptyitems = new ArrayList<IItemIngredient>();
		List<IFluidIngredient> helium = new ArrayList<IFluidIngredient>();
		helium.add(fluidStack("helium", FluidStackHelper.BUCKET_VOLUME*8));
		List<IFluidIngredient> nitrogen = new ArrayList<IFluidIngredient>();
		nitrogen.add(fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME*8));
		
		NCRecipes.supercooler.removeRecipe(NCRecipes.supercooler.getRecipeFromIngredients(emptyitems, helium));
		NCRecipes.supercooler.removeRecipe(NCRecipes.supercooler.getRecipeFromIngredients(emptyitems, nitrogen));
		
		NCRecipes.supercooler.addRecipe(fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_hydrogen", 125), 1D, 15D);
		NCRecipes.supercooler.addRecipe(fluidStack("helium", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_helium", 125), 1D, 20D);
		NCRecipes.supercooler.addRecipe(fluidStack("neon", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_neon", 125), 1D, 15D);
		NCRecipes.supercooler.addRecipe(fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_nitrogen", 125), 1D, 10D);
		NCRecipes.supercooler.addRecipe(fluidStack("argon", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_argon", 125), 1D, 10D);
		NCRecipes.supercooler.addRecipe(fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_oxygen", 125), 1D, 10D);
		
		
		// Decay Hastener
		List<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>();
		itemIngredients.add(new ItemIngredient(new ItemStack(NCItems.plutonium,1,MetaEnums.PlutoniumType._238.getID())));
		List<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>();
		NCRecipes.decay_hastener.removeRecipe(NCRecipes.decay_hastener.getRecipeFromIngredients(itemIngredients, fluidIngredients));
		
		NCRecipes.decay_hastener.addDecayRecipes("Beryllium7","Lithium7", QMDRadSources.BERYLLIUM_7 );
		NCRecipes.decay_hastener.addDecayRecipes("Lead210","Polonium", QMDRadSources.LEAD_210 );
		NCRecipes.decay_hastener.addRecipe("ingotStrontium90","dustZirconium", QMDRadSources.STRONTIUM_90);
		NCRecipes.decay_hastener.addDecayRecipes("Protactinium231","Lead", QMDRadSources.PROTACTINIUM_231);
		NCRecipes.decay_hastener.addDecayRecipes("Plutonium238","Uranium234",RadSources.PLUTONIUM_238);
		NCRecipes.decay_hastener.addDecayRecipes("Uranium234","Radium",QMDRadSources.URANIUM_234);
		NCRecipes.decay_hastener.addDecayRecipes("Promethium147","Neodymium",QMDRadSources.PROMETHIUM_147);
		NCRecipes.decay_hastener.addRecipe("ingotCobalt60","dustNickel",QMDRadSources.COBALT_60);
		NCRecipes.decay_hastener.addRecipe("ingotIridium192","dustPlatinum",QMDRadSources.IRIDIUM_192);
		
		// Assembeler
		NCRecipes.assembler.addRecipe(AbstractRecipeHandler.oreStack("dustBSCCO",3),AbstractRecipeHandler.oreStack("ingotSilver",6),new EmptyItemIngredient(),new EmptyItemIngredient(),AbstractRecipeHandler.oreStack("wireBSCCO",6),1D,1D);
		NCRecipes.assembler.addRecipe(AbstractRecipeHandler.oreStack("ingotTungsten",4),AbstractRecipeHandler.oreStack("ingotGold",2),new EmptyItemIngredient(),new EmptyItemIngredient(),AbstractRecipeHandler.oreStack("wireGoldTungsten",6),1D,1D);
		NCRecipes.assembler.addRecipe(AbstractRecipeHandler.oreStack("dustBismuth",2),AbstractRecipeHandler.oreStack("dustStrontium",2),AbstractRecipeHandler.oreStack("dustCalcium",2),AbstractRecipeHandler.oreStack("dustCopperOxide",3),AbstractRecipeHandler.oreStack("dustBSCCO",3),1D,1D);
		NCRecipes.assembler.addRecipe(AbstractRecipeHandler.oreStack("bioplastic",2),AbstractRecipeHandler.oreStack("dyeBlue",1),new EmptyItemIngredient(),new EmptyItemIngredient(),new ItemStack(QMDItems.part,1,PartType.SCINTILLATOR_PLASTIC.getID()),1D,1D);
		NCRecipes.assembler.addRecipe("siliconNDoped",AbstractRecipeHandler.oreStack("dustRedstone",4),"ingotGold","ingotSilver","processorBasic",1D,1D);
		NCRecipes.assembler.addRecipe("processorBasic",AbstractRecipeHandler.oreStack("dustRedstone",4),"dustHafniumOxide","siliconPDoped","processorAdvanced",1D,1D);
		NCRecipes.assembler.addRecipe("processorAdvanced",AbstractRecipeHandler.oreStack("wireBSCCO",4),"dustHafniumOxide","ingotIridium","processorElite",1D,1D);
		
		//Fission Irradiator
		NCRecipes.fission_irradiator.addRecipe("siliconWafer", new ItemStack(QMDItems.semiconductor,1,SemiconductorType.SILICON_N_DOPED.getID()),120000,0d,0);
		NCRecipes.fission_irradiator.addRecipe("ingotUranium234", "ingotUranium235",1920000,0d,QMDRadSources.URANIUM_234);
		NCRecipes.fission_irradiator.addRecipe("dustProtactinium231", "dustProtactinium233",3840000,0d,QMDRadSources.PROTACTINIUM_231);
		NCRecipes.fission_irradiator.addRecipe("ingotCobalt", "ingotCobalt60",1920000,0d,0);
		
		
		//fuel reprocessor
		NCRecipes.fuel_reprocessor.addRecipe("wasteFissionLight",AbstractRecipeHandler.chanceOreStack("dustStrontium", 1, 15),AbstractRecipeHandler.chanceOreStack("dustZirconium", 1, 20),AbstractRecipeHandler.chanceOreStack("dustSilver", 1, 5),AbstractRecipeHandler.chanceOreStack("dustTin", 1, 10),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteFissionHeavy",AbstractRecipeHandler.chanceOreStack("ingotStrontium90", 1, 15),AbstractRecipeHandler.chanceOreStack("dustNeodymium", 1, 20),AbstractRecipeHandler.chanceOreStack("ingotPromethium147", 1, 10),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationCalifornium",AbstractRecipeHandler.chanceOreStack("dustThorium", 1, 26),AbstractRecipeHandler.chanceOreStack("dustProtactinium231", 1, 13),AbstractRecipeHandler.chanceOreStack("dustRadium", 1, 12),AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 9),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 20),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 20));
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationBerkelium",AbstractRecipeHandler.chanceOreStack("dustRadium", 1, 9),AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 15),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 41),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 35),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationCurium",AbstractRecipeHandler.chanceOreStack("dustRadium", 1, 14),AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 18),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 16),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 52),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationAmericium",AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 23),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 15),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 59),AbstractRecipeHandler.chanceOreStack("dustPlatinum", 1, 2),AbstractRecipeHandler.chanceOreStack("dustGold", 1, 1),new EmptyItemIngredient());	
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationPlutonium",AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 23),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 15),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 57),AbstractRecipeHandler.chanceOreStack("dustPlatinum", 1, 4),AbstractRecipeHandler.chanceOreStack("dustGold", 1, 1),new EmptyItemIngredient());	
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationNeptunium",AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 37),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 17),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 35),AbstractRecipeHandler.chanceOreStack("dustPlatinum", 1, 7),AbstractRecipeHandler.chanceOreStack("dustGold", 1, 2),AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 2));	
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationUranium",AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 21),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 12),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 55),AbstractRecipeHandler.chanceOreStack("dustPlatinum", 1, 6),AbstractRecipeHandler.chanceOreStack("dustGold", 1, 2),AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 4));	
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationThorium",AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 10),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 7),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 62),AbstractRecipeHandler.chanceOreStack("dustPlatinum", 1, 10),AbstractRecipeHandler.chanceOreStack("dustGold", 1, 3),AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 8));	
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationProtactinium",AbstractRecipeHandler.chanceOreStack("dustPolonium", 1, 35),AbstractRecipeHandler.chanceOreStack("dustBismuth", 1, 6),AbstractRecipeHandler.chanceOreStack("dustLead", 1, 39),AbstractRecipeHandler.chanceOreStack("dustPlatinum", 1, 12),AbstractRecipeHandler.chanceOreStack("dustGold", 1, 3),AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 5));	
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationRadium",AbstractRecipeHandler.chanceOreStack("dustLead", 1, 61),AbstractRecipeHandler.chanceOreStack("dustPlatinum", 1, 17),AbstractRecipeHandler.chanceOreStack("dustGold", 1, 5),AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 5),AbstractRecipeHandler.chanceOreStack("ingotIridium192", 1, 3),AbstractRecipeHandler.chanceOreStack("dustOsmium", 1, 9));	
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationPolonium",AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 39),AbstractRecipeHandler.chanceOreStack("ingotIridium192", 1, 15),AbstractRecipeHandler.chanceOreStack("dustOsmium", 1, 23),AbstractRecipeHandler.chanceOreStack("dustTungsten", 1, 13),AbstractRecipeHandler.chanceOreStack("dustHafnium", 1, 10),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationBismuth",AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 30),AbstractRecipeHandler.chanceOreStack("ingotIridium192", 1, 15),AbstractRecipeHandler.chanceOreStack("dustOsmium", 1, 29),AbstractRecipeHandler.chanceOreStack("dustTungsten", 1, 14),AbstractRecipeHandler.chanceOreStack("dustHafnium", 1, 12),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationLead",AbstractRecipeHandler.chanceOreStack("dustIridium", 1, 31),AbstractRecipeHandler.chanceOreStack("dustOsmium", 1, 40),AbstractRecipeHandler.chanceOreStack("dustTungsten", 1, 16),AbstractRecipeHandler.chanceOreStack("dustHafnium", 1, 13),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationGold","dustHafnium",new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationPlatinum",AbstractRecipeHandler.chanceOreStack("dustTungsten", 1, 41),AbstractRecipeHandler.chanceOreStack("dustHafnium", 1, 59),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationIridium","dustHafnium",new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationOsmium","dustHafnium",new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationTungsten","dustNeodymium",new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient());
		NCRecipes.fuel_reprocessor.addRecipe("wasteSpallationHafnium",AbstractRecipeHandler.chanceOreStack("dustNeodymium", 1, 86),AbstractRecipeHandler.chanceOreStack("ingotPromethium147", 1, 14),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient());

		
		//Collectors
		NCRecipes.collector.addRecipe(QMDBlocks.heliumCollector, AbstractRecipeHandler.emptyItemStack(), fluidStack("helium", 1000), UnitHelper.prefix(QMDConfig.processor_passive_rate[0], 5, "B/t", -1));
		NCRecipes.collector.addRecipe(QMDBlocks.neonCollector, AbstractRecipeHandler.emptyItemStack(), fluidStack("neon", 1000), UnitHelper.prefix(QMDConfig.processor_passive_rate[1], 5, "B/t", -1));
		NCRecipes.collector.addRecipe(QMDBlocks.argonCollector, AbstractRecipeHandler.emptyItemStack(), fluidStack("argon", 1000), UnitHelper.prefix(QMDConfig.processor_passive_rate[2], 5, "B/t", -1));
		
		// Fission reflector
		for (int i = 0; i < NeutronReflectorType.values().length; i++) 
		{
			NCRecipes.fission_reflector.addRecipe(new ItemStack(QMDBlocks.fissionReflector, 1, i), QMDConfig.fission_reflector_efficiency[i], QMDConfig.fission_reflector_reflectivity[i]);
		}
		
		
		// Crafting
		QMDCraftingRecipeHandler.registerCraftingRecipes();
		
		// Furnace
		for (int i = 0; i < IngotType.values().length; i++) 
		{
			String type = StringHelper.capitalize( IngotType.values()[i].getName());
			if (!ore_dict_raw_material_recipes) {
				GameRegistry.addSmelting(new ItemStack(QMDItems.dust, 1, i), OreDictHelper.getPrioritisedCraftingStack(new ItemStack(QMDItems.ingot, 1, i), "ingot" + type), 0F);
			}
			else for (ItemStack dust : OreDictionary.getOres("dust" + type)) {
				GameRegistry.addSmelting(dust, OreDictHelper.getPrioritisedCraftingStack(new ItemStack(QMDItems.ingot, 1, i), "ingot" + type), 0F);
			}
		}
		
		for (int i = 0; i < IngotType2.values().length; i++) 
		{
			String type = StringHelper.capitalize( IngotType2.values()[i].getName());
			if (!ore_dict_raw_material_recipes) {
				GameRegistry.addSmelting(new ItemStack(QMDItems.dust2, 1, i), OreDictHelper.getPrioritisedCraftingStack(new ItemStack(QMDItems.ingot2, 1, i), "ingot" + type), 0F);
			}
			else for (ItemStack dust : OreDictionary.getOres("dust" + type)) {
				GameRegistry.addSmelting(dust, OreDictHelper.getPrioritisedCraftingStack(new ItemStack(QMDItems.ingot2, 1, i), "ingot" + type), 0F);
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
