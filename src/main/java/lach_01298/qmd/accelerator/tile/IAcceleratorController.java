package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.multiblock.ILogicMultiblockController;
import net.minecraft.tileentity.TileEntity;

public interface IAcceleratorController<CONTROLLER extends TileEntity & IAcceleratorController<CONTROLLER>> extends IAcceleratorPart, ILogicMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, CONTROLLER, TileContainerInfo<CONTROLLER>>
{
}
