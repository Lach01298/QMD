package lach_01298.qmd.recipe.ingredient;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.particle.ParticleBeam;
import lach_01298.qmd.recipe.IParticleIngredient;
import nc.recipe.IngredientMatchResult;
import nc.recipe.IngredientSorption;
import net.minecraftforge.fluids.FluidStack;

public class ParticleArrayIngredient implements IParticleIngredient {
	
	public List<IParticleIngredient> ingredientList;
	public List<ParticleBeam> cachedStackList = new ArrayList<ParticleBeam>();
	
	public ParticleArrayIngredient(IParticleIngredient... ingredients) {
		this(Lists.newArrayList(ingredients));
	}
	
	public ParticleArrayIngredient(List<IParticleIngredient> ingredientList) {
		this.ingredientList = ingredientList;
		ingredientList.forEach(input -> cachedStackList.add(input.getStack()));
	}
	
	@Override
	public ParticleBeam getStack() {
		if (cachedStackList == null || cachedStackList.isEmpty() || cachedStackList.get(0) == null) return null;
		return cachedStackList.get(0).copy();
	}
	
	@Override
	public String getIngredientName() {
		//return ingredientList.get(0).getIngredientName();
		return getIngredientNamesConcat();
	}
	
	@Override
	public String getIngredientNamesConcat() {
		String names = "";
		for (IParticleIngredient ingredient : ingredientList) names += (", " + ingredient.getIngredientName());
		return "{ " + names.substring(2) + " }";
	}
	
	public String getIngredientRecipeString() {
		String names = "";
		for (IParticleIngredient ingredient : ingredientList) names += (", " + ingredient.getMaxStackSize(0) + " x " + ingredient.getIngredientName());
		return "{ " + names.substring(2) + " }";
	}
	
	@Override
	public int getMaxStackSize(int ingredientNumber) {
		return ingredientList.get(ingredientNumber).getMaxStackSize(0);
	}
	
	@Override
	public void setMaxStackSize(int stackSize) {
		for (IParticleIngredient ingredient : ingredientList) ingredient.setMaxStackSize(stackSize);
		for (ParticleBeam stack : cachedStackList) stack.setLuminosity(stackSize);
	}
	
	@Override
	public List<ParticleBeam> getInputStackList() {
		List<ParticleBeam> stacks = new ArrayList<ParticleBeam>();
		ingredientList.forEach(ingredient -> ingredient.getInputStackList().forEach(obj -> stacks.add(obj)));
		return stacks;
	}
	
	@Override
	public List<ParticleBeam> getOutputStackList() {
		if (cachedStackList == null || cachedStackList.isEmpty()) return new ArrayList<ParticleBeam>();
		return Lists.newArrayList(getStack());
	}
	
	@Override
	public IngredientMatchResult match(Object object, IngredientSorption sorption) {
		for (int i = 0; i < ingredientList.size(); i++) {
			if (ingredientList.get(i).match(object, sorption).matches()) return new IngredientMatchResult(true, i);
		}
		return IngredientMatchResult.FAIL;
	}
	
	@Override
	public boolean isValid() {
		return cachedStackList != null && !cachedStackList.isEmpty();
	}
}
