package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.util.Units;
import mezz.jei.api.IGuiHelper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class BeamDumpRecipe extends JEIRecipeWrapper
{



	public BeamDumpRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, QMDRecipes.beam_dump, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/beam_dump_controller.png"), 0, 0, 143, 6, 26, 14, 49, 2, 0);
	
	}
	

	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		FontRenderer fontRenderer = minecraft.fontRenderer;
		if(recipe.getMaxEnergy() != Long.MAX_VALUE)
		{
			String rangeString = Lang.localize("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticles.get(0).get(0).getMeanEnergy()) + "-" + Units.getSIFormat(recipe.getMaxEnergy(),3,"eV"));
			fontRenderer.drawString(rangeString, 0, 20, Color.gray.getRGB());
		}
	
	}
	
	@Override
	protected int getProgressArrowTime()
	{
		return Math.max(inputParticles.get(0).get(0).getAmount()/Math.max(QMDConstants.ionSourceOutput/200,1),5);
	}

}
