
package lach_01298.qmd.fluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lach_01298.qmd.QMD;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class FluidContainerItemWrapper implements ICapabilityProvider
{

	final ItemStack stack;
	final IFluidContainerItem container;
	final boolean canFill;
	final boolean canDrain;

	public FluidContainerItemWrapper(ItemStack stackIn, IFluidContainerItem containerIn)
	{

		stack = stackIn;
		container = containerIn;
		canFill = true;
		canDrain = true;
	}

	public FluidContainerItemWrapper(ItemStack stackIn, IFluidContainerItem containerIn, boolean canFillIn,
			boolean canDrainIn)
	{

		stack = stackIn;
		container = containerIn;
		canFill = canFillIn;
		canDrain = canDrainIn;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing from)
	{

		return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, final EnumFacing from)
	{

		if (!hasCapability(capability, from))
		{
			return null;
		}
		return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(new IFluidHandlerItem()
		{

			@Override
			public IFluidTankProperties[] getTankProperties()
			{

				return new IFluidTankProperties[] { new FluidTankProperties(container.getFluid(stack),
						container.getCapacity(stack), canFill, canDrain) };
			}

			@Override
			public int fill(FluidStack resource, boolean doFill)
			{

				return container.fill(stack, resource, doFill);
			}

			@Nullable
			@Override
			public FluidStack drain(FluidStack resource, boolean doDrain)
			{
				if(resource != null && container.getFluid(stack) != null)
				{
					if (resource.getFluid() == container.getFluid(stack).getFluid())
					{
						return container.drain(stack, resource.amount, doDrain);
					}
				}
		
				return null;
			}

			@Nullable
			@Override
			public FluidStack drain(int maxDrain, boolean doDrain)
			{

				return container.drain(stack, maxDrain, doDrain);
			}

			@Nonnull
			@Override
			public ItemStack getContainer()
			{

				return stack;
			}

		});
	}

}