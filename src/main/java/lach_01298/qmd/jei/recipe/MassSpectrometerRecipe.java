package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IGuiHelper;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class MassSpectrometerRecipe extends JEIRecipeWrapper
{
	public MassSpectrometerRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.mass_spectrometer, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/mass_spectrometer_controller.png"), 45, 13, 0, 201, 101, 55, 52, 51, 0);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
	}

	@Override
	protected int getProgressArrowTime()
	{
		return (int) Math.max((getBaseProcessTime()/4d),5);
	}


	protected double getBaseProcessTime()
	{
		if (recipe == null)
			return QMDConfig.processor_time[2];
		return  recipe.getBaseProcessTime(QMDConfig.processor_time[2]);
	}


	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> tooltip = new ArrayList<>();

		if (mouseX >= arrowDrawPosX && mouseY >= arrowDrawPosY && mouseX < arrowDrawPosX + arrowWidth + 1 && mouseY < arrowDrawPosY + arrowHeight + 1)
		{
			tooltip.add(TextFormatting.GREEN + BASE_TIME + " " + TextFormatting.WHITE + UnitHelper.applyTimeUnitShort(getBaseProcessTime(), 3));
		}

		return tooltip;
	}

	private static final String BASE_TIME = Lang.localize("jei.nuclearcraft.base_process_time");

}
