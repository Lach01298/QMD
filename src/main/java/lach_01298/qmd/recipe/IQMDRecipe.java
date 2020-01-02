package lach_01298.qmd.recipe;

import java.util.List;

import lach_01298.qmd.particle.ParticleBeam;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;

public interface IQMDRecipe {
	
	public List<IItemIngredient> itemIngredients();
	
	public List<IFluidIngredient> fluidIngredients();
	
	public List<IParticleIngredient> particleIngredients();
	
	public List<IItemIngredient> itemProducts();
	
	public List<IFluidIngredient> fluidProducts();
	
	public List<IParticleIngredient> particleProducts();
	
	public List extras();

	public QMDRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs, List<ParticleBeam> particleInputs);
	
	public QMDRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs, List<ParticleBeam> particleOutputs);
	
	public QMDRecipeMatchResult matchIngredients(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients,List<IParticleIngredient> particleIngredients);
	
	public QMDRecipeMatchResult matchProducts(List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts,List<IParticleIngredient> particleProducts);
}
