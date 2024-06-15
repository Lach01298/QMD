package lach_01298.qmd.gui;

import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.machine.container.*;
import lach_01298.qmd.machine.gui.*;
import lach_01298.qmd.machine.tile.TileQMDProcessor.*;
import lach_01298.qmd.multiblock.container.ContainerAcceleratorIonSource;
import lach_01298.qmd.multiblock.gui.GUIAcceleratorIonSource;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import nclegacy.container.ContainerMachineConfigLegacy;
import net.minecraft.entity.player.EntityPlayer;
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
			case GUI_ID.ACCELERATOR_SOURCE:
				if (tile instanceof TileAcceleratorIonSource)
					return new ContainerAcceleratorIonSource(player,  (TileAcceleratorIonSource)tile);
			
			case GUI_ID.ORE_LEACHER:
				if (tile instanceof TileOreLeacher)
					return new ContainerOreLeacher(player,  (TileOreLeacher)tile);
			
			case GUI_ID.ORE_LEACHER_SIDE_CONFIG:
				if (tile instanceof TileOreLeacher)
					return new ContainerMachineConfigLegacy(player,  (TileOreLeacher)tile);
			
			case GUI_ID.IRRADIATOR:
				if (tile instanceof TileIrradiator)
					return new ContainerIrradiator(player,  (TileIrradiator)tile);
			
			case GUI_ID.IRRADIATOR_SIDE_CONFIG:
				if (tile instanceof TileIrradiator)
					return new ContainerMachineConfigLegacy(player,  (TileIrradiator)tile);
				
			case GUI_ID.CREATIVE_SOURCE:
				if (tile instanceof TileCreativeParticleSource)
					return new ContainerCreativeParticleSource(player,  (TileCreativeParticleSource)tile);
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
			case GUI_ID.ACCELERATOR_SOURCE:
				if (tile instanceof TileAcceleratorIonSource)
					return new GUIAcceleratorIonSource(player, (TileAcceleratorIonSource) tile);
			
			case GUI_ID.ORE_LEACHER:
			if (tile instanceof TileOreLeacher)
					return new GuiOreLeacher(player,(TileOreLeacher) tile);
			
			case GUI_ID.ORE_LEACHER_SIDE_CONFIG:
				if (tile instanceof TileOreLeacher)
					return new GuiOreLeacher.SideConfig(player,(TileOreLeacher) tile);
				
			case GUI_ID.IRRADIATOR:
				if (tile instanceof TileIrradiator)
						return new GuiIrradiator(player,(TileIrradiator) tile);
				
			case GUI_ID.IRRADIATOR_SIDE_CONFIG:
				if (tile instanceof TileIrradiator)
					return new GuiIrradiator.SideConfig(player,(TileIrradiator) tile);
			
			case GUI_ID.CREATIVE_SOURCE:
				if (tile instanceof TileCreativeParticleSource)
					return new GuiCreativeParticleSource(player, (TileCreativeParticleSource) tile);
			}
		}
		
		return null;
	}

}
