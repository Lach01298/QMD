package lach_01298.qmd.recipe.ingredient;

import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.particle.ParticleBeam;
import lach_01298.qmd.recipe.IParticleIngredient;
import nc.recipe.IngredientMatchResult;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.FluidIngredient;
import nc.tile.internal.fluid.Tank;
import nc.util.FluidStackHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ParticleIngredient implements IParticleIngredient
{

	public ParticleBeam stack;
	public String particleName;
	public int meanEnergy;
	public int luminosity;
	public double range;

	public ParticleIngredient(ParticleBeam beam)
	{
		this.stack = beam;
		particleName = beam.getParticle().getName();
		meanEnergy = beam.getMeanEnergy();
		range = beam.getEnergySpread();
	}

	public ParticleIngredient(String particleName, int meanEnergy, int luminosity, double range)
	{
		stack = ParticleBeam.getBeam(particleName, meanEnergy, luminosity, range);
		this.particleName = particleName;
		this.meanEnergy = meanEnergy;
		this.luminosity = luminosity;
		this.range = range;
	}

	@Override
	public ParticleBeam getStack()
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
		if (object instanceof ParticleBeam)
		{
			ParticleBeam beam = (ParticleBeam) object;
			if (!beam.isBeamInRange(beam))
			{
				return IngredientMatchResult.FAIL;
			}
			return new IngredientMatchResult(type.checkStackSize(stack.getLuminosity(), beam.getLuminosity()), 0);
		}
		else if (object instanceof ParticleIngredient && match(((ParticleIngredient) object).stack, type).matches())
		{
			return new IngredientMatchResult(
					type.checkStackSize(getMaxStackSize(0), ((ParticleIngredient) object).getMaxStackSize(0)), 0);
		}
		return IngredientMatchResult.FAIL;
	}

	@Override
	public List<ParticleBeam> getInputStackList()
	{
		return Lists.newArrayList(stack);
	}

	@Override
	public List<ParticleBeam> getOutputStackList()
	{
		return Lists.newArrayList(stack);
	}

	@Override
	public int getMaxStackSize(int ingredientNumber)
	{
		return luminosity;
	}

	@Override
	public void setMaxStackSize(int stackSize)
	{
		luminosity = stackSize;
		stack.setLuminosity(stackSize);
	}

	@Override
	public boolean isValid()
	{
		return stack != null;
	}
}