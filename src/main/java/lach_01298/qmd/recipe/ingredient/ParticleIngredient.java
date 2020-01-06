package lach_01298.qmd.recipe.ingredient;

import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.particle.ParticleStack;
import nc.recipe.IngredientMatchResult;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.FluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.FluidStackHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ParticleIngredient implements IParticleIngredient
{

	public ParticleStack stack;
	public String particleName;
	public int meanEnergy;
	public int amount;
	public double range;

	public ParticleIngredient(ParticleStack stack)
	{
		this.stack = stack;
		particleName = stack.getParticle().getName();
		meanEnergy = stack.getMeanEnergy();
		range = stack.getEnergySpread();
	}

	public ParticleIngredient(String particleName, int meanEnergy, int amount, double range)
	{
		stack = ParticleStack.getParticleStack(particleName, meanEnergy, amount, range);
		this.particleName = particleName;
		this.meanEnergy = meanEnergy;
		this.amount = amount;
		this.range = range;
	}

	@Override
	public ParticleStack getStack()
	{
		return stack == null ? null : stack.copy();
	}

	@Override
	public String getIngredientName()
	{
		return particleName;
	}

	@Override
	public String getIngredientNamesConcat()
	{
		return particleName;
	}

	@Override
	public IngredientMatchResult match(Object object, IngredientSorption type)
	{
		if (object instanceof ParticleStack)
		{
			ParticleStack stack = (ParticleStack) object;
			return new IngredientMatchResult(type.checkStackSize(stack.getAmount(), stack.getAmount()), 0);
		}
		else if (object instanceof ParticleIngredient && match(((ParticleIngredient) object).stack, type).matches())
		{
			return new IngredientMatchResult(
					type.checkStackSize(getMaxStackSize(0), ((ParticleIngredient) object).getMaxStackSize(0)), 0);
		}
		return IngredientMatchResult.FAIL;
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
		return amount;
	}

	@Override
	public void setMaxStackSize(int stackSize)
	{
		amount = stackSize;
		stack.setAmount(stackSize);
	}

	@Override
	public boolean isValid()
	{
		return stack != null;
	}
}