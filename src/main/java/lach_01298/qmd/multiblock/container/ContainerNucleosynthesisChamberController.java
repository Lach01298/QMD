package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerNucleosynthesisChamberController extends ContainerMultiblockController<VacuumChamber, IVacuumChamberController>
{

	public ContainerNucleosynthesisChamberController(EntityPlayer player, IVacuumChamberController controller)
	{
		super(player, controller);
	}
}
