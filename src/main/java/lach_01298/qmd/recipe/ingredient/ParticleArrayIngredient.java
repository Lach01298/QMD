package lach_01298.qmd.recipe.ingredient;

import com.google.common.collect.Lists;
import crafttweaker.api.item.*;
import it.unimi.dsi.fastutil.ints.*;
import lach_01298.qmd.particle.ParticleStack;
import nc.recipe.*;
import net.minecraftforge.fml.common.Optional;

import java.util.*;

public class ParticleArrayIngredient implements IParticleIngredient
{
	
	public List<IParticleIngredient> ingredientList;
	public List<ParticleStack> cachedStackList = new ArrayList<ParticleStack>();
	
	public ParticleArrayIngredient(IParticleIngredient... ingredients)
	{
		this(Lists.newArrayList(ingredients));
	}
	
	public ParticleArrayIngredient(List<IParticleIngredient> ingredientList)
	{
		this.ingredientList = ingredientList;
		ingredientList.forEach(input -> cachedStackList.add(input.getStack()));
	}
	
	@Override
	public ParticleStack getStack()
	{
		return isValid() ? cachedStackList.get(0).copy() : null;
	}
	
	@Override
	public List<ParticleStack> getInputStackList()
	{
		List<ParticleStack> stacks = new ArrayList<ParticleStack>();
		ingredientList.forEach(ingredient -> ingredient.getInputStackList().forEach(obj -> stacks.add(obj)));
		return stacks;
	}
	
	@Override
	public List<ParticleStack> getOutputStackList()
	{
		return isValid() ? Lists.newArrayList(getStack()) : new ArrayList<>();
	}
	
	@Override
	public int getMaxStackSize(int ingredientNumber)
	{
		return ingredientList.get(ingredientNumber).getMaxStackSize(0);
	}
	
	@Override
	public void setMaxStackSize(int stackSize)
	{
		for (IParticleIngredient ingredient : ingredientList)
		{
			ingredient.setMaxStackSize(stackSize);
		}
		for (ParticleStack stack : cachedStackList)
		{
			stack.setAmount(stackSize);
		}
	}
	
	
	@Override
	public String getIngredientName()
	{
		//return ingredientList.get(0).getIngredientName();
		return getIngredientNamesConcat();
	}
	
	@Override
	public String getIngredientNamesConcat()
	{
		String names = "";
		for (IParticleIngredient ingredient : ingredientList) names += (", " + ingredient.getIngredientName());
		return "{ " + names.substring(2) + " }";
	}
	
	public String getIngredientRecipeString()
	{
		String names = "";
		for (IParticleIngredient ingredient : ingredientList) names += (", " + ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
		return "{ " + names.substring(2) + " }";
	}
	
	@Override
	public IntList getFactors()
	{
		IntList list = new IntArrayList();
		for (IParticleIngredient ingredient : ingredientList)
		{
			list.addAll(ingredient.getFactors());
		}
		return new IntArrayList(list);
	}

	@Override
	public IParticleIngredient getFactoredIngredient(int factor)
	{
		List<IParticleIngredient> list = new ArrayList<>();
		for (IParticleIngredient ingredient : ingredientList)
		{
			list.add(ingredient.getFactoredIngredient(factor));
		}
		return new ParticleArrayIngredient(list);
	}
	
	@Override
	public IngredientMatchResult match(Object object, IngredientSorption sorption)
	{
		for (int i = 0; i < ingredientList.size(); i++)
		{
			if (ingredientList.get(i).match(object, sorption).matches()) return new IngredientMatchResult(true, i);
		}
		return IngredientMatchResult.FAIL;
	}
	
	@Override
	public boolean isValid()
	{
		return cachedStackList != null && !cachedStackList.isEmpty();
	}

	@Override
	@Optional.Method(modid = "crafttweaker")
	public IIngredient ct()
	{
		IIngredient[] array = new IIngredient[ingredientList.size()];
		for (int i = 0; i < ingredientList.size(); i++) {
			array[i] = ingredientList.get(i).ct();
		}
		return new IngredientOr(array);
		
	}

	@Override
	public IngredientMatchResult matchWithData(Object object, IngredientSorption type, List extras)
	{
		for (int i = 0; i < ingredientList.size(); i++)
		{
			if (ingredientList.get(i).matchWithData(object, type ,extras).matches()) return new IngredientMatchResult(true, i);
		}
		return IngredientMatchResult.FAIL;
	}

	
}
