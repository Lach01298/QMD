package lach_01298.qmd.crafttweaker;

import crafttweaker.IAction;
import lach_01298.qmd.recipe.QMDRecipeHandler;

public class RemoveAllQMDRecipes implements IAction 
{
	
	public static boolean hasErrored = false;
	public final QMDRecipeHandler recipeHandler;
	
	public RemoveAllQMDRecipes(QMDRecipeHandler recipeHandler) 
	{
		this.recipeHandler = recipeHandler;
	}
	
	@Override
	public void apply() 
	{
		recipeHandler.removeAllRecipes();
	}
	
	@Override
	public String describe() 
	{
		return String.format("Removing all %s recipes", recipeHandler.getRecipeName());
	}
}
