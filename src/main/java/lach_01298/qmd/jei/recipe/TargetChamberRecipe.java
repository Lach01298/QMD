package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.text.DecimalFormat;

import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.util.Units;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class TargetChamberRecipe extends JEIRecipeWrapper
{
	
	public TargetChamberRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/target_chamber_controller.png"), 0, 0, 182, 0, 21, 12, 62, 28);
	}
	

	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		FontRenderer fontRenderer = minecraft.fontRenderer;
		String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getSIFormat(inputParticles.get(0).get(0).getMeanEnergy(),3,"eV") + "-" + Units.getSIFormat(recipe.getMaxEnergy(),3,"eV"));
		
		DecimalFormat df = new DecimalFormat("#.##");
		String crossSectionString = Lang.localise("gui.qmd.jei.reaction.cross_section", df.format(recipe.getCrossSection()*100));
		String energyReleasedString = Lang.localise("gui.qmd.jei.reaction.energy_released", Units.getParticleEnergy(recipe.getEnergyReleased()));
		

		fontRenderer.drawString(rangeString, 0, 70, Color.gray.getRGB());
		fontRenderer.drawString(crossSectionString, 0, 80, Color.gray.getRGB());
		fontRenderer.drawString(energyReleasedString, 0, 90, Color.gray.getRGB());

		
	}




	@Override
	protected int getProgressArrowTime()
	{
		return inputParticles.get(0).get(0).getAmount()/100000;
	}

}
