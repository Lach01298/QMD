package lach_01298.qmd;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.capabilities.CapabilityHandler;
import lach_01298.qmd.capabilities.QMDCapabilities;
import lach_01298.qmd.commands.CommandQMD;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.gui.GUIHandler;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.proxy.CommonProxy;
import lach_01298.qmd.research.Researches;
import lach_01298.qmd.tiles.QMDTiles;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = QMD.MOD_ID, name = "Quantum Mincraft Dynamics", version = QMD.VERSION, acceptedMinecraftVersions = QMD.MCVERSION, dependencies = "after:nuclearcraft;after:phys_core")
public class QMD
{

	public static final String MOD_ID = "qmd";
	public static final String VERSION = "1.12.2-0.2";
	public static final String MCVERSION = "1.12.2";

	@Instance(MOD_ID)
	public static QMD instance;
	
	
	@SidedProxy(clientSide = "lach_01298.qmd.proxy.ClientProxy", serverSide = "lach_01298.qmd.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
		QMDConfig.preInit();
		QMDBlocks.init();
		QMDItems.init();
		QMDBlocks.register();
		QMDBlocks.registerRenders();
		QMDItems.register();
		QMDTiles.register();
		
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());
		//MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
	    //MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandQMD());
	}

}