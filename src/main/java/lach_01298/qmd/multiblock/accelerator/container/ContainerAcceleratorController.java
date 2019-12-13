package lach_01298.qmd.multiblock.accelerator.container;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerAcceleratorController extends ContainerMultiblockController<Accelerator, TileAcceleratorController>
{

	public ContainerAcceleratorController(EntityPlayer player, TileAcceleratorController controller)
	{
		super(player, controller);
	}
}
