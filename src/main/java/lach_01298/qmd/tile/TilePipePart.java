package lach_01298.qmd.tile;

import lach_01298.qmd.pipe.PipeMultiblock;
import nc.multiblock.BlockFacing;
import nc.multiblock.TileMultiblockPart;
import nc.multiblock.cuboidal.PartPosition;

public abstract class TilePipePart<T extends PipeMultiblock> extends TileMultiblockPart<T>
{

	public TilePipePart(Class<T> tClass)
	{
		super(tClass);
	}
	
	
	// Handlers from MultiblockTileEntityBase

	@Override
	public void onAttached(T newMultiblock)
	{
		super.onAttached(newMultiblock);

	}

	@Override
	public void onMachineAssembled(T multiblock)
	{
	}

	@Override
	public void onMachineBroken()
	{
	}
	
	
}
