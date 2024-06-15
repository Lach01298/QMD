package lach_01298.qmd.jei.recipe;

import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.List;

public class AtmosphereCollectorRecipe implements IRecipeWrapper
{
	private final int dimesionId;
	private final List<FluidStack> outputFluid;

	public AtmosphereCollectorRecipe(int dimesionId, List<FluidStack> stack)
	{
		this.dimesionId = dimesionId;
		this.outputFluid = stack;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients)
	{
		ingredients.setOutputs(VanillaTypes.FLUID, outputFluid);
	}
	
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		FontRenderer fontRenderer = minecraft.fontRenderer;
		String rangeString = Lang.localize("gui.qmd.jei.dimension",  dimesionId);
		fontRenderer.drawString(rangeString, 0, 0, Color.gray.getRGB());
		
	}

}
