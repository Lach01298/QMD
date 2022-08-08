package lach_01298.qmd.jei.recipe;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import lach_01298.qmd.util.Units;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidStack;

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
		String rangeString = Lang.localise("gui.qmd.jei.dimension",  dimesionId);
		fontRenderer.drawString(rangeString, 0, 0, Color.gray.getRGB());
		
	}

}
