package lach_01298.qmd.recipe;

import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.recipe.IngredientSorption;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;

public class QMDRecipe  implements IQMDRecipe
{
	protected List<IItemIngredient> itemIngredients, itemProducts;
	protected List<IFluidIngredient> fluidIngredients, fluidProducts;
	protected List<IParticleIngredient> particleIngredients, particleProducts;
	
	protected List extras;
	public boolean isShapeless;


	public QMDRecipe(List<IItemIngredient> itemIngredientsList, List<IFluidIngredient> fluidIngredientsList,List<IParticleIngredient> particleIngredientsList, List<IItemIngredient> itemProductsList,
			List<IFluidIngredient> fluidProductsList, List<IParticleIngredient> particleProductsList, List extrasList, boolean shapeless)
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
	public List getExtras()
	{
		return extras;
	}
	
	public boolean isShapeless() 
	{
		return isShapeless;
	}
	

	@Override
	public QMDRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs,List<ParticleStack> particleInputs, List extras)
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
	
	public long getMaxEnergy()
	{
		return (long) extras.get(0);
	}
	
	public double getCrossSection()
	{
		return (double) extras.get(1);
	}
	
	public long getEnergyRelased()
	{
		return (long) extras.get(2);
	}
	
	
	public double getBaseProcessRadiation() 
	{
		return (double) extras.get(3);
	}
	



	
	
}