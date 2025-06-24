package lach_01298.qmd.groovyscript.particle;

import com.cleanroommc.groovyscript.api.GroovyLog;
import com.cleanroommc.groovyscript.api.documentation.annotations.Property;
import com.cleanroommc.groovyscript.api.documentation.annotations.RecipeBuilderMethodDescription;
import com.cleanroommc.groovyscript.helper.recipe.AbstractRecipeBuilder;
import lach_01298.qmd.particle.ParticleStack;

import java.util.Collection;

public abstract class ParticleRecipeBuilder<T> extends AbstractRecipeBuilder<T>
{

	@Property(value = "groovyscript.wiki.qmd.particleInput.value", needsOverride = true, priority = 300, hierarchy = 20)
	protected final ParticleStackList particleInput = new ParticleStackList();
	@Property(value = "groovyscript.wiki.qmd.particleOutput.value", needsOverride = true, priority = 800, hierarchy = 20)
	protected final ParticleStackList particleOutput = new ParticleStackList();

	@RecipeBuilderMethodDescription
	public ParticleRecipeBuilder<T> particleInput(ParticleStack particle)
	{
		this.particleInput.add(particle);
		return this;
	}

	@RecipeBuilderMethodDescription
	public ParticleRecipeBuilder<T> particleInput(Collection<ParticleStack> particles)
	{
		for (ParticleStack particle : particles)
		{
			particleInput(particle);
		}
		return this;
	}

	@RecipeBuilderMethodDescription
	public ParticleRecipeBuilder<T> particleInput(ParticleStack... particles)
	{
		for (ParticleStack particle : particles)
		{
			particleInput(particle);
		}
		return this;
	}

	@RecipeBuilderMethodDescription
	public ParticleRecipeBuilder<T> particleOutput(ParticleStack particle)
	{
		this.particleOutput.add(particle);
		return this;
	}

	@RecipeBuilderMethodDescription
	public ParticleRecipeBuilder<T> particleOutput(Collection<ParticleStack> particles)
	{
		for (ParticleStack particle : particles)
		{
			particleOutput(particle);
		}
		return this;
	}

	@RecipeBuilderMethodDescription
	public ParticleRecipeBuilder<T> particleOutput(ParticleStack... particles)
	{
		for (ParticleStack particle : particles)
		{
			particleOutput(particle);
		}
		return this;
	}


	public void validateParticles(GroovyLog.Msg msg, int minInput, int maxInput, int minOutput, int maxOutput)
	{
		particleInput.trim();
		particleOutput.trim();
		validateCustom(msg, particleInput, minInput, maxInput, "particle input");
		validateCustom(msg, particleOutput, minOutput, maxOutput, "particle output");
	}

	public void validateParticles(GroovyLog.Msg msg)
	{
		validateParticles(msg, 0, 0, 0, 0);
	}
}