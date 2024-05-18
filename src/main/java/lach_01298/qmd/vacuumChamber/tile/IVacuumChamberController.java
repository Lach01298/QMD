package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.tile.multiblock.ILogicMultiblockController;

public interface IVacuumChamberController<CONTROLLER extends IVacuumChamberController<CONTROLLER>> extends IVacuumChamberPart, ILogicMultiblockController<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, CONTROLLER>
{

}
