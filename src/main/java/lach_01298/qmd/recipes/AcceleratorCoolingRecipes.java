package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import nc.recipe.BasicRecipeHandler;

public class AcceleratorCoolingRecipes extends BasicRecipeHandler
{

	public AcceleratorCoolingRecipes()
	{
		super("accelerator_cooling", 0, 1, 0, 1);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(fluidStack("liquid_helium", 1), fluidStack("helium", 64), 1000);
		addRecipe(fluidStack("liquid_nitrogen", 1), fluidStack("nitrogen", 64), 1000);
		addRecipe(fluidStack("liquid_neon", 1), fluidStack("neon", 64), 1000);
		addRecipe(fluidStack("liquid_argon", 1), fluidStack("argon", 64), 1000);
		addRecipe(fluidStack("water", 1), fluidStack("preheated_water", 1), 1000); //TODO change to condensate water when heat exchanghers get here
		
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Integer ? (int) extras.get(0) : 1000);
		return fixed;
	}

}
