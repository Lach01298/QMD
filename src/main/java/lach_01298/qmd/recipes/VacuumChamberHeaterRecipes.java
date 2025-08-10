package lach_01298.qmd.recipes;

import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.BasicRecipeHandler;

import java.util.ArrayList;
import java.util.List;

public class VacuumChamberHeaterRecipes extends QMDRecipeHandler
{

	public VacuumChamberHeaterRecipes()
	{
		super("vacuum_chamber_heating", 0, 1,0, 0, 1,0);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(fluidStack("mercury", 1), fluidStack("high_pressure_mercury", 2), 512);
		addRecipe(fluidStack("hot_mercury", 1), fluidStack("high_pressure_mercury", 2), 256);
	}

	@Override
	public List fixedExtras(List extras)
	{
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Integer.class, 1000); 		// heat required
		return fixer.fixed;
	}

}
