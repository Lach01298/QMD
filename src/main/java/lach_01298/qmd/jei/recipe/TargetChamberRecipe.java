package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IIngredientType;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
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
		String spreadString = Lang.localise("gui.qmd.jei.particle.spread", String.format("%.2f",inputParticle.getEnergySpread()*100));
		String luminosityString = Lang.localise("gui.qmd.jei.particle.luminosity", UnitHelper.prefix(inputParticle.getLuminosity(),4,"lu"));
		
		
		fontRenderer.drawString(spreadString, 0, 70, Color.gray.getRGB());
		//fontRenderer.drawSplitString(luminosityString, 0, 80,100, Color.gray.getRGB());
	}

}
