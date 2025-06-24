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

public class IrradiatorFuelRecipe extends JEIRecipeWrapper
{
	public IrradiatorFuelRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.irradiator_fuel, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/irradiator.png"), 62, 5, 176, 10, 40, 19, 68, 38,2);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
	}

	@Override
	protected int getProgressArrowTime()
	{
		return (int) (100/getIrradatorSpeed());
	}

	protected double getIrradatorSpeed()
	{
		if (recipe == null) return 1D;
		return recipe.getBaseProcessTime(1);
	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY)
	{
		List<String> tooltip = new ArrayList<>();

		if (mouseX >= 5 && mouseY >= 30 && mouseX < 45 && mouseY < 50)
		{
			tooltip.add(TextFormatting.AQUA + SPEED + " " + TextFormatting.WHITE+ "x" +
					+ getIrradatorSpeed());
		}

		return tooltip;
	}

	private static final String SPEED = Lang.localize("gui.nc.container.speed_multiplier");
}
