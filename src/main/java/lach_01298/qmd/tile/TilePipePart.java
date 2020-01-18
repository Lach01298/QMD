package lach_01298.qmd.tile;


import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.pipe.Pipe;
import lach_01298.qmd.pipe.PipeMultiblock;
import nc.multiblock.BlockFacing;
import nc.multiblock.TileMultiblockPart;
import nc.multiblock.cuboidal.PartPosition;

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
