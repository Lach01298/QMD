package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.item.QMDItems;
import nc.init.NCBlocks;
import nc.recipe.BasicRecipeHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class IrradiatorRecipes extends BasicRecipeHandler
{

	public IrradiatorRecipes()
	{
		super("irradiator", 1, 0, 1 ,0 );
		
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
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(3);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 1D);
		fixed.add(0D);
		fixed.add(0D);
		return fixed;
	}

}
