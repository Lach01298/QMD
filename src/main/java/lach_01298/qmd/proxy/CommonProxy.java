package lach_01298.qmd.proxy;

import java.util.Locale;

import crafttweaker.CraftTweakerAPI;
import lach_01298.qmd.ArmourBonusHandler;
import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDOreDictionary;
import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.accelerator.CoolerPlacement;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.crafttweaker.QMDCTRegistration;
import lach_01298.qmd.crafttweaker.QMDCTRegistration.QMDRegistrationInfo;
import lach_01298.qmd.entity.QMDEntities;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.item.QMDArmour;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.multiblock.Multiblocks;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.sound.QMDSounds;
import lach_01298.qmd.tile.QMDTiles;
import nc.ModCheck;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	


	
	public void preInit(FMLPreInitializationEvent preEvent) 
	{
		if (ModCheck.craftTweakerLoaded()) {
			CraftTweakerAPI.tweaker.loadScript(false, "qmd_preinit");
		}
		
		QMDSounds.init();
		QMDBlocks.init();
		QMDItems.init();
		QMDArmour.init();
		QMDFluids.init();
		Particles.init();
		
		QMDBlocks.register();
		QMDItems.register();
		QMDArmour.register();
		QMDFluids.register();
		QMDTiles.register();
		Particles.register();
		
		Multiblocks.init();
		CoolerPlacement.preInit();
		
		QMDOreDictionary.register();
		
		QMDPacketHandler.registerMessages(QMD.MOD_ID);
		
		
		MinecraftForge.EVENT_BUS.register(new QMDRecipes());
		
		for (QMDRegistrationInfo info : QMDCTRegistration.INFO_LIST) 
		{
			info.preInit();
		}
		
	}
	
	public void init(FMLInitializationEvent event) 
	{
		QMDRecipes.init();
		QMDRadSources.init();
		QMDEntities.register();
		CoolerPlacement.init();
		MinecraftForge.EVENT_BUS.register(new ArmourBonusHandler());
		
		for (QMDRegistrationInfo info : QMDCTRegistration.INFO_LIST) 
		{
			info.init();
		}
	}
	
	public void postInit(FMLPostInitializationEvent postEvent) 
	{
		CapabilityParticleStackHandler.register();
		//MinecraftForge.EVENT_BUS.register(new TickItemHandler());
		
		QMDArmour.addRadResistance();
		CoolerPlacement.postInit();
		
		for (QMDRegistrationInfo info : QMDCTRegistration.INFO_LIST) 
		{
			info.postInit();
		}
	}
	
	
	
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent) 
	{
		QMDRecipes.refreshRecipeCaches();
		QMDRadSources.init();
		QMDArmour.addRadResistance();
		CoolerPlacement.recipe_handler.refreshCache();
	}
	
	
	public void registerFluidBlockRendering(Block block, String name) 
	{
		name = name.toLowerCase(Locale.ROOT);
	}
	
	public EntityPlayer getPlayerClient() 
	{
		return null;
	}
	
}
