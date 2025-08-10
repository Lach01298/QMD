package lach_01298.qmd.jei.category;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.recipe.AtmosphereCollectorRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.integration.jei.JEIHelper;
import nc.recipe.IngredientSorption;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;



public class AtmosphereCollectorCategory implements IRecipeCategory<AtmosphereCollectorRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public AtmosphereCollectorCategory(IGuiHelper guiHelper)
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/atmosphere_collector.png");
		background = guiHelper.createDrawable(gui_texture, 0, 0, 100, 40);
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
		JEIHelper.RecipeFluidMapper fluidMapper = new JEIHelper.RecipeFluidMapper();
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 0, 74, 8, 16, 16);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
	}
	

}
