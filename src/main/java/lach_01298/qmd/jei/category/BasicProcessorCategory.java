package lach_01298.qmd.jei.category;

import lach_01298.qmd.QMD;
import nc.integration.jei.wrapper.JEIRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.recipe.ingredient.ChanceFluidIngredient;
import nc.recipe.ingredient.ChanceItemIngredient;
import nc.util.Lang;
import nc.util.NCMath;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public abstract class BasicProcessorCategory<WRAPPER extends JEIRecipeWrapper> implements IRecipeCategory<WRAPPER>
{

	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	protected String name;
	protected final int backPosX, backPosY;



	public BasicProcessorCategory(IGuiHelper guiHelper, String name, String backgroundTexture, ItemStack iconItem, int backX, int backY, int backWidth, int backHeight)
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + backgroundTexture);
		background = guiHelper.createDrawable(gui_texture, backX, backY, backWidth, backHeight);
		icon = guiHelper.createDrawableIngredient(iconItem);
		this.name = name;
		backPosX = backX;
		backPosY = backY;

	}


	@Override
	public String getUid()
	{
		return QMD.MOD_ID + "." + name;
	}

	@Override
	public String getTitle()
	{
		return Lang.localize(QMD.MOD_ID+ ".gui.jei.category." + name);
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
	public void setRecipe(IRecipeLayout recipeLayout, WRAPPER recipeWrapper, IIngredients ingredients)
	{
		recipeLayout.getItemStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) ->
		{
			int outputIndex = slotIndex - recipeWrapper.recipeHandler.getItemInputSize();
			if (outputIndex >= 0 && outputIndex <= recipeWrapper.recipeHandler.getItemOutputSize() && recipeWrapper.recipe.getItemProducts().get(outputIndex) instanceof ChanceItemIngredient)
			{
				ChanceItemIngredient chanceIngredient = (ChanceItemIngredient)recipeWrapper.recipe.getItemProducts().get(outputIndex);
				tooltip.add(TextFormatting.WHITE + Lang.localize("jei.nuclearcraft.chance_output", chanceIngredient.minStackSize, chanceIngredient.getMaxStackSize(0), NCMath.decimalPlaces(chanceIngredient.meanStackSize, 2)));
			}
		});

		recipeLayout.getFluidStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) ->
		{
			int outputIndex = slotIndex - recipeWrapper.recipeHandler.getFluidInputSize();
			if (outputIndex >= 0 && outputIndex <= recipeWrapper.recipeHandler.getFluidOutputSize() && recipeWrapper.recipe.getFluidProducts().get(outputIndex) instanceof ChanceFluidIngredient)
			{
				ChanceFluidIngredient chanceIngredient = (ChanceFluidIngredient)recipeWrapper.recipe.getFluidProducts().get(outputIndex);
				tooltip.add(TextFormatting.WHITE + Lang.localize("jei.nuclearcraft.chance_output", chanceIngredient.minStackSize, chanceIngredient.getMaxStackSize(0), NCMath.decimalPlaces(chanceIngredient.meanStackSize, 2)));
			}
		});
	}

}
