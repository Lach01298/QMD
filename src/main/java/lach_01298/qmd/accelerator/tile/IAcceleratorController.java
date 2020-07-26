package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import nc.multiblock.tile.ILogicMultiblockController;

public interface IAcceleratorController extends IAcceleratorPart, ILogicMultiblockController<Accelerator> 
{

	public void updateBlockState(boolean isActive);
	
	public void doMeltdown();
}
