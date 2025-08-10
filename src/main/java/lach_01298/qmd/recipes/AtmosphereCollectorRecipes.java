package lach_01298.qmd.recipes;

import lach_01298.qmd.recipe.ingredient.WorldIngredient;
import lach_01298.qmd.util.Util;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.FluidRegHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class AtmosphereCollectorRecipes
{
	public static Map<WorldIngredient,FluidStack> recipes = new HashMap<WorldIngredient,FluidStack>();



	public static FluidStack getRecipe(String biome, int dimesionId)
	{
		for (WorldIngredient recipeWorldIngredient : recipes.keySet())
		{
			if(recipeWorldIngredient.isSatisfied(Blocks.AIR,biome,dimesionId))
			{
				return recipes.get(recipeWorldIngredient);
			}
		}
		return null;
	}

	public static void addRecipe(List<String> biomes, List<Integer> dimensions, FluidStack stack)
	{
		if(stack == null)
		{
			Util.getLogger().error("An atmosphere collector recipe with a null fluidStack output tried to register");
			return;
		}

		for (String biome : biomes)
		{
			for (int dimension : dimensions)
			{
				if (getRecipe(biome,dimension) != null)
				{
					Util.getLogger().error("There is already a atmosphere Collector recipe in biome: " + biome + " and dimension: " + dimension);
					return;
				}
			}
		}

		WorldIngredient worldIngredient = new WorldIngredient(new ArrayList<Block>(),biomes,dimensions);
		recipes.put(worldIngredient, stack);
	}
	
	public static void registerRecipes()
	{
		addRecipe(new ArrayList<String>(),new ArrayList<Integer>(Arrays.asList(0)), fluidStack("compressed_air",1000).getStack());
	}


	public static FluidIngredient fluidStack(String fluidName, int stackSize)
	{
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new FluidIngredient(fluidName, stackSize);
	}
	
	
}
