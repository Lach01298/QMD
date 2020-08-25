package lach_01298.qmd.containment.tile;

import lach_01298.qmd.containment.Containment;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileContainmentGlass extends TileContainmentPart
{

	public TileContainmentGlass()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}


	@Override
	public void onMachineAssembled(Containment controller)
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