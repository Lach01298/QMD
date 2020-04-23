package lach_01298.qmd.pipe;

import nc.multiblock.Multiblock;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.multiblock.tile.ITileMultiblockPart;
import net.minecraft.world.World;

public abstract class PipeMultiblock<T extends ITileMultiblockPart,PACKET extends MultiblockUpdatePacket> extends Multiblock<T,PACKET>
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
	
	
	public int getLengthX()
	{
		return Math.abs(getMaximumCoord().getX() - getMinimumCoord().getX()) + 1;
	}

	public int getLengthY()
	{
		return Math.abs(getMaximumCoord().getY() - getMinimumCoord().getY()) + 1;
	}

	public int getLengthZ()
	{
		return Math.abs(getMaximumCoord().getZ() - getMinimumCoord().getZ()) + 1;
	}

	public int length()
	{
		if(this.getLengthX() > this.getLengthZ())
		{
			return this.getLengthX();
		}
		else
		{
			return this.getLengthZ();
		}
	}
	
	
	@Override
	protected boolean isMachineWhole(Multiblock multiblock)
	{
		return true;
	}
	
	
}
