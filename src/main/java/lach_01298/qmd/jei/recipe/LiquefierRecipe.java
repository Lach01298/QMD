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

public class LiquefierRecipe extends JEIRecipeWrapper
{
	public LiquefierRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.liquefier, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/liquefier.png"), 0, 0, 90, 0, 36, 15, 27, 6,0);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
	}

	@Override
	protected int getProgressArrowTime()
	{
		return  Math.max((getBaseHeat() / 100),5);
	}

	protected double getBaseEnergy()
	{
		if (recipe == null)
			return 50D;
		return (double) recipe.getExtras().get(0);
	}

	protected int getBaseHeat()
	{
		if (recipe == null)
			return 1000;
		return (int) recipe.getExtras().get(1);
	}

	protected int getInversionTemperature()
	{
		if (recipe == null)
			return 500;
		return (int) recipe.getExtras().get(2);
	}

	protected int getCompressedGasTemperature()
	{
		if (recipe == null)
			return 400;
		return (int) recipe.getExtras().get(3);
	}

	protected double getPressureCoefficient()
	{
		if (recipe == null)
			return 0.25D;
		return (double) recipe.getExtras().get(4);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> tooltip = new ArrayList<>();

		if (mouseX >= 73 - 47 && mouseY >= 34 - 30 && mouseX < 73 - 47 + 37 + 1 && mouseY < 34 - 30 + 18 + 1)
		{
			tooltip.add(TextFormatting.GREEN + BASE_ENERGY + " " + TextFormatting.WHITE
					+ getBaseEnergy() + " RF");
			tooltip.add(TextFormatting.GOLD + BASE_HEAT + " " + TextFormatting.WHITE
					+ getBaseHeat() + " H");
			tooltip.add(TextFormatting.AQUA + INV_TEMPERATURE + " " + TextFormatting.WHITE
					+ getInversionTemperature() + " K");
			tooltip.add(TextFormatting.RED + GAS_TEMPERATURE + " " + TextFormatting.WHITE
					+ getCompressedGasTemperature() + " K");
			tooltip.add(TextFormatting.LIGHT_PURPLE + PRESSURE_COEFFICIENT + " " + TextFormatting.WHITE
					+ getPressureCoefficient());
		}

		return tooltip;
	}
	private static final String BASE_ENERGY = Lang.localize("qmd.gui.jei.liquefier.base_energy");
	private static final String BASE_HEAT = Lang.localize("qmd.gui.jei.liquefier.base_heat");
	private static final String INV_TEMPERATURE = Lang.localize("qmd.gui.jei.liquefier.inversion_temp");
	private static final String GAS_TEMPERATURE = Lang.localize("qmd.gui.jei.liquefier.compressed_gas_temp");
	private static final String PRESSURE_COEFFICIENT = Lang.localize("qmd.gui.jei.liquefier.pressure_coefficient");




}
