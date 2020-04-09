package lach_01298.qmd.recipes;

import static nc.config.NCConfig.ore_dict_raw_material_recipes;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.MaterialEnums;
import lach_01298.qmd.item.QMDItems;
import nc.config.NCConfig;
import nc.enumm.MetaEnums;
import nc.enumm.MetaEnums.IngotType;
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
		NCRecipes.alloy_furnace.addAlloyIngotIngotRecipes("Tungsten", 1, "Graphite", 1, "TungstenCarbide", 2, 2D, 2D);

		// Fluid Infuser
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_hydrogen),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("helium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_helium),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_deuterium),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.canister),fluidStack("diborane", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDItems.canister_diborane),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.DETECTOR_CASING.getID()),fluidStack("liquid_hydrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.particleChamberDetector,1,EnumTypes.DetectorType.BUBBLE_CHAMBER.getID()),1D,1D);
	
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("water", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler1,1,EnumTypes.CoolerType1.WATER.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("liquid_helium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_HELIUM.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("liquid_nitrogen", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.LIQUID_NITROGEN.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("cryotheum", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.CRYOTHEUM.getID()),1D,1D);
		NCRecipes.infuser.addRecipe(new ItemStack(QMDItems.part,1,MaterialEnums.PartType.EMTPY_COOLER.getID()),fluidStack("enderium", FluidStackHelper.BUCKET_VOLUME),new ItemStack(QMDBlocks.acceleratorCooler2,1,EnumTypes.CoolerType2.ENDERIUM.getID()),1D,1D);
		
		NCRecipes.infuser.addOxidizingRecipe("dustCopper",FluidStackHelper.BUCKET_VOLUME);
		NCRecipes.infuser.addOxidizingRecipe("dustTungsten",FluidStackHelper.BUCKET_VOLUME);
		
		// Fluid Enricher		
		NCRecipes.enricher.addRecipe("dustTungstenOxide",fluidStack("sodium_hydroxide_solution", FluidStackHelper.GEM_VOLUME*2),fluidStack("sodium_tungstate_solution", FluidStackHelper.GEM_VOLUME), 1D, 1D);	
		NCRecipes.enricher.addRecipe("dustLead",fluidStack("nitric_acid", FluidStackHelper.GEM_VOLUME*2),fluidStack("lead_nitrate_solution", FluidStackHelper.GEM_VOLUME), 1D, 1D);		
		NCRecipes.enricher.addRecipe("ingotYttrium",fluidStack("alumina", 240),fluidStack("yag", 48), 2D, 2D);	
		NCRecipes.enricher.addRecipe("ingotNeodymium",fluidStack("yag", FluidStackHelper.INGOT_BLOCK_VOLUME),fluidStack("nd_yag", FluidStackHelper.INGOT_BLOCK_VOLUME), 2D, 2D);	
			
		NCRecipes.enricher.addRecipe("dustSodiumChloride",fluidStack("water", FluidStackHelper.BUCKET_VOLUME),fluidStack("sodium_chloride_solution", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);		
				
		// Chemical reactor
		NCRecipes.chemical_reactor.addRecipe(fluidStack("sodium_tungstate_solution", FluidStackHelper.GEM_VOLUME), fluidStack("lead_nitrate_solution", FluidStackHelper.GEM_VOLUME), fluidStack("lead_tungstate_solution", FluidStackHelper.GEM_VOLUME),fluidStack("sodium_nitrate_solution", FluidStackHelper.GEM_VOLUME), 1D, 0.5D);
		
		
		NCRecipes.chemical_reactor.addRecipe(fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME), fluidStack("chlorine", FluidStackHelper.BUCKET_VOLUME), fluidStack("hydrochloric_acid", FluidStackHelper.BUCKET_VOLUME),new EmptyFluidIngredient(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME*2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), 1D, 1D);
		NCRecipes.chemical_reactor.addRecipe(fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME*3), fluidStack("water", FluidStackHelper.BUCKET_VOLUME), fluidStack("nitric_acid", FluidStackHelper.BUCKET_VOLUME*2),fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME), 1D, 1D);
		
		// Separator
		NCRecipes.separator.addRecipe(AbstractRecipeHandler.oreStackList(Lists.newArrayList("ingotMagnesium", "dustMagnesium"), 9), AbstractRecipeHandler.oreStack("ingotMagnesium24", 8), AbstractRecipeHandler.oreStack("ingotMagnesium26", 1), 6D, 1D);
		
		//Electrolyzer
		NCRecipes.electrolyzer.addRecipe(fluidStack("sodium_chloride_solution", FluidStackHelper.BUCKET_VOLUME), fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*2), fluidStack("chlorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient(), 1D, 1D);
		
		// Manufactory
		NCRecipes.manufactory.addRecipe("siliconBoule", AbstractRecipeHandler.oreStack("siliconWafer",4), 1D, 1D);
		NCRecipes.manufactory.addRecipe(AbstractRecipeHandler.oreStack("ingotTungsten",2), QMDItems.tungsten_filament, 1D, 1D);
		
		// Melter
		NCRecipes.melter.addRecipe("itemSilicon", fluidStack("silicon", FluidStackHelper.INGOT_VOLUME));
		
		// Crystallizer
		NCRecipes.crystallizer.addRecipe(fluidStack("silicon", FluidStackHelper.INGOT_BLOCK_VOLUME), "siliconBoule", 2D, 2D);
		NCRecipes.crystallizer.addRecipe(fluidStack("lead_tungstate_solution", FluidStackHelper.GEM_VOLUME), new ItemStack(QMDItems.part,1,MaterialEnums.PartType.SCINTILLATOR_PWO.getID()), 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("sodium_nitrate_solution", FluidStackHelper.GEM_VOLUME), "dustSodiumNitrate", 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("sodium_chloride_solution", FluidStackHelper.GEM_VOLUME), "dustSodiumChloride", 1D, 1D);
		NCRecipes.crystallizer.addRecipe(fluidStack("nd_yag", FluidStackHelper.INGOT_VOLUME), "rodNdYAG", 2D, 2D);
		
		// SuperCooler
		NCRecipes.supercooler.addRecipe(fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*8), fluidStack("liquid_hydrogen", 25), 1D, 1D);
		
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
		NCRecipes.assembler.addRecipe(AbstractRecipeHandler.oreStack("dustBismuth",2),AbstractRecipeHandler.oreStack("dustStrontium",2),AbstractRecipeHandler.oreStack("dustCalcium",2),AbstractRecipeHandler.oreStack("dustCopperOxide",3),AbstractRecipeHandler.oreStack("dustBSCCO",3),1D,1D);
		NCRecipes.assembler.addRecipe(AbstractRecipeHandler.oreStack("bioplastic",2),AbstractRecipeHandler.oreStack("dyeBlue",1),new EmptyItemIngredient(),new EmptyItemIngredient(),new ItemStack(QMDItems.part,1,MaterialEnums.PartType.SCINTILLATOR_PLASTIC.getID()),1D,1D);
		NCRecipes.assembler.addRecipe("siliconNDoped",AbstractRecipeHandler.oreStack("dustRedstone",4),"ingotGold","ingotSilver","processorBasic",1D,1D);
		NCRecipes.assembler.addRecipe("processorBasic",AbstractRecipeHandler.oreStack("dustRedstone",4),"ingotIridium","siliconPDoped","processorAdvanced",1D,1D);
		NCRecipes.assembler.addRecipe("processorAdvanced",AbstractRecipeHandler.oreStack("wireBSCCO",4),"ingotIridium","ingotGermanium","processorElite",1D,1D);
		
		//Fission Irradiator
		NCRecipes.fission_irradiator.addRecipe("siliconWafer", new ItemStack(QMDItems.semiconductor,1,MaterialEnums.SemiconductorType.SILICON_N_DOPED.getID()),120000,0d,0);
		NCRecipes.fission_irradiator.addRecipe("ingotUranium234", "ingotUranium235",1920000,0d,QMDRadSources.URANIUM_234);
		NCRecipes.fission_irradiator.addRecipe("ingotStrontium", "ingotYttrium",1920000,0d,0);
		NCRecipes.fission_irradiator.addRecipe("ingotNeodymium", "ingotPromethium147",9600000,0d,0);
		NCRecipes.fission_irradiator.addRecipe("dustProtactinium231", "dustProtactinium233",3840000,0d,QMDRadSources.PROTACTINIUM_231);
		NCRecipes.fission_irradiator.addRecipe("ingotCobalt", "ingotCobalt60",1920000,0d,0);
		
		// Fission reflector
		for (int i = 0; i < EnumTypes.NeutronReflectorType.values().length; i++) 
		{
			NCRecipes.fission_reflector.addRecipe(new ItemStack(QMDBlocks.fissionReflector, 1, i), QMDConfig.fission_reflector_efficiency[i], QMDConfig.fission_reflector_reflectivity[i]);
		}
		
		
		// Crafting
		QMDCraftingRecipeHandler.registerCraftingRecipes();
		
		// Furnace
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
