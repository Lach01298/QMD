package lach_01298.qmd.proxy;

import lach_01298.qmd.QMDOreDictionary;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.tile.QMDTiles;
import nc.init.NCFluids;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void preInit(FMLPreInitializationEvent preEvent) 
	{
		QMDBlocks.init();
		QMDItems.init();
		QMDFluids.init();
		Particles.init();
		
		QMDBlocks.register();
		QMDItems.register();
		QMDFluids.register();
		QMDTiles.register();
		
		QMDOreDictionary.register();
		
		QMDPacketHandler.registerMessages();
	}
	
	public void init(FMLInitializationEvent event) 
	{
		QMDRecipes.init();
	}
	
	public void postInit(FMLPostInitializationEvent postEvent) 
	{
		
	}
}
