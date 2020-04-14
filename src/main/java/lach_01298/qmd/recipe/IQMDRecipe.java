package lach_01298.qmd.recipe;

import java.util.List;

import crafttweaker.annotations.ZenRegister;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.tile.internal.fluid.Tank;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.qmd.IQMDRecipe")
@ZenRegister
public interface IQMDRecipe {
	
	public List<IItemIngredient> getItemIngredients();
	
	public List<IFluidIngredient> getFluidIngredients();
	
	public List<IParticleIngredient> getParticleIngredients();
	
	public List<IItemIngredient> getItemProducts();
	
	public List<IFluidIngredient> getFluidProducts();
	
	public List<IParticleIngredient> getParticleProducts();
	
	@ZenMethod("getItemIngredient")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctItemIngredient(int index) 
	{
		return getItemIngredients().get(index).ct();
	}
	
	@ZenMethod("getFluidIngredient")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctFluidIngredient(int index) 
	{
		return getFluidIngredients().get(index).ct();
	}
	
	@ZenMethod("getParticleIngredient")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctParticleIngredient(int index) 
	{
		return getParticleIngredients().get(index).ct();
	}
	
	@ZenMethod("getItemProduct")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctItemProduct(int index) 
	{
		return getItemProducts().get(index).ct();
	}
	
	@ZenMethod("getFluidProduct")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctFluidProduct(int index) 
	{
		return getFluidProducts().get(index).ct();
	}
	
	@ZenMethod("getParticleProduct")
	@Optional.Method(modid = "crafttweaker")
	public default crafttweaker.api.item.IIngredient ctParticleProduct(int index) 
	{
		return getParticleProducts().get(index).ct();
	}
	
	@ZenMethod
	public List getExtras();

	public QMDRecipeMatchResult matchInputs(List<ItemStack> itemInputs, List<Tank> fluidInputs, List<ParticleStack> particleInputs, List extras);
	
	public QMDRecipeMatchResult matchOutputs(List<ItemStack> itemOutputs, List<Tank> fluidOutputs, List<ParticleStack> particleOutputs);
	
	public QMDRecipeMatchResult matchIngredients(List<IItemIngredient> itemIngredients, List<IFluidIngredient> fluidIngredients,List<IParticleIngredient> particleIngredients);
	
	public QMDRecipeMatchResult matchProducts(List<IItemIngredient> itemProducts, List<IFluidIngredient> fluidProducts,List<IParticleIngredient> particleProducts);
}
