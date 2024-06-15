package lach_01298.qmd.recipe;

import javax.annotation.Nonnull;
import java.util.List;

public class QMDRecipeInfo<T extends IQMDRecipe>
{

	public final @Nonnull T recipe;

	private final QMDRecipeMatchResult matchResult;

	public QMDRecipeInfo(@Nonnull T recipe, QMDRecipeMatchResult matchResult)
	{
		this.recipe = recipe;
		this.matchResult = matchResult;
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
