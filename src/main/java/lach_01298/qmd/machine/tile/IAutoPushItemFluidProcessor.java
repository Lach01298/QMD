package lach_01298.qmd.machine.tile;

import nc.tile.fluid.ITileFluid;
import nc.tile.internal.fluid.Tank;
import nc.tile.inventory.ITileInventory;
import nc.tile.processor.IProcessor;
import nclegacy.tile.IItemFluidProcessorLegacy;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface IAutoPushItemFluidProcessor extends ITickable, ITileInventory, ITileFluid, IItemFluidProcessorLegacy
{

	default boolean autoPush()
	{
		IProcessor.HandlerPair[] adjacentHandlers = getAdjacentHandlers();
		if (adjacentHandlers == null)
		{
			return false;
		}

		List<EnumFacing> dirs = new ArrayList<>();
		for (int i = 0; i < 6; ++i)
		{
			if (adjacentHandlers[i] != null)
			{
				dirs.add(EnumFacing.VALUES[i]);
			}
		}

		return autoPushInternal(adjacentHandlers, getInventoryStacks(), getTanks(), dirs, dirs.size(), (int) getTileWorld().getTotalWorldTime());
	}

	default boolean autoPushInternal(IProcessor.HandlerPair[] adjacentHandlers, NonNullList<ItemStack> stacks, List<Tank> tanks, List<EnumFacing> dirs, int dirCount, int indexOffset)
	{
		boolean pushed = false;

		for (int i = 0; i < getItemInputSize(); ++i)
		{
			pushed |= tryPushSlot(adjacentHandlers, stacks, i, dirs, dirCount, indexOffset);
		}
		for (int i = 0; i < getFluidInputSize(); ++i)
		{
			pushed |= tryPushTank(adjacentHandlers, tanks, i, dirs, dirCount, indexOffset);
		}
		for (int i = 0; i < getItemOutputSize(); ++i)
		{
			pushed |= tryPushSlot(adjacentHandlers, stacks, i + getItemInputSize(), dirs, dirCount, indexOffset);
		}
		for (int i = 0; i < getFluidOutputputSize(); ++i)
		{
			pushed |= tryPushTank(adjacentHandlers, tanks,i + getFluidInputSize(), dirs, dirCount, indexOffset);
		}

		return pushed;
	}



	default @Nullable IProcessor.HandlerPair[] getAdjacentHandlers()
	{
		return null;
	}

	default void updateAdjacentHandlers()
	{
	}


}
