package lach_01298.qmd.recipes;

import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.item.QMDItems;
import nc.recipe.BasicRecipeHandler;
import net.minecraft.item.ItemStack;

import java.util.*;

public class IrradiatorFuel extends BasicRecipeHandler
{

	public IrradiatorFuel()
	{
		super("irradiator_fuel", 1, 0, 0, 0);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(new ItemStack(QMDItems.source,1, SourceType.COBALT_60.getID()), 1D);
		addRecipe(new ItemStack(QMDItems.source,1, SourceType.IRIDIUM_192.getID()), 10D);
		
	}

	@Override
	public List fixedExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 1D);
		return fixed;
	}

	

}
