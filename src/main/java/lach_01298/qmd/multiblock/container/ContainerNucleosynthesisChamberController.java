package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberPart;
import lach_01298.qmd.vacuumChamber.tile.TileNucleosynthesisChamberController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerNucleosynthesisChamberController extends ContainerMultiblockController<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileNucleosynthesisChamberController>
{

	public ContainerNucleosynthesisChamberController(EntityPlayer player, TileNucleosynthesisChamberController controller)
	{
		super(player, controller);
	}
}
