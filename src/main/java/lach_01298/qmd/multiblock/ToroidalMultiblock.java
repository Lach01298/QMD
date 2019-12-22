package lach_01298.qmd.multiblock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockValidationError;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.util.NCMath;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public abstract class ToroidalMultiblock
{



	public static boolean isMachineWhole(CuboidalOrToroidalMultiblock multiblock, int thickness)
	{

		if (multiblock.getNumConnectedBlocks() < multiblock.getMinimumNumberOfBlocksForAssembledMachine())
		{
			multiblock.setLastError(MultiblockValidationError.VALIDATION_ERROR_TOO_FEW_PARTS);
			return false;
		}

		BlockPos maximumCoord = multiblock.getMaximumCoord();
		BlockPos minimumCoord = multiblock.getMinimumCoord();
		BlockPos maxInCoord = getMaxInCoord(maximumCoord, thickness);
		BlockPos minInCoord = getMinInCoord(minimumCoord, thickness);

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

		int maxXSize = multiblock.getMaximumXSize();
		int maxYSize = multiblock.getMaximumYSize();
		int maxZSize = multiblock.getMaximumZSize();
		int minXSize = multiblock.getMinimumXSize();
		int minYSize = multiblock.getMinimumYSize();
		int minZSize = multiblock.getMinimumZSize();

		if (maxXSize > 0 && deltaX > maxXSize)
		{
			multiblock.setLastError("zerocore.api.nc.multiblock.validation.machine_too_large", null, maxXSize, "X");
			return false;
		}
		if (maxYSize > 0 && deltaY > maxYSize)
		{
			multiblock.setLastError("zerocore.api.nc.multiblock.validation.machine_too_large", null, maxYSize, "Y");
			return false;
		}
		if (maxZSize > 0 && deltaZ > maxZSize)
		{
			multiblock.setLastError("zerocore.api.nc.multiblock.validation.machine_too_large", null, maxZSize, "Z");
			return false;
		}
		if (deltaX < minXSize)
		{
			multiblock.setLastError("zerocore.api.nc.multiblock.validation.machine_too_small", null, minXSize, "X");

			return false;
		}
		if (deltaY < minYSize)
		{
			multiblock.setLastError("zerocore.api.nc.multiblock.validation.machine_too_small", null, minYSize, "Y");

			return false;
		}
		if (deltaZ < minZSize)
		{
			multiblock.setLastError("zerocore.api.nc.multiblock.validation.machine_too_small", null, minZSize, "Z");

			return false;
		}

		// Now we run a simple check on each block within that volume.
		// Any block deviating = NO DEAL SIR
		TileEntity te;
		TileCuboidalOrToroidalMultiblockPart part;
		Class<? extends CuboidalOrToroidalMultiblock> myClass = multiblock.getClass();
		int extremes;
		boolean isPartValid;

		for (int x = minX; x <= maxX; x++)
		{
			for (int y = minY; y <= maxY; y++)
			{
				for (int z = minZ; z <= maxZ; z++)
				{

					// don't check the hole
					if (z > minInCoord.getZ() && z < maxInCoord.getZ() && x > minInCoord.getX()
							&& x < maxInCoord.getX())
					{
						continue;
					}

					// Okay, figure out what sort of block this should be.
					te = multiblock.WORLD.getTileEntity(new BlockPos(x, y, z));
					if (te instanceof TileCuboidalOrToroidalMultiblockPart)
					{
						part = (TileCuboidalOrToroidalMultiblockPart) te;

						// Ensure this part should actually be allowed within a cube of this
						// multiblock's type
						if (!myClass.equals(part.getMultiblockType()))
						{

							multiblock.setLastError("zerocore.api.nc.multiblock.validation.invalid_part",
									new BlockPos(x, y, z), x, y, z);
							return false;
						}
					}
					else
					{
						// This is permitted so that we can incorporate certain non-multiblock parts
						// inside interiors
						part = null;
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

						isPartValid = part != null ? part.isGoodForFrame(multiblock)
								: multiblock.standardLastError(x, y, z, multiblock);

						if (!isPartValid)
						{
							if (null == multiblock.getLastError())
							{
								multiblock.setLastError("zerocore.api.nc.multiblock.validation.invalid_part_for_frame",
										new BlockPos(x, y, z), x, y, z);
							}
							return false;
						}
					}
					else if (extremes == 1)
					{
						if (y == maxY)
						{

							isPartValid = part != null ? part.isGoodForTop(multiblock)
									: multiblock.standardLastError(x, y, z, multiblock);

							if (!isPartValid)
							{
								if (null == multiblock.getLastError())
								{
									multiblock.setLastError(
											"zerocore.api.nc.multiblock.validation.invalid_part_for_top",
											new BlockPos(x, y, z), x, y, z);
								}
								return false;
							}
						}
						else if (y == minY)
						{

							isPartValid = part != null ? part.isGoodForBottom(multiblock)
									: multiblock.standardLastError(x, y, z, multiblock);

							if (!isPartValid)
							{
								if (null == multiblock.getLastError())
								{
									multiblock.setLastError(
											"zerocore.api.nc.multiblock.validation.invalid_part_for_bottom",
											new BlockPos(x, y, z), x, y, z);
								}
								return false;
							}
						}
						else
						{
							// Side
							isPartValid = part != null ? part.isGoodForSides(multiblock)
									: multiblock.standardLastError(x, y, z, multiblock);

							if (!isPartValid)
							{
								if (null == multiblock.getLastError())
								{
									multiblock.setLastError(
											"zerocore.api.nc.multiblock.validation.invalid_part_for_sides",
											new BlockPos(x, y, z), x, y, z);
								}
								return false;
							}
						}
					}
					else
					{

						isPartValid = part != null ? part.isGoodForInterior(multiblock)
								: multiblock.standardLastError(x, y, z, multiblock);

						if (!isPartValid)
						{
							if (null == multiblock.getLastError())
							{
								multiblock.setLastError(
										"zerocore.api.nc.multiblock.validation.reactor.invalid_part_for_interior",
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

	private static BlockPos getMinInCoord(BlockPos minimum,int thickness)
	{

		return new BlockPos(minimum.getX() + thickness - 1, minimum.getY(),
				minimum.getZ() + thickness - 1);
	}

	private static BlockPos getMaxInCoord(BlockPos maximmum, int thickness)
	{
		return new BlockPos(maximmum.getX() - thickness + 1, maximmum.getY(),
				maximmum.getZ() - thickness + 1);
	}

}
