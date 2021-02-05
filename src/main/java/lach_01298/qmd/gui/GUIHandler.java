package lach_01298.qmd.gui;


import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.accelerator.tile.TileBeamDiverterController;
import lach_01298.qmd.accelerator.tile.TileDeceleratorController;
import lach_01298.qmd.accelerator.tile.TileLinearAcceleratorController;
import lach_01298.qmd.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.machine.container.ContainerCreativeParticleSource;
import lach_01298.qmd.machine.container.ContainerIrradiator;
import lach_01298.qmd.machine.container.ContainerOreLeacher;
import lach_01298.qmd.machine.gui.GuiCreativeParticleSource;
import lach_01298.qmd.machine.gui.GuiIrradiator;
import lach_01298.qmd.machine.gui.GuiOreLeacher;
import lach_01298.qmd.machine.tile.TileQMDProcessor.TileIrradiator;
import lach_01298.qmd.machine.tile.TileQMDProcessor.TileOreLeacher;
import lach_01298.qmd.multiblock.container.ContainerAcceleratorSource;
import lach_01298.qmd.multiblock.container.ContainerBeamDiverterController;
import lach_01298.qmd.multiblock.container.ContainerBeamDumpController;
import lach_01298.qmd.multiblock.container.ContainerCollisionChamberController;
import lach_01298.qmd.multiblock.container.ContainerDecayChamberController;
import lach_01298.qmd.multiblock.container.ContainerDeceleratorController;
import lach_01298.qmd.multiblock.container.ContainerLinearAcceleratorController;
import lach_01298.qmd.multiblock.container.ContainerNeutralContainmentController;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import lach_01298.qmd.multiblock.container.ContainerTargetChamberController;
import lach_01298.qmd.multiblock.gui.GUIAcceleratorSource;
import lach_01298.qmd.multiblock.gui.GuiBeamDiverterController;
import lach_01298.qmd.multiblock.gui.GuiBeamDumpController;
import lach_01298.qmd.multiblock.gui.GuiCollisionChamberController;
import lach_01298.qmd.multiblock.gui.GuiDecayChamberController;
import lach_01298.qmd.multiblock.gui.GuiDeceleratorController;
import lach_01298.qmd.multiblock.gui.GuiLinearAcceleratorController;
import lach_01298.qmd.multiblock.gui.GuiNeutralContainmentController;
import lach_01298.qmd.multiblock.gui.GuiRingAcceleratorController;
import lach_01298.qmd.multiblock.gui.GuiTargetChamberController;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import lach_01298.qmd.particleChamber.tile.TileCollisionChamberController;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import nc.container.processor.ContainerMachineConfig;
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
			case GUI_ID.LINEAR_ACCELERATOR:
				if (tile instanceof TileLinearAcceleratorController)
					return new ContainerLinearAcceleratorController(player,  (TileLinearAcceleratorController) tile);
			
			case GUI_ID.RING_ACCELERATOR:
				if (tile instanceof TileRingAcceleratorController)
					return new ContainerRingAcceleratorController(player,  (TileRingAcceleratorController) tile);
			
			case GUI_ID.ACCELERATOR_SOURCE:
				if (tile instanceof TileAcceleratorSource)
					return new ContainerAcceleratorSource(player,  (TileAcceleratorSource)tile);
			
			case GUI_ID.TARGET_CHAMBER:
				if (tile instanceof TileTargetChamberController)
					return new ContainerTargetChamberController(player,  (TileTargetChamberController)tile);
				
			case GUI_ID.DECAY_CHAMBER:
				if (tile instanceof TileDecayChamberController)
					return new ContainerDecayChamberController(player,  (TileDecayChamberController)tile);
			case GUI_ID.ORE_LEACHER:
				if (tile instanceof TileOreLeacher)
					return new ContainerOreLeacher(player,  (TileOreLeacher)tile);
			
			case GUI_ID.ORE_LEACHER_SIDE_CONFIG:
				if (tile instanceof TileOreLeacher)
					return new ContainerMachineConfig(player,  (TileOreLeacher)tile);
			
			case GUI_ID.BEAM_DIVERTER:
				if (tile instanceof TileBeamDiverterController)
					return new ContainerBeamDiverterController(player,  (TileBeamDiverterController) tile);
				
			case GUI_ID.IRRADIATOR:
				if (tile instanceof TileIrradiator)
					return new ContainerIrradiator(player,  (TileIrradiator)tile);
			
			case GUI_ID.IRRADIATOR_SIDE_CONFIG:
				if (tile instanceof TileIrradiator)
					return new ContainerMachineConfig(player,  (TileIrradiator)tile);
			
			case GUI_ID.BEAM_DUMP:
				if (tile instanceof TileBeamDumpController)
					return new ContainerBeamDumpController(player,  (TileBeamDumpController)tile);
			
			case GUI_ID.NEUTRAL_CONTAINMENT:
				if (tile instanceof TileNeutralContainmentController)
					return new ContainerNeutralContainmentController(player,  (TileNeutralContainmentController)tile);
				
			case GUI_ID.DECELERATOR:
				if (tile instanceof TileDeceleratorController)
					return new ContainerDeceleratorController(player,  (TileDeceleratorController)tile);
				
			case GUI_ID.COLLISION_CHAMBER:
				if (tile instanceof TileCollisionChamberController)
					return new ContainerCollisionChamberController(player,  (TileCollisionChamberController)tile);
				
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
			case GUI_ID.LINEAR_ACCELERATOR:
				if (tile instanceof TileLinearAcceleratorController)
					return new GuiLinearAcceleratorController(player, (TileLinearAcceleratorController) tile);
				
			case GUI_ID.RING_ACCELERATOR:
				if (tile instanceof TileRingAcceleratorController)
					return new GuiRingAcceleratorController(player, (TileRingAcceleratorController) tile);
				
			case GUI_ID.ACCELERATOR_SOURCE:
				if (tile instanceof TileAcceleratorSource)
					return new GUIAcceleratorSource(player, (TileAcceleratorSource) tile);
			
			case GUI_ID.TARGET_CHAMBER:
				if (tile instanceof TileTargetChamberController)
					return new GuiTargetChamberController(player, (TileTargetChamberController) tile);
		
			case GUI_ID.DECAY_CHAMBER:
				if (tile instanceof TileDecayChamberController)
					return new GuiDecayChamberController(player, (TileDecayChamberController) tile);
			
			case GUI_ID.ORE_LEACHER:
			if (tile instanceof TileOreLeacher)
					return new GuiOreLeacher(player,(TileOreLeacher) tile);
			
			case GUI_ID.ORE_LEACHER_SIDE_CONFIG:
				if (tile instanceof TileOreLeacher)
					return new GuiOreLeacher.SideConfig(player,(TileOreLeacher) tile);
				
			case GUI_ID.BEAM_DIVERTER:
				if (tile instanceof TileBeamDiverterController)
					return new GuiBeamDiverterController(player, (TileBeamDiverterController) tile);
			
			case GUI_ID.IRRADIATOR:
				if (tile instanceof TileIrradiator)
						return new GuiIrradiator(player,(TileIrradiator) tile);
				
			case GUI_ID.IRRADIATOR_SIDE_CONFIG:
				if (tile instanceof TileIrradiator)
					return new GuiIrradiator.SideConfig(player,(TileIrradiator) tile);
			
			case GUI_ID.BEAM_DUMP:
				if (tile instanceof TileBeamDumpController)
					return new GuiBeamDumpController(player, (TileBeamDumpController) tile);
			
			case GUI_ID.NEUTRAL_CONTAINMENT:
				if (tile instanceof TileNeutralContainmentController)
					return new GuiNeutralContainmentController(player, (TileNeutralContainmentController) tile);
				
			case GUI_ID.DECELERATOR:
				if (tile instanceof TileDeceleratorController)
					return new GuiDeceleratorController(player, (TileDeceleratorController) tile);
				
			case GUI_ID.COLLISION_CHAMBER:
				if (tile instanceof TileCollisionChamberController)
					return new GuiCollisionChamberController(player, (TileCollisionChamberController) tile);
			
			case GUI_ID.CREATIVE_SOURCE:
				if (tile instanceof TileCreativeParticleSource)
					return new GuiCreativeParticleSource(player, (TileCreativeParticleSource) tile);
				
			}
		}

		return null;
	}

}
