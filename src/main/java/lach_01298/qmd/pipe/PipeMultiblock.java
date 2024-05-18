package lach_01298.qmd.pipe;

import nc.multiblock.Multiblock;
import nc.tile.multiblock.ITileMultiblockPart;
import net.minecraft.world.World;

public abstract class PipeMultiblock<MULTIBLOCK extends Multiblock<MULTIBLOCK, T>, T extends ITileMultiblockPart<MULTIBLOCK, T>>
		extends Multiblock<MULTIBLOCK, T>
{

	protected PipeMultiblock(World world, Class<MULTIBLOCK> multiblockClass, Class<T> tClass)
	{
		super(world, multiblockClass, tClass);
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
		
		if (this.getLengthX() > this.getLengthZ() && this.getLengthX() > this.getLengthY())
		{
			return this.getLengthX();
		}
		else if(this.getLengthZ() > this.getLengthY())
		{
			return this.getLengthZ();
		}
		else
		{
			return this.getLengthY();
		}
	}

	@Override
	protected boolean isMachineWhole()
	{
		return true;
	}

}
