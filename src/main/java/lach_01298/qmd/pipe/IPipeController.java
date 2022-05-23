package lach_01298.qmd.pipe;

import nc.multiblock.tile.ILogicMultiblockController;

public interface IPipeController<CONTROLLER extends IPipeController<CONTROLLER>> extends IPipePart, ILogicMultiblockController<Pipe, IPipePart, PipeUpdatePacket, CONTROLLER> 
{

}
