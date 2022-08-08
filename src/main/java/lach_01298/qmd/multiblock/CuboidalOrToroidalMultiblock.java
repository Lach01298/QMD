package lach_01298.qmd.multiblock;

import lach_01298.qmd.network.QMDPacketHandler;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockValidationError;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.util.NCMath;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public abstract class CuboidalOrToroidalMultiblock<MULTIBLOCK extends CuboidalOrToroidalMultiblock<MULTIBLOCK, T>, T extends ITileMultiblockPart<MULTIBLOCK, T>>
		extends Multiblock<MULTIBLOCK, T>
{

	private int thickness;

	protected CuboidalOrToroidalMultiblock(World world, Class<MULTIBLOCK> multiblockClass, Class<T> tClass, int thickness)
	{
		super(world, multiblockClass, tClass);
		this.thickness = thickness;
	}

	/**
	 * @return True if the machine is "whole" and should be assembled. False
	 *         otherwise.
	 */
	@Override
	protected boolean isMachineWhole()
	{

		if (connectedParts.size() < getMinimumNumberOfBlocksForAssembledMachine())
		{
			setLastError(MultiblockValidationError.VALIDATION_ERROR_TOO_FEW_PARTS);
			return false;
		}

		BlockPos maximumCoord = getMaximumCoord();
		BlockPos minimumCoord = getMinimumCoord();
		BlockPos maxInCoord = getMaxInCoord();
		BlockPos minInCoord = getMinInCoord();

		int minX = minimumCoord.getX();
		int minY = minimumCoord.getY();
		int minZ = minimumCoord.getZ();
		int maxX = maximumCoord.getX();
		int maxY = maximumCoord.getY();
		int maxZ = maximumCoord.getZ();

		// Quickly check for exceeded dimensions
		int deltaX = maxX - minX + 1;
		int deltaY = maxY - minY + 1;
		int deltaZ = maxZ - minZ + 1;

		int maxXSize = getMaximumXSize();
		int maxYSize = getMaximumYSize();
		int maxZSize = getMaximumZSize();
		int minXSize = getMinimumXSize();
		int minYSize = getMinimumYSize();
		int minZSize = getMinimumZSize();

		if (maxXSize > 0 && deltaX > maxXSize)
		{
			setLastError("zerocore.api.nc.multiblock.validation.machine_too_large", null, maxXSize, "X");
			return false;
		}
		if (maxYSize > 0 && deltaY > maxYSize)
		{
			setLastError("zerocore.api.nc.multiblock.validation.machine_too_large", null, maxYSize, "Y");
			return false;
		}
		if (maxZSize > 0 && deltaZ > maxZSize)
		{
			setLastError("zerocore.api.nc.multiblock.validation.machine_too_large", null, maxZSize, "Z");
			return false;
		}
		if (deltaX < minXSize)
		{
			setLastError("zerocore.api.nc.multiblock.validation.machine_too_small", null, minXSize, "X");
			return false;
		}
		if (deltaY < minYSize)
		{
			setLastError("zerocore.api.nc.multiblock.validation.machine_too_small", null, minYSize, "Y");
			return false;
		}
		if (deltaZ < minZSize)
		{
			setLastError("zerocore.api.nc.multiblock.validation.machine_too_small", null, minZSize, "Z");
			return false;
		}

		// Now we run a simple check on each block within that volume.
		// Any block deviating = NO DEAL SIR
		TileEntity te;
		TileCuboidalOrToroidalMultiblockPart part;
		Class<? extends CuboidalOrToroidalMultiblock> myClass = getClass();
		int extremes;
		boolean isPartValid;

		for (int x = minX; x <= maxX; x++)
		{
			for (int y = minY; y <= maxY; y++)
			{
				for (int z = minZ; z <= maxZ; z++)
				{
					// Okay, figure out what sort of block this should be.
					BlockPos pos = new BlockPos(x, y, z);
					te = WORLD.getTileEntity(pos);
					if (te instanceof TileCuboidalOrToroidalMultiblockPart)
					{

						part = (TileCuboidalOrToroidalMultiblockPart) te;

						// Ensure this part should actually be allowed within a cube of this
						// multiblock's type
						if (!multiblockClass.equals(part.getMultiblockClass()))
						{

							setLastError("zerocore.api.nc.multiblock.validation.invalid_part", new BlockPos(x, y, z), x,
									y, z);
							return false;
						}
					}
					else
					{
						// This is permitted so that we can incorporate certain non-multiblock parts
						// inside interiors
						part = null;
					}

					// don't check the hole
					if (z > minInCoord.getZ() && z < maxInCoord.getZ() && x > minInCoord.getX()
							&& x < maxInCoord.getX())
					{
						if (z > minInCoord.getZ() + 1 && z < maxInCoord.getZ() - 1 && x > minInCoord.getX() + 1
								&& x < maxInCoord.getX() - 1)
						{
							continue;
						}

						if (part != null) // check inner edge isn't a multiblock part
						{

							setLastError("zerocore.api.nc.multiblock.validation.cannot_be_multiblock_part",
									new BlockPos(x, y, z), x, y, z);
							return false;
						}
						else
						{
							continue;
						}
					}

					// Validate block type against both part-level and material-level validators.
					extremes = 0;

					if (x == minX || (x == maxInCoord.getX() && z > minInCoord.getZ() && z < maxInCoord.getZ()))
						extremes++;
					if (y == minY)
						extremes++;
					if (z == minZ || (z == maxInCoord.getZ() && x > minInCoord.getX() && x < maxInCoord.getX()))
						extremes++;
					if (x == maxX || (x == minInCoord.getX() && z > minInCoord.getZ() && z < maxInCoord.getZ()))
						extremes++;
					if (y == maxY)
						extremes++;
					if (z == maxZ || (z == minInCoord.getZ() && x > minInCoord.getX() && x < maxInCoord.getX()))
						extremes++;

					if (extremes >= 2)
					{

						isPartValid = part != null ? part.isGoodForFrame(this) : isBlockGoodForFrame(WORLD, pos);

						if (!isPartValid)
						{
							if (null == getLastError())
							{
								setLastError("zerocore.api.nc.multiblock.validation.invalid_part_for_frame",
										new BlockPos(x, y, z), x, y, z);
							}
							return false;
						}
					}
					else if (extremes == 1)
					{
						if (y == maxY)
						{

							isPartValid = part != null ? part.isGoodForTop(this) : isBlockGoodForTop(WORLD, pos);

							if (!isPartValid)
							{
								if (null == getLastError())
								{
									setLastError("zerocore.api.nc.multiblock.validation.invalid_part_for_top",
											new BlockPos(x, y, z), x, y, z);
								}
								return false;
							}
						}
						else if (y == minY)
						{

							isPartValid = part != null ? part.isGoodForBottom(this) : isBlockGoodForBottom(WORLD, pos);

							if (!isPartValid)
							{
								if (null == getLastError())
								{
									setLastError("zerocore.api.nc.multiblock.validation.invalid_part_for_bottom",
											new BlockPos(x, y, z), x, y, z);
								}
								return false;
							}
						}
						else
						{
							// Side
							isPartValid = part != null ? part.isGoodForSides(this) : isBlockGoodForSides(WORLD, pos);

							if (!isPartValid)
							{
								if (null == getLastError())
								{
									setLastError("zerocore.api.nc.multiblock.validation.invalid_part_for_sides",
											new BlockPos(x, y, z), x, y, z);
								}
								return false;
							}
						}
					}
					else
					{

						isPartValid = part != null ? part.isGoodForInterior(this) : isBlockGoodForInterior(WORLD, pos);

						if (!isPartValid)
						{
							if (null == getLastError())
							{
								setLastError("zerocore.api.nc.multiblock.validation.reactor.invalid_part_for_interior",
										new BlockPos(x, y, z), x, y, z);
							}
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private BlockPos getMinInCoord()
	{

		return new BlockPos(getMinimumCoord().getX() + thickness - 1, getMinimumCoord().getY(),
				getMinimumCoord().getZ() + thickness - 1);
	}

	private BlockPos getMaxInCoord()
	{
		return new BlockPos(getMaximumCoord().getX() - thickness + 1, getMaximumCoord().getY(),
				getMaximumCoord().getZ() - thickness + 1);
	}

	protected BlockPos getMinimumInteriorCoord()
	{
		return new BlockPos(getMinInteriorX(), getMinInteriorY(), getMinInteriorZ());
	}

	protected BlockPos getMaximumInteriorCoord()
	{
		return new BlockPos(getMaxInteriorX(), getMaxInteriorY(), getMaxInteriorZ());
	}

	public int getMinInteriorX()
	{
		return getMinimumCoord().getX() + 1;
	}

	public int getMinInteriorY()
	{
		return getMinimumCoord().getY() + 1;
	}

	public int getMinInteriorZ()
	{
		return getMinimumCoord().getZ() + 1;
	}

	public int getMaxInteriorX()
	{
		return getMaximumCoord().getX() - 1;
	}

	public int getMaxInteriorY()
	{
		return getMaximumCoord().getY() - 1;
	}

	public int getMaxInteriorZ()
	{
		return getMaximumCoord().getZ() - 1;
	}

	public BlockPos getExtremeInteriorCoord(boolean maxX, boolean maxY, boolean maxZ)
	{
		return new BlockPos(maxX ? getMaxInteriorX() : getMinInteriorX(), maxY ? getMaxInteriorY() : getMinInteriorY(),
				maxZ ? getMaxInteriorZ() : getMinInteriorZ());
	}

	public int getExteriorLengthX()
	{
		return Math.abs(getMaximumCoord().getX() - getMinimumCoord().getX()) + 1;
	}

	public int getExteriorLengthY()
	{
		return Math.abs(getMaximumCoord().getY() - getMinimumCoord().getY()) + 1;
	}

	public int getExteriorLengthZ()
	{
		return Math.abs(getMaximumCoord().getZ() - getMinimumCoord().getZ()) + 1;
	}

	public int getInteriorLengthX()
	{
		return getExteriorLengthX() - 2;
	}

	public int getInteriorLengthY()
	{
		return getExteriorLengthY() - 2;
	}

	public int getInteriorLengthZ()
	{
		return getExteriorLengthZ() - 2;
	}

	public int getExteriorVolume()
	{
		if (isToroidal())
		{
			return (getExteriorLengthX() * getExteriorLengthZ()
					- (getExteriorLengthX() - 2 * thickness) * (getExteriorLengthZ() - 2 * thickness))
					* getExteriorLengthY();
		}
		return getExteriorLengthX() * getExteriorLengthY() * getExteriorLengthZ();
	}

	private boolean isToroidal()
	{
		if (getMaxInCoord().getX() <= getMinInCoord().getX() || getMaxInCoord().getZ() <= getMinInCoord().getZ())
		{
			return false;
		}
		return true;
	}

	public int getInteriorVolume()
	{
		if (isToroidal())
		{
			return ((getExteriorLengthX() - 1) * (getExteriorLengthZ() - 1)
					- (getExteriorLengthX() - 2 * (thickness + 1)) * (getExteriorLengthZ() - 2 * (thickness + 1)))
					* getInteriorLengthY();
		}

		return getInteriorLengthX() * getInteriorLengthY() * getInteriorLengthZ();
	}

	public int getExteriorSurfaceArea()
	{
		if (isToroidal())
		{
			return 4 * (thickness + getExteriorLengthY())
					* (getExteriorLengthX() + getExteriorLengthZ() - 2 * thickness);
		}
		return 2 * (getExteriorLengthX() * getExteriorLengthY() + getExteriorLengthY() * getExteriorLengthZ()
				+ getExteriorLengthZ() * getExteriorLengthX());
	}

	public int getInteriorSurfaceArea()
	{
		if (isToroidal())
		{
			return 4 * (thickness - 2 + getInteriorLengthY())
					* (getInteriorLengthX() + getInteriorLengthZ() - 2 * (thickness - 2));
		}
		return 2 * (getInteriorLengthX() * getInteriorLengthY() + getInteriorLengthY() * getInteriorLengthZ()
				+ getInteriorLengthZ() * getInteriorLengthX());
	}

	public int getInteriorLength(EnumFacing dir)
	{
		switch (dir)
		{
		case DOWN:
			return getInteriorLengthY();
		case UP:
			return getInteriorLengthY();
		case NORTH:
			return getInteriorLengthZ();
		case SOUTH:
			return getInteriorLengthZ();
		case WEST:
			return getInteriorLengthX();
		case EAST:
			return getInteriorLengthX();
		default:
			return getInteriorLengthY();
		}
	}

	protected abstract int getMinimumInteriorLength();

	protected abstract int getMaximumInteriorLength();

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine()
	{
		return NCMath.hollowCube(getMinimumInteriorLength() + 2);
	}

	@Override
	protected int getMinimumXSize()
	{
		return getMinimumInteriorLength() + 2;
	}

	@Override
	protected int getMinimumYSize()
	{
		return getMinimumInteriorLength() + 2;
	}

	@Override
	protected int getMinimumZSize()
	{
		return getMinimumInteriorLength() + 2;
	}

	@Override
	protected int getMaximumXSize()
	{
		return getMaximumInteriorLength() + 2;
	}

	@Override
	protected int getMaximumYSize()
	{
		return getMaximumInteriorLength() + 2;
	}

	@Override
	protected int getMaximumZSize()
	{
		return getMaximumInteriorLength() + 2;
	}

	public Iterable<MutableBlockPos> getWallMinX()
	{
		return BlockPos.getAllInBoxMutable(getExtremeCoord(false, false, false), getExtremeCoord(false, true, true));
	}

	public Iterable<MutableBlockPos> getWallMaxX()
	{
		return BlockPos.getAllInBoxMutable(getExtremeCoord(true, false, false), getExtremeCoord(true, true, true));
	}

	public Iterable<MutableBlockPos> getWallMinY()
	{
		return BlockPos.getAllInBoxMutable(getExtremeCoord(false, false, false), getExtremeCoord(true, false, true));
	}

	public Iterable<MutableBlockPos> getWallMaxY()
	{
		return BlockPos.getAllInBoxMutable(getExtremeCoord(false, true, false), getExtremeCoord(true, true, true));
	}

	public Iterable<MutableBlockPos> getWallMinZ()
	{
		return BlockPos.getAllInBoxMutable(getExtremeCoord(false, false, false), getExtremeCoord(true, true, false));
	}

	public Iterable<MutableBlockPos> getWallMaxZ()
	{
		return BlockPos.getAllInBoxMutable(getExtremeCoord(false, false, true), getExtremeCoord(true, true, true));
	}

	public Iterable<MutableBlockPos> getWallMin(EnumFacing.Axis axis)
	{
		switch (axis)
		{
		case X:
			return getWallMinX();
		case Y:
			return getWallMinY();
		case Z:
			return getWallMinZ();
		default:
			return BlockPos.getAllInBoxMutable(getExtremeCoord(false, false, false),
					getExtremeCoord(false, false, false));
		}
	}

	public Iterable<MutableBlockPos> getWallMax(EnumFacing.Axis axis)
	{
		switch (axis)
		{
		case X:
			return getWallMaxX();
		case Y:
			return getWallMaxY();
		case Z:
			return getWallMaxZ();
		default:
			return BlockPos.getAllInBoxMutable(getExtremeCoord(true, true, true), getExtremeCoord(true, true, true));
		}
	}

	public boolean isInMinWall(EnumFacing.Axis axis, BlockPos pos)
	{
		switch (axis)
		{
		case X:
			return pos.getX() == getMinX() || (pos.getX() == getMaxInCoord().getX()
					&& pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
		case Y:
			return pos.getY() == getMinY();
		case Z:
			return pos.getZ() == getMinZ() || (pos.getZ() == getMaxInCoord().getZ()
					&& pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
		default:
			return false;
		}
	}

	public boolean isInMaxWall(EnumFacing.Axis axis, BlockPos pos)
	{
		switch (axis)
		{
		case X:
			return pos.getX() == getMaxX() || (pos.getX() == getMinInCoord().getX()
					&& pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
		case Y:
			return pos.getY() == getMaxY();
		case Z:
			return pos.getZ() == getMaxZ() || (pos.getZ() == getMinInCoord().getZ()
					&& pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
		default:
			return false;
		}
	}

	public boolean isInWall(EnumFacing side, BlockPos pos)
	{
		switch (side)
		{
		case DOWN:
			return pos.getY() == getMinY();
		case UP:
			return pos.getY() == getMaxY();
		case NORTH:
			return pos.getZ() == getMinZ() || (pos.getZ() == getMaxInCoord().getZ()
					&& pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
		case SOUTH:
			return pos.getZ() == getMaxZ() || (pos.getZ() == getMinInCoord().getZ()
					&& pos.getX() > getMinInCoord().getX() && pos.getX() < getMaxInCoord().getX());
		case WEST:
			return pos.getX() == getMinX() || (pos.getX() == getMaxInCoord().getX()
					&& pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
		case EAST:
			return pos.getX() == getMaxX() || (pos.getX() == getMinInCoord().getX()
					&& pos.getZ() > getMinInCoord().getZ() && pos.getZ() < getMaxInCoord().getZ());
		default:
			return false;
		}
	}

	public BlockPos getMinimumInteriorPlaneCoord(EnumFacing normal, int depth, int uCushion, int vCushion)
	{
		switch (normal)
		{
		case DOWN:
			return getExtremeInteriorCoord(false, false, false).offset(EnumFacing.UP, depth)
					.offset(EnumFacing.SOUTH, uCushion).offset(EnumFacing.EAST, vCushion);
		case UP:
			return getExtremeInteriorCoord(false, true, false).offset(EnumFacing.DOWN, depth)
					.offset(EnumFacing.SOUTH, uCushion).offset(EnumFacing.EAST, vCushion);
		case NORTH:
			return getExtremeInteriorCoord(false, false, false).offset(EnumFacing.SOUTH, depth)
					.offset(EnumFacing.EAST, uCushion).offset(EnumFacing.UP, vCushion);
		case SOUTH:
			return getExtremeInteriorCoord(false, false, true).offset(EnumFacing.NORTH, depth)
					.offset(EnumFacing.EAST, uCushion).offset(EnumFacing.UP, vCushion);
		case WEST:
			return getExtremeInteriorCoord(false, false, false).offset(EnumFacing.EAST, depth)
					.offset(EnumFacing.UP, uCushion).offset(EnumFacing.SOUTH, vCushion);
		case EAST:
			return getExtremeInteriorCoord(true, false, false).offset(EnumFacing.WEST, depth)
					.offset(EnumFacing.UP, uCushion).offset(EnumFacing.SOUTH, vCushion);
		default:
			return getExtremeInteriorCoord(false, false, false);
		}
	}

	public BlockPos getMaximumInteriorPlaneCoord(EnumFacing normal, int depth, int uCushion, int vCushion)
	{
		switch (normal)
		{
		case DOWN:
			return getExtremeInteriorCoord(true, false, true).offset(EnumFacing.UP, depth)
					.offset(EnumFacing.NORTH, uCushion).offset(EnumFacing.WEST, vCushion);
		case UP:
			return getExtremeInteriorCoord(true, true, true).offset(EnumFacing.DOWN, depth)
					.offset(EnumFacing.NORTH, uCushion).offset(EnumFacing.WEST, vCushion);
		case NORTH:
			return getExtremeInteriorCoord(true, true, false).offset(EnumFacing.SOUTH, depth)
					.offset(EnumFacing.WEST, uCushion).offset(EnumFacing.DOWN, vCushion);
		case SOUTH:
			return getExtremeInteriorCoord(true, true, true).offset(EnumFacing.NORTH, depth)
					.offset(EnumFacing.WEST, uCushion).offset(EnumFacing.DOWN, vCushion);
		case WEST:
			return getExtremeInteriorCoord(false, true, true).offset(EnumFacing.EAST, depth)
					.offset(EnumFacing.DOWN, uCushion).offset(EnumFacing.NORTH, vCushion);
		case EAST:
			return getExtremeInteriorCoord(true, true, true).offset(EnumFacing.WEST, depth)
					.offset(EnumFacing.DOWN, uCushion).offset(EnumFacing.NORTH, vCushion);
		default:
			return getExtremeInteriorCoord(true, true, true);
		}
	}

	public Iterable<MutableBlockPos> getInteriorPlaneMinX(int depth, int minUCushion, int minVCushion, int maxUCushion,
			int maxVCushion)
	{
		return BlockPos.getAllInBoxMutable(
				getMinimumInteriorPlaneCoord(EnumFacing.WEST, depth, minUCushion, minVCushion),
				getMaximumInteriorPlaneCoord(EnumFacing.WEST, depth, maxUCushion, maxVCushion));
	}

	public Iterable<MutableBlockPos> getInteriorPlaneMaxX(int depth, int minUCushion, int minVCushion, int maxUCushion,
			int maxVCushion)
	{
		return BlockPos.getAllInBoxMutable(
				getMinimumInteriorPlaneCoord(EnumFacing.EAST, depth, minUCushion, minVCushion),
				getMaximumInteriorPlaneCoord(EnumFacing.EAST, depth, maxUCushion, maxVCushion));
	}

	public Iterable<MutableBlockPos> getInteriorPlaneMinY(int depth, int minUCushion, int minVCushion, int maxUCushion,
			int maxVCushion)
	{
		return BlockPos.getAllInBoxMutable(
				getMinimumInteriorPlaneCoord(EnumFacing.DOWN, depth, minUCushion, minVCushion),
				getMaximumInteriorPlaneCoord(EnumFacing.DOWN, depth, maxUCushion, maxVCushion));
	}

	public Iterable<MutableBlockPos> getInteriorPlaneMaxY(int depth, int minUCushion, int minVCushion, int maxUCushion,
			int maxVCushion)
	{
		return BlockPos.getAllInBoxMutable(getMinimumInteriorPlaneCoord(EnumFacing.UP, depth, minUCushion, minVCushion),
				getMaximumInteriorPlaneCoord(EnumFacing.UP, depth, maxUCushion, maxVCushion));
	}

	public Iterable<MutableBlockPos> getInteriorPlaneMinZ(int depth, int minUCushion, int minVCushion, int maxUCushion,
			int maxVCushion)
	{
		return BlockPos.getAllInBoxMutable(
				getMinimumInteriorPlaneCoord(EnumFacing.NORTH, depth, minUCushion, minVCushion),
				getMaximumInteriorPlaneCoord(EnumFacing.NORTH, depth, maxUCushion, maxVCushion));
	}

	public Iterable<MutableBlockPos> getInteriorPlaneMaxZ(int depth, int minUCushion, int minVCushion, int maxUCushion,
			int maxVCushion)
	{
		return BlockPos.getAllInBoxMutable(
				getMinimumInteriorPlaneCoord(EnumFacing.SOUTH, depth, minUCushion, minVCushion),
				getMaximumInteriorPlaneCoord(EnumFacing.SOUTH, depth, maxUCushion, maxVCushion));
	}

	public Iterable<MutableBlockPos> getInteriorPlane(EnumFacing normal, int depth, int minUCushion, int minVCushion,
			int maxUCushion, int maxVCushion)
	{
		switch (normal)
		{
		case DOWN:
			return getInteriorPlaneMinY(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
		case UP:
			return getInteriorPlaneMaxY(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
		case NORTH:
			return getInteriorPlaneMinZ(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
		case SOUTH:
			return getInteriorPlaneMaxZ(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
		case WEST:
			return getInteriorPlaneMinX(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
		case EAST:
			return getInteriorPlaneMaxX(depth, minUCushion, minVCushion, maxUCushion, maxVCushion);
		default:
			return BlockPos.getAllInBoxMutable(getExtremeInteriorCoord(false, false, false),
					getExtremeInteriorCoord(false, false, false));
		}
	}

}