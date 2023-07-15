package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.util.Units;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class BeamDumpRecipe extends JEIRecipeWrapper
{



	public BeamDumpRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		super(guiHelper, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/beam_dump_controller.png"), 0, 0, 143, 6, 26, 14, 18, 2);
	
	}
	

	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		FontRenderer fontRenderer = minecraft.fontRenderer;
		if(recipe.getMaxEnergy() != Long.MAX_VALUE)
		{
			String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticles.get(0).get(0).getMeanEnergy()) + "-" + Units.getSIFormat(recipe.getMaxEnergy(),3,"eV"));
			fontRenderer.drawString(rangeString, 0, 18, Color.gray.getRGB());
		}
	
	}
	
	@Override
	protected int getProgressArrowTime()
	{
		return Math.max(inputParticles.get(0).get(0).getAmount()/Math.max(QMDConfig.ion_source_output/200,1),5);
	}

}
