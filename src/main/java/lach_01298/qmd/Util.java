package lach_01298.qmd;

import net.minecraft.util.ResourceLocation;

public class Util
{

	
	
	
	public static ResourceLocation appendPath(ResourceLocation location, String  stringToAppend)
	{
		String domain =location.getResourceDomain();
		String path =location.getResourcePath();
		ResourceLocation newLocation = new ResourceLocation(domain,path+stringToAppend);
		
		return newLocation;
	}
	
	
}
