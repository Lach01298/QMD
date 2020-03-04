package lach_01298.qmd.recipes;

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

}
