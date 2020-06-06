package lach_01298.qmd.pipe;


import nc.multiblock.tile.TileMultiblockPart;

public abstract class TilePipePart extends TileMultiblockPart<Pipe>
{

	public TilePipePart()
	{
		super(Pipe.class);
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
