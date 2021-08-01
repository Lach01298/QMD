package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.util.List;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Units;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fluids.FluidStack;

public class NucleosynthesisChamberRecipe implements IRecipeWrapper
{

	
	private final List<List<ParticleStack>> inputParticle;
	private final List<List<FluidStack>> inputFluids;
	private final List<List<FluidStack>> outputFluids;

	private final long heatReleased;
	private final long maxEnergy;


	public NucleosynthesisChamberRecipe(List<List<ParticleStack>> inputParticle, List<List<FluidStack>> inputFluids,  List<List<FluidStack>> outputFluids, long heat, long maxEnergy)
	{
		
		this.inputParticle = inputParticle;
		this.inputFluids = inputFluids;
		this.outputFluids = outputFluids;
		this.heatReleased = heat;
		this.maxEnergy = maxEnergy;
	
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputLists(ParticleType.Particle, inputParticle);
		ingredients.setInputLists(VanillaTypes.FLUID, inputFluids);
		ingredients.setOutputLists(VanillaTypes.FLUID, outputFluids);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		String heatString = Lang.localise("gui.qmd.jei.reaction.heat_released",  Units.getSIFormat(heatReleased,0,"H"));
		fontRenderer.drawString(heatString, 0, 47, Color.gray.getRGB());
		
		if(maxEnergy != Long.MAX_VALUE)
		{
			String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticle.get(0).get(0).getMeanEnergy()) + "-" + Units.getParticleEnergy(maxEnergy));
			fontRenderer.drawString(rangeString, 0, 57, Color.gray.getRGB());
		}
		


		
	}

}
