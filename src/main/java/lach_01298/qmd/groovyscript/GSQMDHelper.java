package lach_01298.qmd.groovyscript;

import com.cleanroommc.groovyscript.api.IIngredient;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipe.ingredient.ParticleIngredient;

public class GSQMDHelper
{


	public static IParticleIngredient buildAdditionParticleIngredient(Object object) {
		if (object != null && !object.equals(IIngredient.EMPTY)) {
			if (object instanceof IParticleIngredient) {
				IParticleIngredient particleIngredient = (IParticleIngredient)object;
				return particleIngredient;
			} else if (object instanceof ParticleStack) {
				ParticleStack stack = (ParticleStack)object;
				return QMDRecipeHelper.buildParticleIngredient(stack);
			} else {
				throw invalidIngredientException(object);
			}
		} else {
			return new EmptyParticleIngredient();
		}
	}

	public static IParticleIngredient buildRemovalParticleIngredient(Object object) {
		if (object != null && !object.equals(IIngredient.EMPTY)) {
			if (object instanceof IParticleIngredient) {
				IParticleIngredient particleIngredient = (IParticleIngredient)object;
				return particleIngredient;
			} else if (object instanceof ParticleStack) {
				ParticleStack stack = (ParticleStack)object;
				return new ParticleIngredient(stack);
			} else {
				throw invalidIngredientException(object);
			}
		} else {
			return new EmptyParticleIngredient();
		}
	}

	public static RuntimeException invalidIngredientException(Object object) {
		return new IllegalArgumentException(String.format("QMD: Invalid ingredient: %s, %s", object.getClass().getName(), object));
	}

}
