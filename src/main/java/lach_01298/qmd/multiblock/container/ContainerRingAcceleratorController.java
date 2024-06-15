package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerRingAcceleratorController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileRingAcceleratorController, TileContainerInfo<TileRingAcceleratorController>>
{

	public ContainerRingAcceleratorController(EntityPlayer player,TileRingAcceleratorController controller)
	{
		super(player, controller);
	}
}
