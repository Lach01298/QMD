package lach_01298.qmd.multiblock.container;


import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDeceleratorController extends ContainerMultiblockController<Accelerator, IAcceleratorController>
{

	public ContainerDeceleratorController(EntityPlayer player,IAcceleratorController controller)
	{
		super(player, controller);
	}
}