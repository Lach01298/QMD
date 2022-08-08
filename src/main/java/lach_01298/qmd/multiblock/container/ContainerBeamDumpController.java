package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerBeamDumpController extends ContainerMultiblockController<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileBeamDumpController>
{
	public ContainerBeamDumpController(EntityPlayer player, TileBeamDumpController controller)
	{
		super(player, controller);
		
	}
}
