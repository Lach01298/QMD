package lach_01298.qmd.gui;

import lach_01298.qmd.container.ContainerIonEmitter;
import lach_01298.qmd.container.ContainerLinearAccelerator;
import lach_01298.qmd.container.ContainerWorkTable;
import lach_01298.qmd.tile.TileIonEmitter;
import lach_01298.qmd.tile.TileLinearAcceleratorController;
import lach_01298.qmd.tile.TileWorkTable;
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
			case GUI_ID.linAcc:
				if (tile instanceof TileLinearAcceleratorController)
					return new ContainerLinearAccelerator(player, (IInventory) tile);
				
			case GUI_ID.IonEmitter:
				if (tile instanceof TileIonEmitter)
					return new ContainerIonEmitter(player, (IInventory) tile);
			
			case GUI_ID.WORKTABLE:
				if (tile instanceof TileWorkTable)
					return new ContainerWorkTable(player, (TileWorkTable) tile);
			
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
			case GUI_ID.linAcc:
				if (tile instanceof TileLinearAcceleratorController)
					return new GUILinearAccelerator(player, (TileLinearAcceleratorController) tile);
				
			case GUI_ID.IonEmitter:
				if (tile instanceof TileIonEmitter)
					return new GUIIonEmitter(player, (TileIonEmitter) tile);
			
			case GUI_ID.WORKTABLE:
				if (tile instanceof TileWorkTable)
					return new GUIWorkTable(player, (TileWorkTable) tile);
			}
		}

		return null;
	}

}
