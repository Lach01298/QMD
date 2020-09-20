package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.util.List;

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

public class NeutralContainmentRecipe implements IRecipeWrapper
{

	private final List<List<ParticleStack>> inputParticles;
	private final List<List<FluidStack>> outputFluid;

	private final long maxEnergy;


	public NeutralContainmentRecipe(List<List<ParticleStack>> inputParticles, List<List<FluidStack>> outputFluid, long maxEnergy)
	{
		
		this.inputParticles = inputParticles;
		this.outputFluid = outputFluid;
		this.maxEnergy = maxEnergy;
	
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		
		ingredients.setInputLists(ParticleType.Particle, inputParticles);
		ingredients.setOutputLists(VanillaTypes.FLUID, outputFluid);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		if(maxEnergy != Long.MAX_VALUE)
		{
			String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticles.get(0).get(0).getMeanEnergy()) + "-" + Units.getSIFormat(maxEnergy,3,"eV"));
			fontRenderer.drawString(rangeString, 0, 1, Color.gray.getRGB());
		}
		
		


		
	}

}
