package lach_01298.qmd.accelerator.tile;


import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.TileCuboidalOrToroidalMultiblockPart;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public abstract class TileAcceleratorPart extends TileCuboidalOrToroidalMultiblockPart<Accelerator, IAcceleratorPart> implements IAcceleratorPart
{


	public TileAcceleratorPart(CuboidalPartPositionType positionType)
	{
		super(Accelerator.class, IAcceleratorPart.class, positionType, 5);
	}

	@Override
	public Accelerator createNewMultiblock()
	{
		return new Accelerator(world);
	}



}
