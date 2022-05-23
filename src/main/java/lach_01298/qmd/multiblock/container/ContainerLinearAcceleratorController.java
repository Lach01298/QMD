package lach_01298.qmd.multiblock.container;


import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileLinearAcceleratorController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerLinearAcceleratorController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileLinearAcceleratorController>
{

	public ContainerLinearAcceleratorController(EntityPlayer player,TileLinearAcceleratorController controller)
	{
		super(player, controller);
	}
}
