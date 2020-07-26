package lach_01298.qmd.jei.recipe;

import java.awt.Color;

import lach_01298.qmd.Units;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fluids.FluidStack;

public class BeamDumpRecipe implements IRecipeWrapper
{

	private final ParticleStack inputParticle;
	private final FluidStack outputFluid;

	private final long maxEnergy;


	public BeamDumpRecipe(ParticleStack inputParticle, FluidStack outputFluid, long maxEnergy)
	{
		
		this.inputParticle = inputParticle;
		this.outputFluid = outputFluid;
		this.maxEnergy = maxEnergy;
	
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInput(ParticleType.Particle, inputParticle);
		ingredients.setOutput(VanillaTypes.FLUID, outputFluid);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		if(maxEnergy != Long.MAX_VALUE)
		{
			String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticle.getMeanEnergy()) + "-" + Units.getSIFormat(maxEnergy,3,"eV"));
			fontRenderer.drawString(rangeString, 0, 18, Color.gray.getRGB());
		}
		
		


		
	}

}
