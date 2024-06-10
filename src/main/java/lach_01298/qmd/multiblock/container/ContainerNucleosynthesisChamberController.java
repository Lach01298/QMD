package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.*;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerNucleosynthesisChamberController extends ContainerMultiblockController<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileNucleosynthesisChamberController, TileContainerInfo<TileNucleosynthesisChamberController>>
{

	public ContainerNucleosynthesisChamberController(EntityPlayer player, TileNucleosynthesisChamberController controller)
	{
		super(player, controller);
	}
}
