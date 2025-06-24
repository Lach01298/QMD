package lach_01298.qmd.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.GroovyLog;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lach_01298.qmd.groovyscript.particle.ParticleRecipeBuilder;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

public class GSQMDRecipeBuilder<BUILDER extends GSQMDRecipeBuilder<BUILDER>> extends ParticleRecipeBuilder<QMDRecipe>
{
	public final GSQMDRecipeRegistry registry;
	public final ObjectArrayList<Object> extras = new ObjectArrayList();

	public GSQMDRecipeBuilder(GSQMDRecipeRegistry registry)
	{
		this.registry = registry;
	}

	@SuppressWarnings("unchecked")
	protected BUILDER getThis()
	{
		return (BUILDER) this;
	}

	@GroovyBlacklist
	protected QMDRecipeHandler getRecipeHandler()
	{
		return this.registry.getRecipeHandler();
	}

	public BUILDER setExtra(int index, Object extra)
	{
		this.extras.ensureCapacity(index + 1);
		if (this.extras.size() <= index)
		{
			this.extras.size(index + 1);
		}

		this.extras.set(index, extra);
		return this.getThis();
	}

	public String getErrorMsg()
	{
		return "Error building QMD " + this.getRecipeHandler().getName() + " recipe";
	}

	public void validate(GroovyLog.Msg msg)
	{
		QMDRecipeHandler recipeHandler = this.getRecipeHandler();
		validateItems(msg, 0, recipeHandler.itemInputSize, 0, recipeHandler.itemOutputSize);
		validateFluids(msg, 0, recipeHandler.fluidInputSize, 0, recipeHandler.fluidOutputSize);
		validateParticles(msg, 0, recipeHandler.particleInputSize, 0, recipeHandler.particleOutputSize);
	}

	@Override
	public @Nullable QMDRecipe register() {
		if (!validate()) {
			return null;
		}
		else {
			return registry.addRecipeInternal(Stream.of(input, fluidInput,particleInput, output, fluidOutput, particleOutput, extras).flatMap(List::stream).toArray());
		}
	}
}