package lach_01298.qmd.recipe;

import java.util.List;

import javax.annotation.Nonnull;

public class QMDRecipeInfo<T extends IQMDRecipe>
{

	private final T recipe;

	private final QMDRecipeMatchResult matchResult;

	public QMDRecipeInfo(@Nonnull T recipe, QMDRecipeMatchResult matchResult)
	{
		this.recipe = recipe;
		this.matchResult = matchResult;
	}

	public @Nonnull T getRecipe()
	{
		return recipe;
	}

	/** Already takes item input order into account! */
	public List<Integer> getItemIngredientNumbers()
	{
		return matchResult.itemIngredientNumbers;
	}

	/** Already takes fluid input order into account! */
	public List<Integer> getFluidIngredientNumbers()
	{
		return matchResult.fluidIngredientNumbers;
	}
	
	/** Already takes particle input order into account! */
	public List<Integer> getParticleIngredientNumbers()
	{
		return matchResult.particleIngredientNumbers;
	}

	public List<Integer> getItemInputOrder()
	{
		return matchResult.itemInputOrder;
	}

	public List<Integer> getFluidInputOrder()
	{
		return matchResult.fluidInputOrder;
	}
	
	public List<Integer> getParticleInputOrder()
	{
		return matchResult.particleInputOrder;
	}
}