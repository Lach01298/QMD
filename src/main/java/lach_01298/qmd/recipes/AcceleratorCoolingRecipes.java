package lach_01298.qmd.recipes;

import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.BasicRecipeHandler;

import java.util.ArrayList;
import java.util.List;

public class AcceleratorCoolingRecipes extends QMDRecipeHandler
{

	public AcceleratorCoolingRecipes()
	{
		super("accelerator_cooling", 0, 1,0 , 0, 1, 0);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(fluidStack("liquid_helium", 1), fluidStack("helium", 64), 1000,4);
		addRecipe(fluidStack("liquid_nitrogen", 1), fluidStack("nitrogen", 64), 1000,77);
		addRecipe(fluidStack("liquid_neon", 1), fluidStack("neon", 64), 1000,27);
		addRecipe(fluidStack("liquid_argon", 1), fluidStack("argon", 64), 1000,87);
		addRecipe(fluidStack("water", 1), fluidStack("condensate_water", 1), 1000,300);
		
	}

	@Override
	public List fixedExtras(List extras)
	{
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Integer.class, 1000); 		// heat required
		fixer.add(Integer.class, 300);		// coolant temperature

		return fixer.fixed;
	}

}
