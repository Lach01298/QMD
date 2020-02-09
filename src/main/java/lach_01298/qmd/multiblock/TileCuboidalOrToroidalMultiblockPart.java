package lach_01298.qmd.multiblock;


import nc.multiblock.BlockFacing;
import nc.multiblock.Multiblock;
import nc.multiblock.TileMultiblockPart;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.cuboidal.PartPosition;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TileCuboidalOrToroidalMultiblockPart<T extends CuboidalOrToroidalMultiblock> extends TileMultiblockPart<T>
{

	/**
	 * Minimum bounding box coordinate of the internal. Blocks do not necessarily exist at this coord if your machine
	 * is not a cube/rectangular prism.
	 */
	private BlockPos miniumInCoord;
	
	/**
	 * Maximum bounding box coordinate of the internal hole. Blocks do not necessarily exist at this coord if your machine
	 * is not a cube/rectangular prism.
	 */
	private BlockPos maximumInCoord;
	
	
	/**The amount of blocks from the outer edge that the inner edge is
	 * 
	 */
	private int toroidThickness;
	
	private final CuboidalPartPositionType positionType;
	private PartPosition position;
	private BlockFacing externalFacings;

	public TileCuboidalOrToroidalMultiblockPart(Class<T> tClass,CuboidalPartPositionType positionType, int toroidThickness)
	{
		super(tClass);
		this.positionType = positionType;
		position = PartPosition.Unknown;
		externalFacings = BlockFacing.NONE;
		this.toroidThickness = toroidThickness;
	}

	// Positional Data

	
	public CuboidalPartPositionType getPartPositionType() {
		return positionType;
	}
	
	
	/**
	 * Get the external facing of the part in the formed multiblock
	 *
	 * @return the outward facing of the part. A face is "set" in the BlockFacings
	 *         object if that face is facing outward
	 */
	@Nonnull
	public BlockFacing getExternalDir()
	{

		return externalFacings;
	}

	/**
	 * Get the position of the part in the formed multiblock
	 *
	 * @return the position of the part
	 */
	@Nonnull
	public PartPosition getPartPosition()
	{

		return position;
	}

	/**
	 * Return the single direction this part is facing if the part is in one side of
	 * the multiblock
	 *
	 * @return the direction toward with the part is facing or null if the part is
	 *         not in one side of the multiblock
	 */
	@Nullable
	public EnumFacing getExternalFacing()
	{

		
		EnumFacing facing = null != this.position ? this.position.getFacing() : null;

		if (null == facing)
		{

			BlockFacing out = this.getExternalDir();

			if (!out.none() && 1 == out.countFacesIf(true))
				facing = out.firstIf(true);
		}

		return facing;
	}

	/**
	 * Return the single direction this part is facing based on it's position in the
	 * multiblock
	 *
	 * @return the direction toward with the part is facing or null if the part is
	 *         not in one side of the multiblock
	 */
	@Nullable
	public EnumFacing getExternalFacingFromWorldPosition()
	{

		BlockFacing facings = null;
		T multiblock = this.getMultiblock();

		if (null != multiblock)
		{

			BlockPos position = pos;
			BlockPos min = multiblock.getMinimumCoord();
			BlockPos max = multiblock.getMaximumCoord();
			int x = position.getX(), y = position.getY(), z = position.getZ();

			facings = BlockFacing.from(min.getY() == y, max.getY() == y, 
					min.getZ() == z || (z == maximumInCoord.getZ() && x > miniumInCoord.getX() && x < maximumInCoord.getX()),
					max.getZ() == z || (z == miniumInCoord.getZ() && x > miniumInCoord.getX() && x < maximumInCoord.getX()),
					min.getX() == x || (x == maximumInCoord.getX() && z > miniumInCoord.getZ() && z < maximumInCoord.getZ()),
					max.getX() == x || (x == miniumInCoord.getX() && z > miniumInCoord.getZ() && z < maximumInCoord.getZ()));
		}

		return null != facings && !facings.none() && 1 == facings.countFacesIf(true) ? facings.firstIf(true) : null;
	}

	// Handlers from MultiblockTileEntityBase

	@Override
	public void onAttached(T newMultiblock)
	{
		super.onAttached(newMultiblock);
		recalculateExternalDirection(newMultiblock.getMinimumCoord(), newMultiblock.getMaximumCoord());
	}

	@Override
	public void onMachineAssembled(T multiblock)
	{
		// Discover where I am on the multiblock
		recalculateExternalDirection(multiblock.getMinimumCoord(), multiblock.getMaximumCoord());
	}

	@Override
	public void onMachineBroken()
	{
		position = PartPosition.Unknown;
		externalFacings = BlockFacing.NONE;
	}

	// Positional helpers

	public void recalculateExternalDirection(BlockPos minCoord, BlockPos maxCoord)
	{
		BlockPos myPosition = this.getPos();
		BlockPos minInCoord = new BlockPos(minCoord.getX() + toroidThickness - 1, minCoord.getY(), minCoord.getZ() + toroidThickness - 1);
		BlockPos maxInCoord = new BlockPos(maxCoord.getX() - toroidThickness + 1, maxCoord.getY(), maxCoord.getZ() - toroidThickness + 1);
		int myX = myPosition.getX();
		int myY = myPosition.getY();
		int myZ = myPosition.getZ();
		int facesMatching = 0;

		// witch direction are we facing?

		boolean downFacing = myY == minCoord.getY();
		boolean upFacing = myY == maxCoord.getY();
		boolean northFacing = myZ == minCoord.getZ() || (myZ == maxInCoord.getZ() && myX > minInCoord.getX() && myX < maxInCoord.getX());
		boolean southFacing = myZ == maxCoord.getZ() || (myZ == minInCoord.getZ() && myX > minInCoord.getX() && myX < maxInCoord.getX());
		boolean westFacing = myX == minCoord.getX() || (myX == maxInCoord.getX() && myZ > minInCoord.getZ() && myZ < maxInCoord.getZ());
		boolean eastFacing = myX == maxCoord.getX() || (myX == minInCoord.getX() && myZ > minInCoord.getZ() && myZ < maxInCoord.getZ());

		this.externalFacings = BlockFacing.from(downFacing, upFacing, northFacing, southFacing, westFacing, eastFacing);

		// how many faces are facing outward?

		if (eastFacing || westFacing)
			++facesMatching;

		if (upFacing || downFacing)
			++facesMatching;

		if (southFacing || northFacing)
			++facesMatching;

		// what is our position in the multiblock structure?

		if (facesMatching <= 0)
			this.position = PartPosition.Interior;
		else if (facesMatching >= 3)
			this.position = PartPosition.FrameCorner;

		else if (facesMatching == 2)
		{

			if (!eastFacing && !westFacing)
				this.position = PartPosition.FrameEastWest;
			else if (!southFacing && !northFacing)
				this.position = PartPosition.FrameSouthNorth;
			else
				this.position = PartPosition.FrameUpDown;

		}
		else
		{

			// only 1 face matches

			if (eastFacing)
			{

				this.position = PartPosition.EastFace;

			}
			else if (westFacing)
			{

				this.position = PartPosition.WestFace;

			}
			else if (southFacing)
			{

				this.position = PartPosition.SouthFace;

			}
			else if (northFacing)
			{

				this.position = PartPosition.NorthFace;

			}
			else if (upFacing)
			{

				this.position = PartPosition.TopFace;

			}
			else
			{

				this.position = PartPosition.BottomFace;
			}
		}
	}

	///// Validation Helpers (IMultiblockPart)

	
	public boolean isGoodForFrame(Multiblock multiblock) {
		if (positionType.isGoodForFrame()) return true;
		setStandardLastError(multiblock);
		return false;
	}

	public boolean isGoodForSides(Multiblock multiblock) {
		if (positionType.isGoodForWall()) return true;
		setStandardLastError(multiblock);
		return false;
	}

	public boolean isGoodForTop(Multiblock multiblock) {
		if (positionType.isGoodForWall()) return true;
		setStandardLastError(multiblock);
		return false;
	}

	public boolean isGoodForBottom(Multiblock multiblock) {
		if (positionType.isGoodForWall()) return true;
		setStandardLastError(multiblock);
		return false;
	}

	public boolean isGoodForInterior(Multiblock multiblock) {
		if (positionType.isGoodForInterior()) return true;
		setStandardLastError(multiblock);
		return false;
	}
}