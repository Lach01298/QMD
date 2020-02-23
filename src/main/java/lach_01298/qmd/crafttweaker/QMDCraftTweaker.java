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
import nc.recipe.NCRecipes;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

public class QMDCraftTweaker
{
	@ZenClass("mods.qmd.ore_leacher")
	@ZenRegister
	public static class ManufactoryHandler 
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
}
