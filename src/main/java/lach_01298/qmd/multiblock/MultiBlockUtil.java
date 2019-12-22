package lach_01298.qmd.multiblock;

import java.util.ArrayList;
import java.util.List;

import nc.multiblock.Multiblock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class MultiBlockUtil
{

	public List<BlockPos> getPlane(Multiblock multiblock, EnumFacing.Axis axis, int offset)
	{
		List<BlockPos> plane = new ArrayList<BlockPos>();
		
		
		if (axis == Axis.X)
		{
			for (int y = multiblock.getMinY(); y <= multiblock.getMaxY(); y++)
			{
				for (int z = multiblock.getMinZ(); z <= multiblock.getMaxZ(); z++)
				{
					plane.add(new BlockPos(multiblock.getMinX() + offset, y, z));
				}
			}
		}
		
		if (axis == Axis.Y)
		{
			for (int x = multiblock.getMinX(); x <= multiblock.getMaxX(); x++)
			{
				for (int z = multiblock.getMinY(); z <= multiblock.getMaxY(); z++)
				{
					plane.add(new BlockPos( x, multiblock.getMinY()+ offset, z));
				}
			}
		}
		
		if (axis == Axis.Z)
		{
			for (int x = multiblock.getMinX(); x <= multiblock.getMaxX(); x++)
			{
				for (int y = multiblock.getMinY(); y <= multiblock.getMaxY(); y++)
				{
					plane.add(new BlockPos(x, y, multiblock.getMinZ()+offset));
				}
			}
		}
		
		return plane;
	}
	
	
	public List<BlockPos> getVolume(BlockPos a, BlockPos b)
	{
		BlockPos min = new BlockPos(a.getX() < b.getX() ? a.getX() : b.getX(),a.getY() < b.getY() ? a.getY() : b.getY(),a.getZ() < b.getZ() ? a.getZ() : b.getZ());
		BlockPos max = new BlockPos(a.getX() > b.getX() ? a.getX() : b.getX(),a.getY() > b.getY() ? a.getY() : b.getY(),a.getZ() > b.getZ() ? a.getZ() : b.getZ());
		
		List<BlockPos> volume = new ArrayList<BlockPos>();
		
		for (int x = min.getX(); x <= max.getX(); x++)
		{
			for (int y = min.getY(); y <= max.getY(); y++)
			{
				for (int z = min.getZ(); z <= max.getZ(); z++)
				{
					volume.add(new BlockPos(x, y, z));
				}
				
			}
		}
		
		return volume;
	}
		
	
	
	
}
