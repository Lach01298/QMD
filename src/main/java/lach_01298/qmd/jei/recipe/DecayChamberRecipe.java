package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.Units;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.recipe.ingredient.IIngredient;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class DecayChamberRecipe implements IRecipeWrapper
{

	private final ParticleStack inputParticle;
	private final ParticleStack outputParticlePlus;
	private final ParticleStack outputParticleNeutral;
	private final ParticleStack outputParticleMinus;

	public DecayChamberRecipe( ParticleStack inputParticle, ParticleStack outputParticlePlus, ParticleStack outputParticleNeutral, ParticleStack outputParticleMinus)
	{
		this.inputParticle = inputParticle;
		this.outputParticlePlus = outputParticlePlus;
		this.outputParticleNeutral = outputParticleNeutral;
		this.outputParticleMinus= outputParticleMinus;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInput(ParticleType.Particle, inputParticle);
		
		List<ParticleStack> outputParticles = Lists.newArrayList(outputParticlePlus,outputParticleNeutral,outputParticleMinus);
		ingredients.setOutputs(ParticleType.Particle, outputParticles);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		
		
		double efficacy = 1;

		if(outputParticlePlus != null)
		{
			efficacy = outputParticlePlus.getEnergySpread();

		}
		if(outputParticleNeutral != null)
		{
			efficacy = outputParticleNeutral.getEnergySpread();

		}
		if(outputParticleMinus != null)
		{
			efficacy = outputParticleMinus.getEnergySpread();

		}
		

		DecimalFormat df = new DecimalFormat("#.##");
		String efficacyString = Lang.localise("gui.qmd.jei.reaction.efficiency", df.format(efficacy * 100));
		fontRenderer.drawString(efficacyString, 0, 72, Color.gray.getRGB());
		
			
	}

}