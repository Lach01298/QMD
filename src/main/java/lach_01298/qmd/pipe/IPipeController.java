package lach_01298.qmd.pipe;

import nc.tile.TileContainerInfo;
import nc.tile.multiblock.ILogicMultiblockController;
import net.minecraft.tileentity.TileEntity;

public interface IPipeController<CONTROLLER extends TileEntity & IPipeController<CONTROLLER>> extends IPipePart, ILogicMultiblockController<Pipe, IPipePart, PipeUpdatePacket, CONTROLLER, TileContainerInfo<CONTROLLER>>
{

}
