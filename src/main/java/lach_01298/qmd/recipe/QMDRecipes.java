package lach_01298.qmd.recipe;

import java.util.List;

import nc.radiation.RadSources;
import nc.radiation.RadBlockEffects.RadiationBlockMutation;
import nc.radiation.RadBlockEffects.RadiationBlockPurification;
import nc.recipe.RecipeHelper;
import nc.recipe.generator.DecayGeneratorRecipes;
import nc.recipe.generator.FusionRecipes;
import nc.recipe.multiblock.CondenserRecipes;
import nc.recipe.multiblock.CoolantHeaterRecipes;
import nc.recipe.multiblock.FissionHeatingRecipes;
import nc.recipe.multiblock.FissionModeratorRecipes;
import nc.recipe.multiblock.FissionReflectorRecipes;
import nc.recipe.multiblock.HeatExchangerRecipes;
import nc.recipe.multiblock.SaltFissionRecipes;
import nc.recipe.multiblock.SolidFissionRecipes;
import nc.recipe.multiblock.TurbineRecipes;
import nc.recipe.other.CollectorRecipes;
import nc.recipe.other.RadiationScrubberRecipes;
import nc.recipe.processor.AlloyFurnaceRecipes;
import nc.recipe.processor.CentrifugeRecipes;
import nc.recipe.processor.ChemicalReactorRecipes;
import nc.recipe.processor.CrystallizerRecipes;
import nc.recipe.processor.DecayHastenerRecipes;
import nc.recipe.processor.ElectrolyzerRecipes;
import nc.recipe.processor.EnricherRecipes;
import nc.recipe.processor.ExtractorRecipes;
import nc.recipe.processor.FuelReprocessorRecipes;
import nc.recipe.processor.InfuserRecipes;
import nc.recipe.processor.IngotFormerRecipes;
import nc.recipe.processor.IrradiatorRecipes;
import nc.recipe.processor.IsotopeSeparatorRecipes;
import nc.recipe.processor.ManufactoryRecipes;
import nc.recipe.processor.MelterRecipes;
import nc.recipe.processor.PressurizerRecipes;
import nc.recipe.processor.RockCrusherRecipes;
import nc.recipe.processor.SaltMixerRecipes;
import nc.recipe.processor.SupercoolerRecipes;
import nc.recipe.vanilla.CraftingRecipeHandler;
import nc.recipe.vanilla.FurnaceFuelHandler;
import nc.recipe.vanilla.FurnaceRecipeHandler;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class QMDRecipes
{
	private static boolean initialized = false;
	
	
	public static AcceleratorSourceRecipes accelerator_source;
	public static AcceleratorCoolingRecipes accelerator_cooling;
	
	
	
	
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) 
	{
		if (initialized) return;
		
		accelerator_source = new AcceleratorSourceRecipes();
		 accelerator_cooling = new AcceleratorCoolingRecipes();
		
		//CraftingRecipeHandler.registerCraftingRecipes();
		//FurnaceRecipeHandler.registerFurnaceRecipes();
		//GameRegistry.registerFuelHandler(new FurnaceFuelHandler());
		
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
	}
	
	
	
}
