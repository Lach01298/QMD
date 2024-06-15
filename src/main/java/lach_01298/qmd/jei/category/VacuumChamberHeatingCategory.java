package lach_01298.qmd.jei.category;

import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.integration.jei.JEIHelper.RecipeFluidMapper;
import nc.recipe.IngredientSorption;
import nc.util.Lang;
import nclegacy.jei.IJEIHandlerLegacy;
import net.minecraft.util.text.TextFormatting;

public class VacuumChamberHeatingCategory extends JEIQMDMachineCategory<QMDRecipeWrapper.VacuumChamberHeating>
{
	
	public VacuumChamberHeatingCategory(IGuiHelper guiHelper, IJEIHandlerLegacy handler)
	{
		super(guiHelper, handler, "vacuum_chamber_heating", 0, 0, 90, 26);
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, QMDRecipeWrapper.VacuumChamberHeating recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);
		
		RecipeFluidMapper fluidMapper = new RecipeFluidMapper();
		fluidMapper.put(IngredientSorption.INPUT, 0, 0, 9 - backPosX, 5 - backPosY, 16, 16);
		fluidMapper.put(IngredientSorption.OUTPUT, 0, 1, 65 - backPosX, 1 - backPosY, 24, 24);
		fluidMapper.apply(recipeLayout.getFluidStacks(), ingredients);
		
		recipeLayout.getFluidStacks().addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {
			
			if (slotIndex == 0)
			{
				if(recipeWrapper.recipe.getFluidIngredients().get(0) != null)
				{
				tooltip.add( TextFormatting.AQUA + TEMPERATURE + TextFormatting.WHITE + " " + recipeWrapper.recipe.getFluidIngredients().get(0).getStack().getFluid().getTemperature() + "K");
				}
			}
			else if (slotIndex == 1)
			{
				if(recipeWrapper.recipe.getFluidProducts().get(0) != null)
				{
					tooltip.add(TextFormatting.RED  + TEMPERATURE + TextFormatting.WHITE + " " + recipeWrapper.recipe.getFluidProducts().get(0).getStack().getFluid().getTemperature() + "K");
				}
			}
		});
	}
	private static final String TEMPERATURE = Lang.localize("jei.nuclearcraft.exchanger_fluid_temp");
}
