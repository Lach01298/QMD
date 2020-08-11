package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.item.QMDItems;
import nc.recipe.ProcessorRecipeHandler;
import nc.recipe.RecipeHelper;
import nc.recipe.ingredient.IItemIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CellFillingRecipes extends ProcessorRecipeHandler
{

	public CellFillingRecipes()
	{
		super("cell_filling", 1, 1, 1, 1);
	}

	@Override
	public void addRecipes()
	{
		addCellRecipe("Antihydrogen",1000);
		addCellRecipe("Antideuterium",1000);
		addCellRecipe("Antitritium",1000);
		addCellRecipe("Antihelium3",1000);
		addCellRecipe("Antihelium",1000);
		
	}

	
	
	
	public void addCellRecipe(String fluid, int amount)
	{
		ItemStack cell = new ItemStack(QMDItems.cell);
		cell.setTagCompound(new NBTTagCompound());
		IItemIngredient ingredent = RecipeHelper.buildItemIngredient("cell"+fluid);
		ItemStack item =  ingredent.getStack();
		item.setTagCompound(new NBTTagCompound());
		
		addRecipe(cell,fluidStack(fluid.toLowerCase(), amount),"cell"+fluid,emptyFluidStack());
		addRecipe(item,emptyFluidStack(),cell,fluidStack(fluid.toLowerCase(), amount));
	}
	
	
	
	
	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Integer ? (int) extras.get(0) : 1000);
		return fixed;
	}

}

