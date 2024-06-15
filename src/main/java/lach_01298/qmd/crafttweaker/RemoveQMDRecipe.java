package lach_01298.qmd.crafttweaker;

import crafttweaker.*;
import crafttweaker.api.item.IIngredient;
import lach_01298.qmd.recipe.*;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.integration.crafttweaker.CTHelper;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.*;

import java.util.*;

public class RemoveQMDRecipe implements IAction
{
	
	public static boolean hasErrored = false;
	
	public List<IItemIngredient> itemIngredients;
	public List<IFluidIngredient> fluidIngredients;
	public List<IParticleIngredient> particleIngredients;
	public IngredientSorption type;
	public QMDRecipe recipe;
	public boolean ingredientError, wasNull, wrongSize;
	public final QMDRecipeHandler recipeHandler;

	public RemoveQMDRecipe(QMDRecipeHandler recipeHandler, IngredientSorption type, List<IIngredient> ctIngredients)
	{
		this.recipeHandler = recipeHandler;
		this.type = type;
		int itemSize = type == IngredientSorption.INPUT ? recipeHandler.getItemInputSize() : recipeHandler.getItemOutputSize();
		int fluidSize = type == IngredientSorption.INPUT ? recipeHandler.getFluidInputSize() : recipeHandler.getFluidOutputSize();
		int particleSize = type == IngredientSorption.INPUT ? recipeHandler.getParticleInputSize() : recipeHandler.getParticleOutputSize();
		if (ctIngredients.size() != itemSize + fluidSize + particleSize)
		{
			CraftTweakerAPI.logError("A " + recipeHandler.getRecipeName() + " recipe was the wrong size");
			wrongSize = true;
			return;
		}
		List<IItemIngredient> itemIngredients = new ArrayList<>();
		List<IFluidIngredient> fluidIngredients = new ArrayList<>();
		List<IParticleIngredient> particleIngredients = new ArrayList<>();
		for (int i = 0; i < itemSize; i++)
		{
			IItemIngredient ingredient = CTHelper.buildRemovalItemIngredient(ctIngredients.get(i));
			if (ingredient == null)
			{
				ingredientError = true;
				return;
			}
			itemIngredients.add(ingredient);
		}
		for (int i = itemSize; i < itemSize+fluidSize; i++)
		{
			IFluidIngredient ingredient = CTHelper.buildRemovalFluidIngredient(ctIngredients.get(i));
			if (ingredient == null)
			{
				ingredientError = true;
				return;
			}
			fluidIngredients.add(ingredient);
		}
		
		for (int i = itemSize + fluidSize; i < itemSize + fluidSize+particleSize; i++)
		{
			IParticleIngredient ingredient = QMDCTHelper.buildRemovalParticleIngredient(ctIngredients.get(i));
			
			if (ingredient == null)
			{
				ingredientError = true;
				return;
			}
			particleIngredients.add(ingredient);
		}

		this.itemIngredients = itemIngredients;
		this.fluidIngredients = fluidIngredients;
		this.particleIngredients = particleIngredients;
		
		this.recipe = type == IngredientSorption.INPUT ? recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients, particleIngredients) : null;
	
		if (recipe == null) wasNull = true;
	}
	
	@Override
	public void apply()
	{
		if (!ingredientError && !wasNull && !wrongSize)
		{
			boolean removed = recipeHandler.removeRecipe(recipe);
			while (removed) {
				
				recipe = type == IngredientSorption.INPUT ? recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients, particleIngredients) : null;
				removed = recipeHandler.removeRecipe(recipe);
			}
		}
	}
	
	@Override
	public String describe()
	{
		if (ingredientError || wasNull || wrongSize)
		{
			if (ingredientError || wrongSize) callError();
			return String.format("Error: Failed to remove %s recipe with %s as the " + (type == IngredientSorption.INPUT ? "input" : "output"), recipeHandler.getRecipeName(), QMDRecipeHelper.getAllIngredientNamesConcat(itemIngredients, fluidIngredients, particleIngredients));
		}
		if (type == IngredientSorption.INPUT)
		{
			return String.format("Removing %s recipe: %s", recipeHandler.getRecipeName(), QMDRecipeHelper.getRecipeString(recipe));
		}
		else return String.format("Removing %s recipes for: %s", recipeHandler.getRecipeName(), QMDRecipeHelper.getAllIngredientNamesConcat(itemIngredients, fluidIngredients, particleIngredients));
	}
	
	public static void callError()
	{
		if (!hasErrored)
		{
			CraftTweakerAPI.logError("At least one QMD CraftTweaker recipe removal method has errored - check the CraftTweaker log for more details");
		}
		hasErrored = true;
	}
}
