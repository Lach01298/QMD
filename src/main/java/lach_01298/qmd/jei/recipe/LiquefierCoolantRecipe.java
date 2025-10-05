package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.wrapper.JEIRecipeWrapper;
import nc.recipe.BasicRecipe;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class LiquefierCoolantRecipe extends JEIRecipeWrapper
{
	public LiquefierCoolantRecipe(IGuiHelper guiHelper, BasicRecipe recipe)
	{
		super(guiHelper, QMDRecipes.liquefier_coolant, recipe, QMD.MOD_ID + ":textures/gui/jei/liquefier_coolant.png", 0, 0, 27, 6, 36, 15, 90, 0);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
	}

	@Override
	protected int getProgressArrowTime()
	{
		return  Math.max((getHeatRequired() / 100),5);
	}

	protected int getHeatRequired()
	{
		if (recipe == null)
			return 1000;
		return (int) recipe.getExtras().get(0);
	}

	protected int getInputTemperature()
	{
		if (recipe == null)
			return 300;
		return (int) recipe.getExtras().get(1);
	}

	protected int getOutputTemperature()
	{
		if (recipe == null)
			return 300;
		return (int) recipe.getExtras().get(2);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> tooltip = new ArrayList<>();

		if (mouseX >= 73 - 47 && mouseY >= 34 - 30 && mouseX < 73 - 47 + 37 + 1 && mouseY < 34 - 30 + 18 + 1)
		{
			tooltip.add(TextFormatting.AQUA + INPUT_TEMPERATURE + " " + TextFormatting.WHITE
					+ getInputTemperature() + " K");
			tooltip.add(TextFormatting.RED + OUTPUT_TEMPERATURE + " " + TextFormatting.WHITE
					+ getOutputTemperature() + " K");
			tooltip.add(TextFormatting.GOLD + HEAT_REQUIRED + " " + TextFormatting.WHITE
					+ getHeatRequired() + " H");
		}

		return tooltip;
	}

	private static final String HEAT_REQUIRED = Lang.localize("qmd.gui.jei.liquefier_coolant.heat_required");
	private static final String INPUT_TEMPERATURE = Lang.localize("qmd.gui.jei.liquefier_coolant.input_temp");
	private static final String OUTPUT_TEMPERATURE = Lang.localize("qmd.gui.jei.liquefier_coolant.output_temp");



}
