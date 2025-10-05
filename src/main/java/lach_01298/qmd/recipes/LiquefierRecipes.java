package lach_01298.qmd.recipes;

import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.BasicRecipeHandler;

import java.util.List;

public class LiquefierRecipes extends QMDRecipeHandler
{
	public LiquefierRecipes()
	{
		super("liquefier", 0, 1, 0, 0,1,0);
	}


	@Override
	public void addRecipes()
	{
		//addRecipe(fluidInput, fluidOutput, int base RF, int base heat, int inversion temperature,int compressed gas temperature, double pressure coefficient)
		addRecipe(fluidStack("helium", 64),fluidStack("liquid_helium", 1), 50D, 1000, 40, 400, 0.1);
		addRecipe(fluidStack("nitrogen", 64),fluidStack("liquid_nitrogen", 1), 50D, 1000, 600, 450, 0.25);
		addRecipe(fluidStack("hydrogen", 64),fluidStack("liquid_hydrogen", 1), 50D, 1000, 200, 400, 0.1);
		addRecipe(fluidStack("neon", 64),fluidStack("liquid_neon", 1), 50D, 1000, 230, 400, 0.2);
		addRecipe(fluidStack("argon", 64),fluidStack("liquid_argon", 1), 50D, 1000, 600, 450, 0.25);
		addRecipe(fluidStack("oxygen", 64),fluidStack("liquid_oxygen", 1), 50D, 1000, 700, 450, 0.25);
		addRecipe(fluidStack("compressed_air", 64),fluidStack("liquid_air", 1), 25D, 1000, 600, 450, 0.25);


	}

	@Override
	public List fixedExtras(List extras)
	{
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Double.class, 50D); 		// base energy
		fixer.add(Integer.class, 1000);		//  base heat
		fixer.add(Integer.class, 500);		// inversion temperature
		fixer.add(Integer.class, 400);		// compressed gas temperature
		fixer.add(Double.class, 0.25D);		// pressure coefficient

		return fixer.fixed;
	}



}
