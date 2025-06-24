package lach_01298.qmd.recipe.ingredient;

import com.google.common.collect.Lists;
import crafttweaker.api.item.IIngredient;
import it.unimi.dsi.fastutil.ints.*;
import lach_01298.qmd.crafttweaker.particle.CTParticleStack;
import lach_01298.qmd.particle.ParticleStack;
import nc.recipe.*;
import net.minecraftforge.fml.common.Optional;

import java.util.List;

public class ParticleIngredient implements IParticleIngredient
{

	public ParticleStack stack;

	public ParticleIngredient(ParticleStack stack)
	{
		this.stack = stack;

	}

	public ParticleIngredient(String particleName, int amount, long meanEnergy, double focus)
	{
		stack = ParticleStack.getParticleStack(particleName, amount, meanEnergy, focus);
	}

	@Override
	public ParticleStack getStack()
	{
		return stack == null ? null : stack.copy();
	}


	@Override
	public List<ParticleStack> getInputStackList()
	{
		return Lists.newArrayList(stack);
	}

	@Override
	public List<ParticleStack> getOutputStackList()
	{
		return Lists.newArrayList(stack);
	}

	@Override
	public int getMaxStackSize(int ingredientNumber)
	{
		return stack.getAmount();
	}

	@Override
	public void setMaxStackSize(int stackSize)
	{
		stack.setAmount(stackSize);
	}

	@Override
	public String getIngredientName()
	{
		return stack.getParticle().getName();
	}
	
	@Override
	public String getIngredientNamesConcat()
	{
		return stack.getParticle().getName();
	}
	
	@Override
	public IntList getFactors()
	{
		return new IntArrayList(Lists.newArrayList(stack.getAmount()));
	}

	@Override
	public IParticleIngredient getFactoredIngredient(int factor)
	{
		ParticleStack newStack = stack.copy();
		newStack.setAmount(stack.getAmount()/factor);
		return new ParticleIngredient(newStack);
	}
	
	@Override
	public IngredientMatchResult match(Object object, IngredientSorption type)
	{
		
		if (object instanceof ParticleStack)
		{
			ParticleStack particleStack = (ParticleStack) object;
			if(!stack.matchesType(particleStack))
			{
				return IngredientMatchResult.FAIL;
			}
			
			return new IngredientMatchResult(type.checkStackSize((int)stack.getAmount(), (int)stack.getAmount()), 0);
		}
		else if (object instanceof ParticleIngredient && match(((ParticleIngredient) object).stack, type).matches())
		{
			return new IngredientMatchResult(type.checkStackSize(getMaxStackSize(0), ((ParticleIngredient) object).getMaxStackSize(0)), 0);
		}
		
		return IngredientMatchResult.FAIL;
	}
	
	@Override
	public IngredientMatchResult matchWithData(Object object, IngredientSorption type, List extras)
	{
		
		if (object instanceof ParticleStack)
		{
			ParticleStack particleStack = (ParticleStack) object;
			if(!stack.isInRange(particleStack, (long) extras.get(0)))
			{
				return IngredientMatchResult.FAIL;
			}
			
			return new IngredientMatchResult(type.checkStackSize((int)stack.getAmount(), (int)stack.getAmount()), 0);
		}
		else if (object instanceof ParticleIngredient && matchWithData(((ParticleIngredient) object).stack, type, extras).matches())
		{
			return new IngredientMatchResult(type.checkStackSize(getMaxStackSize(0), ((ParticleIngredient) object).getMaxStackSize(0)), 0);
		}
		
		return IngredientMatchResult.FAIL;
	}
	
	@Override
	public boolean isValid()
	{
		return stack != null;
	}

	@Override
	public boolean isEmpty()
	{
		return this.stack == null || this.stack.getAmount() <= 0;
	}

	@Override
	@Optional.Method(modid = "crafttweaker")
	public IIngredient ct()
	{
		return new CTParticleStack(stack);
	}

	
}
