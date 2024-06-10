package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.*;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerBeamDumpController extends ContainerMultiblockController<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileBeamDumpController, TileContainerInfo<TileBeamDumpController>>
{
	public ContainerBeamDumpController(EntityPlayer player, TileBeamDumpController controller)
	{
		super(player, controller);
		
	}
}
