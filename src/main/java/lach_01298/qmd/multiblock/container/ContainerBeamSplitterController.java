package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerBeamSplitterController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileBeamSplitterController, TileContainerInfo<TileBeamSplitterController>>
{

	public ContainerBeamSplitterController(EntityPlayer player,TileBeamSplitterController controller)
	{
		super(player, controller);
	}
}
