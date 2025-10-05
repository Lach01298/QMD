package lach_01298.qmd.recipes;

import nc.recipe.BasicRecipeHandler;

import java.util.List;

public class LiquefierCoolantRecipes extends BasicRecipeHandler
{
	public LiquefierCoolantRecipes()
	{
		super("liquefier_coolant", 0, 1, 0, 1);
	}


	@Override
	public void addRecipes()
	{
		//addRecipe(fluidInput, fluidOutput, int heat, int input temperature, int output temperature)
		addRecipe(fluidStack("liquid_helium", 1), fluidStack("helium", 64), 1000, 4, 300);
		addRecipe(fluidStack("liquid_nitrogen", 1), fluidStack("nitrogen", 64), 1000, 77, 300);
		addRecipe(fluidStack("liquid_neon", 1), fluidStack("neon", 64), 1000, 27, 300);
		addRecipe(fluidStack("liquid_argon", 1), fluidStack("argon", 64), 1000, 87, 300);
		addRecipe(fluidStack("water", 1), fluidStack("condensate_water", 1), 64, 300, 350);


	}

	@Override
	public List fixedExtras(List extras)
	{
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Integer.class, 1000); 		// heat required
		fixer.add(Integer.class, 300);		//  input temperature
		fixer.add(Integer.class, 300);		// output temperature

		return fixer.fixed;
	}
}