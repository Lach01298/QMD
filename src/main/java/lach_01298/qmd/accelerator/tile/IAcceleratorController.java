package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.multiblock.tile.ILogicMultiblockController;

public interface IAcceleratorController<CONTROLLER extends IAcceleratorController<CONTROLLER>> extends IAcceleratorPart, ILogicMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, CONTROLLER> 
{
}
