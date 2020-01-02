package lach_01298.qmd.recipe.ingredient;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.particle.ParticleBeam;
import lach_01298.qmd.recipe.IParticleIngredient;
import nc.recipe.IngredientMatchResult;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.tile.internal.fluid.Tank;
import net.minecraftforge.fluids.FluidStack;

public class EmptyParticleIngredient implements IParticleIngredient
{

	public EmptyParticleIngredient()
	{
	}

	@Override
	public ParticleBeam getStack()
	{
		return null;
	}

	@Override
	public List<ParticleBeam> getInputStackList()
	{
		return new ArrayList<>();
	}

	@Override
	public List<ParticleBeam> getOutputStackList()
	{
		return new ArrayList<>();
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
	public int getMaxStackSize(int ingredientNumber)
	{
		return 0;
	}

	@Override
	public void setMaxStackSize(int stackSize)
	{
	}

	@Override
	public IngredientMatchResult match(Object object, IngredientSorption sorption)
	{
		if (object == null)
			return IngredientMatchResult.PASS_0;
		if (object instanceof ParticleBeam)
		{
			return new IngredientMatchResult(((ParticleBeam) object).getParticle() == null, 0);
		}
		return new IngredientMatchResult(object instanceof EmptyParticleIngredient, 0);
	}

	@Override
	public boolean isValid()
	{
		return true;
	}

	@Override
	public List<ParticleBeam> getInputStackHashingList()
	{
		return Lists.newArrayList((ParticleBeam) null);
	}
}
