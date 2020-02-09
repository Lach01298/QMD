package lach_01298.qmd;

import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nc.Global;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class Util
{

	private static Logger logger;
	
	public static Logger getLogger()
	{
		if (logger == null)
		{
			logger = LogManager.getFormatterLogger(QMD.MOD_ID);
		}
		return logger;
	}
	
	public static ResourceLocation appendPath(ResourceLocation location, String  stringToAppend)
	{
		String domain =location.getNamespace();
		String path =location.getPath();
		ResourceLocation newLocation = new ResourceLocation(domain,path+stringToAppend);
		
		return newLocation;
	}
	
	
	
	
	
	
	
	public static EnumFacing getAxisFacing(EnumFacing.Axis axis, boolean positive)
	{
		if(axis == EnumFacing.Axis.X)
		{
			if(positive)
			{
				return EnumFacing.EAST;
			}
			return EnumFacing.WEST;
		}
		if(axis == EnumFacing.Axis.Y)
		{
			if(positive)
			{
				return EnumFacing.UP;
			}
			return EnumFacing.DOWN;
		}
		if(axis == EnumFacing.Axis.Z)
		{
			if(positive)
			{
				return EnumFacing.SOUTH;
			}
			return EnumFacing.NORTH;
		}
	
		return null;
	}
	
	
	
	public static int getTaxiDistance(BlockPos a, BlockPos b)
	{
		int x = Math.abs(a.getX()- b.getX());
		int y = Math.abs(a.getY()- b.getY());
		int z = Math.abs(a.getZ()- b.getZ());	
		return x+y+z;
	}
	
	
	
	
	
}
