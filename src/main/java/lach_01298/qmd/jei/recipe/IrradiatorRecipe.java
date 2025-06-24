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

public class IrradiatorRecipe extends JEIRecipeWrapper
{
	public IrradiatorRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.irradiator, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/irradiator.png"), 41, 38, 176, 0, 52, 10, 62, 57, 0);
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
