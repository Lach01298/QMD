package lach_01298.qmd.jei.category;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.AtmosphereCollectorRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;



public class AtmosphereCollectorCategory implements IRecipeCategory<AtmosphereCollectorRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public AtmosphereCollectorCategory(IGuiHelper guiHelper)
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/generic.png");
		background = guiHelper.createDrawable(gui_texture, 26, 0, 90, 31);
		icon = guiHelper.createDrawableIngredient(new ItemStack(QMDBlocks.atmosphereCollector));
		
	}
	
	
	@Override
	public String getUid()
	{
		
		return QMDRecipeCategoryUid.ATMOSPHERE_COLLECTOR;
	}

	@Override
	public String getTitle()
	{
		
		return Lang.localize("qmd.gui.jei.category.atmosphere_collector");
	}

	@Override
	public String getModName()
	{
		
		return QMD.MOD_NAME;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public IDrawable getIcon()
	{
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AtmosphereCollectorRecipe recipeWrapper, IIngredients ingredients)
	{
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		List<List<FluidStack>> fluidOutputs =ingredients.getOutputs(VanillaTypes.FLUID);
		guiFluidStacks.init(1, false, 35, 9, 16, 16, fluidOutputs.get(0)== null ? 1000 : Math.max(1, fluidOutputs.get(0).size()), false, null);
		guiFluidStacks.set(1, fluidOutputs.get(0));
	}
	

}
