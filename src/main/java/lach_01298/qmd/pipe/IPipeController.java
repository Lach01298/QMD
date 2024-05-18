package lach_01298.qmd.pipe;

import nc.tile.multiblock.ILogicMultiblockController;

public interface IPipeController<CONTROLLER extends IPipeController<CONTROLLER>> extends IPipePart, ILogicMultiblockController<Pipe, IPipePart, PipeUpdatePacket, CONTROLLER>
{

}
