package lach_01298.qmd.recipes;

import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.BasicRecipeHandler;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IrradiatorFuel extends QMDRecipeHandler
{

	public IrradiatorFuel()
	{
		super("irradiator_fuel", 1, 0,0,0, 0, 0);
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
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Double.class, 1D);		// speed multiplier

		return fixer.fixed;
	}

	

}
