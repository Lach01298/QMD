package lach_01298.qmd.crafttweaker;

import com.google.common.collect.Lists;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.integration.crafttweaker.AddProcessorRecipe;
import nc.integration.crafttweaker.RemoveAllProcessorRecipes;
import nc.integration.crafttweaker.RemoveProcessorRecipe;
import nc.recipe.IngredientSorption;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

public class QMDCraftTweaker
{
	@ZenClass("mods.qmd.ore_leacher")
	@ZenRegister
	public static class OreLeacherHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4, IIngredient output1,IIngredient output2,IIngredient output3, @Optional(valueDouble = 1D) double timeMultiplier, @Optional(valueDouble = 1D) double powerMultiplier, @Optional double processRadiation) 
		{
			CraftTweakerAPI.apply(new AddProcessorRecipe(QMDRecipes.ore_leacher, Lists.newArrayList(input1, input2, input3, input4, output1, output2, output3, timeMultiplier, powerMultiplier, processRadiation)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input1,IIngredient input2,IIngredient input3,IIngredient input4) 
		{
			CraftTweakerAPI.apply(new RemoveProcessorRecipe(QMDRecipes.ore_leacher, IngredientSorption.INPUT, Lists.newArrayList(input1,input2,input3,input4)));
		}
		
		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output1,IIngredient output2,IIngredient output3) 
		{
			CraftTweakerAPI.apply(new RemoveProcessorRecipe(QMDRecipes.ore_leacher, IngredientSorption.OUTPUT, Lists.newArrayList(output1, output2, output3)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new RemoveAllProcessorRecipes(QMDRecipes.ore_leacher));
		}
	}
	
	@ZenClass("mods.qmd.irradiator")
	@ZenRegister
	public static class IrradiatorHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient input, IIngredient output, @Optional(valueDouble = 1D) double timeMultiplier, @Optional(valueDouble = 1D) double powerMultiplier, @Optional double processRadiation) 
		{
			CraftTweakerAPI.apply(new AddProcessorRecipe(QMDRecipes.irradiator, Lists.newArrayList(input, output, timeMultiplier, powerMultiplier, processRadiation)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient input) 
		{
			CraftTweakerAPI.apply(new RemoveProcessorRecipe(QMDRecipes.irradiator, IngredientSorption.INPUT, Lists.newArrayList(input)));
		}
		
		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient output) 
		{
			CraftTweakerAPI.apply(new RemoveProcessorRecipe(QMDRecipes.irradiator, IngredientSorption.OUTPUT, Lists.newArrayList(output)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new RemoveAllProcessorRecipes(QMDRecipes.irradiator));
		}
	}
	
	@ZenClass("mods.qmd.accelerator_cooling")
	@ZenRegister
	public static class AcceleratorCoolingHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient fluidInput,IIngredient fluidOutput, int heatRemoved) 
		{
			CraftTweakerAPI.apply(new AddProcessorRecipe(QMDRecipes.accelerator_cooling, Lists.newArrayList(fluidInput, fluidOutput, heatRemoved)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient fluidInput) 
		{
			CraftTweakerAPI.apply(new RemoveProcessorRecipe(QMDRecipes.accelerator_cooling, IngredientSorption.INPUT, Lists.newArrayList(fluidInput)));
		}
		
		@ZenMethod
		public static void removeRecipeWithOutput(IIngredient fluidOutput) 
		{
			CraftTweakerAPI.apply(new RemoveProcessorRecipe(QMDRecipes.accelerator_cooling, IngredientSorption.OUTPUT, Lists.newArrayList(fluidOutput)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new RemoveAllProcessorRecipes(QMDRecipes.accelerator_cooling));
		}
	}
	
	@ZenClass("mods.qmd.target_chamber")
	@ZenRegister
	public static class TargetChamberHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient inputItem, IIngredient inputParticle, IIngredient outputItem, IIngredient outputParticle1, IIngredient outputParticle2, IIngredient outputParticle3, long maxEnergy, double crossSection, @Optional(valueLong = 0) long energyReleased, @Optional(valueDouble = 0) double processRadiation) 
		{
			CraftTweakerAPI.apply(new AddQMDRecipe(QMDRecipes.target_chamber, Lists.newArrayList(inputItem, inputParticle, outputItem, outputParticle1, outputParticle2, outputParticle3, maxEnergy, crossSection, energyReleased, processRadiation)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient inputItem, IIngredient inputParticle) 
		{
			CraftTweakerAPI.apply(new RemoveQMDRecipe(QMDRecipes.target_chamber, IngredientSorption.INPUT, Lists.newArrayList(inputItem, inputParticle)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new RemoveAllQMDRecipes(QMDRecipes.target_chamber));
		}
	}
	
	@ZenClass("mods.qmd.decay_chamber")
	@ZenRegister
	public static class DecayChamberHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient inputParticle, IIngredient outputParticle1, IIngredient outputParticle2, IIngredient outputParticle3 , double crossSection, @Optional(valueLong = 0) long energyReleased, @Optional(valueDouble = 0) double processRadiation, @Optional(valueLong = Long.MAX_VALUE) long maxEnergy) 
		{
			CraftTweakerAPI.apply(new AddQMDRecipe(QMDRecipes.decay_chamber, Lists.newArrayList( inputParticle, outputParticle1, outputParticle2, outputParticle3, maxEnergy, crossSection, energyReleased, processRadiation)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient inputParticle) 
		{
			CraftTweakerAPI.apply(new RemoveQMDRecipe(QMDRecipes.decay_chamber, IngredientSorption.INPUT, Lists.newArrayList(inputParticle)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new RemoveAllQMDRecipes(QMDRecipes.decay_chamber));
		}
	}
	
	@ZenClass("mods.qmd.beam_dump")
	@ZenRegister
	public static class BeamDumpHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient inputParticle, IIngredient outputFluid, @Optional(valueLong = Long.MAX_VALUE) long maxEnergy) 
		{
			CraftTweakerAPI.apply(new AddQMDRecipe(QMDRecipes.beam_dump, Lists.newArrayList( inputParticle, outputFluid, maxEnergy)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient inputParticle) 
		{
			CraftTweakerAPI.apply(new RemoveQMDRecipe(QMDRecipes.beam_dump, IngredientSorption.INPUT, Lists.newArrayList(inputParticle)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new RemoveAllQMDRecipes(QMDRecipes.beam_dump));
		}
	}
	
	@ZenClass("mods.qmd.accelerator_source")
	@ZenRegister
	public static class AcceleratorSourceHandler 
	{
		
		@ZenMethod
		public static void addRecipe(IIngredient inputItem, IIngredient outputParticle) 
		{
			CraftTweakerAPI.apply(new AddQMDRecipe(QMDRecipes.accelerator_source, Lists.newArrayList( inputItem, outputParticle)));
		}
		
		@ZenMethod
		public static void removeRecipeWithInput(IIngredient inputItem) 
		{
			CraftTweakerAPI.apply(new RemoveQMDRecipe(QMDRecipes.accelerator_source, IngredientSorption.INPUT, Lists.newArrayList(inputItem)));
		}
		
		@ZenMethod
		public static void removeAllRecipes() 
		{
			CraftTweakerAPI.apply(new RemoveAllQMDRecipes(QMDRecipes.accelerator_source));
		}
	}
	
	@ZenClass("mods.qmd.particle")
	@ZenRegister
	public static class ParticleHandler 
	{
		@ZenMethod
		public static void addParticle(String name, String textureLocation, double mass, double charge, double spin, @Optional(valueBoolean = false) boolean weakCharged, @Optional(valueBoolean = false) boolean coloured)
		{
			CraftTweakerAPI.apply(new CTAddParticle(name, textureLocation, mass, charge, spin,weakCharged,coloured));
		}
		@ZenMethod
		public static void addAntiParticle(IIngredient particle, IIngredient antiParticle)
		{
			CraftTweakerAPI.apply(new CTAddAntiParticle(particle, antiParticle));
		}
		
		@ZenMethod
		public static void addComponentParticle(IIngredient parentParticle, IIngredient particle)
		{
			CraftTweakerAPI.apply(new CTAddComponentParticle(parentParticle, particle));
		}
		
	}
	
	
	
	
	
	
	
}
