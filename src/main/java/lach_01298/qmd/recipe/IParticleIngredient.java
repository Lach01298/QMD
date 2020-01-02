package lach_01298.qmd.recipe;

import java.util.List;

import lach_01298.qmd.particle.ParticleBeam;
import nc.recipe.ingredient.IIngredient;
import net.minecraftforge.fluids.FluidStack;

public interface IParticleIngredient extends IIngredient<ParticleBeam>
{
	@Override
	public default ParticleBeam getNextStack(int ingredientNumber) {
		ParticleBeam nextStack = getStack();
		nextStack.setLuminosity( getNextStackSize(ingredientNumber));
		return nextStack;
	}
	
	@Override
	public default List<ParticleBeam> getInputStackHashingList() {
		return getInputStackList();
	}
}
