package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
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
			ParticleStack p = new ParticleStack(particle);

			List<ParticleStack> c = new ArrayList<ParticleStack>();
			if (particle.hasComponentParticles())
			{
				for (Entry<Particle, Integer> component : particle.getComponentParticles().entrySet())
				{		
					c.add(new ParticleStack(component.getKey(), component.getValue()));
				}
			}
			ParticleInfoRecipe jeiRecipe = new ParticleInfoRecipe(p, c);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
