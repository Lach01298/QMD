package lach_01298.qmd.proxy;

import lach_01298.qmd.*;
import lach_01298.qmd.accelerator.CoolerPlacement;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.entity.QMDEntities;
import lach_01298.qmd.fluid.QMDFluids;
import lach_01298.qmd.item.*;
import lach_01298.qmd.multiblock.Multiblocks;
import lach_01298.qmd.network.QMDPackets;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.sound.QMDSounds;
import lach_01298.qmd.tile.*;
import lach_01298.qmd.vacuumChamber.HeaterPlacement;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;

import java.util.Locale;

public class CommonProxy
{
	


	
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		QMDTileInfoHandler.preInit();
		
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
		HeaterPlacement.preInit();
		
		QMDOreDictionary.register();
		
		QMDPackets.registerMessages(QMD.MOD_ID);
		
		MinecraftForge.EVENT_BUS.register(new QMDRecipes());
	}
	
	public void init(FMLInitializationEvent event)
	{
		QMDRecipes.init();
		QMDRadSources.init();
		QMDEntities.register();
		CoolerPlacement.init();
		HeaterPlacement.init();
		QMDArmour.blacklistShielding();
		MinecraftForge.EVENT_BUS.register(new ArmourBonusHandler());
		QMDTileInfoHandler.init();
	}
	
	public void postInit(FMLPostInitializationEvent postEvent)
	{
		CapabilityParticleStackHandler.register();
		
		QMDArmour.addRadResistance();
		CoolerPlacement.postInit();
		HeaterPlacement.postInit();
	}
	
	
	
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent)
	{
		QMDRecipes.refreshRecipeCaches();
		QMDRadSources.init();
		QMDArmour.addRadResistance();
		CoolerPlacement.recipe_handler.refreshCache();
		HeaterPlacement.recipe_handler.refreshCache();
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
