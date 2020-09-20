package lach_01298.qmd.jei.recipe;

import java.util.List;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class AcceleratorSourceRecipe implements IRecipeWrapper
{
	private final List<List<ItemStack>> input;
	private final List<List<ParticleStack>> output;

	public AcceleratorSourceRecipe(List<List<ItemStack>> input, List<List<ParticleStack>> output)
	{
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputLists(VanillaTypes.ITEM, input);
		ingredients.setOutputLists(ParticleType.Particle, output);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{

	}

}
