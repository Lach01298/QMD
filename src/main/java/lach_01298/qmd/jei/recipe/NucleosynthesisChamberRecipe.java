package lach_01298.qmd.jei.recipe;

import java.awt.Color;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.util.Units;
import mezz.jei.api.IGuiHelper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class NucleosynthesisChamberRecipe extends JEIRecipeWrapper

{
	public NucleosynthesisChamberRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/nucleosynthesis_chamber_controller.png"), 0, 0, 188, 2, 62, 28, 42, 9);
	}
	

	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		FontRenderer fontRenderer = minecraft.fontRenderer;
		String heatString = Lang.localise("gui.qmd.jei.reaction.heat_released",  Units.getSIFormat(recipe.getHeatReleased(),0,"H"));
		fontRenderer.drawString(heatString, 0, 47, Color.gray.getRGB());
		
		if(recipe.getMaxEnergy() != Long.MAX_VALUE)
		{
			String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticles.get(0).get(0).getMeanEnergy()) + "-" + Units.getParticleEnergy(recipe.getMaxEnergy()));
			fontRenderer.drawString(rangeString, 0, 57, Color.gray.getRGB());
		}

	}




	@Override
	protected int getProgressArrowTime()
	{	
		return Math.max(inputParticles.get(0).get(0).getAmount()/(QMDConfig.ion_source_output*4),8);
	}

}
