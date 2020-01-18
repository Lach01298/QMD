package lach_01298.qmd.multiblock.particleChamber.tile;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import nc.multiblock.ILogicMultiblockController;

public interface IParticleChamberController extends IParticleChamberPart, ILogicMultiblockController<ParticleChamber> 
{

	public void updateBlockState(boolean isActive);
	

}
