package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.particleChamber.tile.TileCollisionChamberController;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerDecayChamberController extends ContainerMultiblockController<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileDecayChamberController>
{
	public ContainerDecayChamberController(EntityPlayer player, TileDecayChamberController controller)
	{
		super(player, controller);
		
	}
}
