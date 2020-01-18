package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ParticleInfoRecipe implements IRecipeWrapper
{
	private final ParticleStack particle;
	private final  List<ParticleStack> components;

	public ParticleInfoRecipe(ParticleStack particle, List<ParticleStack> components)
	{
		this.particle = particle;
		this.components = components;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputs(ParticleType.Particle, components);
		ingredients.setOutput(ParticleType.Particle, particle);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		
		String nameString = Lang.localise("qmd.particle."+particle.getParticle().getName()+".name");
		if(!components.isEmpty())
		{
			String componentsString = Lang.localise("gui.qmd.jei.particle.components");
			fontRenderer.drawString(componentsString, 50, 15, Color.gray.getRGB());
		}
		
		
		String massString = Lang.localise("gui.qmd.jei.particle.mass", UnitHelper.prefix(particle.getParticle().getMass()*1000000,4,"eV/c^2"));
		
		DecimalFormat df = new DecimalFormat("#.##");
		String chargeString = Lang.localise("gui.qmd.jei.particle.charge", df.format(particle.getParticle().getCharge()));
		String spinString = Lang.localise("gui.qmd.jei.particle.spin", particle.getParticle().getSpin());
		String colourString = Lang.localise("gui.qmd.jei.particle.colour", particle.getParticle().interactsWithStrong());
		String weakChargeString = Lang.localise("gui.qmd.jei.particle.weak", particle.getParticle().interactsWithWeak());
		String descString = Lang.localise("qmd.particle."+particle.getParticle().getName()+".desc");
		
		
		
		fontRenderer.drawString(nameString, 0, 0, Color.BLACK.getRGB());
		fontRenderer.drawString(massString, 0, 27, Color.gray.getRGB());
		fontRenderer.drawString(chargeString, 0, 37, Color.gray.getRGB());
		fontRenderer.drawString(spinString, 0, 47, Color.gray.getRGB());
		fontRenderer.drawString(colourString, 0, 57, Color.gray.getRGB());
		fontRenderer.drawString(weakChargeString, 0, 67, Color.gray.getRGB());
		
		fontRenderer.drawSplitString(descString, 0, 80,150, Color.gray.getRGB());
		
	}

}
