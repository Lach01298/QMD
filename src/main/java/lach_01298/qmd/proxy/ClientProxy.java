package lach_01298.qmd.proxy;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.item.QMDItems;
import nc.config.NCConfig;
import nc.handler.RenderHandler;
import nc.handler.SoundHandler;
import nc.handler.TooltipHandler;
import nc.radiation.RadiationRenders;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy 
{

	@Override
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		super.preInit(preEvent);
		QMDBlocks.registerRenders();
		QMDItems.registerRenders();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}

	@Override
	public void postInit(FMLPostInitializationEvent postEvent)
	{
		super.postInit(postEvent);
	}

}
