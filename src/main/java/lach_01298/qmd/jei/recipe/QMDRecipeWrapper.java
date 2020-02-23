package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import nc.config.NCConfig;
import nc.integration.jei.IJEIHandler;
import nc.integration.jei.JEIRecipeWrapperProcessor;
import nc.integration.jei.JEIRecipeWrapper.Infuser;
import nc.recipe.ProcessorRecipe;
import nc.recipe.ProcessorRecipeHandler;

public class QMDRecipeWrapper
{

	public static class OreLeacher extends JEIRecipeWrapperProcessor<OreLeacher>
	{

		public OreLeacher(IGuiHelper guiHelper, IJEIHandler jeiHandler, ProcessorRecipeHandler recipeHandler,
				ProcessorRecipe recipe)
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

}
