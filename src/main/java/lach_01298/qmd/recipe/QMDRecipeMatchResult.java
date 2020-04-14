package lach_01298.qmd.recipe;

import java.util.List;

public class QMDRecipeMatchResult
{

	public static final QMDRecipeMatchResult FAIL = new QMDRecipeMatchResult(false, AbstractQMDRecipeHandler.INVALID, 
			AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID,
			AbstractQMDRecipeHandler.INVALID, AbstractQMDRecipeHandler.INVALID);

	private final boolean match;

	final List<Integer> itemIngredientNumbers, fluidIngredientNumbers, particleIngredientNumbers, itemInputOrder,
			fluidInputOrder, particleInputOrder;

	public QMDRecipeMatchResult(boolean match, List<Integer> itemIngredientNumbers,
			List<Integer> fluidIngredientNumbers, List<Integer> particleIngredientNumbers, List<Integer> itemInputOrder,
			List<Integer> fluidInputOrder, List<Integer> particleInputOrder)
	{
		this.match = match;
		this.itemIngredientNumbers = itemIngredientNumbers;
		this.fluidIngredientNumbers = fluidIngredientNumbers;
		this.particleIngredientNumbers = particleIngredientNumbers;
		this.itemInputOrder = itemInputOrder;
		this.fluidInputOrder = fluidInputOrder;
		this.particleInputOrder = particleInputOrder;
	}

	public boolean matches()
	{
		return match;
	}
}
