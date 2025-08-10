package lach_01298.qmd.recipe.ingredient;

import net.minecraft.block.Block;

import java.util.List;

public class WorldIngredient
{
	public List<Block> blocks;
	public List<String> biomes;
	public List<Integer> dimensions;


	public WorldIngredient(List<Block> blocks, List<String> biomes, List<Integer> dimensions)
	{
		this.blocks = blocks;
		this.biomes = biomes;
		this.dimensions = dimensions;
	}


	public boolean hasBlockRequirement()
	{
		return !this.blocks.isEmpty();
	}

	public boolean hasBiomeRequirement()
	{
		return !this.biomes.isEmpty();
	}

	public boolean hasDimensionRequirement()
	{
		return !this.dimensions.isEmpty();
	}

	public boolean isSatisfied(Block block, String biome, int dimension)
	{
		boolean blockSatisfied = false;
		boolean biomeSatisfied = false;
		boolean dimensionSatisfied = false;


		if(hasBlockRequirement())
		{
			blockSatisfied = this.blocks.contains(block);
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
