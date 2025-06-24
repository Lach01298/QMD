package lach_01298.qmd.recipes;

import lach_01298.qmd.recipe.QMDRecipeHandler;

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
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Integer ? (int) extras.get(0) : 1000);
		return fixed;
	}

}
