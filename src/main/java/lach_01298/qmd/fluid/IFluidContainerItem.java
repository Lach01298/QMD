package lach_01298.qmd.fluid;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


public interface IFluidContainerItem 
{

	FluidStack getFluid(ItemStack container);

	int getCapacity(ItemStack container);

	int fill(ItemStack container, FluidStack resource, boolean doFill);

	FluidStack drain(ItemStack container, int maxDrain, boolean doDrain);

}