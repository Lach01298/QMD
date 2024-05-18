package lach_01298.qmd.particleChamber.tile;

import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.tile.multiblock.ILogicMultiblockController;

public interface IParticleChamberController<CONTROLLER extends IParticleChamberController<CONTROLLER>> extends IParticleChamberPart, ILogicMultiblockController<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, CONTROLLER>
{


}
