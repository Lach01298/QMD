package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.config.QMDConfig;
import mezz.jei.api.IGuiHelper;
import nc.integration.jei.JEIBasicRecipeWrapper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;
import nc.util.Lang;
import net.minecraft.util.text.TextFormatting;

public class QMDRecipeWrapper
{

	public static class OreLeacher extends JEIMachineRecipeWrapper
	{

		public OreLeacher(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 30, 7, 0, 0, 0, 0, 0, 0, 94, 42, 16, 16);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return QMDConfig.processor_time[0];
			return recipe.getBaseProcessTime(QMDConfig.processor_time[0]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			if (recipe == null)
				return QMDConfig.processor_power[0];
			return recipe.getBaseProcessPower(QMDConfig.processor_power[0]);
		}
	}
	
	public static class Irradiator extends JEIMachineRecipeWrapper
	{

		public Irradiator(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 41, 38, 0, 0, 0, 0, 0, 0, 62, 57, 52, 10);
		}

		@Override
		protected double getBaseProcessTime()
		{
			if (recipe == null)
				return QMDConfig.processor_time[0];
			return recipe.getBaseProcessTime(QMDConfig.processor_time[0]);
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}

	}
	
	public static class IrradiatorFuel extends JEIBasicRecipeWrapper
	{

		public IrradiatorFuel(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 62, 5, 0, 0, 0, 0, 0, 0);
		}

		@Override
		protected int getProgressArrowTime()
		{
			return (int) (getIrradatorSpeed()/2D);
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

		private static final String SPEED = Lang.localise("gui.nc.container.speed_multiplier");

	}
	
	public static class AcceleratorCooling extends JEIBasicRecipeWrapper
	{

		public AcceleratorCooling(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler,
				BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 0, 0, 0, 0, 0, 0, 27, 6);
		}

		@Override
		protected int getProgressArrowTime()
		{
			return getFissionHeatingHeatPerInputMB() / 4;
		}

		protected int getFissionHeatingHeatPerInputMB()
		{
			if (recipe == null)
				return 64;
			return recipe.getFissionHeatingHeatPerInputMB();
		}

		@Override
		public List<String> getTooltipStrings(int mouseX, int mouseY)
		{
			List<String> tooltip = new ArrayList<>();

			if (mouseX >= 73 - 47 && mouseY >= 34 - 30 && mouseX < 73 - 47 + 37 + 1 && mouseY < 34 - 30 + 18 + 1)
			{
				tooltip.add(TextFormatting.YELLOW + HEAT_PER_MB + " " + TextFormatting.WHITE
						+ getFissionHeatingHeatPerInputMB() + " H/mB");
			}

			return tooltip;
		}

		private static final String HEAT_PER_MB = Lang.localise("jei.nuclearcraft.fission_heating_heat_per_mb");
	}
	
	public static class CellFilling extends JEIMachineRecipeWrapper
	{

		public CellFilling(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		}

		@Override
		protected double getBaseProcessTime()
		{
			return 0;
		}

		@Override
		protected double getBaseProcessPower()
		{
			return 0;
		}


	}
	
	public static class VacuumChamberHeating extends JEIBasicRecipeWrapper
	{

		public VacuumChamberHeating(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler,
				BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, 0, 0, 0, 0, 0, 0, 27, 6);
		}

		@Override
		protected int getProgressArrowTime()
		{
			return getFissionHeatingHeatPerInputMB() / 4;
		}

		protected int getFissionHeatingHeatPerInputMB()
		{
			if (recipe == null)
				return 64;
			return recipe.getFissionHeatingHeatPerInputMB();
		}

		@Override
		public List<String> getTooltipStrings(int mouseX, int mouseY)
		{
			List<String> tooltip = new ArrayList<>();

			if (mouseX >= 73 - 47 && mouseY >= 34 - 30 && mouseX < 73 - 47 + 37 + 1 && mouseY < 34 - 30 + 18 + 1)
			{
				tooltip.add(TextFormatting.YELLOW + HEAT_PER_MB + " " + TextFormatting.WHITE
						+ getFissionHeatingHeatPerInputMB() + " H/mB");
			}

			return tooltip;
		}

		private static final String HEAT_PER_MB = Lang.localise("jei.nuclearcraft.fission_heating_heat_per_mb");
	}

}
