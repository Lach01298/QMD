package lach_01298.qmd.recipe;

import it.unimi.dsi.fastutil.ints.IntList;

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
	public IntList getItemIngredientNumbers()
	{
		return matchResult.itemIngredientNumbers;
	}

	/** Already takes fluid input order into account! */
	public IntList getFluidIngredientNumbers()
	{
		return matchResult.fluidIngredientNumbers;
	}
	
	/** Already takes particle input order into account! */
	public IntList getParticleIngredientNumbers()
	{
		return matchResult.particleIngredientNumbers;
	}

	public IntList getItemInputOrder()
	{
		return matchResult.itemInputOrder;
	}

	public IntList getFluidInputOrder()
	{
		return matchResult.fluidInputOrder;
	}
	
	public IntList getParticleInputOrder()
	{
		return matchResult.particleInputOrder;
	}
}
