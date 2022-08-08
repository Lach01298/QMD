package lach_01298.qmd.multiblock.container;


import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileDeceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDeceleratorController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileDeceleratorController>
{

	public ContainerDeceleratorController(EntityPlayer player, TileDeceleratorController controller)
	{
		super(player, controller);
	}
}