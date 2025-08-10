package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.recipe.ingredient.WorldIngredient;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AtmosphereCollectorRecipe implements IRecipeWrapper
{
	private final WorldIngredient worldIngredient;
	private final List<FluidStack> outputFluid;

	public AtmosphereCollectorRecipe(WorldIngredient worldIngredient, List<FluidStack> outputFluid)
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
		int biomesAreaX = 25;
		int biomesAreaWidth = 18;
		int biomesAreaY = 8;
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

		int dimensionsAreaX = 3;
		int dimensionsAreaWidth = 18;
		int dimensionsAreaY = 8;
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
