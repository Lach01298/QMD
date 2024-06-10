package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.*;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDecayChamberController extends ContainerMultiblockController<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileDecayChamberController, TileContainerInfo<TileDecayChamberController>>
{
	public ContainerDecayChamberController(EntityPlayer player, TileDecayChamberController controller)
	{
		super(player, controller);
		
	}
}
