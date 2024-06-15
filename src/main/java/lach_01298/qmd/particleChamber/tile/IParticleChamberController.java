package lach_01298.qmd.particleChamber.tile;

import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.tile.TileContainerInfo;
import nc.tile.multiblock.ILogicMultiblockController;
import net.minecraft.tileentity.TileEntity;

public interface IParticleChamberController<CONTROLLER extends TileEntity & IParticleChamberController<CONTROLLER>> extends IParticleChamberPart, ILogicMultiblockController<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, CONTROLLER, TileContainerInfo<CONTROLLER>>
{


}
