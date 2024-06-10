package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDeceleratorController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileDeceleratorController, TileContainerInfo<TileDeceleratorController>>
{

	public ContainerDeceleratorController(EntityPlayer player, TileDeceleratorController controller)
	{
		super(player, controller);
	}
}
