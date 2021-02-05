package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.List;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Units;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class DecayChamberRecipe implements IRecipeWrapper
{

	private final List<List<ParticleStack>> inputParticles;
	private final List<List<ParticleStack>> outputParticles;
	private final long maxEnergy;
	private final double crossSection;
	private final long energyReleased;

	public DecayChamberRecipe(List<List<ParticleStack>> inputParticles, List<List<ParticleStack>> outputParticles, long maxEnergy, double crossSection, long energyReleased)
	{
		this.inputParticles = inputParticles;
		this.outputParticles = outputParticles;
		this.maxEnergy = maxEnergy;
		this.crossSection = crossSection;
		this.energyReleased = energyReleased;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputLists(ParticleType.Particle, inputParticles);
		ingredients.setOutputLists(ParticleType.Particle, outputParticles);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;

		
		DecimalFormat df = new DecimalFormat("#.##");
		String maxEnergyString = Lang.localise("gui.qmd.jei.reaction.max_energy", Units.getSIFormat(maxEnergy,3,"eV"));
		String crossSectionString = Lang.localise("gui.qmd.jei.reaction.cross_section", df.format(crossSection*100));
		String energyReleasedString = Lang.localise("gui.qmd.jei.reaction.energy_released", Units.getParticleEnergy(energyReleased));
		
		
		fontRenderer.drawString(crossSectionString, 0, 72, Color.gray.getRGB());
		fontRenderer.drawString(energyReleasedString, 0, 82, Color.gray.getRGB());
		
		if(maxEnergy != Long.MAX_VALUE)
		{
			fontRenderer.drawString(maxEnergyString, 0, 92, Color.gray.getRGB());	
		}
	}

}