package lach_01298.qmd.recipe;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
import nc.Global;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.integration.gtce.GTCERecipeHelper;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.ProcessorRecipe;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.util.NCUtil;

public abstract class QMDRecipeHandler extends AbstractQMDRecipeHandler<QMDRecipe>
{

	public final int itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize,
			particleOutputSize;
	public final boolean isShapeless;
	private final String recipeName;

	public QMDRecipeHandler(@Nonnull String recipeName, int itemInputSize, int fluidInputSize, int particleInputSize,
			int itemOutputSize, int fluidOutputSize, int ParticleOutputSize)
	{
		this(recipeName, itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize,
				ParticleOutputSize, true);
	}

	public QMDRecipeHandler(@Nonnull String recipeName, int itemInputSize, int fluidInputSize, int particleInputSize,
			int itemOutputSize, int fluidOutputSize, int particleOutputSize, boolean isShapeless)
	{
		this.itemInputSize = itemInputSize;
		this.fluidInputSize = fluidInputSize;
		this.particleInputSize = particleInputSize;
		this.itemOutputSize = itemOutputSize;
		this.fluidOutputSize = fluidOutputSize;
		this.particleOutputSize = particleOutputSize;
		this.isShapeless = isShapeless;
		this.recipeName = recipeName;

		addRecipes();
	}

	public void addRecipe(Object... objects)
	{
		List itemInputs = new ArrayList(), fluidInputs = new ArrayList(), particleInputs = new ArrayList(),
				itemOutputs = new ArrayList(), fluidOutputs = new ArrayList(), particleOutputs = new ArrayList(),
				extras = new ArrayList();
		for (int i = 0; i < objects.length; i++)
		{
			Object object = objects[i];
			if (i < itemInputSize)
			{
				itemInputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize)
			{
				fluidInputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize + particleInputSize)
			{
				particleOutputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize + particleInputSize + itemOutputSize)
			{
				itemOutputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize)
			{
				fluidOutputs.add(object);
			}
			else if (i < itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize
					+ particleOutputSize)
			{
				particleOutputs.add(object);
			}
			else
			{
				extras.add(object);
			}
		}
		QMDRecipe recipe = buildRecipe(itemInputs, fluidInputs, particleInputs, itemOutputs, fluidOutputs,
				particleOutputs, extras, isShapeless);
		addRecipe(recipe);

	}

	@Nullable
	public QMDRecipe buildRecipe(List itemInputs, List fluidInputs, List particleInputs, List itemOutputs,
			List fluidOutputs, List particleOutputs, List extras, boolean shapeless)
	{
		List<IItemIngredient> itemIngredients = new ArrayList<IItemIngredient>(),
				itemProducts = new ArrayList<IItemIngredient>();
		List<IFluidIngredient> fluidIngredients = new ArrayList<IFluidIngredient>(),
				fluidProducts = new ArrayList<IFluidIngredient>();
		List<IParticleIngredient> particleIngredients = new ArrayList<IParticleIngredient>(),
				particleProducts = new ArrayList<IParticleIngredient>();

		for (Object obj : itemInputs)
		{
			if (obj != null && isValidItemInputType(obj))
			{
				IItemIngredient input = RecipeHelper.buildItemIngredient(obj);
				if (input == null)
					return null;
				itemIngredients.add(input);
			}
			else
				return null;
		}
		for (Object obj : fluidInputs)
		{
			if (obj != null && isValidFluidInputType(obj))
			{
				IFluidIngredient input = RecipeHelper.buildFluidIngredient(obj);
				if (input == null)
					return null;
				fluidIngredients.add(input);
			}
			else
				return null;
		}
		for (Object obj : particleInputs)
		{
			if (obj != null && isValidParticleInputType(obj))
			{
				IParticleIngredient input = RecipeHelper.buildParticleIngredient(obj);
				if (input == null)
					return null;
				particleIngredients.add(input);
			}
			else
				return null;
		}
		for (Object obj : itemOutputs)
		{
			if (obj != null && isValidItemOutputType(obj))
			{
				IItemIngredient output = RecipeHelper.buildItemIngredient(obj);
				if (output == null)
					return null;
				itemProducts.add(output);
			}
			else
				return null;
		}
		for (Object obj : fluidOutputs)
		{
			if (obj != null && isValidFluidOutputType(obj))
			{
				IFluidIngredient output = RecipeHelper.buildFluidIngredient(obj);
				if (output == null)
					return null;
				fluidProducts.add(output);
			}
			else
				return null;
		}
		for (Object obj : particleOutputs)
		{
			if (obj != null && isValidParticleOutputType(obj))
			{
				IParticleIngredient output = RecipeHelper.buildParticleIngredient(obj);
				if (output == null)
					return null;
				particleProducts.add(output);
			}
			else
				return null;
		}
		if (!isValidRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts,
				particleProducts))
		{
			Util.getLogger()
					.info(getRecipeName() + " - a recipe was removed: " + RecipeHelper.getRecipeString(itemIngredients,
							fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts));
		}
		return new QMDRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts,
				particleProducts, extras, shapeless);
	}

	public boolean isValidRecipe(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients,
			List<IParticleIngredient> particleIngredients, List<IItemIngredient> itemProducts,
			List<IFluidIngredient> fluidProducts, List<IParticleIngredient> particleProducts)
	{
		return itemIngredients.size() == itemInputSize && fluidIngredients.size() == fluidInputSize
				&& particleIngredients.size() == particleInputSize && itemProducts.size() == itemOutputSize
				&& fluidProducts.size() == fluidOutputSize && particleProducts.size() == particleOutputSize;
	}

	public String getRecipeName()
	{
		return QMD.MOD_ID + "_" + recipeName;
	}
}
