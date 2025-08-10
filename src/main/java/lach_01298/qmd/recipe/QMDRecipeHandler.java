package lach_01298.qmd.recipe;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.crafttweaker.CTAddQMDRecipe;
import lach_01298.qmd.crafttweaker.CTRemoveAllQMDRecipes;
import lach_01298.qmd.crafttweaker.CTRemoveQMDRecipe;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.util.Util;
import nc.config.NCConfig;
import nc.recipe.BasicRecipe;
import nc.recipe.IngredientSorption;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.*;
import nc.tile.internal.fluid.Tank;
import nc.util.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.Optional.Method;
import org.apache.commons.lang3.tuple.Triple;
import stanhebben.zenscript.annotations.*;

import javax.annotation.*;
import java.math.*;
import java.util.*;
import java.util.function.*;

@ZenClass("mods.qmd.QMDRecipeHandler")
@ZenRegister
public abstract class QMDRecipeHandler extends AbstractQMDRecipeHandler<QMDRecipe>
{

	private final String name;
	public final int itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize, particleOutputSize;
	public final int itemInputLastIndex, fluidInputLastIndex, particleInputLastIndex, itemOutputLastIndex, fluidOutputLastIndex, particleOutputLastIndex;
	public final boolean isShapeless;
	public final List<Set<String>> validFluids = new ArrayList<>();

	public QMDRecipeHandler(@Nonnull String recipeName, int itemInputSize, int fluidInputSize, int particleInputSize,
			int itemOutputSize, int fluidOutputSize, int ParticleOutputSize)
	{
		this(recipeName, itemInputSize, fluidInputSize, particleInputSize, itemOutputSize, fluidOutputSize,
				ParticleOutputSize, true);
	}

	public QMDRecipeHandler(@Nonnull String name, int itemInputSize, int fluidInputSize, int particleInputSize,
			int itemOutputSize, int fluidOutputSize, int particleOutputSize, boolean isShapeless)
	{
		this.name = name;
		this.isShapeless = isShapeless;

		this.itemInputSize = itemInputSize;
		this.fluidInputSize = fluidInputSize;
		this.particleInputSize = particleInputSize;
		this.itemOutputSize = itemOutputSize;
		this.fluidOutputSize = fluidOutputSize;
		this.particleOutputSize = particleOutputSize;
		this.itemInputLastIndex = itemInputSize;
		this.fluidInputLastIndex = itemInputSize + fluidInputSize;
		this.particleInputLastIndex = itemInputSize + fluidInputSize + particleInputSize;
		this.itemOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize;
		this.fluidOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize;
		this.particleOutputLastIndex = itemInputSize + fluidInputSize + particleInputSize + itemOutputSize + fluidOutputSize + particleOutputSize;

		addRecipes();
	}

	public void addRecipe(Object... objects)
	{
		QMDRecipe recipe = this.buildRecipe(objects);
		this.addRecipe(NCConfig.factor_recipes ? factorRecipe(recipe) : recipe);
	}

