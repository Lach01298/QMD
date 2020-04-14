package lach_01298.qmd.recipe.ingredient;

import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import nc.recipe.IngredientMatchResult;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.IIngredient;
import nc.recipe.ingredient.IItemIngredient;

public interface IParticleIngredient extends IIngredient<ParticleStack>
{
	@Override
	public default ParticleStack getNextStack(int ingredientNumber)
	{
		ParticleStack nextStack = getStack();
		nextStack.setAmount(getNextStackSize(ingredientNumber));
		return nextStack;
	}

	@Override
	public default List<ParticleStack> getInputStackHashingList()
	{
		return getInputStackList();
	}

	@Override
	public IParticleIngredient getFactoredIngredient(int factor);
	
	
	public IngredientMatchResult match(Object object, IngredientSorption sorption);

	public IngredientMatchResult matchWithData(Object object, IngredientSorption type, List extras);
}
