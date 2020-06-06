package lach_01298.qmd.particleChamber.tile;

import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileParticleChamberCasing extends TileParticleChamberPart
{

	public TileParticleChamberCasing()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}



	@Override
	public void onMachineAssembled(ParticleChamber controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
	}



}
