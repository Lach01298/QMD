package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.ingredient.WorldIngredient;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LiquidCollectorRecipe implements IRecipeWrapper
{
	private final WorldIngredient worldIngredient;
	private final List<FluidStack> outputFluid;

	public LiquidCollectorRecipe(WorldIngredient worldIngredient, List<FluidStack> outputFluid)
	{
		this.worldIngredient = worldIngredient;
		this.outputFluid = outputFluid;

	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setOutputs(VanillaTypes.FLUID, outputFluid);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		int blocksAreaX = 21;
		int blocksAreaWidth = 18;
		int blocksAreaY = 7;
		int blocksAreaHeight = 18;

		if (mouseX >= blocksAreaX && mouseX <= blocksAreaX + blocksAreaWidth && mouseY >= blocksAreaY && mouseY <= blocksAreaY + blocksAreaHeight)
		{
			String blockNames = Lang.localize("gui.qmd.jei.collector.any");
			if (!worldIngredient.blockStates.isEmpty())
			{
				List<String> blocks = new ArrayList<>();
				for(IBlockState blockState :worldIngredient.blockStates)
				{
					Block block = blockState.getBlock();
					int meta = block.getMetaFromState(blockState);

					if(!Item.getItemFromBlock(block).equals(Items.AIR))
					{
						ItemStack stack = new ItemStack(Item.getItemFromBlock(block), 1, meta);
						blocks.add(stack.getDisplayName());
					}
					else
					{
						blocks.add(blockState.getBlock().getLocalizedName());
					}
				}
				blockNames = String.join(", ", blocks);
			}
			String blockString = Lang.localize("gui.qmd.jei.collector.blocks",  blockNames);
			return new ArrayList<String>(Arrays.asList(blockString));
		}


		int biomesAreaX = 32;
		int biomesAreaWidth = 18;
		int biomesAreaY = 29;
		int biomesAreaHeight = 18;

		if (mouseX >= biomesAreaX && mouseX <= biomesAreaX + biomesAreaWidth && mouseY >= biomesAreaY && mouseY <= biomesAreaY + biomesAreaHeight)
		{
			String biomeNames = Lang.localize("gui.qmd.jei.collector.any");
			if (!worldIngredient.biomes.isEmpty())
			{
				List<String> biomes = new ArrayList<>();
				for(String biome :worldIngredient.biomes)
				{
					if(Biome.REGISTRY.getObject(new ResourceLocation(biome)) != null)
					{
						biomes.add(Biome.REGISTRY.getObject(new ResourceLocation(biome)).getBiomeName());
					}
				}
				biomeNames = String.join(", ", biomes);
			}
			String biomesString = Lang.localize("gui.qmd.jei.collector.biomes",  biomeNames);
			return new ArrayList<String>(Arrays.asList(biomesString));
		}

		int dimensionsAreaX = 10;
		int dimensionsAreaWidth = 18;
		int dimensionsAreaY = 29;
		int dimensionsAreaHeight = 18;

		if (mouseX >= dimensionsAreaX && mouseX <= dimensionsAreaX + dimensionsAreaWidth && mouseY >= dimensionsAreaY && mouseY <= dimensionsAreaY + dimensionsAreaHeight)
		{
			String dimensionNames = Lang.localize("gui.qmd.jei.collector.any");
			if (!worldIngredient.dimensions.isEmpty())
			{
				List<String> dimensions = new ArrayList<>();
				for(int dimId :worldIngredient.dimensions)
				{
					if(DimensionManager.getProviderType(dimId) != null)
					{
						dimensions.add(DimensionManager.getProviderType(dimId).getName());
					}
				}
				dimensionNames = String.join(", ", dimensions);
			}
			String dimensionsString = Lang.localize("gui.qmd.jei.collector.dimensions",  dimensionNames);
			return new ArrayList<String>(Arrays.asList(dimensionsString));
		}

		return Collections.emptyList();
	}


}
