package lach_01298.qmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nc.Global;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

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
	
	
	
	
	
	public static String getSIPrefix(double number,int power)
	{
		int index =(int) Math.log10(number) + power;
		
		switch(index)
		{
		case -15:
		case -14:
		case -13:
			return "f";
		case -12:
		case -11:
		case -10:
			return "p";
		case -9:
		case -8:
		case -7:
			return "n";
		case -6:
		case -5:
		case -4:
			return "u";
		case -3:
		case -2:
		case -1:
			return "m";
		case 0:
		case 1:
		case 2:
			return "";
		case 3:
		case 4:
		case 5:
			return "k";
		case 6:
		case 7:
		case 8:
			return "M";
		case 9:
		case 10:
		case 11:
			return "G";
		case 12:
		case 13:
		case 14:
			return "T";
		case 15:
			return "P";
			
		}
		return "";
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
	
	
	
	
}
