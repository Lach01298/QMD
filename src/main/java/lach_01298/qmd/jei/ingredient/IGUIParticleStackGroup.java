package lach_01298.qmd.jei.ingredient;

import javax.annotation.Nullable;

import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.item.ItemStack;

public interface IGUIParticleStackGroup extends IGuiIngredientGroup<ParticleStack>
{
	@Override
	void init(int slotIndex, boolean input, int xPosition, int yPosition);

	@Override
	void set(int slotIndex, @Nullable ParticleStack itemStack);

	@Override
	void addTooltipCallback(ITooltipCallback<ParticleStack> tooltipCallback);
}