	public QMDRecipe newRecipe(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients,List<IParticleIngredient> particleIngredients, List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts, List<IParticleIngredient> particleProducts, List<Object> extras, boolean shapeless)
	{
		return new QMDRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts, extras, shapeless);
	}

	@Override
	public boolean isValidRecipe(QMDRecipe recipe) {
		if (itemOutputSize == 0 && fluidOutputSize == 0 && particleOutputSize == 0) {
			return true;
		}

		for (IIngredient<?> ingredient : recipe.getItemIngredients()) {
			if (!ingredient.isEmpty()) {
				return true;
			}
		}

		for (IIngredient<?> ingredient : recipe.getFluidIngredients()) {
			if (!ingredient.isEmpty()) {
				return true;
			}
		}

		for (IIngredient<?> ingredient : recipe.getParticleIngredients()) {
			if (!ingredient.isEmpty()) {
				return true;
			}
		}

		for (IIngredient<?> ingredient : recipe.getItemProducts()) {
			if (!ingredient.isEmpty()) {
				return true;
			}
		}

		for (IIngredient<?> ingredient : recipe.getFluidProducts()) {
			if (!ingredient.isEmpty()) {
				return true;
			}
		}

		for (IIngredient<?> ingredient : recipe.getParticleProducts()) {
			if (!ingredient.isEmpty()) {
				return true;
			}
		}

		return false;
	}


	public abstract List<Object> fixedExtras(List<Object> extras);

	public static class ExtrasFixer
	{

		protected final List<Object> extras;
		public final List<Object> fixed = new ArrayList<>();

		protected int currentIndex = 0;

		public ExtrasFixer(List<Object> extras)
		{
			this.extras = extras;
		}

		public <T> void add(Class<? extends T> clazz, T defaultValue)
		{
			int index = currentIndex++;
			Object extra;
			fixed.add(index < extras.size() && (extra = tryCast(clazz, extras.get(index))) != null ? extra : defaultValue);
		}

		public static Object tryCast(Class<?> targetClass, Object value)
		{
			if (value == null)
			{
				return null;
			}

			if (value instanceof Byte byteValue)
			{
				return castInt(targetClass, byteValue);
			}
			else if (value instanceof Short shortValue)
			{
				return castInt(targetClass, shortValue);
			}
			else if (value instanceof Integer intValue)
			{
				return castInt(targetClass, intValue);
			}
			else if (value instanceof Long longValue)
			{
				return castInt(targetClass, longValue);
			}
			else if (value instanceof BigInteger bigIntValue)
			{
				return castInt(targetClass, bigIntValue);
			}
			else if (value instanceof Float floatValue)
			{
				return castFloat(targetClass, floatValue);
			}
			else if (value instanceof Double doubleValue)
			{
				return castFloat(targetClass, doubleValue);
			}
			else if (value instanceof BigDecimal bigDecimalValue)
			{
				return castFloat(targetClass, bigDecimalValue);
			}

			return castDefault(targetClass, value);
		}

		private static <T extends Number> Number castInt(Class<?> targetClass, T value)
		{
			if (targetClass.equals(byte.class) || targetClass.equals(Byte.class))
			{
				return value.byteValue();
			}
			else if (targetClass.equals(short.class) || targetClass.equals(Short.class))
			{
				return value.shortValue();
			}
			else if (targetClass.equals(int.class) || targetClass.equals(Integer.class))
			{
				return value.intValue();
			}
			else if (targetClass.equals(long.class) || targetClass.equals(Long.class))
			{
				return value.longValue();
			}
			else
			{
				return castDefault(targetClass, value);
			}
		}

		private static <T extends Number> Number castFloat(Class<?> targetClass, T value)
		{
			if (targetClass.equals(float.class) || targetClass.equals(Float.class))
			{
				return value.floatValue();
			}
			else if (targetClass.equals(double.class) || targetClass.equals(Double.class))
			{
				return value.doubleValue();
			}
			else
			{
				return castDefault(targetClass, value);
			}
		}

		private static <T> T castDefault(Class<?> targetClass, T value)
		{
			return targetClass.isInstance(value) ? value : null;
		}
	}




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
	
	public IntList getExtraFactors(List<Object> extras)
	{
		return new IntArrayList();
	}
	
	public List getFactoredExtras(List<Object> extras, int factor)
	{
		return extras;
	}

	@Nullable
	public QMDRecipe buildRecipe(Object... objects)
	{
		List<Object> itemIngredients = new ArrayList();
		List<Object> itemProducts = new ArrayList<>();
		List<Object> fluidIngredients = new ArrayList();
		List<Object> fluidProducts = new ArrayList();
		List<Object> particleIngredients = new ArrayList();
		List<Object> particleProducts = new ArrayList();
		List<Object> extras = new ArrayList();

		for (int i = 0; i < objects.length; ++i)
		{
			Object object = objects[i];
			if (i < this.itemInputLastIndex)
			{
				itemIngredients.add(object);
			}
			else if (i < this.fluidInputLastIndex)
			{
				fluidIngredients.add(object);
			}
			else if (i < this.particleInputLastIndex)
			{
				particleIngredients.add(object);
			}
			else if (i < this.itemOutputLastIndex)
			{
				itemProducts.add(object);
			}
			else if (i < this.fluidOutputLastIndex)
			{
				fluidProducts.add(object);
			}
			else if (i < this.particleOutputLastIndex)
			{
				particleProducts.add(object);
			}
			else
			{
				extras.add(object);
			}
		}
		return this.buildRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts, extras, this.isShapeless);
	}

	protected static <T, V extends IIngredient<T>> V buildIngredientInternal(Object obj, Predicate<Object> isValidType, Function<Object, V> buildIngredient)
	{
		if (obj != null && isValidType.test(obj))
		{
			V ingredient = (V) buildIngredient.apply(obj);
			if (ingredient != null)
			{
				return ingredient;
			}
		}

		return null;
	}

	protected static IItemIngredient buildItemInputIngredientInternal(Object obj)
	{
		return (IItemIngredient) buildIngredientInternal(obj, AbstractQMDRecipeHandler::isValidItemInputType, QMDRecipeHelper::buildItemIngredient);
	}

	protected static IFluidIngredient buildFluidInputIngredientInternal(Object obj)
	{
		return (IFluidIngredient) buildIngredientInternal(obj, AbstractQMDRecipeHandler::isValidFluidInputType, QMDRecipeHelper::buildFluidIngredient);
	}

	protected static IParticleIngredient buildParticleInputIngredientInternal(Object obj)
	{
		return (IParticleIngredient) buildIngredientInternal(obj, AbstractQMDRecipeHandler::isValidParticleInputType, QMDRecipeHelper::buildParticleIngredient);
	}

	protected static IItemIngredient buildItemOutputIngredientInternal(Object obj)
	{
		return (IItemIngredient) buildIngredientInternal(obj, AbstractQMDRecipeHandler::isValidItemOutputType, QMDRecipeHelper::buildItemIngredient);
	}

	protected static IFluidIngredient buildFluidOutputIngredientInternal(Object obj)
	{
		return (IFluidIngredient) buildIngredientInternal(obj, AbstractQMDRecipeHandler::isValidFluidOutputType, QMDRecipeHelper::buildFluidIngredient);
	}

	protected static IParticleIngredient buildParticleOutputIngredientInternal(Object obj)
	{
		return (IParticleIngredient) buildIngredientInternal(obj, AbstractQMDRecipeHandler::isValidParticleOutputType, QMDRecipeHelper::buildParticleIngredient);
	}

	@Nullable
	public QMDRecipe buildRecipe(List<?> itemInputs, List<?> fluidInputs, List<?> particleInputs, List<?> itemOutputs, List<?> fluidOutputs, List<?> particleOutputs, List<Object> extras, boolean shapeless)
	{
		List<IItemIngredient> itemIngredients = new ArrayList<>(), itemProducts = new ArrayList<>();
		List<IFluidIngredient> fluidIngredients = new ArrayList<>(), fluidProducts = new ArrayList<>();
		List<IParticleIngredient> particleIngredients = new ArrayList<>(), particleProducts = new ArrayList<>();
		for (Object obj : itemInputs)
		{
			IItemIngredient input = buildItemInputIngredientInternal(obj);
			if (input == null)
			{
				return null;
			}
			itemIngredients.add(input);
		}
		for (Object obj : fluidInputs)
		{
			IFluidIngredient input = buildFluidInputIngredientInternal(obj);
			if (input == null)
			{
				return null;
			}
			fluidIngredients.add(input);
		}
		for (Object obj : particleInputs)
		{
			IParticleIngredient input = buildParticleInputIngredientInternal(obj);
			if (input == null)
			{
				return null;
			}
			particleIngredients.add(input);
		}
		for (Object obj : itemOutputs)
		{
			IItemIngredient output = buildItemOutputIngredientInternal(obj);
			if (output == null)
			{
				return null;
			}
			itemProducts.add(output);
		}
		for (Object obj : fluidOutputs)
		{
			IFluidIngredient output = buildFluidOutputIngredientInternal(obj);
			if (output == null)
			{
				return null;
			}
			fluidProducts.add(output);
		}
		for (Object obj : particleOutputs)
		{
			IParticleIngredient output = buildParticleOutputIngredientInternal(obj);
			if (output == null)
			{
				return null;
			}
			particleProducts.add(output);
		}
		if (!isValidRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts))
		{
			Util.getLogger().info(name + " - a recipe failed to be registered: " + QMDRecipeHelper.getRecipeString(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts));
		}
		return newRecipe(itemIngredients, fluidIngredients, particleIngredients, itemProducts, fluidProducts, particleProducts, fixedExtras(extras), shapeless);
	}

	public boolean isValidRecipe(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients, List<IParticleIngredient> particleIngredients, List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts, List<IParticleIngredient> particleProducts)
	{
		return itemIngredients.size() == itemInputSize && fluidIngredients.size() == fluidInputSize && particleIngredients.size() == particleInputSize && itemProducts.size() == itemOutputSize && fluidProducts.size() == fluidOutputSize && particleProducts.size() == particleOutputSize;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@ZenMethod
	public static QMDRecipeHandler get(String name) {
		return QMDRecipes.getHandler(name);
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

	@ZenMethod("addRecipe")
	@Method(modid = "crafttweaker")
	public void ctAddRecipe(Object... objects)
	{
		CraftTweakerAPI.apply(new CTAddQMDRecipe(this, Arrays.asList(objects)));
	}

	@ZenMethod("removeRecipeWithInput")
	@Method(modid = "crafttweaker")
	public void ctRemoveRecipeWithInput(crafttweaker.api.item.IIngredient... ctIngredients)
	{CraftTweakerAPI.apply(new CTRemoveQMDRecipe(this, IngredientSorption.INPUT, Arrays.asList(ctIngredients)));}

	@ZenMethod("removeRecipeWithOutput")
	@Method(modid = "crafttweaker")
	public void ctRemoveRecipeWithOutput(crafttweaker.api.item.IIngredient... ctIngredients)
	{CraftTweakerAPI.apply(new CTRemoveQMDRecipe(this, IngredientSorption.OUTPUT, Arrays.asList(ctIngredients)));}

	@ZenMethod("removeAllRecipes")
	@Method(modid = "crafttweaker")
	public void ctRemoveAllRecipes()
	{
		CraftTweakerAPI.apply(new CTRemoveAllQMDRecipes(this));
	}

	protected void setValidFluids()
	{
		this.validFluids.clear();
		this.validFluids.addAll(QMDRecipeHelper.validFluids(this));
	}

	public void postInit()
	{
		super.postInit();
		setValidFluids();
	}

	@Override
	protected void fillHashCache()
	{
		for (QMDRecipe recipe : recipeList)
		{
			List<Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>>> materialListTuples = new ArrayList();

			if (!prepareMaterialListTuples(recipe, materialListTuples))
			{
				continue;
			}

			for (Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>> materials : materialListTuples)
			{
				if (isShapeless)
				{
					for (List<ItemStack> items : PermutationHelper.permutations(materials.getLeft()))
					{
						for (List<FluidStack> fluids : PermutationHelper.permutations(materials.getMiddle()))
						{
							for (List<ParticleStack> particles : PermutationHelper.permutations(materials.getRight()))
							{
								addToHashCache(recipe, items, fluids, particles);
							}
						}
					}
				}
				else
				{
					addToHashCache(recipe, materials.getLeft(), materials.getMiddle(), materials.getRight());
				}
			}
		}
	}

	protected void addToHashCache(QMDRecipe recipe, List<ItemStack> items, List<FluidStack> fluids, List<ParticleStack> particles)
	{
		long hash = QMDRecipeHelper.hashMaterials(items, fluids, particles);
		if (recipeCache.containsKey(hash))
		{
			recipeCache.get(hash).add(recipe);
		}
		else
		{
			ObjectSet<QMDRecipe> set = new ObjectOpenHashSet<>();
			set.add(recipe);
			recipeCache.put(hash, set);
		}
	}

	protected <T, V extends IIngredient<T>> boolean isValidInput(T stack, int index, Function<QMDRecipe, List<V>> ingredientsFunction)
	{
		for (QMDRecipe recipe : recipeList)
		{
			if (isShapeless)
			{
				for (V input : ingredientsFunction.apply(recipe))
				{
					if (input.match(stack, IngredientSorption.NEUTRAL).matches())
					{
						return true;
					}
				}
			}
			else
			{
				if (ingredientsFunction.apply(recipe).get(index).match(stack, IngredientSorption.NEUTRAL).matches())
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidItemInput(ItemStack stack, int slot)
	{
		return isValidInput(stack, slot, QMDRecipe::getItemIngredients);
	}




	public boolean isValidFluidInput(FluidStack stack, int tankNumber)
	{
		return isValidInput(stack, tankNumber, QMDRecipe::getFluidIngredients);
	}

	public boolean isValidParticleInput(ParticleStack stack, int slot)
	{
		return isValidInput(stack, slot, QMDRecipe::getParticleIngredients);
	}

	/**
	 * Smart insertion - don't insert if stack is not valid for any possible recipes
	 */
	public <T, U, V extends IIngredient<T>, W extends IIngredient<U>> boolean isValidInput(T stack, int index, List<T> inputs, List<U> associatedInputs, QMDRecipeInfo<QMDRecipe> recipeInfo, int inputSize, int associatedInputSize, Predicate<T> isEmptyFunction, Predicate<U> associatedIsEmptyFunction, Predicate<T> isEqualFunction, Function<QMDRecipe, List<V>> ingredientsFunction, Function<QMDRecipe, List<W>> associatedIngredientsFunction)
	{
		List<T> otherInputs = inputsExcludingIndex(inputs, index);
		if ((otherInputs.stream().allMatch(isEmptyFunction) && associatedInputs.stream().allMatch(associatedIsEmptyFunction)) || isEqualFunction.test(inputs.get(index)))
		{
			return isValidInput(stack, index, ingredientsFunction);
		}

		if (recipeInfo == null)
		{
			ObjectSet<QMDRecipe> recipes = new ObjectOpenHashSet<>(recipeList);
			recipeLoop:
			for (QMDRecipe recipe : recipeList)
			{
				List<V> ingredients = ingredientsFunction.apply(recipe);
				List<W> associatedIngredients = associatedIngredientsFunction.apply(recipe);
				if (isShapeless)
				{
					stackLoop:
					for (T inputStack : inputs)
					{
						if (!isEmptyFunction.test(inputStack))
						{
							for (V recipeInput : ingredients)
							{
								if (recipeInput.match(inputStack, IngredientSorption.NEUTRAL).matches())
								{
									continue stackLoop;
								}
							}
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}

					associatedStackLoop:
					for (U inputStack : associatedInputs)
					{
						if (!associatedIsEmptyFunction.test(inputStack))
						{
							for (W recipeInput : associatedIngredients)
							{
								if (recipeInput.match(inputStack, IngredientSorption.NEUTRAL).matches())
								{
									continue associatedStackLoop;
								}
							}
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}
				}
				else
				{
					for (int i = 0; i < inputSize; ++i)
					{
						T inputStack = inputs.get(i);
						if (!isEmptyFunction.test(inputStack) && !ingredients.get(i).match(inputStack, IngredientSorption.NEUTRAL).matches())
						{
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}

					for (int i = 0; i < associatedInputSize; ++i)
					{
						U inputStack = associatedInputs.get(i);
						if (!associatedIsEmptyFunction.test(inputStack) && !associatedIngredients.get(i).match(inputStack, IngredientSorption.NEUTRAL).matches())
						{
							recipes.remove(recipe);
							continue recipeLoop;
						}
					}
				}
			}

			for (QMDRecipe recipe : recipes)
			{
				if (isValidInputInternal(stack, index, otherInputs, ingredientsFunction.apply(recipe), isEmptyFunction))
				{
					return true;
				}
			}
			return false;
		}
		else
		{
			return isValidInputInternal(stack, index, otherInputs, ingredientsFunction.apply(recipeInfo.recipe), isEmptyFunction);
		}
	}

	protected <T, V extends IIngredient<T>> boolean isValidInputInternal(T stack, int index, List<T> otherInputs, List<V> ingredients, Predicate<T> isEmptyFunction)
	{
		if (isShapeless)
		{
			for (V input : ingredients)
			{
				if (input.match(stack, IngredientSorption.NEUTRAL).matches())
				{
					for (T other : otherInputs)
					{
						if (!isEmptyFunction.test(other) && input.match(other, IngredientSorption.NEUTRAL).matches())
						{
							return false;
						}
					}
					return true;
				}
			}
			return false;
		}
		else
		{
			return ingredients.get(index).match(stack, IngredientSorption.NEUTRAL).matches();
		}
	}

	protected static <T> List<T> inputsExcludingIndex(List<T> inputs, int index)
	{
		List<T> inputsExcludingIndex = new ArrayList<>(inputs);
		inputsExcludingIndex.remove(index);
		return inputsExcludingIndex;
	}



}
