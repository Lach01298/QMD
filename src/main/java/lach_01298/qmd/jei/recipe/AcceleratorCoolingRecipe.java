package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IGuiHelper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class AcceleratorCoolingRecipe extends JEIRecipeWrapper
{
	public AcceleratorCoolingRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.accelerator_cooling, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/accelerator_cooling.png"), 0, 0, 90, 0, 36, 15, 27, 6,0);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
	}

	@Override
	protected int getProgressArrowTime()
	{
		return getHeatRequired() / 100 > 0 ? getHeatRequired() / 100 : 1;
	}

	protected int getHeatRequired()
	{
		if (recipe == null)
			return 1000;
		return recipe.getHeatRequired();
	}

	protected int getTemperature()
	{
		if (recipe == null)
			return 300;
		return recipe.getInputTemperature();
	}

	@Override
	public java.util.List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> tooltip = new ArrayList<>();

		if (mouseX >= 73 - 47 && mouseY >= 34 - 30 && mouseX < 73 - 47 + 37 + 1 && mouseY < 34 - 30 + 18 + 1)
		{
			tooltip.add(TextFormatting.YELLOW + HEAT_REQUIRED + " " + TextFormatting.WHITE
					+ getHeatRequired() + " H");
			tooltip.add(TextFormatting.AQUA + TEMPERATURE + " " + TextFormatting.WHITE
					+ getTemperature() + " K");
		}

		return tooltip;
	}

	private static final String HEAT_REQUIRED = Lang.localize("qmd.gui.jei.accelerator_cooling.heat_required");
	private static final String TEMPERATURE = Lang.localize("qmd.gui.jei.accelerator_cooling.temperature");




}
