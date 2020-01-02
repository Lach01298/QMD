package lach_01298.qmd.jei;

import net.minecraft.enchantment.EnchantmentData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraft.item.ItemStack;
import lach_01298.qmd.particle.ParticleBeam;
import mezz.jei.api.recipe.IIngredientType;


public final class ParticleType {
	public static final IIngredientType<ParticleBeam> Particle = () -> ParticleBeam.class;


	private ParticleType() 
	{

	}
}