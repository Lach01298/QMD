package lach_01298.qmd.recipe.ingredient;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

import java.util.List;

public class WorldIngredient
{
	public List<IBlockState> blockStates;
	public List<String> biomes;
	public List<Integer> dimensions;


	public WorldIngredient(List<IBlockState> blockStates, List<String> biomes, List<Integer> dimensions)
	{
		this.blockStates = blockStates;
		this.biomes = biomes;
		this.dimensions = dimensions;
	}


	public boolean hasBlockRequirement()
	{
		return !this.blockStates.isEmpty();
	}

	public boolean hasBiomeRequirement()
	{
		return !this.biomes.isEmpty();
	}

	public boolean hasDimensionRequirement()
	{
		return !this.dimensions.isEmpty();
	}

	public boolean isSatisfied(IBlockState blockState, String biome, int dimension)
	{
		boolean blockSatisfied = false;
		boolean biomeSatisfied = false;
		boolean dimensionSatisfied = false;


		if(hasBlockRequirement())
		{
			blockSatisfied = this.blockStates.contains(blockState);
		}
		else
		{
			blockSatisfied = true;
		}

		if(hasBiomeRequirement())
		{
			biomeSatisfied = this.biomes.contains(biome);
		}
		else
		{
			biomeSatisfied = true;
		}

		if(hasDimensionRequirement())
		{
			dimensionSatisfied = this.dimensions.contains(dimension);
		}
		else
		{
			dimensionSatisfied = true;
		}



		return blockSatisfied && biomeSatisfied && dimensionSatisfied;
	}






}
