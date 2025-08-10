package lach_01298.qmd.recipes;

import lach_01298.qmd.recipe.ingredient.WorldIngredient;
import lach_01298.qmd.util.Util;
import nc.recipe.ingredient.FluidIngredient;
import nc.util.FluidRegHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class LiquidCollectorRecipes
{
	public static Map<WorldIngredient,FluidStack> recipes = new HashMap<WorldIngredient,FluidStack>();



	public static FluidStack getRecipe(IBlockState blockState, String biome, int dimesionId)
	{
		for (WorldIngredient recipeWorldIngredient : recipes.keySet())
		{
			if(recipeWorldIngredient.isSatisfied(blockState,biome,dimesionId))
			{
				return recipes.get(recipeWorldIngredient);
			}
		}
		return null;
	}



	public static void addRecipe(List<IBlockState> blocks, List<String> biomes, List<Integer> dimensions, FluidStack stack)
	{
		if(stack == null)
		{
			Util.getLogger().error("An atmosphere collector recipe with a null fluidStack output tried to register");
			return;
		}
		for (IBlockState blockState : blocks)
		{
			for (String biome : biomes)
			{
				for (int dimension : dimensions)
				{
					if (getRecipe(blockState,biome,dimension) != null)
					{
						Util.getLogger().error("There is already a liquid Collector recipe with block: " + blockState.getBlock().getTranslationKey() + " in biome: " + biome + " and dimension: " + dimension);
						return;
					}
				}
			}
		}
		WorldIngredient worldIngredient = new WorldIngredient(blocks,biomes,dimensions);
		recipes.put(worldIngredient, stack);
	}

	public static void registerRecipes()
	{
		addRecipe(new ArrayList<IBlockState>(Arrays.asList(Blocks.WATER.getDefaultState())),new ArrayList<String>(Arrays.asList("minecraft:river","minecraft:frozen_river","minecraft:swampland","mutated_swampland")),new ArrayList<Integer>(Arrays.asList(0)), fluidStack("water",5000).getStack());
		addRecipe(new ArrayList<IBlockState>(Arrays.asList(Blocks.WATER.getDefaultState())),new ArrayList<String>(Arrays.asList("minecraft:ocean","minecraft:deep_ocean","minecraft:frozen_ocean","minecraft:beaches","minecraft:stone_beach","minecraft:cold_beach")),new ArrayList<Integer>(Arrays.asList(0)), fluidStack("salt_water",1000).getStack());
		addRecipe(new ArrayList<IBlockState>(Arrays.asList(Blocks.LAVA.getDefaultState())),new ArrayList<String>(),new ArrayList<Integer>(Arrays.asList(-1)), fluidStack("lava",100).getStack());

	}


	public static FluidIngredient fluidStack(String fluidName, int stackSize)
	{
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new FluidIngredient(fluidName, stackSize);
	}



}
