package lach_01298.qmd.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.*;
import lach_01298.qmd.crafttweaker.particle.IParticleStack;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipe.ingredient.*;

import java.util.*;






public class QMDCTHelper
{

	public static ParticleStack getParticleStack(IParticleStack stack)
	{
		if(stack == null) return null;
		
		Object internal = stack.getInternal();
		if (!(internal instanceof ParticleStack))
		{
			CraftTweakerAPI.logError("Not a valid particle stack: " + stack);
		}
		return ((ParticleStack) internal).copy();
	}
	
	public static IParticleIngredient buildAdditionParticleIngredient(IIngredient ingredient)
	{
		if (ingredient == null)
		{
			return new EmptyParticleIngredient();
		}
		else if (ingredient instanceof IParticleStack)
		{
			return QMDRecipeHelper.buildParticleIngredient(getParticleStack((IParticleStack) ingredient));
		}
		else if (ingredient instanceof IngredientOr)
		{
			return buildAdditionParticleIngredientArray((IngredientOr) ingredient);
		}
		else
		{
			CraftTweakerAPI.logError(String.format("QMD: Invalid ingredient: %s, %s", ingredient.getClass().getName(), ingredient));
			return null;
		}
	}
	
	public static IParticleIngredient buildRemovalParticleIngredient(IIngredient ingredient)
	{
		if (ingredient == null)
		{
			return new EmptyParticleIngredient();
		}
		else if (ingredient instanceof IParticleStack)
		{
			return new ParticleIngredient(((IParticleStack)ingredient).getName(), ((IParticleStack)ingredient).getAmount(), ((IParticleStack)ingredient).getMeanEnergy(),((IParticleStack)ingredient).getFocus());
		}
		else if (ingredient instanceof IngredientOr)
		{
			return buildRemovalParticleIngredientArray((IngredientOr) ingredient);
		}
		else
		{
			CraftTweakerAPI.logError(String.format("QMD: Invalid ingredient: %s, %s", ingredient.getClass().getName(), ingredient));
			return null;
		}
	}
	
	public static IParticleIngredient buildAdditionParticleIngredientArray(IngredientOr ingredient)
	{
		if (!(ingredient.getInternal() instanceof IIngredient[]))
		{
			CraftTweakerAPI.logError(String.format("QMD: Invalid ingredient: %s, %s", ingredient.getClass().getName(), ingredient));
			return null;
		}
		List<IParticleIngredient> ingredientList = new ArrayList<>();
		for (IIngredient ctIngredient : (IIngredient[])ingredient.getInternal())
		{
			ingredientList.add(buildAdditionParticleIngredient(ctIngredient));
		}
		return QMDRecipeHelper.buildParticleIngredient(ingredientList);
	}
	
	public static IParticleIngredient buildRemovalParticleIngredientArray(IngredientOr ingredient)
	{
		if (!(ingredient.getInternal() instanceof IIngredient[]))
		{
			CraftTweakerAPI.logError(String.format("QMD: Invalid ingredient: %s, %s", ingredient.getClass().getName(), ingredient));
			return null;
		}
		List<IParticleIngredient> ingredientList = new ArrayList<>();
		for (IIngredient ctIngredient : (IIngredient[])ingredient.getInternal())
		{
			ingredientList.add(buildRemovalParticleIngredient(ctIngredient));
		}
		return QMDRecipeHelper.buildParticleIngredient(ingredientList);
	}
	
}
