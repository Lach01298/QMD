package lach_01298.qmd.recipe.ingredient;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import crafttweaker.api.item.IIngredient;
import crafttweaker.mc1120.item.MCItemStack;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import lach_01298.qmd.crafttweaker.particle.CTParticleStack;
import lach_01298.qmd.particle.ParticleStack;
import nc.recipe.IngredientMatchResult;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.tile.internal.fluid.Tank;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;

public class EmptyParticleIngredient implements IParticleIngredient
{

	public EmptyParticleIngredient()
	{
	}

	@Override
	public ParticleStack getStack()
	{
		return null;
	}

	@Override
	public List<ParticleStack> getInputStackList()
	{
		return new ArrayList<>();
	}
	
	@Override
	public List<ParticleStack> getInputStackHashingList()
	{
		return Lists.newArrayList((ParticleStack) null);
	}

	@Override
	public List<ParticleStack> getOutputStackList()
	{
		return new ArrayList<>();
	}

	@Override
	public int getMaxStackSize(int ingredientNumber)
	{
		return 0;
	}

	@Override
	public void setMaxStackSize(int stackSize)
	{
	}
	
	@Override
	public String getIngredientName()
	{
		return "null";
	}

	@Override
	public String getIngredientNamesConcat()
	{
		return "null";
	}

	@Override
	public IntList getFactors()
	{
		return new IntArrayList();
	}

	@Override
	public IParticleIngredient getFactoredIngredient(int factor)
	{
		return new EmptyParticleIngredient();
	}	

	@Override
	public IngredientMatchResult match(Object object, IngredientSorption sorption)
	{
		if (object == null)
			return IngredientMatchResult.PASS_0;
		if (object instanceof ParticleStack)
		{
			return new IngredientMatchResult(((ParticleStack) object).getParticle() == null, 0);
		}
		return new IngredientMatchResult(object instanceof EmptyParticleIngredient, 0);
	}

	@Override
	public boolean isValid()
	{
		return true;
	}

	

	@Override
	public IIngredient ct()
	{
		return new CTParticleStack();
	}

	@Override
	public IngredientMatchResult matchWithData(Object object, IngredientSorption type, List extras)
	{
		return match(object, type);
	}

	


}
