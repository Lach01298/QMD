package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.tile.TileContainerInfo;
import nc.tile.multiblock.ILogicMultiblockController;
import net.minecraft.tileentity.TileEntity;

public interface IVacuumChamberController<CONTROLLER extends TileEntity & IVacuumChamberController<CONTROLLER>> extends IVacuumChamberPart, ILogicMultiblockController<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, CONTROLLER, TileContainerInfo<CONTROLLER>>
{

}
