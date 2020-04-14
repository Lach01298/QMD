package lach_01298.qmd.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import com.google.common.collect.Lists;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipe.ingredient.ParticleArrayIngredient;
import lach_01298.qmd.recipe.ingredient.ParticleIngredient;
import nc.ModCheck;
import nc.recipe.IngredientMatchResult;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.ChanceFluidIngredient;
import nc.recipe.ingredient.ChanceItemIngredient;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.recipe.ingredient.FluidArrayIngredient;
import nc.recipe.ingredient.FluidIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.recipe.ingredient.ItemArrayIngredient;
import nc.recipe.ingredient.ItemIngredient;
import nc.recipe.ingredient.OreIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.CollectionHelper;
import nc.util.FluidRegHelper;
import nc.util.GasHelper;
import nc.util.OreDictHelper;
import nc.util.StringHelper;
import net.minecraft.block.Block;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class QMDRecipeHelper
{

	public static boolean containsItemIngredient(List<IItemIngredient> list, IItemIngredient ingredient)
	{
		for (IItemIngredient i : list)
		{
			if (i == null)
				continue;
			if (i.match(ingredient, IngredientSorption.NEUTRAL).matches())
				return true;
		}
		return false;
	}

	public static boolean containsFluidIngredient(List<IFluidIngredient> list, IFluidIngredient ingredient)
	{
		for (IFluidIngredient i : list)
		{
			if (i == null)
				continue;
			if (i.match(ingredient, IngredientSorption.NEUTRAL).matches())
				return true;
		}
		return false;
	}

	public static boolean containsParticleIngredient(List<IParticleIngredient> list, IParticleIngredient ingredient)
	{
		for (IParticleIngredient i : list)
		{
			if (i == null)
				continue;
			if (i.match(ingredient, IngredientSorption.NEUTRAL).matches())
				return true;
		}
		return false;
	}

	public static ItemStack fixItemStack(Object object)
	{
		if (object == null)
			return null;

		else if (object instanceof ItemStack)
		{
			ItemStack stack = ((ItemStack) object).copy();
			if (stack.getCount() <= 0)
			{
				stack.setCount(1);
			}
			return stack;
		}
		else if (object instanceof Item)
		{
			return new ItemStack((Item) object, 1);
		}
		else
		{
			if (!(object instanceof Block))
			{
				throw new RuntimeException(String.format("Invalid ItemStack: %s", object));
			}
			return new ItemStack((Block) object, 1);
		}
	}

	public static FluidStack fixFluidStack(Object object)
	{
		if (object == null)
			return null;

		else if (object instanceof FluidStack)
		{
			FluidStack fluidstack = ((FluidStack) object).copy();
			if (fluidstack.amount <= 0)
			{
				fluidstack.amount = 1000;
			}
			return fluidstack;
		}
		else
		{
			if (!(object instanceof Fluid))
			{
				throw new RuntimeException(String.format("Invalid FluidStack: %s", object));
			}
			return new FluidStack((Fluid) object, 1000);
		}
	}

	public static ParticleStack fixParticleStack(Object object)
	{
		if (object == null)
			return null;

		else if (object instanceof ParticleStack)
		{
			ParticleStack stack = ((ParticleStack) object).copy();
			if (stack.getAmount() <= 0)
			{
				stack.setAmount(1);
			}
			return stack;
		}
		else
		{
			if (!(object instanceof Particle))
			{
				throw new RuntimeException(String.format("Invalid ParticleStack: %s", object));
			}
			return new ParticleStack((Particle) object, 0, 1,0);
		}
	}

	public static OreIngredient oreStackFromString(String name)
	{
		if (OreDictHelper.oreExists(name))
			return new OreIngredient(name, 1);
		return null;
	}

	public static FluidIngredient fluidStackFromString(String name)
	{
		if (FluidRegHelper.fluidExists(name))
			return new FluidIngredient(name, 1000);
		return null;
	}

	public static ParticleIngredient particleStackFromString(String name)
	{
		if (Particles.getParticleFromName(name) != null)
			return new ParticleIngredient(name, 0, 1, 0);
		return null;
	}

	public static List<List<ItemStack>> getItemInputLists(List<IItemIngredient> ingredientList)
	{
		List<List<ItemStack>> values = new ArrayList<List<ItemStack>>();
		ingredientList.forEach(ingredient -> values.add(ingredient.getInputStackList()));
		return values;
	}

	public static List<List<FluidStack>> getFluidInputLists(List<IFluidIngredient> ingredientList)
	{
		List<List<FluidStack>> values = new ArrayList<List<FluidStack>>();
		ingredientList.forEach(ingredient -> values.add(ingredient.getInputStackList()));
		return values;
	}

	public static List<List<ParticleStack>> getParticleInputLists(List<IParticleIngredient> ingredientList)
	{
		List<List<ParticleStack>> values = new ArrayList<List<ParticleStack>>();
		ingredientList.forEach(ingredient -> values.add(ingredient.getInputStackList()));
		return values;
	}

	public static List<List<ItemStack>> getItemOutputLists(List<IItemIngredient> ingredientList)
	{
		List<List<ItemStack>> values = new ArrayList<List<ItemStack>>();
		ingredientList.forEach(ingredient -> values.add(getItemOutputStackList(ingredient)));
		return values;
	}

	public static List<List<FluidStack>> getFluidOutputLists(List<IFluidIngredient> ingredientList)
	{
		List<List<FluidStack>> values = new ArrayList<List<FluidStack>>();
		ingredientList.forEach(ingredient -> values.add(getFluidOutputStackList(ingredient)));
		return values;
	}

	public static List<List<ParticleStack>> getParticleOutputLists(List<IParticleIngredient> ingredientList)
	{
		List<List<ParticleStack>> values = new ArrayList<List<ParticleStack>>();
		ingredientList.forEach(ingredient -> values.add(getParticleOutputStackList(ingredient)));
		return values;
	}

	public static List<ItemStack> getItemOutputStackList(IItemIngredient ingredient)
	{
		if (ingredient instanceof ChanceItemIngredient)
			return ingredient.getOutputStackList();
		else
			return Lists.newArrayList(ingredient.getStack());
	}

	public static List<FluidStack> getFluidOutputStackList(IFluidIngredient ingredient)
	{
		if (ingredient instanceof ChanceFluidIngredient)
			return ingredient.getOutputStackList();
		else
			return Lists.newArrayList(ingredient.getStack());
	}

	public static List<ParticleStack> getParticleOutputStackList(IParticleIngredient ingredient)
	{
		return Lists.newArrayList(ingredient.getStack());
	}

	@Nullable
	public static List<ItemStack> getItemOutputList(List<IItemIngredient> list)
	{
		if (list.contains(null))
			return new ArrayList<ItemStack>();
		List stacks = new ArrayList<ItemStack>();
		list.forEach(ingredient -> stacks.add(ingredient.getStack()));
		if (stacks.contains(null))
			return new ArrayList<ItemStack>();
		return stacks;
	}

	@Nullable
	public static List<FluidStack> getFluidOutputList(List<IFluidIngredient> list)
	{
		if (list.contains(null))
			return new ArrayList<FluidStack>();
		List stacks = new ArrayList<FluidStack>();
		list.forEach(ingredient -> stacks.add(ingredient.getStack()));
		if (stacks.contains(null))
			return new ArrayList<FluidStack>();
		return stacks;
	}

	@Nullable
	public static List<ParticleStack> getParticleOutputList(List<IParticleIngredient> list)
	{
		if (list.contains(null))
			return new ArrayList<ParticleStack>();
		List stacks = new ArrayList<ParticleStack>();
		list.forEach(ingredient -> stacks.add(ingredient.getStack()));
		if (stacks.contains(null))
			return new ArrayList<ParticleStack>();
		return stacks;
	}

	@Nullable
	public static ItemStack getItemStackFromIngredientList(List<IItemIngredient> list, int pos)
	{
		if (!list.isEmpty() && pos < list.size())
		{
			IItemIngredient object = list.get(pos);
			return object.getStack();
		}
		return null;
	}

	@Nullable
	public static FluidStack getFluidStackFromIngredientList(List<IFluidIngredient> list, int pos)
	{
		if (!list.isEmpty() && pos < list.size())
		{
			IFluidIngredient object = list.get(pos);
			return object.getStack();
		}
		return null;
	}

	@Nullable
	public static ParticleStack getParticleStackFromIngredientList(List<IParticleIngredient> list, int pos)
	{
		if (!list.isEmpty() && pos < list.size())
		{
			IParticleIngredient object = list.get(pos);
			return object.getStack();
		}
		return null;
	}

	@Nullable
	public static IItemIngredient buildItemIngredient(Object object)
	{
		if (AbstractQMDRecipeHandler.requiresItemFixing(object))
		{
			object = QMDRecipeHelper.fixItemStack(object);
		}
		if (object instanceof IItemIngredient)
		{
			return checkedItemIngredient((IItemIngredient) object);
		}
		else if (object instanceof List)
		{
			List list = (List) object;
			List<IItemIngredient> buildList = new ArrayList<IItemIngredient>();
			if (!list.isEmpty())
			{
				for (Object listObject : list)
				{
					if (listObject instanceof IItemIngredient)
					{
						buildList.add((IItemIngredient) listObject);
					}
					else if (listObject != null)
					{
						IItemIngredient recipeObject = checkedItemIngredient(buildItemIngredient(listObject));
						if (recipeObject != null)
						{
							buildList.add(recipeObject);
						}
					}
				}
				if (buildList.isEmpty())
					return null;
				return checkedItemIngredient(new ItemArrayIngredient(buildList));
			}
			else
			{
				return null;
			}
		}
		else if (object instanceof String)
		{
			return checkedItemIngredient(QMDRecipeHelper.oreStackFromString((String) object));
		}
		if (object instanceof ItemStack)
		{
			return checkedItemIngredient(new ItemIngredient((ItemStack) object));
		}
		return null;
	}

	@Nullable
	public static IItemIngredient checkedItemIngredient(IItemIngredient ingredient)
	{
		return ingredient == null || !ingredient.isValid() ? null : ingredient;
	}

	@Nullable
	public static IFluidIngredient buildFluidIngredient(Object object)
	{
		if (AbstractQMDRecipeHandler.requiresFluidFixing(object))
		{
			object = QMDRecipeHelper.fixFluidStack(object);
		}
		if (needsExpanding() && object instanceof FluidIngredient)
		{
			return checkedFluidIngredient(buildFluidIngredient(expandedFluidStackList((FluidIngredient) object)));
		}
		if (object instanceof IFluidIngredient)
		{
			return checkedFluidIngredient((IFluidIngredient) object);
		}
		else if (object instanceof List)
		{
			List list = (List) object;
			List<IFluidIngredient> buildList = new ArrayList<IFluidIngredient>();
			if (!list.isEmpty())
			{
				for (Object listObject : list)
				{
					if (listObject instanceof IFluidIngredient)
					{
						buildList.add((IFluidIngredient) listObject);
					}
					else if (listObject != null)
					{
						IFluidIngredient recipeObject = checkedFluidIngredient(buildFluidIngredient(listObject));
						if (recipeObject != null)
							buildList.add(recipeObject);
					}
				}
				if (buildList.isEmpty())
					return null;
				return checkedFluidIngredient(new FluidArrayIngredient(buildList));
			}
			else
			{
				return null;
			}
		}
		else if (object instanceof String)
		{
			return checkedFluidIngredient(QMDRecipeHelper.fluidStackFromString((String) object));
		}
		if (object instanceof FluidStack)
		{
			return checkedFluidIngredient(new FluidIngredient((FluidStack) object));
		}
		return null;
	}

	@Nullable
	public static IFluidIngredient checkedFluidIngredient(IFluidIngredient ingredient)
	{
		return ingredient == null || !ingredient.isValid() ? null : ingredient;
	}

	@Nullable
	public static IParticleIngredient buildParticleIngredient(Object object)
	{
		if (AbstractQMDRecipeHandler.requiresParticleFixing(object))
		{
			object = QMDRecipeHelper.fixParticleStack(object);
		}
		if (object instanceof IParticleIngredient)
		{
			return checkedParticleIngredient((IParticleIngredient) object);
		}
		else if (object instanceof List)
		{
			List list = (List) object;
			List<IParticleIngredient> buildList = new ArrayList<IParticleIngredient>();
			if (!list.isEmpty())
			{
				for (Object listObject : list)
				{
					if (listObject instanceof IParticleIngredient)
					{
						buildList.add((IParticleIngredient) listObject);
					}
					else if (listObject != null)
					{
						IParticleIngredient recipeObject = checkedParticleIngredient(
								buildParticleIngredient(listObject));
						if (recipeObject != null)
							buildList.add(recipeObject);
					}
				}
				if (buildList.isEmpty())
					return null;
				return checkedParticleIngredient(new ParticleArrayIngredient(buildList));
			}
			else
			{
				return null;
			}
		}
		else if (object instanceof String)
		{
			return checkedParticleIngredient(QMDRecipeHelper.particleStackFromString((String) object));
		}
		if (object instanceof ParticleStack)
		{
			return checkedParticleIngredient(new ParticleIngredient((ParticleStack) object));
		}
		return null;
	}

	@Nullable
	public static IParticleIngredient checkedParticleIngredient(IParticleIngredient ingredient)
	{
		return ingredient == null || !ingredient.isValid() ? null : ingredient;
	}

	public static boolean needsExpanding()
	{
		return ModCheck.mekanismLoaded() || ModCheck.techRebornLoaded();
	}

	/** For Mekanism and Tech Reborn fluids */
	public static List<FluidIngredient> expandedFluidStackList(FluidIngredient stack)
	{
		List<FluidIngredient> fluidStackList = Lists.newArrayList(stack);

		if (ModCheck.mekanismLoaded() && !stack.fluidName.equals("helium"))
		{
			if (GasHelper.TRANSLATION_MAP.containsKey(stack.fluidName))
			{
				fluidStackList.add(AbstractQMDRecipeHandler.fluidStack(GasHelper.TRANSLATION_MAP.get(stack.fluidName),
						stack.stack.amount));
			}
			else
			{
				fluidStackList.add(AbstractQMDRecipeHandler.fluidStack("liquid" + stack.fluidName, stack.stack.amount));
			}
		}

		if (ModCheck.techRebornLoaded())
		{
			fluidStackList.add(AbstractQMDRecipeHandler.fluidStack("fluid" + stack.fluidName, stack.stack.amount));
		}

		return fluidStackList;
	}

	public static QMDRecipeMatchResult matchIngredients(IngredientSorption sorption,List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients,
			List<IParticleIngredient> particleIngredients, List items, List fluids, List particles, boolean shapeless, List extras)
	{
		if (itemIngredients.size() != items.size() || fluidIngredients.size() != fluids.size()|| particleIngredients.size() != particles.size())
			return QMDRecipeMatchResult.FAIL;

		List<Integer> itemIngredientNumbers = new ArrayList<Integer>(Collections.nCopies(itemIngredients.size(), 0));
		List<Integer> fluidIngredientNumbers = new ArrayList<Integer>(Collections.nCopies(fluidIngredients.size(), 0));
		List<Integer> particleIngredientNumbers = new ArrayList<Integer>(Collections.nCopies(particleIngredients.size(), 0));
		List<Integer> itemInputOrder = CollectionHelper.increasingList(itemIngredients.size());
		List<Integer> fluidInputOrder = CollectionHelper.increasingList(fluidIngredients.size());
		List<Integer> particleInputOrder = CollectionHelper.increasingList(particleIngredients.size());

		if (!shapeless)
		{
			for (int i = 0; i < items.size(); i++)
			{
				IngredientMatchResult matchResult = itemIngredients.get(i).match(items.get(i), sorption);
				if (matchResult.matches())
				{
					itemIngredientNumbers.set(i, matchResult.getIngredientNumber());
					continue;
				}
				return QMDRecipeMatchResult.FAIL;
			}
			
			for (int i = 0; i < fluids.size(); i++)
			{
				Object fluid = fluids.get(i) instanceof Tank ? ((Tank) fluids.get(i)).getFluid() : fluids.get(i);
				IngredientMatchResult matchResult = fluidIngredients.get(i).match(fluid, sorption);
				if (matchResult.matches())
				{
					fluidIngredientNumbers.set(i, matchResult.getIngredientNumber());
					continue;
				}
				return QMDRecipeMatchResult.FAIL;
			}
			for (int i = 0; i < particles.size(); i++)
			{
				IngredientMatchResult matchResult = particleIngredients.get(i).matchWithData(particles.get(i), sorption, extras);
				if (matchResult.matches())
				{
					particleIngredientNumbers.set(i, matchResult.getIngredientNumber());
					continue;
				}
				return QMDRecipeMatchResult.FAIL;
			}
		}
		else
		{
			List<IItemIngredient> itemIngredientsRemaining = new ArrayList<IItemIngredient>(itemIngredients);
			itemInputs: for (int i = 0; i < items.size(); i++)
			{
				for (int j = 0; j < itemIngredients.size(); j++)
				{
					IItemIngredient itemIngredient = itemIngredientsRemaining.get(j);
					if (itemIngredient == null)
						continue;
					IngredientMatchResult matchResult = itemIngredient.match(items.get(i), sorption);
					if (matchResult.matches())
					{
						itemIngredientsRemaining.set(j, null);
						itemIngredientNumbers.set(i, matchResult.getIngredientNumber());
						itemInputOrder.set(i, j);
						continue itemInputs;
					}
				}
				return QMDRecipeMatchResult.FAIL;
			}
			List<IFluidIngredient> fluidIngredientsRemaining = new ArrayList<IFluidIngredient>(fluidIngredients);
			fluidInputs: for (int i = 0; i < fluids.size(); i++)
			{
				Object fluid = fluids.get(i) instanceof Tank ? ((Tank) fluids.get(i)).getFluid() : fluids.get(i);
				for (int j = 0; j < fluidIngredients.size(); j++)
				{
					IFluidIngredient fluidIngredient = fluidIngredientsRemaining.get(j);
					if (fluidIngredient == null)
						continue;
					IngredientMatchResult matchResult = fluidIngredient.match(fluid, sorption);
					if (matchResult.matches())
					{
						fluidIngredientsRemaining.set(j, null);
						fluidIngredientNumbers.set(i, matchResult.getIngredientNumber());
						fluidInputOrder.set(i, j);
						continue fluidInputs;
					}
				}
				return QMDRecipeMatchResult.FAIL;
			}
			List<IParticleIngredient> particleIngredientsRemaining = new ArrayList<IParticleIngredient>(
					particleIngredients);
			particleInputs: for (int i = 0; i < particles.size(); i++)
			{
				for (int j = 0; j < particleIngredients.size(); j++)
				{
					IParticleIngredient particleIngredient = particleIngredientsRemaining.get(j);
					if (particleIngredient == null)
						continue;
					IngredientMatchResult matchResult = particleIngredient.matchWithData(particles.get(i), sorption,extras);
					if (matchResult.matches())
					{
						particleIngredientsRemaining.set(j, null);
						particleIngredientNumbers.set(i, matchResult.getIngredientNumber());
						particleInputOrder.set(i, j);
						continue particleInputs;
					}
				}
				return QMDRecipeMatchResult.FAIL;
			}
		}
		return new QMDRecipeMatchResult(true, itemIngredientNumbers, fluidIngredientNumbers, particleIngredientNumbers,
				itemInputOrder, fluidInputOrder, particleInputOrder);
	}

	public static List<String> getItemIngredientNames(List<IItemIngredient> ingredientList)
	{
		List<String> ingredientNames = new ArrayList<String>();
		for (IItemIngredient ingredient : ingredientList)
		{
			if (ingredient == null || ingredient instanceof EmptyItemIngredient)
				ingredientNames.add("null");
			else if (ingredient instanceof ItemArrayIngredient)
				ingredientNames.add(((ItemArrayIngredient) ingredient).getIngredientRecipeString());
			else
				ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
		}
		return ingredientNames;
	}

	public static List<String> getFluidIngredientNames(List<IFluidIngredient> ingredientList)
	{
		List<String> ingredientNames = new ArrayList<String>();
		for (IFluidIngredient ingredient : ingredientList)
		{
			if (ingredient == null || ingredient instanceof EmptyFluidIngredient)
				ingredientNames.add("null");
			else if (ingredient instanceof FluidArrayIngredient)
				ingredientNames.add(((FluidArrayIngredient) ingredient).getIngredientRecipeString());
			else
				ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
		}
		return ingredientNames;
	}

	public static List<String> getParticleIngredientNames(List<IParticleIngredient> ingredientList)
	{
		List<String> ingredientNames = new ArrayList<String>();
		for (IParticleIngredient ingredient : ingredientList)
		{
			if (ingredient == null || ingredient instanceof EmptyParticleIngredient)
				ingredientNames.add("null");
			else if (ingredient instanceof ParticleArrayIngredient)
				ingredientNames.add(((ParticleArrayIngredient) ingredient).getIngredientRecipeString());
			else
				ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
		}
		return ingredientNames;
	}

	public static String getAllIngredientNamesConcat(List<IItemIngredient> itemIngredientList,
			List<IFluidIngredient> fluidIngredientList, List<IParticleIngredient> particleIngredientList)
	{
		return StringHelper.stringListConcat(getItemIngredientNames(itemIngredientList),
				getFluidIngredientNames(fluidIngredientList), getParticleIngredientNames(particleIngredientList));
	}

	public static String getRecipeString(List<IItemIngredient> itemIngredientList,
			List<IFluidIngredient> fluidIngredientList, List<IParticleIngredient> particleIngredientList,
			List<IItemIngredient> itemProductList, List<IFluidIngredient> fluidProductList,
			List<IParticleIngredient> particleProductList)
	{
		return getAllIngredientNamesConcat(itemIngredientList, fluidIngredientList, particleIngredientList) + " -> "
				+ getAllIngredientNamesConcat(itemProductList, fluidProductList, particleProductList);
	}

	public static String getRecipeString(IQMDRecipe recipe)
	{
		if (recipe == null)
			return "nullRecipe";
		return getRecipeString(recipe.getItemIngredients(), recipe.getFluidIngredients(), recipe.getParticleIngredients(),
				recipe.getItemProducts(), recipe.getFluidProducts(), recipe.getParticleProducts());
	}

	public static List<String> buildItemIngredientNames(List ingredientList)
	{
		List<String> ingredientNames = new ArrayList<String>();
		for (Object obj : ingredientList)
		{
			if (obj == null)
				ingredientNames.add("null");
			else
			{
				if (!(obj instanceof IItemIngredient))
					obj = buildItemIngredient(obj);
				IItemIngredient ingredient = (IItemIngredient) obj;
				if (ingredient instanceof ItemArrayIngredient)
					ingredientNames.add(((ItemArrayIngredient) ingredient).getIngredientRecipeString());
				else
					ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
			}
		}
		return ingredientNames;
	}

	public static List<String> buildFluidIngredientNames(List ingredientList)
	{
		List<String> ingredientNames = new ArrayList<String>();
		for (Object obj : ingredientList)
		{
			if (obj == null)
				ingredientNames.add("null");
			else
			{
				if (!(obj instanceof IFluidIngredient))
					obj = buildFluidIngredient(obj);
				IFluidIngredient ingredient = (IFluidIngredient) obj;
				if (ingredient instanceof FluidArrayIngredient)
					ingredientNames.add(((FluidArrayIngredient) ingredient).getIngredientRecipeString());
				else
					ingredientNames.add(ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
			}
		}
		return ingredientNames;
	}

	public static List<List<String>> validFluids(QMDRecipeHandler recipes)
	{
		return validFluids(recipes, new ArrayList<String>());
	}

	public static List<List<String>> validFluids(QMDRecipeHandler recipes, List<String> exceptions)
	{
		int fluidInputSize = recipes.fluidInputSize;
		int fluidOutputSize = recipes.fluidOutputSize;

		List<FluidStack> fluidStackList = new ArrayList<FluidStack>();
		for (Fluid fluid : FluidRegistry.getRegisteredFluids().values())
			fluidStackList.add(new FluidStack(fluid, 1000));

		List<String> fluidNameList = new ArrayList<String>();
		for (FluidStack fluidStack : fluidStackList)
		{
			String fluidName = fluidStack.getFluid().getName();
			if (recipes.isValidFluidInput(fluidStack) && !exceptions.contains(fluidName))
				fluidNameList.add(fluidName);
		}

		List<List<String>> allowedFluidLists = new ArrayList<List<String>>();
		for (int i = 0; i < fluidInputSize; i++)
			allowedFluidLists.add(fluidNameList);
		for (int i = fluidInputSize; i < fluidInputSize + fluidOutputSize; i++)
			allowedFluidLists.add(null);

		return allowedFluidLists;
	}

	public static OreIngredient getOreStackFromItems(List<ItemStack> stackList, int stackSize)
	{
		if (stackList == null || stackList.isEmpty())
			return null;
		String oreName = OreDictHelper.getOreNameFromStacks(stackList);
		if (oreName.equals("Unknown"))
			return null;
		return new OreIngredient(oreName, stackSize);
	}

	public static long hashMaterialsRaw(List<ItemStack> items, List<Tank> fluids, List<ParticleStack> particles)
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
			hash = 31L * hash + (tank == null ? 0L: tank.getFluid() == null ? 0L : tank.getFluid().getFluid().getName().hashCode());
		}
		Iterator<ParticleStack> particleIter = particles.iterator();
		while (particleIter.hasNext())
		{
			ParticleStack stack = particleIter.next();
			hash = 31L * hash + (stack == null ? 0L : stack.getParticle().getName().hashCode());
		}
		return hash;
	}

	public static long hashMaterials(List<ItemStack> items, List<FluidStack> fluids, List<ParticleStack> particles)
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
			ParticleStack stack = particleIter.next();
			hash = 31L * hash + (stack == null ? 0L : stack.getParticle().getName().hashCode());
		}
		return hash;
	}
	
	
	
	public static InventoryCrafting fakeCrafter(int width, int height)
	{
		return new FakeCrafting(width, height);
	}

	private static class FakeCrafting extends InventoryCrafting
	{

		private static final FakeCraftingContainer FAKE_CONTAINER = new FakeCraftingContainer();

		private static class FakeCraftingContainer extends Container
		{

			@Override
			public void onCraftMatrixChanged(IInventory inventory)
			{

			}

			@Override
			public boolean canInteractWith(EntityPlayer player)
			{
				return false;
			}
		}

		private FakeCrafting(int width, int height)
		{
			super(FAKE_CONTAINER, width, height);
		}
	}
}