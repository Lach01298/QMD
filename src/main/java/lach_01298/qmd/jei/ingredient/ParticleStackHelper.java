package lach_01298.qmd.jei.ingredient;

import java.awt.Color;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;

import lach_01298.qmd.QMD;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.ingredients.IIngredientHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ParticleStackHelper implements IIngredientHelper<ParticleStack>
{
	@Override
	@Nullable
	public ParticleStack getMatch(Iterable<ParticleStack> ingredients, ParticleStack toMatch)
	{
		for (ParticleStack particleStack : ingredients)
		{
			if(particleStack == null) continue;
			if (toMatch.getParticle() == particleStack.getParticle())
			{
				return particleStack;
			}
		}
		return null;
	}

	@Override
	public String getDisplayName(ParticleStack ingredient)
	{
		return ingredient.getParticle().getUnlocalizedName();
	}

	@Override
	public String getUniqueId(ParticleStack ingredient)
	{

		return "particle:" + ingredient.getParticle().getName();
	}

	@Override
	public String getWildcardId(ParticleStack ingredient)
	{
		return getUniqueId(ingredient);
	}

	@Override
	public String getModId(ParticleStack ingredient)
	{
			return QMD.MOD_ID;
	}

	@Override
	public Iterable<Color> getColors(ParticleStack ingredient)
	{
		return null;
	}

	@Override
	public String getResourceId(ParticleStack ingredient)
	{

		ResourceLocation particleResourceName = ingredient.getParticle().getTexture();
		return particleResourceName.getPath();
	}

	@Override
	public ItemStack getCheatItemStack(ParticleStack ingredient)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public ParticleStack copyIngredient(ParticleStack ingredient)
	{
		return ingredient.copy();
	}

	@Override
	public String getErrorInfo(@Nullable ParticleStack ingredient)
	{
		if (ingredient == null)
		{
			return "null";
		}
		MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(ParticleStack.class);

		Particle particle = ingredient.getParticle();
		if (particle != null)
		{
			toStringHelper.add("Particle", particle.getName());
		}
		else
		{
			toStringHelper.add("Particle", "null");
		}

		toStringHelper.add("amount", ingredient.getAmount());
		toStringHelper.add("Energy", ingredient.getMeanEnergy());
		toStringHelper.add("Focus", ingredient.getFocus());


		return toStringHelper.toString();
	}
}