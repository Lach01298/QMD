package lach_01298.qmd.recipe;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.*;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;

import java.util.List;

public class QMDRecipe  implements IQMDRecipe
{
	protected List<IItemIngredient> itemIngredients, itemProducts;
	protected List<IFluidIngredient> fluidIngredients, fluidProducts;
	protected List<IParticleIngredient> particleIngredients, particleProducts;
	
	protected List<Object> extras;
	public boolean isShapeless;


	public QMDRecipe(List<IItemIngredient> itemIngredientsList, List<IFluidIngredient> fluidIngredientsList,List<IParticleIngredient> particleIngredientsList, List<IItemIngredient> itemProductsList,
			List<IFluidIngredient> fluidProductsList, List<IParticleIngredient> particleProductsList, List<Object> extrasList, boolean shapeless)
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
	public List<IItemIngredient> getItemIngredients()
	{
		return itemIngredients;
	}

	@Override
	public List<IFluidIngredient> getFluidIngredients()
	{
		return fluidIngredients;
	}

	@Override
	public List<IParticleIngredient> getParticleIngredients()
	{
		return particleIngredients;
	}

	@Override
	public List<IItemIngredient> getItemProducts()
	{
		return itemProducts;
	}

	@Override
	public List<IFluidIngredient> getFluidProducts()
	{
		return fluidProducts;
	}

	@Override
	public List<IParticleIngredient> getParticleProducts()
	{
		return particleProducts;
	}

	@Override
	public List<Object> getExtras()
	{
		return extras;
	}
	
	public boolean isShapeless()
	{
		return isShapeless;
	}
	

	@Override
	public QMDRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs,List<ParticleStack> particleInputs, List<Object> extras)
	{
		return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, itemIngredients, fluidIngredients,particleIngredients, itemInputs, fluidInputs, particleInputs, isShapeless, extras);
	}

	@Override
	public QMDRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs, List<ParticleStack> particleOutputs)
	{
		return QMDRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, itemProducts, fluidProducts,particleIngredients, itemOutputs, fluidOutputs, particleOutputs, isShapeless, extras);
	}

	@Override
	public QMDRecipeMatchResult matchIngredients(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients, List<IParticleIngredient> particleIngredients)
	{
		return QMDRecipeHelper.matchIngredients(IngredientSorption.INPUT, this.itemIngredients, this.fluidIngredients, this.particleIngredients, itemIngredients, fluidIngredients, particleIngredients, isShapeless, extras);
	}

	@Override
	public QMDRecipeMatchResult matchProducts(List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts, List<IParticleIngredient> particleProducts)
	{
		return QMDRecipeHelper.matchIngredients(IngredientSorption.OUTPUT, this.itemProducts, this.fluidProducts, this.particleProducts, itemProducts, fluidProducts, particleProducts, isShapeless, extras);
	}



	/* ================================== Recipe Extras ===================================== */
	
	//most recipes
	public long getMaxEnergy()
	{
		return (long) extras.get(0);
	}
	
	public double getCrossSection()
	{
		return (double) extras.get(1);
	}

	public long getEnergyReleased()
	{
		return (long) extras.get(2);
	}

	// nuclearsynthesis chamber
	public long getHeatReleased()
	{
		return (long) extras.get(1);
	}

	// fluid heating recipes
	public int getHeatRequired() { return (int) extras.get(0); }

	public int getInputTemperature() { return (int) extras.get(1); }



	// machine recipes
	public double getProcessTimeMultiplier()
	{
		return (double) extras.get(0);
	}

	public double getProcessPowerMultiplier()
	{
		return (double) extras.get(1);
	}

	public double getBaseProcessTime(double defaultProcessTime)
	{
		return this.getProcessTimeMultiplier() * defaultProcessTime;
	}

	public double getBaseProcessPower(double defaultProcessPower)
	{
		return this.getProcessPowerMultiplier() * defaultProcessPower;
	}

	public double getBaseProcessRadiation()
	{
		return (double) extras.get(2);
	}
}
