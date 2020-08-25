package lach_01298.qmd.containment.tile;


import lach_01298.qmd.containment.Containment;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.cuboidal.TileCuboidalMultiblockPart;

public abstract class TileContainmentPart extends TileCuboidalMultiblockPart<Containment> implements IContainmentPart
{


	public TileContainmentPart(CuboidalPartPositionType positionType)
	{
		super(Containment.class, positionType);
	}

	@Override
	public Containment createNewMultiblock()
	{
		return new Containment(getWorld());
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
