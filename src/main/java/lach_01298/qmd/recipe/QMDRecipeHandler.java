package lach_01298.qmd.recipe;

import crafttweaker.annotations.ZenRegister;
import it.unimi.dsi.fastutil.ints.*;
import lach_01298.qmd.QMD;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.util.Util;
import nc.config.NCConfig;
import nc.recipe.ingredient.*;
import nc.util.NCMath;
import stanhebben.zenscript.annotations.*;

import javax.annotation.*;
import java.util.*;

@ZenClass("mods.qmd.QMDRecipeHandler")
@ZenRegister
public abstract class QMDRecipeHandler extends AbstractQMDRecipeHandler<QMDRecipe>
{

	private final String recipeName;
	public final int itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize, particleOutputSize;
	public final boolean isShapeless;

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
				particleInputs.add(object);
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
		QMDRecipe recipe = buildRecipe(itemInputs, fluidInputs, particleInputs, itemOutputs, fluidOutputs,particleOutputs, fixedExtras(extras), isShapeless);
		
		addRecipe(NCConfig.factor_recipes ? factorRecipe(recipe) : recipe);
	}

	public abstract List fixedExtras(List extras);
	
	public QMDRecipe factorRecipe(QMDRecipe recipe)
	{
		if (recipe == null) return null;
		if (recipe.getItemIngredients().size() != 0 || recipe.getItemProducts().size() != 0)
		{
			return recipe;
		}
		
		IntList stackSizes = new IntArrayList();
		for (IFluidIngredient ingredient : recipe.getFluidIngredients())
		{
			stackSizes.addAll(ingredient.getFactors());
		}
		for (IFluidIngredient ingredient : recipe.getFluidProducts())
		{
			stackSizes.addAll(ingredient.getFactors());
		}
		for (IParticleIngredient ingredient : recipe.getParticleIngredients())
		{
			stackSizes.addAll(ingredient.getFactors());
		}
		for (IParticleIngredient ingredient : recipe.getParticleProducts())
		{
			stackSizes.addAll(ingredient.getFactors());
		}
		stackSizes.addAll(getExtraFactors(recipe.getExtras()));
		
		int hcf = NCMath.hcf(stackSizes.toIntArray());
		if (hcf == 1) return recipe;
		
		List<IFluidIngredient> fluidIngredients = new ArrayList<>(), fluidProducts = new ArrayList<>();
		List<IParticleIngredient> particleIngredients = new ArrayList<>(), particleProducts = new ArrayList<>();
		
		for (IFluidIngredient ingredient : recipe.getFluidIngredients())
		{
			fluidIngredients.add(ingredient.getFactoredIngredient(hcf));
		}
		for (IFluidIngredient ingredient : recipe.getFluidProducts())
		{
			fluidProducts.add(ingredient.getFactoredIngredient(hcf));
		}
		
		for (IParticleIngredient ingredient : recipe.getParticleIngredients())
		{
			particleIngredients.add(ingredient.getFactoredIngredient(hcf));
		}
		for (IParticleIngredient ingredient : recipe.getParticleProducts())
		{
			particleProducts.add(ingredient.getFactoredIngredient(hcf));
		}
		
		return new QMDRecipe(recipe.getItemIngredients(), fluidIngredients, particleIngredients, recipe.getItemProducts(), fluidProducts, particleProducts, getFactoredExtras(recipe.getExtras(), hcf), recipe.isShapeless());
	}
	
	public IntList getExtraFactors(List extras)
	{
		return new IntArrayList();
	}
	
	public List getFactoredExtras(List extras, int factor)
	{
		return extras;
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
				IItemIngredient input = QMDRecipeHelper.buildItemIngredient(obj);
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
				IFluidIngredient input = QMDRecipeHelper.buildFluidIngredient(obj);
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
				IParticleIngredient input = QMDRecipeHelper.buildParticleIngredient(obj);
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
				IItemIngredient output = QMDRecipeHelper.buildItemIngredient(obj);
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
				IFluidIngredient output = QMDRecipeHelper.buildFluidIngredient(obj);
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
				IParticleIngredient output = QMDRecipeHelper.buildParticleIngredient(obj);
				if (output == null)
					return null;
				particleProducts.add(output);
			}
			else
				return null;
		}
		if (!isValidRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts))
		{
			Util.getLogger().info(getRecipeName() + " - a recipe was removed: " + QMDRecipeHelper.getRecipeString(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts));
		}
		return new QMDRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts,particleProducts, extras, shapeless);
	}

	public boolean isValidRecipe(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients,List<IParticleIngredient> particleIngredients, List<IItemIngredient> itemProducts,
			List<IFluidIngredient> fluidProducts, List<IParticleIngredient> particleProducts)
	{
		return itemIngredients.size() == itemInputSize && fluidIngredients.size() == fluidInputSize
				&& particleIngredients.size() == particleInputSize && itemProducts.size() == itemOutputSize
				&& fluidProducts.size() == fluidOutputSize && particleProducts.size() == particleOutputSize;
	}
	
	@Override
	public String getRecipeName()
	{
		return QMD.MOD_ID + "_" + recipeName;
	}
	
	@Override
	@ZenMethod
	public List<QMDRecipe> getRecipeList()
	{
		return recipeList;
	}

	@ZenMethod
	public int getItemInputSize()
	{
		return itemInputSize;
	}

	@ZenMethod
	public int getFluidInputSize()
	{
		return fluidInputSize;
	}
	
	@ZenMethod
	public int getParticleInputSize()
	{
		return particleInputSize;
	}

	@ZenMethod
	public int getItemOutputSize()
	{
		return itemOutputSize;
	}

	@ZenMethod
	public int getFluidOutputSize()
	{
		return fluidOutputSize;
	}
	
	@ZenMethod
	public int getParticleOutputSize()
	{
		return particleOutputSize;
	}

	@ZenMethod
	public boolean isShapeless()
	{
		return isShapeless;
	}
}
