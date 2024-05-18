package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.container.multiblock.controller.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDeceleratorController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileDeceleratorController>
{

	public ContainerDeceleratorController(EntityPlayer player, TileDeceleratorController controller)
	{
		super(player, controller);
	}
}
