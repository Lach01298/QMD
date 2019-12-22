package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerAcceleratorSource extends Container
{
	public ContainerAcceleratorSource(EntityPlayer player, TileAcceleratorSource tile)
	{
		super();
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
