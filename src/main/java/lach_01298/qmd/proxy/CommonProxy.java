package lach_01298.qmd.proxy;

import java.util.Locale;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDOreDictionary;
import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.TickItemHandler;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.entity.QMDEntities;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.multiblock.Multiblocks;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.sound.QMDSounds;
import lach_01298.qmd.tile.QMDTiles;
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
		QMDSounds.init();
		QMDBlocks.init();
		QMDItems.init();
		QMDFluids.init();
		Particles.init();
		
		QMDBlocks.register();
		QMDItems.register();
		QMDFluids.register();
		QMDTiles.register();
		Particles.register();
		
		Multiblocks.init();
		
		QMDOreDictionary.register();
		
		QMDPacketHandler.registerMessages(QMD.MOD_ID);
		
		
		MinecraftForge.EVENT_BUS.register(new QMDRecipes());
		
	}
	
	public void init(FMLInitializationEvent event) 
	{
		QMDRecipes.init();
		QMDRadSources.init();
		QMDEntities.register();
	}
	
	public void postInit(FMLPostInitializationEvent postEvent) 
	{
		CapabilityParticleStackHandler.register();
		//MinecraftForge.EVENT_BUS.register(new TickItemHandler());
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
