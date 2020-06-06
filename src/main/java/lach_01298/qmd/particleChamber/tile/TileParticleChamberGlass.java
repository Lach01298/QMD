package lach_01298.qmd.particleChamber.tile;

import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileParticleChamberGlass extends TileParticleChamberPart
{

	public TileParticleChamberGlass()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}


	@Override
	public void onMachineAssembled(ParticleChamber controller)
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