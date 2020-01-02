package lach_01298.qmd.multiblock.container;


import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerRingAcceleratorController extends ContainerMultiblockController<Accelerator, IAcceleratorController>
{

	public ContainerRingAcceleratorController(EntityPlayer player,IAcceleratorController controller)
	{
		super(player, controller);
	}
}