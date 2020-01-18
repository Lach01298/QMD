package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class AcceleratorSourceRecipe implements IRecipeWrapper
{
	private final ItemStack input;
	private final ParticleStack output;

	public AcceleratorSourceRecipe(ItemStack input, ParticleStack output)
	{
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInput(VanillaTypes.ITEM, input);
		ingredients.setOutput(ParticleType.Particle, output);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{

	}

}
