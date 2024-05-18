package lach_01298.qmd.pipe;

import nc.tile.multiblock.TileMultiblockPart;

public abstract class TilePipePart extends TileMultiblockPart<Pipe, IPipePart>
{

	public TilePipePart()
	{
		super(Pipe.class, IPipePart.class);
	}
	
	

	@Override
	public Pipe createNewMultiblock()
	{
		return new Pipe(getWorld());
	}

	@Override
	public void onAttached(Pipe newMultiblock)
	{
		super.onAttached(newMultiblock);

	}

	@Override
	public void onMachineAssembled(Pipe multiblock)
	{
	}

	@Override
	public void onMachineBroken()
	{
	}
	
	
}
