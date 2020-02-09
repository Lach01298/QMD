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

public class TargetChamberRecipe implements IRecipeWrapper
{
	private final ItemStack inputItem;
	private final ItemStack outputItem;
	private final ParticleStack inputParticle;
	private final ParticleStack outputParticlePlus;
	private final ParticleStack outputParticleNeutral;
	private final ParticleStack outputParticleMinus;

	public TargetChamberRecipe(ItemStack inputItem, ParticleStack inputParticle,ItemStack outputItem, ParticleStack outputParticlePlus, ParticleStack outputParticleNeutral, ParticleStack outputParticleMinus)
	{
		this.inputItem = inputItem;
		this.outputItem = outputItem;
		this.inputParticle = inputParticle;
		this.outputParticlePlus = outputParticlePlus;
		this.outputParticleNeutral = outputParticleNeutral;
		this.outputParticleMinus= outputParticleMinus;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInput(VanillaTypes.ITEM, inputItem);
		ingredients.setInput(ParticleType.Particle, inputParticle);
		ingredients.setOutput(VanillaTypes.ITEM, outputItem);
		
		List<ParticleStack> outputParticles = Lists.newArrayList(outputParticlePlus,outputParticleNeutral,outputParticleMinus);
		ingredients.setOutputs(ParticleType.Particle, outputParticles);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getSIFormat(inputParticle.getMeanEnergy(),3,"eV") + "-" + Units.getSIFormat(inputParticle.getMeanEnergy()*(1+inputParticle.getEnergySpread()),3,"eV"));
		
		double efficacy = 1;
		boolean displayEfficacy = false;
		if(outputParticlePlus != null)
		{
			efficacy = outputParticlePlus.getEnergySpread();
			displayEfficacy = true;
		}
		if(outputParticleNeutral != null)
		{
			efficacy = outputParticleNeutral.getEnergySpread();
			displayEfficacy = true;
		}
		if(outputParticleMinus != null)
		{
			efficacy = outputParticleMinus.getEnergySpread();
			displayEfficacy = true;
		}
		
		fontRenderer.drawString(rangeString, 0, 70, Color.gray.getRGB());
		
		if(displayEfficacy)
		{
			DecimalFormat df = new DecimalFormat("#.##");
			String efficacyString = Lang.localise("gui.qmd.jei.reaction.efficiency",  df.format(efficacy*100));
			fontRenderer.drawString(efficacyString, 0, 80, Color.gray.getRGB());
		}
		
		
		
	}

}
