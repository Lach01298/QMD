package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ParticleInfoRecipeMaker
{
	private ParticleInfoRecipeMaker()
	{
	}

	public static List<ParticleInfoRecipe> getRecipes(IJeiHelpers helpers)
	{
		Map<String, Particle> particles = Particles.list;

		List<ParticleInfoRecipe> jeiRecipes = new ArrayList<>();

		for (Particle particle : particles.values())
		{
			ParticleStack p = new ParticleStack(particle, 0, 1);

			List<ParticleStack> c = new ArrayList<ParticleStack>();
			if (particle.hasComponentParticles())
			{
				List<Particle> used = new ArrayList<Particle>();
				for (Particle component : particle.getComponentParticles())
				{
					if (used.contains(component))
					{
						continue;
					}
					c.add(new ParticleStack(component, 0, Collections.frequency(particle.getComponentParticles(), component)));
					used.add(component);
				}

			}
			ParticleInfoRecipe jeiRecipe = new ParticleInfoRecipe(p, c);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
