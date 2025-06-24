package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.*;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.recipe.BasicRecipeHandler;
import nclegacy.jei.IJEIHandlerLegacy;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public abstract class JEIRecipeWrapper implements IRecipeWrapper
{

	public final QMDRecipeHandler recipeHandler;
	public final QMDRecipe recipe;
	protected final List<List<ItemStack>> inputItems;
	protected final List<List<ItemStack>> outputItems;
	protected final List<List<FluidStack>> inputFluids;
	protected final List<List<FluidStack>> outputFluids;
	protected final List<List<ParticleStack>> inputParticles;
	protected final List<List<ParticleStack>> outputParticles;
	
	
	public final boolean drawArrow;
	public final IDrawable arrow;
	public final int arrowDrawPosX, arrowDrawPosY, arrowWidth, arrowHeight;

	
	public JEIRecipeWrapper(IGuiHelper guiHelper, QMDRecipeHandler recipeHandler, QMDRecipe recipe, ResourceLocation arrowTextureLocation, int backX, int backY, int arrowX, int arrowY, int arrowWidth, int arrowHeight, int arrowPosX, int arrowPosY, int arrowDirection)
	{
		this.recipeHandler = recipeHandler;
		this.recipe = recipe;
		inputItems = QMDRecipeHelper.getItemInputLists(recipe.getItemIngredients());
		inputFluids = QMDRecipeHelper.getFluidInputLists(recipe.getFluidIngredients());
		outputItems = QMDRecipeHelper.getItemOutputLists(recipe.getItemProducts());
		outputFluids = QMDRecipeHelper.getFluidOutputLists(recipe.getFluidProducts());
		inputParticles = QMDRecipeHelper.getParticleInputLists(recipe.getParticleIngredients());
		outputParticles = QMDRecipeHelper.getParticleOutputLists(recipe.getParticleProducts());


		this.drawArrow = true;

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(arrowTextureLocation, arrowX, arrowY, Math.max(arrowWidth, 1), Math.max(arrowHeight, 1));


		switch (arrowDirection)
		{
			default:
				arrow = guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.LEFT, false);
				break;
			case 1:
				arrow = guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.RIGHT, false);
				break;
			case 2:
				arrow = guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.TOP, false);
				break;
			case 3:
				arrow = guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrowTime(), IDrawableAnimated.StartDirection.BOTTOM, false);
				break;
		}
		arrowDrawPosX = arrowPosX - backX;
		arrowDrawPosY = arrowPosY - backY;
		this.arrowWidth = arrowWidth;
		this.arrowHeight = arrowHeight;

	}

	public JEIRecipeWrapper(IGuiHelper guiHelper, QMDRecipeHandler recipeHandler, QMDRecipe recipe)
	{
		this.recipeHandler = recipeHandler;
		this.recipe = recipe;
		inputItems = QMDRecipeHelper.getItemInputLists(recipe.getItemIngredients());
		inputFluids = QMDRecipeHelper.getFluidInputLists(recipe.getFluidIngredients());
		outputItems = QMDRecipeHelper.getItemOutputLists(recipe.getItemProducts());
		outputFluids = QMDRecipeHelper.getFluidOutputLists(recipe.getFluidProducts());
		inputParticles = QMDRecipeHelper.getParticleInputLists(recipe.getParticleIngredients());
		outputParticles = QMDRecipeHelper.getParticleOutputLists(recipe.getParticleProducts());


		this.drawArrow = false;
		arrow = null;

		arrowDrawPosX = 0;
		arrowDrawPosY = 0;
		this.arrowWidth = 0;
		this.arrowHeight = 0;

	}
	
	protected int getProgressArrowTime()
	{
		return 0;
	}
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
