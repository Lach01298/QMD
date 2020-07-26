package lach_01298.qmd.containment.tile;

import lach_01298.qmd.containment.Containment;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileContainmentCasing extends TileContainmentPart
{

	public TileContainmentCasing()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}



	@Override
	public void onMachineAssembled(Containment controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
	}



}
