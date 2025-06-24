package lach_01298.qmd.groovyscript.particle;

import com.cleanroommc.groovyscript.api.IIngredient;
import lach_01298.qmd.particle.ParticleStack;
import org.jetbrains.annotations.Nullable;

public class QMDIngredientHelper
{


	public static boolean isEmpty(@Nullable IIngredient ingredient)
	{
		return ingredient == null || ingredient.isEmpty();
	}

	public static boolean isEmpty(@Nullable ParticleStack particleStack)
	{
		return particleStack == null || particleStack.getAmount() <= 0;
	}


}
