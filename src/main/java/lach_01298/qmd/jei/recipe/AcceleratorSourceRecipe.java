package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class AcceleratorSourceRecipe implements IRecipeWrapper
{
	private final List<List<ItemStack>> inputItem;
	private final List<List<FluidStack>> inputFluid;
	private final List<List<ParticleStack>> output;

	public AcceleratorSourceRecipe(List<List<ItemStack>> inputItem, List<List<FluidStack>> inputFluid, List<List<ParticleStack>> output)
	{
		this.inputItem = inputItem;
		this.inputFluid = inputFluid;
		this.output = output;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputLists(VanillaTypes.ITEM, inputItem);
		ingredients.setInputLists(VanillaTypes.FLUID, inputFluid);
		ingredients.setOutputLists(ParticleType.Particle, output);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{

	}

}
