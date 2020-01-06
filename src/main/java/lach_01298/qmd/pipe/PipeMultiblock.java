package lach_01298.qmd.pipe;

import nc.multiblock.Multiblock;
import nc.multiblock.network.MultiblockUpdatePacket;
import net.minecraft.world.World;

public abstract class PipeMultiblock<PACKET extends MultiblockUpdatePacket> extends Multiblock<PACKET>
{

	protected PipeMultiblock(World world)
	{
		super(world);
	}

	
	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine()
	{
		return 1;
	}

	@Override
	protected int getMaximumXSize()
	{	
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMaximumZSize()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	protected int getMaximumYSize()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	protected boolean isMachineWhole(Multiblock multiblock)
	{
		return true;
	}
	
	
}
