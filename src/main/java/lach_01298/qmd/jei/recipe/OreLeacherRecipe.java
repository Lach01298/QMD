package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.*;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.util.Units;
import mezz.jei.api.IGuiHelper;
import nc.radiation.RadiationHelper;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OreLeacherRecipe extends JEIRecipeWrapper
{
	private static final String BASE_TIME = Lang.localize("jei.nuclearcraft.base_process_time");
	private static final String BASE_POWER = Lang.localize("jei.nuclearcraft.base_process_power");
	private static final String BASE_RADIATION = Lang.localize("jei.nuclearcraft.base_process_radiation");

	public OreLeacherRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.ore_leacher, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/ore_leacher.png"), 30, 7, 176, 12, 16, 8, 94, 46, 0);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
	}

	@Override
	protected int getProgressArrowTime()
	{
		return (int)(this.getBaseProcessTime() / 4.0);
	}


	protected double getBaseProcessTime()
	{
		if (recipe == null)
			return QMDConfig.processor_time[0];
		return recipe.getBaseProcessTime(QMDConfig.processor_time[0]);
	}

	protected double getBaseProcessPower()
	{
		if (recipe == null)
			return QMDConfig.processor_power[0];
		return recipe.getBaseProcessPower(QMDConfig.processor_power[0]);
	}

	protected double getBaseProcessRadiation() {
		return this.recipe == null ? 0.0 : this.recipe.getBaseProcessRadiation();
	}

	@Override
	public java.util.List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> tooltip = new ArrayList();
		if (mouseX >= this.arrowDrawPosX && mouseY >= this.arrowDrawPosY && mouseX < this.arrowDrawPosX + this.arrowWidth && mouseY < this.arrowDrawPosY + this.arrowHeight)
		{
			tooltip.add(TextFormatting.GREEN + BASE_TIME + " " + TextFormatting.WHITE + UnitHelper.applyTimeUnitShort(this.getBaseProcessTime(), 3));
			tooltip.add(TextFormatting.LIGHT_PURPLE + BASE_POWER + " " + TextFormatting.WHITE + UnitHelper.prefix(this.getBaseProcessPower(), 5, "RF/t"));
			double radiation = this.getBaseProcessRadiation();
			if (radiation > 0.0) {
				tooltip.add(TextFormatting.GOLD + BASE_RADIATION + " " + RadiationHelper.radsColoredPrefix(radiation, true));
			}
		}

		return tooltip;
	}

}