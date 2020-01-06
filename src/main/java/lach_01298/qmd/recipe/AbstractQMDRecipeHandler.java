package lach_01298.qmd.recipe;

import static nc.util.PermutationHelper.permutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipe.ingredient.ParticleIngredient;
import nc.recipe.IngredientSorption;
import nc.recipe.RecipeTupleGenerator;
import nc.recipe.ingredient.ChanceFluidIngredient;
import nc.recipe.ingredient.ChanceItemIngredient;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.recipe.ingredient.ItemIngredient;
import nc.recipe.ingredient.OreIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.FluidRegHelper;
import nc.util.ItemStackHelper;
import nc.util.OreDictHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class AbstractQMDRecipeHandler<T extends IQMDRecipe> {
	
	private List<T> recipeList = new ArrayList<>();
	
	private Long2ObjectMap<T> recipeCache = new Long2ObjectOpenHashMap<>();
	
	private static List<Class<?>> validItemInputs = Lists.newArrayList(IItemIngredient.class, ArrayList.class, String.class, Item.class, Block.class, ItemStack.class, ItemStack[].class);
	private static List<Class<?>> validFluidInputs = Lists.newArrayList(IFluidIngredient.class, ArrayList.class, String.class, Fluid.class, FluidStack.class, FluidStack[].class);
	private static List<Class<?>> validParticleInputs = Lists.newArrayList(IParticleIngredient.class, ArrayList.class, String.class, Particle.class, ParticleStack.class, ParticleStack[].class);
	private static List<Class<?>> validItemOutputs = Lists.newArrayList(IItemIngredient.class, String.class, Item.class, Block.class, ItemStack.class);
	private static List<Class<?>> validFluidOutputs = Lists.newArrayList(IFluidIngredient.class, String.class, Fluid.class, FluidStack.class);
	private static List<Class<?>> validParticleOutputs = Lists.newArrayList(IParticleIngredient.class, String.class, Particle.class, ParticleStack.class);
	
	private static List<Class<?>> needItemAltering = Lists.newArrayList(Item.class, Block.class);
	private static List<Class<?>> needFluidAltering = Lists.newArrayList(Fluid.class);
	private static List<Class<?>> needParticleAltering = Lists.newArrayList(Particle.class);
	
	public static final List<Integer> INVALID = Lists.newArrayList(-1);
	
	public abstract void addRecipes();
	
	public abstract String getRecipeName();

	public List<T> getRecipeList()
	{
		return recipeList;
	}

	public abstract void addRecipe(Object... objects);

	public @Nullable QMDRecipeInfo<T> getRecipeInfoFromInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs,
			List<ParticleStack> particleInputs)
	{
		T recipe = recipeCache.get(hashMaterialsRaw(itemInputs, fluidInputs,particleInputs));
		if (recipe != null)
		{
			QMDRecipeMatchResult matchResult = recipe.matchInputs(itemInputs, fluidInputs,particleInputs);
			if (matchResult.matches())
				return new QMDRecipeInfo(recipe, matchResult);
		}
		return null;
	}
	
	public @Nullable T getRecipeFromIngredients(List<IItemIngredient> itemIngredients,
			List<IFluidIngredient> fluidIngredients, List<IParticleIngredient> particleIngredients)
	{
		for (T recipe : recipeList)
		{
			if (recipe.matchIngredients(itemIngredients, fluidIngredients, particleIngredients).matches())
				return recipe;
		}
		return null;
	}

	public @Nullable T getRecipeFromProducts(List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts,
			List<IParticleIngredient> particleProducts)
	{
		for (T recipe : recipeList)
		{
			if (recipe.matchProducts(itemProducts, fluidProducts, particleProducts).matches())
				return recipe;
		}
		return null;
	}
	
	public boolean addRecipe(T recipe)
	{
		return recipe != null ? recipeList.add(recipe) : false;
	}

	public boolean removeRecipe(T recipe)
	{
		return recipe != null ? recipeList.remove(recipe) : false;
	}

	public void removeAllRecipes()
	{
		recipeList.clear();
		recipeCache.clear();
	}

	public void refreshCache()
	{
		recipeCache.clear();

		recipeLoop: for (T recipe : recipeList)
		{
			List<List<ItemStack>> itemInputLists = new ArrayList<>();
			List<List<FluidStack>> fluidInputLists = new ArrayList<>();
			List<List<ParticleStack>> particleInputLists = new ArrayList<>();

			for (IItemIngredient item : recipe.itemIngredients())
				itemInputLists.add(item.getInputStackHashingList());
			for (IFluidIngredient fluid : recipe.fluidIngredients())
				fluidInputLists.add(fluid.getInputStackHashingList());
			for (IParticleIngredient particle : recipe.particleIngredients())
				particleInputLists.add(particle.getInputStackHashingList());

			int arrSize = recipe.itemIngredients().size() + recipe.fluidIngredients().size()
					+ recipe.particleIngredients().size();
			int[] inputNumbers = new int[arrSize];
			Arrays.fill(inputNumbers, 0);

			int[] maxNumbers = new int[arrSize];
			for (int i = 0; i < itemInputLists.size(); i++)
			{
				int maxNumber = itemInputLists.get(i).size() - 1;
				if (maxNumber < 0)
					continue recipeLoop;
				maxNumbers[i] = maxNumber;
			}
			for (int i = 0; i < fluidInputLists.size(); i++)
			{
				int maxNumber = fluidInputLists.get(i).size() - 1;
				if (maxNumber < 0)
					continue recipeLoop;
				maxNumbers[i + itemInputLists.size()] = maxNumber;
			}
			for (int i = 0; i < particleInputLists.size(); i++)
			{
				int maxNumber = particleInputLists.get(i).size() - 1;
				if (maxNumber < 0)
					continue recipeLoop;
				maxNumbers[i + itemInputLists.size() + fluidInputLists.size()] = maxNumber;
			}

			List<Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>>> materialListTuples = new ArrayList<>();

			QMDRecipeTupleGenerator.INSTANCE.generateMaterialListTuples(materialListTuples, maxNumbers, inputNumbers,
					itemInputLists, fluidInputLists, particleInputLists);

			for (Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>> materials : materialListTuples)
			{
				for (List<ItemStack> items : permutations(materials.getLeft()))
				{
					for (List<FluidStack> fluids : permutations(materials.getMiddle()))
					{
						for (List<ParticleStack> particles : permutations(materials.getRight()))
						{
							recipeCache.put(hashMaterials(items, fluids, particles), recipe);
						}
					}
				}
			}
		}
	}

	private static long hashMaterialsRaw(List<ItemStack> items, List<Tank> fluids, List<ParticleStack> particles)
	{
		long hash = 1L;
		Iterator<ItemStack> itemIter = items.iterator();
		while (itemIter.hasNext())
		{
			ItemStack stack = itemIter.next();
			hash = 31L * hash + (stack == null ? 0L : RecipeItemHelper.pack(stack));
		}
		Iterator<Tank> fluidIter = fluids.iterator();
		while (fluidIter.hasNext())
		{
			Tank tank = fluidIter.next();
			hash = 31L * hash + (tank == null ? 0L
					: tank.getFluid() == null ? 0L : tank.getFluid().getFluid().getName().hashCode());
		}
		Iterator<ParticleStack> particleIter = particles.iterator();
		while (particleIter.hasNext())
		{
			ParticleStack stack = particleIter.next();
			hash = 31L * hash
					+ (stack == null ? 0L : stack.getParticle() == null ? 0L : stack.getParticle().getName().hashCode());
		}
		return hash;
	}

	private static long hashMaterials(List<ItemStack> items, List<FluidStack> fluids, List<ParticleStack> particles)
	{
		long hash = 1L;
		Iterator<ItemStack> itemIter = items.iterator();
		while (itemIter.hasNext())
		{
			ItemStack stack = itemIter.next();
			hash = 31L * hash + (stack == null ? 0L : RecipeItemHelper.pack(stack));
		}
		Iterator<FluidStack> fluidIter = fluids.iterator();
		while (fluidIter.hasNext())
		{
			FluidStack stack = fluidIter.next();
			hash = 31L * hash + (stack == null ? 0L : stack.getFluid().getName().hashCode());
		}
		Iterator<ParticleStack> particleIter = particles.iterator();
		while (particleIter.hasNext())
		{
			ParticleStack particleStack = particleIter.next();
			hash = 31L * hash
					+ (particleStack == null ? 0L : particleStack.getParticle() == null ? 0L : particleStack.getParticle().getName().hashCode());
		}
		return hash;
	}

	public static void addValidItemInput(Class itemInputType)
	{
		validItemInputs.add(itemInputType);
	}

	public static void addValidFluidInput(Class fluidInputType)
	{
		validFluidInputs.add(fluidInputType);
	}

	public static void addValidParticleInput(Class particleInputType)
	{
		validParticleInputs.add(particleInputType);
	}

	public static void addValidItemOutput(Class itemOutputType)
	{
		validItemOutputs.add(itemOutputType);
	}

	public static void addValidFluidOutput(Class fluidOutputType)
	{
		validFluidOutputs.add(fluidOutputType);
	}

	public static void addValidParticleOutput(Class particleOutputType)
	{
		validParticleOutputs.add(particleOutputType);
	}

	public static boolean isValidItemInputType(Object itemInput)
	{
		for (Class<?> itemInputType : validItemInputs)
		{
			if (itemInput instanceof ArrayList && itemInputType == ArrayList.class)
			{
				ArrayList list = (ArrayList) itemInput;
				for (Object obj : list)
					if (isValidItemInputType(obj))
						return true;
			}
			else if (itemInputType.isInstance(itemInput))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isValidFluidInputType(Object fluidInput)
	{
		for (Class<?> fluidInputType : validFluidInputs)
		{
			if (fluidInput instanceof ArrayList && fluidInputType == ArrayList.class)
			{
				ArrayList list = (ArrayList) fluidInput;
				for (Object obj : list)
					if (isValidFluidInputType(obj))
						return true;
			}
			else if (fluidInputType.isInstance(fluidInput))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isValidParticleInputType(Object particleInput)
	{
		for (Class<?> particleInputType : validParticleInputs)
		{
			if (particleInput instanceof ArrayList && particleInputType == ArrayList.class)
			{
				ArrayList list = (ArrayList) particleInput;
				for (Object obj : list)
					if (isValidParticleInputType(obj))
						return true;
			}
			else if (particleInputType.isInstance(particleInput))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isValidItemOutputType(Object itemOutput)
	{
		for (Class<?> itemOutputType : validItemOutputs)
		{
			if (itemOutputType.isInstance(itemOutput))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isValidFluidOutputType(Object fluidOutput)
	{
		for (Class<?> fluidOutputType : validFluidOutputs)
		{
			if (fluidOutputType.isInstance(fluidOutput))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean isValidParticleOutputType(Object particleOutput)
	{
		for (Class<?> particleOutputType : validParticleOutputs)
		{
			if (particleOutputType.isInstance(particleOutput))
			{
				return true;
			}
		}
		return false;
	}

	public static boolean requiresItemFixing(Object object)
	{
		for (Class<?> objectType : needItemAltering)
		{
			if (objectType.isInstance(object))
				return true;
		}
		return false;
	}

	public static boolean requiresFluidFixing(Object object)
	{
		for (Class<?> objectType : needFluidAltering)
		{
			if (objectType.isInstance(object))
				return true;
		}
		return false;
	}

	public static boolean requiresParticleFixing(Object object)
	{
		for (Class<?> objectType : needParticleAltering)
		{
			if (objectType.isInstance(object))
				return true;
		}
		return false;
	}

	public boolean isValidItemInput(ItemStack stack)
	{
		for (T recipe : recipeList)
		{
			for (IItemIngredient input : recipe.itemIngredients())
			{
				if (input.match(stack, IngredientSorption.NEUTRAL).matches())
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidFluidInput(FluidStack stack)
	{
		for (T recipe : recipeList)
		{
			for (IFluidIngredient input : recipe.fluidIngredients())
			{
				if (input.match(stack, IngredientSorption.NEUTRAL).matches())
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidParticleInput(ParticleStack stack)
	{
		for (T recipe : recipeList)
		{
			for (IParticleIngredient input : recipe.particleIngredients())
			{
				if (input.match(stack, IngredientSorption.NEUTRAL).matches())
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidItemOutput(ItemStack stack)
	{
		for (T recipe : recipeList)
		{
			for (IItemIngredient output : recipe.itemProducts())
			{
				if (output.match(stack, IngredientSorption.OUTPUT).matches())
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidFluidOutput(FluidStack stack)
	{
		for (T recipe : recipeList)
		{
			for (IFluidIngredient output : recipe.fluidProducts())
			{
				if (output.match(stack, IngredientSorption.OUTPUT).matches())
				{
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidParticleOutput(ParticleStack stack)
	{
		for (T recipe : recipeList)
		{
			for (IParticleIngredient output : recipe.particleProducts())
			{
				if (output.match(stack, IngredientSorption.OUTPUT).matches())
				{
					return true;
				}
			}
		}
		return false;
	}

	/** Smart item insertion */
	public boolean isValidItemInput(ItemStack stack, ItemStack slotStack, List<ItemStack> otherInputs)
	{
		if (otherInputs.isEmpty()
				|| (stack.isItemEqual(slotStack) && ItemStackHelper.areItemStackTagsEqual(stack, slotStack)))
		{
			return isValidItemInput(stack);
		}

		List<ItemStack> otherStacks = new ArrayList<>();
		for (ItemStack otherInput : otherInputs)
		{
			if (!otherInput.isEmpty())
				otherStacks.add(otherInput);
		}
		if (otherStacks.isEmpty())
			return isValidItemInput(stack);

		List<ItemStack> allStacks = Lists.newArrayList(stack);
		allStacks.addAll(otherStacks);

		List<T> recipeList = new ArrayList(this.recipeList);
		recipeLoop: for (T recipe : this.recipeList)
		{
			objLoop: for (ItemStack obj : allStacks)
			{
				for (IItemIngredient input : recipe.itemIngredients())
				{
					if (input.match(obj, IngredientSorption.NEUTRAL).matches())
						continue objLoop;
				}
				recipeList.remove(recipe);
				continue recipeLoop;
			}
		}

		for (T recipe : recipeList)
		{
			for (IItemIngredient input : recipe.itemIngredients())
			{
				if (input.match(stack, IngredientSorption.NEUTRAL).matches())
				{
					for (ItemStack other : otherStacks)
					{
						if (input.match(other, IngredientSorption.NEUTRAL).matches())
							return false;
					}
					return true;
				}
			}
		}

		return false;
	}

	// Stacks

	public static OreIngredient oreStack(String oreType, int stackSize)
	{
		if (!OreDictHelper.oreExists(oreType))
			return null;
		return new OreIngredient(oreType, stackSize);
	}

	public static FluidIngredient fluidStack(String fluidName, int stackSize)
	{
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new FluidIngredient(fluidName, stackSize);
	}

	public static ParticleIngredient particleStack(String particleName, int meanEnergy, int amount, double spread)
	{
		if (Particles.getParticleFromName(particleName) == null)
			return null;
		return new ParticleIngredient(particleName, meanEnergy, amount, spread);
	}

	public static List<OreIngredient> oreStackList(List<String> oreTypes, int stackSize)
	{
		List<OreIngredient> oreStackList = new ArrayList<>();
		for (String oreType : oreTypes)
			if (oreStack(oreType, stackSize) != null)
				oreStackList.add(oreStack(oreType, stackSize));
		return oreStackList;
	}

	public static List<FluidIngredient> fluidStackList(List<String> fluidNames, int stackSize)
	{
		List<FluidIngredient> fluidStackList = new ArrayList<>();
		for (String fluidName : fluidNames)
			if (fluidStack(fluidName, stackSize) != null)
				fluidStackList.add(fluidStack(fluidName, stackSize));
		return fluidStackList;
	}

	public static EmptyItemIngredient emptyItemStack()
	{
		return new EmptyItemIngredient();
	}

	public static EmptyFluidIngredient emptyFluidStack()
	{
		return new EmptyFluidIngredient();
	}

	public static ChanceItemIngredient chanceItemStack(ItemStack stack, int chancePercent)
	{
		if (stack == null)
			return null;
		return new ChanceItemIngredient(new ItemIngredient(stack), chancePercent);
	}

	public static ChanceItemIngredient chanceItemStack(ItemStack stack, int chancePercent, int minStackSize)
	{
		if (stack == null)
			return null;
		return new ChanceItemIngredient(new ItemIngredient(stack), chancePercent, minStackSize);
	}

	public static ChanceItemIngredient chanceOreStack(String oreType, int stackSize, int chancePercent)
	{
		if (!OreDictHelper.oreExists(oreType))
			return null;
		return new ChanceItemIngredient(oreStack(oreType, stackSize), chancePercent);
	}

	public static ChanceItemIngredient chanceOreStack(String oreType, int stackSize, int chancePercent,
			int minStackSize)
	{
		if (!OreDictHelper.oreExists(oreType))
			return null;
		return new ChanceItemIngredient(oreStack(oreType, stackSize), chancePercent, minStackSize);
	}

	public static ChanceFluidIngredient chanceFluidStack(String fluidName, int stackSize, int chancePercent,
			int stackDiff)
	{
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new ChanceFluidIngredient(fluidStack(fluidName, stackSize), chancePercent, stackDiff);
	}

	public static ChanceFluidIngredient chanceFluidStack(String fluidName, int stackSize, int chancePercent,
			int stackDiff, int minStackSize)
	{
		if (!FluidRegHelper.fluidExists(fluidName))
			return null;
		return new ChanceFluidIngredient(fluidStack(fluidName, stackSize), chancePercent, stackDiff, minStackSize);
	}

	public static List<ChanceItemIngredient> chanceOreStackList(List<String> oreTypes, int stackSize, int chancePercent)
	{
		List<ChanceItemIngredient> oreStackList = new ArrayList<>();
		for (String oreType : oreTypes)
			if (chanceOreStack(oreType, stackSize, chancePercent) != null)
				oreStackList.add(chanceOreStack(oreType, stackSize, chancePercent));
		return oreStackList;
	}

	public static List<ChanceItemIngredient> chanceOreStackList(List<String> oreTypes, int stackSize, int chancePercent,
			int minStackSize)
	{
		List<ChanceItemIngredient> oreStackList = new ArrayList<>();
		for (String oreType : oreTypes)
			if (chanceOreStack(oreType, stackSize, chancePercent, minStackSize) != null)
				oreStackList.add(chanceOreStack(oreType, stackSize, chancePercent, minStackSize));
		return oreStackList;
	}

	public static List<ChanceFluidIngredient> chanceFluidStackList(List<String> fluidNames, int stackSize,
			int chancePercent, int stackDiff)
	{
		List<ChanceFluidIngredient> fluidStackList = new ArrayList<>();
		for (String fluidName : fluidNames)
			if (chanceFluidStack(fluidName, stackSize, chancePercent, stackDiff) != null)
				fluidStackList.add(chanceFluidStack(fluidName, stackSize, chancePercent, stackDiff));
		return fluidStackList;
	}

	public static List<ChanceFluidIngredient> chanceFluidStackList(List<String> fluidNames, int stackSize,
			int chancePercent, int stackDiff, int minStackSize)
	{
		List<ChanceFluidIngredient> fluidStackList = new ArrayList<>();
		for (String fluidName : fluidNames)
			if (chanceFluidStack(fluidName, stackSize, chancePercent, stackDiff, minStackSize) != null)
				fluidStackList.add(chanceFluidStack(fluidName, stackSize, chancePercent, stackDiff, minStackSize));
		return fluidStackList;
	}
}
