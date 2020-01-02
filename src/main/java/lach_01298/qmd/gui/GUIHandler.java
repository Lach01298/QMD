package lach_01298.qmd.gui;


import lach_01298.qmd.multiblock.accelerator.tile.TileLinearAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.container.ContainerAcceleratorSource;
import lach_01298.qmd.multiblock.container.ContainerLinearAcceleratorController;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import lach_01298.qmd.multiblock.gui.GUIAcceleratorSource;
import lach_01298.qmd.multiblock.gui.GuiLinearAcceleratorController;
import lach_01298.qmd.multiblock.gui.GuiRingAcceleratorController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

		if (tile != null)
		{
			switch (ID)
			{
			case GUI_ID.LINEAR_ACCELERATOR:
				if (tile instanceof TileLinearAcceleratorController)
					return new ContainerLinearAcceleratorController(player,  (TileLinearAcceleratorController) tile);
			
			case GUI_ID.RING_ACCELERATOR:
				if (tile instanceof TileRingAcceleratorController)
					return new ContainerRingAcceleratorController(player,  (TileRingAcceleratorController) tile);
			
			case GUI_ID.ACCELERATOR_SOURCE:
				if (tile instanceof TileAcceleratorSource)
					return new ContainerAcceleratorSource(player,  (TileAcceleratorSource)tile);
			
		
			
			}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
	
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

		if (tile != null)
		{
			switch (ID)
			{
			case GUI_ID.LINEAR_ACCELERATOR:
				if (tile instanceof TileLinearAcceleratorController)
					return new GuiLinearAcceleratorController(((TileLinearAcceleratorController) tile).getMultiblock(), tile.getPos(), ((TileLinearAcceleratorController) tile).getMultiblock().getContainer(player));
				
			case GUI_ID.RING_ACCELERATOR:
				if (tile instanceof TileRingAcceleratorController)
					return new GuiRingAcceleratorController(((TileRingAcceleratorController) tile).getMultiblock(), tile.getPos(), ((TileRingAcceleratorController) tile).getMultiblock().getContainer(player));
				
			case GUI_ID.ACCELERATOR_SOURCE:
				if (tile instanceof TileAcceleratorSource)
					return new GUIAcceleratorSource(player, (TileAcceleratorSource) tile);
			
		
			}
		}

		return null;
	}

}
