package lach_01298.qmd.vacuumChamber.tile;


import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.cuboidal.TileCuboidalMultiblockPart;

public abstract class TileVacuumChamberPart extends TileCuboidalMultiblockPart<VacuumChamber> implements IVacuumChamberPart
{


	public TileVacuumChamberPart(CuboidalPartPositionType positionType)
	{
		super(VacuumChamber.class, positionType);
	}

	@Override
	public VacuumChamber createNewMultiblock()
	{
		return new VacuumChamber(world);
	}



}
