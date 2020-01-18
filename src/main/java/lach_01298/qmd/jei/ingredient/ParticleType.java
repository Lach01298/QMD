package lach_01298.qmd.jei.ingredient;

import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.recipe.IIngredientType;


public final class ParticleType {
	public static final IIngredientType<ParticleStack> Particle = () -> ParticleStack.class;


	private ParticleType() 
	{

	}
}