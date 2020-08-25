package lach_01298.qmd.containment.tile;

import lach_01298.qmd.containment.Containment;
import nc.multiblock.tile.ILogicMultiblockController;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IContainmentController extends IContainmentPart, ILogicMultiblockController<Containment> 
{

	public void updateBlockState(boolean isActive);

	public NonNullList<ItemStack> getInventoryStacks();

	public boolean isRenderer();
	
	public void setIsRenderer(boolean isRenderer);

}
