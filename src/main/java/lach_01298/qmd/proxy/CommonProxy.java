package lach_01298.qmd.proxy;

import java.util.Locale;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDOreDictionary;
import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.multiblock.Multiblocks;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipes;
import lach_01298.qmd.tile.QMDTiles;
import nc.init.NCFluids;
import nc.radiation.RadArmor;
import nc.radiation.RadSources;
import nc.recipe.NCRecipes;
import nc.util.OreDictHelper;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
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
		
		Multiblocks.init();
		
		QMDOreDictionary.register();
		
		QMDPacketHandler.registerMessages(QMD.MOD_ID);
		
		MinecraftForge.EVENT_BUS.register(new QMDRecipes());
	}
	
	public void init(FMLInitializationEvent event) 
	{
		QMDRecipes.init();
		QMDRadSources.init();
		
	}
	
	public void postInit(FMLPostInitializationEvent postEvent) 
	{
		
	}
	
	
	
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent) 
	{
		QMDRecipes.refreshRecipeCaches();
		QMDRadSources.init();
	}
	
	
	public void registerFluidBlockRendering(Block block, String name) 
	{
		name = name.toLowerCase(Locale.ROOT);
	}
	
	
	
}
