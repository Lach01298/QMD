package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;

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

	private final ParticleStack inputParticle1;
	private final ParticleStack inputParticle2;
	private final FluidStack outputFluid;

	private final long maxEnergy;


	public NeutralContainmentRecipe(ParticleStack inputParticle1,ParticleStack inputParticle2, FluidStack outputFluid, long maxEnergy)
	{
		
		this.inputParticle1 = inputParticle1;
		this.inputParticle2 = inputParticle2;
		this.outputFluid = outputFluid;
		this.maxEnergy = maxEnergy;
	
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		List<ParticleStack> inputParticles = Lists.newArrayList(inputParticle1,inputParticle2);
		ingredients.setInputs(ParticleType.Particle, inputParticles);
		ingredients.setOutput(VanillaTypes.FLUID, outputFluid);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		if(maxEnergy != Long.MAX_VALUE)
		{
			String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticle1.getMeanEnergy()) + "-" + Units.getSIFormat(maxEnergy,3,"eV"));
			fontRenderer.drawString(rangeString, 0, 1, Color.gray.getRGB());
		}
		
		


		
	}

}
