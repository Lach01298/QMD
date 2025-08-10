package lach_01298.qmd.recipes;

import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.init.NCBlocks;
import nc.recipe.BasicRecipeHandler;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;

import java.util.*;

public class IrradiatorRecipes extends QMDRecipeHandler
{

	public IrradiatorRecipes()
	{
		super("irradiator", 1, 0, 0, 1 ,0, 0 );
		
	}

	@Override
	public void addRecipes()
	{
		addRecipe(Items.ROTTEN_FLESH, QMDItems.flesh,1.0);
		addRecipe(Blocks.BROWN_MUSHROOM, NCBlocks.glowing_mushroom,4.0);
		addRecipe(Blocks.RED_MUSHROOM, NCBlocks.glowing_mushroom,4.0);
		addRecipe(new ItemStack(Blocks.TALLGRASS,1,1), Blocks.DEADBUSH,1.0);
		addRecipe(Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,1.0);
		addRecipe(new ItemStack(Blocks.STONEBRICK,1,1), Blocks.STONEBRICK,1.0);
		addRecipe(new ItemStack(Blocks.COBBLESTONE_WALL,1,1), Blocks.COBBLESTONE_WALL,1.0);
	}

	@Override
	public List fixedExtras(List extras)
	{
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Double.class, 1D); 	// time multiplier
		fixer.add(Double.class, 0D);		//  power multiplier
		fixer.add(Double.class, 0D);		// radiation

		return fixer.fixed;
	}

}
