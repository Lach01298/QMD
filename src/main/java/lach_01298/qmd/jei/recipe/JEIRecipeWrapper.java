package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.*;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public abstract class JEIRecipeWrapper implements IRecipeWrapper
{

	public final QMDRecipe recipe;
	protected final List<List<ItemStack>> inputItems;
	protected final List<List<ItemStack>> outputItems;
	protected final List<List<FluidStack>> inputFluids;
	protected final List<List<FluidStack>> outputFluids;
	protected final List<List<ParticleStack>> inputParticles;
	protected final List<List<ParticleStack>> outputParticles;
	
	
	public final boolean drawArrow;
	public final IDrawable arrow;
	public final int arrowDrawPosX, arrowDrawPosY;
	
	
	public JEIRecipeWrapper(IGuiHelper guiHelper, QMDRecipe recipe, ResourceLocation arrowLocation, int backX, int backY, int arrowX, int arrowY, int arrowWidth, int arrowHeight, int arrowPosX, int arrowPosY)
	{
		this.recipe = recipe;
		
		inputItems = QMDRecipeHelper.getItemInputLists(recipe.getItemIngredients());
		inputFluids = QMDRecipeHelper.getFluidInputLists(recipe.getFluidIngredients());
		outputItems = QMDRecipeHelper.getItemOutputLists(recipe.getItemProducts());
		outputFluids = QMDRecipeHelper.getFluidOutputLists(recipe.getFluidProducts());
		inputParticles = QMDRecipeHelper.getParticleInputLists(recipe.getParticleIngredients());
		outputParticles = QMDRecipeHelper.getParticleOutputLists(recipe.getParticleProducts());
		
		this.drawArrow = arrowLocation != null;
		if(drawArrow)
		{
			IDrawableStatic arrowDrawable = guiHelper.createDrawable(arrowLocation, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));
			arrow = guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.LEFT, false);
		}
		else
		{
			arrow = null;
		}
		arrowDrawPosX = arrowPosX - backX;
		arrowDrawPosY = arrowPosY - backY;
		
		
	}
	
	protected abstract int getProgressArrowTime();
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (drawArrow) {
			arrow.draw(minecraft, arrowDrawPosX, arrowDrawPosY);
		}
	}
	
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setInputLists(VanillaTypes.ITEM, inputItems);
		ingredients.setInputLists(VanillaTypes.FLUID, inputFluids);
		ingredients.setInputLists(ParticleType.Particle, inputParticles);
		ingredients.setOutputLists(VanillaTypes.ITEM, outputItems);
		ingredients.setOutputLists(VanillaTypes.FLUID, outputFluids);
		ingredients.setOutputLists(ParticleType.Particle, outputParticles);
	}
	
	
}
