package lach_01298.qmd;

import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.commands.CommandQMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.gui.GUIHandler;
import lach_01298.qmd.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = QMD.MOD_ID, name = QMD.MOD_NAME, version = QMD.VERSION, acceptedMinecraftVersions = QMD.MCVERSION, dependencies = "after:nuclearcraft")
public class QMD
{
	public static final String MOD_NAME = "Quantum Minecraft Dynamics";
	public static final String MOD_ID = "qmd";
	public static final String VERSION = "@VERSION@";
	public static final String MCVERSION = "1.12.2";

	@Instance(MOD_ID)
	public static QMD instance;
	
	
	@SidedProxy(clientSide = "lach_01298.qmd.proxy.ClientProxy", serverSide = "lach_01298.qmd.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Util.getLogger().info("PreInitialization");
		QMDConfig.preInit();
		proxy.preInit(event);	
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		Util.getLogger().info("Initialization");
		proxy.init(event);
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityParticleStackHandler());
	   
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Util.getLogger().info("PostInitialization");
		proxy.postInit(event);
	}
	
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		Util.getLogger().info("Server Load");
		event.registerServerCommand(new CommandQMD());
	}

	@EventHandler
	public void onIdMapping(FMLModIdMappingEvent idMappingEvent) 
	{
		proxy.onIdMapping(idMappingEvent);
	}
}