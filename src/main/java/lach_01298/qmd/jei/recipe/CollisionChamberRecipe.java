package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.text.DecimalFormat;

import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.util.Units;
import mezz.jei.api.IGuiHelper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class CollisionChamberRecipe extends JEIRecipeWrapper
{

	public CollisionChamberRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, recipe, null, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;

		
		DecimalFormat df = new DecimalFormat("#.###");
		
		String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getSIFormat(inputParticles.get(0).get(0).getMeanEnergy()+inputParticles.get(1).get(0).getMeanEnergy(),3,"eV") + "-" + Units.getSIFormat(recipe.getMaxEnergy(),3,"eV"));
		String crossSectionString = Lang.localise("gui.qmd.jei.reaction.cross_section", df.format(recipe.getCrossSection()*100));
		String energyReleasedString = Lang.localise("gui.qmd.jei.reaction.energy_released", Units.getParticleEnergy(recipe.getEnergyReleased()));
		
		fontRenderer.drawString(rangeString, 0, 82, Color.gray.getRGB());	
		fontRenderer.drawString(crossSectionString, 0, 92, Color.gray.getRGB());
		fontRenderer.drawString(energyReleasedString, 0, 102, Color.gray.getRGB());
		

	}


	@Override
	protected int getProgressArrowTime()
	{
		return 0;
	}

}