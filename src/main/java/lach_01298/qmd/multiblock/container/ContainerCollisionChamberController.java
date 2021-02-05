package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerCollisionChamberController extends ContainerMultiblockController<ParticleChamber, IParticleChamberController>
{
	public ContainerCollisionChamberController(EntityPlayer player, IParticleChamberController controller)
	{
		super(player, controller);
		
	}
}
