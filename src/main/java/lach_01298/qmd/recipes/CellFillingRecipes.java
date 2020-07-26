package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import nc.recipe.ProcessorRecipeHandler;

public class CellFillingRecipes extends ProcessorRecipeHandler
{

	public CellFillingRecipes()
	{
		super("cell_filling", 1, 1, 1, 0);
	}

	@Override
	public void addRecipes()
	{
		
		
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Integer ? (int) extras.get(0) : 1000);
		return fixed;
	}

}

