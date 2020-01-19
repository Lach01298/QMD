package lach_01298.qmd.multiblock.particleChamber.tile;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import nc.multiblock.ILogicMultiblockController;
import nc.tile.inventory.ITileInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IParticleChamberController extends IParticleChamberPart, ILogicMultiblockController<ParticleChamber>, ITileInventory 
{

	public void updateBlockState(boolean isActive);

	

}
