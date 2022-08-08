package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileVacuumChamberGlass extends TileVacuumChamberPart
{

	public TileVacuumChamberGlass()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}


	@Override
	public void onMachineAssembled(VacuumChamber controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		
	}


	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
	}

}