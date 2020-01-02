package lach_01298.qmd.recipe;

import java.util.List;

import lach_01298.qmd.particle.ParticleBeam;
import nc.config.NCConfig;
import nc.recipe.IRecipe;
import nc.recipe.IngredientSorption;


import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;

import nc.tile.internal.fluid.Tank;
import nc.util.InfoHelper;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;

public class QMDRecipe  implements IQMDRecipe
{
	protected List<IItemIngredient> itemIngredients, itemProducts;
	protected List<IFluidIngredient> fluidIngredients, fluidProducts;
	protected List<IParticleIngredient> particleIngredients, particleProducts;
	
	protected List extras;
	public boolean isShapeless;

	public QMDRecipe(List<IItemIngredient> itemIngredientsList, List<IFluidIngredient> fluidIngredientsList,
			List<IParticleIngredient> particleIngredientsList, List<IItemIngredient> itemProductsList,
			List<IFluidIngredient> fluidProductsList, List<IParticleIngredient> particleProductsList, List extrasList,
			boolean shapeless)
	{
		itemIngredients = itemIngredientsList;
		fluidIngredients = fluidIngredientsList;
		particleIngredients = particleIngredientsList;
		itemProducts = itemProductsList;
		fluidProducts = fluidProductsList;
		particleProducts = particleProductsList;

		extras = extrasList;
		isShapeless = shapeless;
	}

	@Override
	public List<IItemIngredient> itemIngredients()
	{
		return itemIngredients;
	}

	@Override
	public List<IFluidIngredient> fluidIngredients()
	{
		return fluidIngredients;
	}

	@Override
	public List<IParticleIngredient> particleIngredients()
	{
		return particleIngredients;
	}

	@Override
	public List<IItemIngredient> itemProducts()
	{
		return itemProducts;
	}

	@Override
	public List<IFluidIngredient> fluidProducts()
	{
		return fluidProducts;
	}

	@Override
	public List<IParticleIngredient> particleProducts()
	{
		return particleProducts;
	}

	@Override
	public List extras()
	{
		return extras;
	}

	@Override
	public QMDRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs,
			List<ParticleBeam> particleInputs)
	{
		
		return RecipeHelper.matchIngredients(IngredientSorption.INPUT, itemIngredients, fluidIngredients,particleIngredients, itemInputs, fluidInputs,particleInputs, isShapeless);
	}

	@Override
	public QMDRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs,
			List<ParticleBeam> particleOutputs)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QMDRecipeMatchResult matchIngredients(List<IItemIngredient> itemIngredients,
			List<IFluidIngredient> fluidIngredients, List<IParticleIngredient> particleIngredients)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QMDRecipeMatchResult matchProducts(List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts,
			List<IParticleIngredient> particleProducts)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* ================================== Recipe Extras ===================================== */
	
	// Processors
	
	public double getBaseProcessTime(double defaultProcessTime)
	{
		if (extras.isEmpty())
			return defaultProcessTime;
		else if (extras.get(0) instanceof Double)
			return ((double) extras.get(0)) * defaultProcessTime;
		else
			return defaultProcessTime;
	}
	
	public double getBaseProcessPower(double defaultProcessPower)
	{
		if (extras.size() < 2)
			return defaultProcessPower;
		else if (extras.get(1) instanceof Double)
			return ((double) extras.get(1)) * defaultProcessPower;
		else
			return defaultProcessPower;
	}
	
	public double getBaseProcessRadiation()
	{
		if (extras.size() < 3)
			return 0D;
		else if (extras.get(2) instanceof Double)
			return (double) extras.get(2);
		else
			return 0D;
	}


	
	
}