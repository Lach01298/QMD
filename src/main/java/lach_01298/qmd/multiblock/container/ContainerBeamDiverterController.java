package lach_01298.qmd.multiblock.container;


import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileBeamDiverterController;
import lach_01298.qmd.accelerator.tile.TileBeamSplitterController;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerBeamDiverterController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileBeamDiverterController>
{

	public ContainerBeamDiverterController(EntityPlayer player,TileBeamDiverterController controller)
	{
		super(player, controller);
	}
}