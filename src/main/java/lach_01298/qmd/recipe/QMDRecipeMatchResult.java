package lach_01298.qmd.recipe;

import it.unimi.dsi.fastutil.ints.IntList;

public class QMDRecipeMatchResult
{

	public static final QMDRecipeMatchResult FAIL = new QMDRecipeMatchResult(false, AbstractQMDRecipeHandler.INVALID,
			AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID,
			AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID);

	public final boolean isMatch;

	public final IntList itemIngredientNumbers, fluidIngredientNumbers, particleIngredientNumbers, itemInputOrder,
			fluidInputOrder, particleInputOrder;

	public QMDRecipeMatchResult(boolean isMatch, IntList itemIngredientNumbers,
								IntList fluidIngredientNumbers, IntList particleIngredientNumbers, IntList itemInputOrder,
								IntList fluidInputOrder, IntList particleInputOrder)
	{
		this.isMatch = isMatch;
		this.itemIngredientNumbers = itemIngredientNumbers;
		this.fluidIngredientNumbers = fluidIngredientNumbers;
		this.particleIngredientNumbers = particleIngredientNumbers;
		this.itemInputOrder = itemInputOrder;
		this.fluidInputOrder = fluidInputOrder;
		this.particleInputOrder = particleInputOrder;
	}
}
