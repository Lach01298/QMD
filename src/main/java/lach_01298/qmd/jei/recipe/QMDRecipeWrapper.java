package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import nc.Global;
import nc.integration.jei.JEIBasicRecipeWrapper;
import nc.integration.jei.JEIMachineRecipeWrapper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.radiation.RadiationHelper;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class QMDRecipeWrapper
{

	public static class OreLeacher extends JEIMachineRecipeWrapper<OreLeacher>
	{
		private static int arrowX = 176;
		private static int arrowY = 12;
		private static int arrowWidth = 16;
		private static int arrowHeight = 8;
		private static int arrowPosX = 94;
		private static int arrowPosY = 46;
		private static int backX = 30;
		private static int backY = 7;
	
		public final IDrawable arrow;
		public final int arrowDrawPosX, arrowDrawPosY;
		
		public OreLeacher(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, backX, backY, arrowX, arrowY, arrowWidth, arrowHeight, arrowPosX, arrowPosY, 94, 42, 16, 16);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID + ":textures/gui/ore_leacher.png");
			IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));
			arrow = staticArrow() ? arrowDrawable : guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.LEFT, false);
			arrowDrawPosX = arrowPosX - backX;
			arrowDrawPosY = arrowPosY - backY;
		}

		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
		{
				arrow.draw(minecraft, arrowDrawPosX, arrowDrawPosY);
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
	
	public static class Irradiator extends JEIMachineRecipeWrapper<Irradiator>
	{

		private static int arrowX = 176;
		private static int arrowY = 0;
		private static int arrowWidth = 52;
		private static int arrowHeight = 10;
		private static int arrowPosX = 62;
		private static int arrowPosY = 57;
		private static int backX = 41;
		private static int backY = 38;
	
		public final IDrawable arrow;
		public final int arrowDrawPosX, arrowDrawPosY;
		
		
		public Irradiator(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, backX, backY, arrowX, arrowY, arrowWidth, arrowHeight, arrowPosX, arrowPosY, 62, 57, 52, 10);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID + ":textures/gui/irradiator.png");
			IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));
			arrow = staticArrow() ? arrowDrawable : guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.LEFT, false);
			arrowDrawPosX = arrowPosX - backX;
			arrowDrawPosY = arrowPosY - backY;
		}

		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
		{
				arrow.draw(minecraft, arrowDrawPosX, arrowDrawPosY);
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
	
	public static class IrradiatorFuel extends JEIBasicRecipeWrapper<IrradiatorFuel>
	{

		private static int arrowX = 176;
		private static int arrowY = 10;
		private static int arrowWidth = 40;
		private static int arrowHeight = 19;
		private static int arrowPosX = 68;
		private static int arrowPosY = 38;
		private static int backX = 62;
		private static int backY = 5;
		
		public final IDrawable arrow;
		public final int arrowDrawPosX, arrowDrawPosY;
		
		
		public IrradiatorFuel(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, backX, backY, arrowX, arrowY, arrowWidth, arrowHeight, arrowPosX, arrowPosY);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID + ":textures/gui/irradiator.png");
			IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));
			arrow = staticArrow() ? arrowDrawable : guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.TOP, false);
			arrowDrawPosX = arrowPosX - backX;
			arrowDrawPosY = arrowPosY - backY;
		}
		
		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
		{
				arrow.draw(minecraft, arrowDrawPosX, arrowDrawPosY);
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

		private static final String SPEED = Lang.localise("gui.nc.container.speed_multiplier");

	}
	
	public static class AcceleratorCooling extends JEIBasicRecipeWrapper<AcceleratorCooling>
	{

		private static int arrowX = 90;
		private static int arrowY = 0;
		private static int arrowWidth = 36;
		private static int arrowHeight = 15;
		private static int arrowPosX = 27;
		private static int arrowPosY = 6;
		private static int backX = 0;
		private static int backY = 0;
		
		public final IDrawable arrow;
		public final int arrowDrawPosX, arrowDrawPosY;
		
		public AcceleratorCooling(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler,
				BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, backX, backY, arrowX, arrowY, arrowWidth, arrowHeight, arrowPosX, arrowPosY);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/accelerator_cooling.png");
			IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));
			arrow = staticArrow() ? arrowDrawable : guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.LEFT, false);
			arrowDrawPosX = arrowPosX - backX;
			arrowDrawPosY = arrowPosY - backY;
		}

		
		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
		{
				arrow.draw(minecraft, arrowDrawPosX, arrowDrawPosY);
		}
		
		
		
		@Override
		protected int getProgressArrowTime()
		{
			return getFissionHeatingHeatPerInputMB() / 100;
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
	
	public static class CellFilling extends JEIMachineRecipeWrapper<CellFilling>
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
	
	public static class VacuumChamberHeating extends JEIBasicRecipeWrapper<VacuumChamberHeating>
	{

		private static int arrowX = 90;
		private static int arrowY = 0;
		private static int arrowWidth = 36;
		private static int arrowHeight = 15;
		private static int arrowPosX = 27;
		private static int arrowPosY = 6;
		private static int backX = 0;
		private static int backY = 0;
		
		public final IDrawable arrow;
		public final int arrowDrawPosX, arrowDrawPosY;
		
		public VacuumChamberHeating(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler,
				BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, backX, backY, arrowX, arrowY, arrowWidth, arrowHeight, arrowPosX, arrowPosY);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/accelerator_cooling.png");
			IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));
			arrow = staticArrow() ? arrowDrawable : guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.LEFT, false);
			arrowDrawPosX = arrowPosX - backX;
			arrowDrawPosY = arrowPosY - backY;
		}

		
		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
		{
				arrow.draw(minecraft, arrowDrawPosX, arrowDrawPosY);
		}
		
		
		
		@Override
		protected int getProgressArrowTime()
		{
			return getFissionHeatingHeatPerInputMB() / 100;
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
	
	
	
	public static class MassSpectrometer extends JEIBasicRecipeWrapper<MassSpectrometer>
	{
		private static int arrowX = 0;
		private static int arrowY = 201;
		private static int arrowWidth = 101;
		private static int arrowHeight = 55;
		private static int arrowPosX = 52;
		private static int arrowPosY = 51;
		private static int backX = 45;
		private static int backY = 13;
	
		public final IDrawable arrow;
		public final int arrowDrawPosX, arrowDrawPosY;
		
		public MassSpectrometer(IGuiHelper guiHelper, IJEIHandler jeiHandler, BasicRecipeHandler recipeHandler, BasicRecipe recipe)
		{
			super(guiHelper, jeiHandler, recipeHandler, recipe, backX, backY, arrowX, arrowY, arrowWidth, arrowHeight, arrowPosX, arrowPosY);
			ResourceLocation location = new ResourceLocation(QMD.MOD_ID + ":textures/gui/mass_spectrometer_controller.png");
			IDrawableStatic arrowDrawable = guiHelper.createDrawable(location, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));
			arrow = staticArrow() ? arrowDrawable : guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.LEFT, false);
			arrowDrawPosX = arrowPosX - backX;
			arrowDrawPosY = arrowPosY - backY;
		}

		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
		{
				arrow.draw(minecraft, arrowDrawPosX, arrowDrawPosY);
		}
		

		@Override
		protected int getProgressArrowTime()
		{			
			return (int) (getBaseProcessTime()/4d);	
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
		
		private static final String BASE_TIME = Lang.localise("jei.nuclearcraft.base_process_time");	
	}
	
	
	
	
	
	

}
