package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileAcceleratorGlass extends TileAcceleratorPart
{

	public TileAcceleratorGlass()
	{
		super(CuboidalPartPositionType.EXTERIOR);
	}


	@Override
	public void onMachineAssembled(Accelerator controller)
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
