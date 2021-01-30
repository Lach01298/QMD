package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.util.Units;
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
	private final List<List<ItemStack>> inputItems;
	private final List<List<ItemStack>> outputItems;
	private final List<List<ParticleStack>> inputParticles;
	private final List<List<ParticleStack>> outputParticles;
	private final long maxEnergy;
	private final double crossSection;
	private final long energyReleased;

	public TargetChamberRecipe(List<List<ItemStack>> inputItems, List<List<ParticleStack>> inputParticles,List<List<ItemStack>> outputItems, List<List<ParticleStack>> outputParticles, long maxEnergy, double crossSection, long energyReleased)
	{
		this.inputItems = inputItems;
		this.outputItems = outputItems;
		this.inputParticles = inputParticles;
		this.outputParticles = outputParticles;
		this.maxEnergy = maxEnergy;
		this.crossSection = crossSection;
		this.energyReleased = energyReleased;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputLists(VanillaTypes.ITEM, inputItems);
		ingredients.setInputLists(ParticleType.Particle, inputParticles);
		ingredients.setOutputLists(VanillaTypes.ITEM, outputItems);
		ingredients.setOutputLists(ParticleType.Particle, outputParticles);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		String rangeString = Lang.localise("gui.qmd.jei.reaction.range",  Units.getSIFormat(inputParticles.get(0).get(0).getMeanEnergy(),3,"eV") + "-" + Units.getSIFormat(maxEnergy,3,"eV"));
		
		DecimalFormat df = new DecimalFormat("#.##");
		String crossSectionString = Lang.localise("gui.qmd.jei.reaction.cross_section", df.format(crossSection*100));
		String energyReleasedString = Lang.localise("gui.qmd.jei.reaction.energy_released", Units.getParticleEnergy(energyReleased));
		

		fontRenderer.drawString(rangeString, 0, 70, Color.gray.getRGB());
		fontRenderer.drawString(crossSectionString, 0, 80, Color.gray.getRGB());
		fontRenderer.drawString(energyReleasedString, 0, 90, Color.gray.getRGB());

		
	}

}
