package lach_01298.qmd.jei;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.Collections;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.google.common.base.MoreObjects;

import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleBeam;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.color.ColorGetter;

public class ParticleBeamHelper implements IIngredientHelper<ParticleBeam>
{
	@Override
	@Nullable
	public ParticleBeam getMatch(Iterable<ParticleBeam> ingredients, ParticleBeam toMatch)
	{
		for (ParticleBeam particleBeam : ingredients)
		{
			if (toMatch.getParticle() == particleBeam.getParticle())
			{
				return particleBeam;
			}
		}
		return null;
	}

	@Override
	public String getDisplayName(ParticleBeam ingredient)
	{
		return ingredient.getParticle().getUnlocalizedName();
	}

	@Override
	public String getUniqueId(ParticleBeam ingredient)
	{

		return "particle:" + ingredient.getParticle().getName();
	}

	@Override
	public String getWildcardId(ParticleBeam ingredient)
	{
		return getUniqueId(ingredient);
	}

	@Override
	public String getModId(ParticleBeam ingredient)
	{
			return "";
	}

	@Override
	public Iterable<Color> getColors(ParticleBeam ingredient)
	{
		Particle particle = ingredient.getParticle();
		TextureMap textureMapBlocks = Minecraft.getMinecraft().getTextureMapBlocks();
		ResourceLocation texture = particle.getTexture();
		if (texture != null)
		{
			TextureAtlasSprite particleSprite = textureMapBlocks.getTextureExtry(texture.toString());
			if (particleSprite != null)
			{
				int renderColor = ingredient.getParticle().getColor();
				return ColorGetter.getColors(particleSprite, renderColor, 1);
			}
		}
		return Collections.emptyList();
	}

	@Override
	public String getResourceId(ParticleBeam ingredient)
	{

		ResourceLocation particleResourceName = ingredient.getParticle().getTexture();
		return particleResourceName.getPath();
	}

	@Override
	public ItemStack getCheatItemStack(ParticleBeam ingredient)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public ParticleBeam copyIngredient(ParticleBeam ingredient)
	{
		return ingredient.copy();
	}

	@Override
	public String getErrorInfo(@Nullable ParticleBeam ingredient)
	{
		if (ingredient == null)
		{
			return "null";
		}
		MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(ParticleBeam.class);

		Particle particle = ingredient.getParticle();
		if (particle != null)
		{
			toStringHelper.add("Particle", particle.getName());
		}
		else
		{
			toStringHelper.add("Particle", "null");
		}

		toStringHelper.add("Luminosity", ingredient.getLuminosity());
		toStringHelper.add("Energy", ingredient.getMeanEnergy());


		return toStringHelper.toString();
	}
}