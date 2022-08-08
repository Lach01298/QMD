package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import nc.recipe.BasicRecipeHandler;

public class VacuumChamberHeaterRecipes extends BasicRecipeHandler
{

	public VacuumChamberHeaterRecipes()
	{
		super("vacuum_chamber_heater", 0, 1, 0, 1);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(fluidStack("mercury", 1), fluidStack("hot_mercury", 1), 1);
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Integer ? (int) extras.get(0) : 1000);
		return fixed;
	}

}
