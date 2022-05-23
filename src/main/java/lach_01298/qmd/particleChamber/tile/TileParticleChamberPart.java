package lach_01298.qmd.particleChamber.tile;


import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.cuboidal.TileCuboidalMultiblockPart;

public abstract class TileParticleChamberPart extends TileCuboidalMultiblockPart<ParticleChamber, IParticleChamberPart> implements IParticleChamberPart
{


	public TileParticleChamberPart(CuboidalPartPositionType positionType)
	{
		super(ParticleChamber.class, IParticleChamberPart.class, positionType);
	}

	@Override
	public ParticleChamber createNewMultiblock()
	{
		return new ParticleChamber(world);
	}


}
