package lach_01298.qmd.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.integration.crafttweaker.CTHelper;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;

public class AddQMDRecipe implements IAction 
{
	
	public static boolean hasErrored = false;
	
	public List<IItemIngredient> itemIngredients;
	public List<IFluidIngredient> fluidIngredients;
	public List<IItemIngredient> itemProducts;
	public List<IFluidIngredient> fluidProducts;
	public List<IParticleIngredient> particleIngredients;
	public List<IParticleIngredient> particleProducts;
	public List extras;
	public QMDRecipe recipe;
	public boolean inputsAllNull = true, ingredientError, wasNull, wrongSize;
	public final QMDRecipeHandler recipeHandler;

	public AddQMDRecipe(QMDRecipeHandler recipeHandler, List objects) 
	{
		this.recipeHandler = recipeHandler;
		
		int listCount = 0, ingredientCount = 0;
		List<IItemIngredient> itemIngredients = new ArrayList<>();
		List<IFluidIngredient> fluidIngredients = new ArrayList<>();
		List<IItemIngredient> itemProducts = new ArrayList<>();
		List<IFluidIngredient> fluidProducts = new ArrayList<>();
		List<IParticleIngredient> particleIngredients = new ArrayList<>();
		List<IParticleIngredient> particleProducts = new ArrayList<>();
		List extras = new ArrayList();
		
		while (listCount < objects.size()) 
		{
			Object object = objects.get(listCount);
			if (ingredientCount < recipeHandler.getItemInputSize()) 
			{
				if (object != null) {
					if (!(object instanceof IIngredient)) 
					{
						ingredientError = true;
						return;
					}
					inputsAllNull = false;
				}
				IItemIngredient ingredient = CTHelper.buildAdditionItemIngredient((IIngredient) object);
				if (ingredient == null) {
					ingredientError = true;
					return;
				}
				itemIngredients.add(ingredient);
			} else if (ingredientCount < recipeHandler.getItemInputSize() + recipeHandler.getFluidInputSize()) 
			{
				if (object != null) {
					if (!(object instanceof IIngredient)) 
					{
						ingredientError = true;
						return;
					}
					inputsAllNull = false;
				}
				IFluidIngredient ingredient = CTHelper.buildAdditionFluidIngredient((IIngredient) object);
				if (ingredient == null) {
					ingredientError = true;
					return;
				}
				fluidIngredients.add(ingredient);
			} else if (ingredientCount < recipeHandler.getItemInputSize() + recipeHandler.getFluidInputSize() + recipeHandler.getParticleInputSize()) 
			{
				if (object != null) {
					if (!(object instanceof IIngredient)) 
					{
						ingredientError = true;
						return;
					}
					inputsAllNull = false;
				}
				IParticleIngredient ingredient = QMDCTHelper.buildAdditionParticleIngredient((IIngredient) object);
				if (ingredient == null) {
					ingredientError = true;
					return;
				}
				particleIngredients.add(ingredient);
			} else if (ingredientCount < recipeHandler.getItemInputSize() + recipeHandler.getFluidInputSize() + recipeHandler.getParticleInputSize() + recipeHandler.getItemOutputSize()) 
			{
				if (object != null) {
					if (!(object instanceof IIngredient)) 
					{
						ingredientError = true;
						return;
					}
				}
				IItemIngredient ingredient = CTHelper.buildAdditionItemIngredient((IIngredient) object);
				if (ingredient == null) {
					ingredientError = true;
					return;
				}
				itemProducts.add(ingredient);
			} 
			else if (ingredientCount < recipeHandler.getItemInputSize() + recipeHandler.getFluidInputSize() + recipeHandler.getParticleInputSize() + recipeHandler.getItemOutputSize() + recipeHandler.getFluidOutputSize()) 
			{
				if (object != null) {
					if (!(object instanceof IIngredient)) 
					{
						ingredientError = true;
						return;
					}
				}
				IFluidIngredient ingredient = CTHelper.buildAdditionFluidIngredient((IIngredient) object);
				if (ingredient == null) {
					ingredientError = true;
					return;
				}
				fluidProducts.add(ingredient);
			} 
			else if (ingredientCount < recipeHandler.getItemInputSize() + recipeHandler.getFluidInputSize() + recipeHandler.getParticleInputSize() + recipeHandler.getItemOutputSize() + recipeHandler.getFluidOutputSize() + recipeHandler.getParticleOutputSize()) 
			{
				if (object != null) {
					if (!(object instanceof IIngredient)) 
					{
						ingredientError = true;
						return;
					}
				}
				IParticleIngredient ingredient = QMDCTHelper.buildAdditionParticleIngredient((IIngredient) object);
				if (ingredient == null) {
					ingredientError = true;
					return;
				}
				particleProducts.add(ingredient);
			} 
			else 
			{
				extras.add(object);
			}
			listCount++;
			ingredientCount++;
		}
		
		if (itemIngredients.size() != recipeHandler.getItemInputSize() || fluidIngredients.size() != recipeHandler.getFluidInputSize() || particleIngredients.size() != recipeHandler.getParticleInputSize() || itemProducts.size() != recipeHandler.getItemOutputSize() || fluidProducts.size() != recipeHandler.getFluidOutputSize() || particleProducts.size() != recipeHandler.getParticleOutputSize()) 
		{
			CraftTweakerAPI.logError("A " + recipeHandler.getRecipeName() + " recipe was the wrong size");
			wrongSize = true;
			return;
		}
		
		this.itemIngredients = itemIngredients;
		this.fluidIngredients = fluidIngredients;
		this.particleIngredients = particleIngredients;
		this.itemProducts = itemProducts;
		this.fluidProducts = fluidProducts;
		this.particleProducts = particleProducts;
		this.extras = extras;
		
		recipe = recipeHandler.buildRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts, extras, recipeHandler.isShapeless());
		if (recipe == null) wasNull = true;
	}
	
	@Override
	public void apply() 
	{
		if (!inputsAllNull && !ingredientError && !wasNull && !wrongSize) 
		{
			recipeHandler.addRecipe(recipe);
		}
	}
	
	@Override
	public String describe() 
	{
		if (inputsAllNull || ingredientError || wasNull || wrongSize) {
			if (ingredientError || wrongSize) callError();
			return String.format("Error: Failed to add %s recipe: %s", recipeHandler.getRecipeName(), QMDRecipeHelper.getRecipeString(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts));
		}
		return String.format("Adding %s recipe: %s", recipeHandler.getRecipeName(), QMDRecipeHelper.getRecipeString(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts));
	}
	
	public static void callError() 
	{
		if (!hasErrored) {
			CraftTweakerAPI.logError("At least one QMD CraftTweaker recipe addition method has errored - check the CraftTweaker log for more details");
		}
		hasErrored = true;
	}
}
