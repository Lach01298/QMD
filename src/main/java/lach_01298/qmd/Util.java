package lach_01298.qmd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nc.Global;
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
	
	
}
