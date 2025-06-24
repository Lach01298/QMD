package lach_01298.qmd.recipes;

import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.RecipeHelper;
import nc.recipe.ingredient.IItemIngredient;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CellFillingRecipes extends QMDRecipeHandler
{

	public CellFillingRecipes()
	{
		super("cell_filling", 1, 1, 0, 1, 1, 0);
	}

	@Override
	public void addRecipes()
	{
		addCellRecipe("Antihydrogen",100);
		addCellRecipe("Antideuterium",100);
		addCellRecipe("Antitritium",100);
		addCellRecipe("Antihelium3",100);
		addCellRecipe("Antihelium",100);
		addCellRecipe("Positronium",100);
		addCellRecipe("Muonium",100);
		addCellRecipe("Tauonium",100);
		addCellRecipe("Glueballs",100);
		
	}

	
	
	
	public void addCellRecipe(String fluid, int amount)
	{
		ItemStack cell = new ItemStack(QMDItems.cell);
		
		IItemIngredient ingredent = RecipeHelper.buildItemIngredient("cell"+fluid);
		ItemStack item =  ingredent.getStack();
		
		addRecipe(cell,fluidStack(fluid.toLowerCase(), amount),"cell"+fluid,emptyFluidStack());
		addRecipe(item,emptyFluidStack(),cell,fluidStack(fluid.toLowerCase(), amount));
	}
	
	
	
	
	@Override
	public List fixedExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Integer ? (int) extras.get(0) : 1000);
		return fixed;
	}

}
