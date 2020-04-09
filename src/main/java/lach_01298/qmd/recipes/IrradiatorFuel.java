package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.item.QMDItems;
import nc.recipe.ProcessorRecipeHandler;

public class IrradiatorFuel extends ProcessorRecipeHandler
{

	public IrradiatorFuel()
	{
		super("irradiator_fuel", 1, 0, 0, 0);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(QMDItems.source_cobalt_60, 1.0);
		addRecipe(QMDItems.source_iridium_192, 10.0);
		
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 1D);
		return fixed;
	}

	

}
