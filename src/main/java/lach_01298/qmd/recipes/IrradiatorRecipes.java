package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.item.QMDItems;
import nc.init.NCBlocks;
import nc.recipe.ProcessorRecipeHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class IrradiatorRecipes extends ProcessorRecipeHandler
{

	public IrradiatorRecipes()
	{
		super("irradiator", 1, 0, 1 ,0 );
		
	}

	@Override
	public void addRecipes()
	{
		addRecipe(Items.ROTTEN_FLESH, QMDItems.flesh,1.0);
		addRecipe(Blocks.BROWN_MUSHROOM, NCBlocks.glowing_mushroom,2.0);
		addRecipe(Blocks.RED_MUSHROOM, NCBlocks.glowing_mushroom,2.0);
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(3);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 1D);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Double ? (double) extras.get(1) : 1D);
		fixed.add(extras.size() > 2 && extras.get(2) instanceof Double ? (double) extras.get(2) : 0D);
		return fixed;
	}

}
