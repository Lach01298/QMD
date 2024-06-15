package lach_01298.qmd.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.mc1120.util.CraftTweakerPlatformUtils;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.CoolerPlacement;
import lach_01298.qmd.accelerator.block.*;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.item.*;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberDetector;
import lach_01298.qmd.tab.QMDTabs;
import lach_01298.qmd.util.Util;
import lach_01298.qmd.vacuumChamber.HeaterPlacement;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberHeater;
import nc.integration.crafttweaker.CTRegistration;
import nc.integration.crafttweaker.CTRegistration.RegistrationInfo;
import nc.item.NCItemMetaArray;
import nc.util.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.apache.commons.io.FileUtils;
import stanhebben.zenscript.annotations.*;

import java.io.*;
import java.util.*;

@ZenClass("mods.qmd.Registration")
@ZenRegister
public class QMDCTRegistration
{

	//Methods
	@ZenMethod
	public static void registerAcceleratorCooler(String coolerID, int cooling, String rule)
	{

		Block cooler = QMDBlocks.withName(new BlockAcceleratorPart()
		{

			@Override
			public TileEntity createNewTileEntity(World world, int metadata)
			{
				return new TileAcceleratorCooler(coolerID, cooling, coolerID + "_cooler");
			}
		}, "accelerator_cooler_" + coolerID);

		CTRegistration.INFO_LIST.add(new AcceleratorCoolerRegistrationInfo(cooler, coolerID, cooling, rule));
		CraftTweakerAPI.logInfo("Registered accelerator cooler with ID \"" + coolerID + "\", cooling rate " + cooling
				+ " H/t and placement rule \"" + rule + "\"");
	}

	@ZenMethod
	public static void registerAcceleratorRFCavity(String name, int voltage, double efficiency, int heat, int basePower, int maxTemp)
	{

		Block rfCavity = QMDBlocks.withName(new BlockAcceleratorPart()
		{

			@Override
			public TileEntity createNewTileEntity(World world, int metadata)
			{
				return new TileAcceleratorRFCavity(voltage, efficiency, heat, basePower, maxTemp, name);
			}
		}, "accelerator_rf_cavity_" + name);

		CTRegistration.INFO_LIST.add(new AcceleratorRFCavityRegistrationInfo(rfCavity, name, voltage, efficiency, heat, basePower, maxTemp));
		CraftTweakerAPI.logInfo("Registered accelerator RF Cavity with name \"" + name + "\" and voltage " + voltage );
	}
	
	@ZenMethod
	public static void registerAcceleratorMagnet(String name, double strength, double efficiency, int heat, int basePower, int maxTemp)
	{

		Block magnet = QMDBlocks.withName(new BlockAcceleratorPart()
		{

			@Override
			public TileEntity createNewTileEntity(World world, int metadata)
			{
				return new TileAcceleratorMagnet(strength, efficiency, heat, basePower, maxTemp, name);
			}
		}, "accelerator_magnet_" + name);

		CTRegistration.INFO_LIST.add(new AcceleratorMagnetRegistrationInfo(magnet, name, strength, efficiency, heat, basePower, maxTemp));
		CraftTweakerAPI.logInfo("Registered accelerator magnet with name \"" + name + "\" and strength " + strength );
	}
	
	@ZenMethod
	public static void registerParticleChamberDetector(String name, double efficiency, int basePower, int distance, boolean within)
	{

		Block detector = QMDBlocks.withName(new BlockAcceleratorPart()
		{

			@Override
			public TileEntity createNewTileEntity(World world, int metadata)
			{
				return new TileParticleChamberDetector(efficiency, basePower, name, distance, within);
			}
		}, "particle_chamber_detector_" + name);

		CTRegistration.INFO_LIST.add(new ParticleChamberDetectorRegistrationInfo(detector, name, efficiency, basePower, distance, within));
		CraftTweakerAPI.logInfo("Registered particle chamber detector with name \"" + name + "\" and efficiency " + efficiency );
	}
	
	@ZenMethod
	public static void registerVaccuumChamberHeater(String coolerID, int cooling, String rule)
	{

		Block cooler = QMDBlocks.withName(new BlockAcceleratorPart()
		{

			@Override
			public TileEntity createNewTileEntity(World world, int metadata)
			{
				return new TileVacuumChamberHeater(coolerID, cooling, coolerID + "_cooler");
			}
		}, "vacuum_chamber_heater_" + coolerID);

		CTRegistration.INFO_LIST.add(new VacuumChamberHeaterRegistrationInfo(cooler, coolerID, cooling, rule));
		CraftTweakerAPI.logInfo("Registered vacuum chamber heater with ID \"" + coolerID + "\", cooling rate " + cooling
				+ " H/t and placement rule \"" + rule + "\"");
	}
	
	
	@ZenMethod
	public static void registerIonSource(String name, int particleOutputMultiplier, double outputFocus, int basePower)
	{

		Block ionSource = QMDBlocks.withName(new BlockAcceleratorSource()
		{

			@Override
			public TileEntity createNewTileEntity(World world, int metadata)
			{
				return new TileAcceleratorIonSource(particleOutputMultiplier, outputFocus, basePower,name);
			}
		}, "accelerator_source_" + name);

		CTRegistration.INFO_LIST.add(new AcceleratorIonSourceRegistrationInfo(ionSource, name, particleOutputMultiplier, outputFocus, basePower));
		CraftTweakerAPI.logInfo("Registered accelerator ion source with name \"" + name);
	}
	
	
	
	@ZenMethod
	public static void registerItemSource(String name, int capacity, int stackSize)
	{
		capacity = capacity < 0 ? 0 : capacity;
		stackSize  = stackSize > 64 ? 64 : stackSize < 1 ? 1 : stackSize;
		
		Item item = QMDItems.withName(new ItemCustomParticleSource(capacity, stackSize), name);
		

		CTRegistration.INFO_LIST.add(new ItemRegistrationInfo(item, QMDTabs.ITEMS));
		CraftTweakerAPI.logInfo("Registered item particle source with name \"" + name + "\"");
	
	}
	
	@ZenMethod
	public static void registerItemSource(String name, int capacity, int stackSize, double explosionSize, double radiation)
	{
		capacity = capacity < 0 ? 0 : capacity;
		stackSize  = stackSize > 64 ? 64 : stackSize < 1 ? 1 : stackSize;
		
		Item item = QMDItems.withName(new ItemCustomParticleSource(capacity, stackSize, explosionSize, radiation), name);
		
		CTRegistration.INFO_LIST.add(new ItemRegistrationInfo(item, QMDTabs.ITEMS));
		CraftTweakerAPI.logInfo("Registered item particle source with name \"" + name + "\"");
	
	}
	

	//Blocks
	public static class QMDBlockRegistrationInfo extends RegistrationInfo
	{

		protected final Block block;

		public QMDBlockRegistrationInfo(Block block)
		{
			this.block = block;
		}

		@Override
		public void preInit()
		{
			registerBlock();

			if (CraftTweakerPlatformUtils.isClient())
			{
				registerRender();
			}
		}

		public void registerBlock()
		{
			QMDBlocks.registerBlock(block);
		}

		public void registerRender()
		{
			QMDBlocks.registerRender(block);
		}

		
		@Override
		public void recipeInit() {}
		
		@Override
		public void init()
		{
		}

		@Override
		public void postInit()
		{
		}
	}

	public static class QMDTileBlockRegistrationInfo extends QMDBlockRegistrationInfo
	{

		public QMDTileBlockRegistrationInfo(Block block)
		{
			super(block);
		}
	}

	public static class AcceleratorCoolerRegistrationInfo extends QMDTileBlockRegistrationInfo
	{

		protected final String coolerID, rule;
		protected final int cooling;

		AcceleratorCoolerRegistrationInfo(Block block, String coolerID, int cooling, String rule)
		{
			super(block);
			this.coolerID = coolerID;
			this.cooling = cooling;
			this.rule = rule;
		}

		@Override
		public void registerBlock()
		{
			QMDBlocks.registerBlock(block, TextFormatting.BLUE, new String[] {
					Lang.localize("tile." + QMD.MOD_ID + ".accelerator.cooler.cooling_rate") + " " + cooling + " H/t" },
					TextFormatting.AQUA, InfoHelper.NULL_ARRAY);
		}

		@Override
		public void init()
		{
			super.init();
			CoolerPlacement.addRule(coolerID + "_cooler", rule, block);
		}
	}
	
	public static class AcceleratorRFCavityRegistrationInfo extends QMDTileBlockRegistrationInfo
	{

		protected final String name;
		protected final int voltage,heat,basePower, maxTemp;
		protected final double efficiency;

		AcceleratorRFCavityRegistrationInfo(Block block, String name, int voltage, double efficiency, int heat, int basePower, int maxTemp)
		{
			super(block);
			this.name = name;
			this.voltage = voltage;
			this.efficiency = efficiency;
			this.heat = heat;
			this.basePower = basePower;
			this.maxTemp = maxTemp;
		}

		@Override
		public void registerBlock()
		{
			String[] info = new String[] {
					Lang.localize("info." + QMD.MOD_ID + ".rf_cavity.voltage", voltage),
					Lang.localize("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D*efficiency) + "%"),
					Lang.localize("info." + QMD.MOD_ID + ".item.heat", heat),
					Lang.localize("info." + QMD.MOD_ID + ".item.power", basePower),
					Lang.localize("info." + QMD.MOD_ID + ".item.max_temp", maxTemp)
					};
			
			QMDBlocks.registerBlock(block,TextFormatting.GREEN ,info,TextFormatting.AQUA,InfoHelper.formattedInfo(Lang.localize("tile." + QMD.MOD_ID + ".rf_cavity.desc")));
		}
	}
	
	public static class AcceleratorMagnetRegistrationInfo extends QMDTileBlockRegistrationInfo
	{

		protected final String name;
		protected final int heat,basePower, maxTemp;
		protected final double  strength,efficiency;

		AcceleratorMagnetRegistrationInfo(Block block, String name, double strength, double efficiency, int heat, int basePower, int maxTemp)
		{
			super(block);
			this.name = name;
			this.strength = strength;
			this.efficiency = efficiency;
			this.heat = heat;
			this.basePower = basePower;
			this.maxTemp = maxTemp;
		}

		@Override
		public void registerBlock()
		{
			String[] info = new String[] {
					Lang.localize("info." + QMD.MOD_ID + ".accelerator_magnet.strength", strength),
					Lang.localize("info." + QMD.MOD_ID + ".item.efficiency", Math.round(100D*efficiency) + "%"),
					Lang.localize("info." + QMD.MOD_ID + ".item.heat", heat),
					Lang.localize("info." + QMD.MOD_ID + ".item.power", basePower),
					Lang.localize("info." + QMD.MOD_ID + ".item.max_temp", maxTemp)
					};
			
			QMDBlocks.registerBlock(block,TextFormatting.GREEN ,info,TextFormatting.AQUA,InfoHelper.formattedInfo(Lang.localize("tile." + QMD.MOD_ID + ".accelerator_magnet.desc")));
		}
	}
	
	public static class ParticleChamberDetectorRegistrationInfo extends QMDTileBlockRegistrationInfo
	{

		protected final String name;
		protected final int basePower,distance;
		protected final double efficiency;
		protected final boolean within;

		ParticleChamberDetectorRegistrationInfo(Block block, String name, double efficiency, int basePower, int distance, boolean within)
		{
			super(block);
			this.name = name;
			this.efficiency = efficiency;
			this.basePower = basePower;
			this.distance = distance;
			this.within = within;
		}

		@Override
		public void registerBlock()
		{
			String[] info = new String[] {
					Lang.localize("info." + QMD.MOD_ID + ".particle_chamber.detector.efficiency", Math.round(1000D*efficiency)/10d + "%"),
					Lang.localize("info." + QMD.MOD_ID + ".particle_chamber.detector.power", basePower)
					};
			
			if(within)
			{
				QMDBlocks.registerBlock(block,TextFormatting.GREEN ,info,TextFormatting.AQUA,InfoHelper.formattedInfo(Lang.localize("tile." + QMD.MOD_ID + ".particle_chamber.detector.in.desc",distance)));
			}
			else
			{
				QMDBlocks.registerBlock(block,TextFormatting.GREEN ,info,TextFormatting.AQUA,InfoHelper.formattedInfo(Lang.localize("tile." + QMD.MOD_ID + ".particle_chamber.detector.out.desc",distance)));
			}
			
			
		}
	}
	
	public static class VacuumChamberHeaterRegistrationInfo extends QMDTileBlockRegistrationInfo
	{

		protected final String coolerID, rule;
		protected final int cooling;

		VacuumChamberHeaterRegistrationInfo(Block block, String coolerID, int cooling, String rule)
		{
			super(block);
			this.coolerID = coolerID;
			this.cooling = cooling;
			this.rule = rule;
		}

		@Override
		public void registerBlock()
		{
			QMDBlocks.registerBlock(block, TextFormatting.BLUE, new String[] {
					Lang.localize("tile." + QMD.MOD_ID + ".accelerator.cooler.cooling_rate") + " " + cooling + " H/t" },
					TextFormatting.AQUA, InfoHelper.NULL_ARRAY);
		}

		@Override
		public void init()
		{
			super.init();
			HeaterPlacement.addRule(coolerID + "_cooler", rule, block);
		}
	}
	
	public static class AcceleratorIonSourceRegistrationInfo extends QMDTileBlockRegistrationInfo
	{

		protected final String name;
		protected final int basePower;
		protected final double  outputMultipler,outputFocus;

		AcceleratorIonSourceRegistrationInfo(Block block, String name, double outputMultipler, double outputFocus, int basePower)
		{
			super(block);
			this.name = name;
			this.outputMultipler = outputMultipler;
			this.outputFocus = outputFocus;
			this.basePower = basePower;
		}

		@Override
		public void registerBlock()
		{
			String[] info = new String[] {
					Lang.localize("info." + QMD.MOD_ID + ".item.power", basePower),
					Lang.localize("info." + QMD.MOD_ID + ".ion_source.output_multiplier", outputMultipler),
					Lang.localize("info." + QMD.MOD_ID + ".ion_source.focus", outputFocus)
					};
			
			QMDBlocks.registerBlock(block,TextFormatting.GREEN ,info,TextFormatting.AQUA,InfoHelper.formattedInfo(Lang.localize("tile." + QMD.MOD_ID + ".ion_source.desc")));
		}
	}
	
	
	
	
	//Items
	
	public static class ItemRegistrationInfo extends RegistrationInfo
	{

		protected final Item item;
		protected final CreativeTabs tab;

		public ItemRegistrationInfo(Item item, CreativeTabs tab)
		{
			this.item = item;
			this.tab = tab;
		}

		@Override
		public void preInit()
		{
			registerItem();

			if (CraftTweakerPlatformUtils.isClient())
			{
				registerRender();
			}
		}

		public void registerItem()
		{
			QMDItems.registerItem(item, tab);
		}

		public void registerRender()
		{
			QMDItems.registerRender(item);
		}

		@Override
		public void recipeInit()
		{
		}

		@Override
		public void init()
		{
		}

		@Override
		public void postInit()
		{
		}
	}

	
	
	
	public static class MetaItemRegistrationInfo extends RegistrationInfo
	{

		protected final String name;
		protected Item item = null;
		protected CreativeTabs tab;

		public final List<String> types = new ArrayList<>();
		public final List<String> models = new ArrayList<>();
		public final List<String> textures = new ArrayList<>();

		public MetaItemRegistrationInfo(String name, CreativeTabs tab)
		{
			this.name = name;
			this.tab = tab;
		}

		public void createModelJson()
		{
			StringBuilder builder = new StringBuilder();
			String s = IOHelper.NEW_LINE;

			builder.append("{" + s + "	\"forge_marker\": 1," + s + "	\"defaults\": {" + s
					+ "		\"model\": \"builtin/generated\"," + s + "		\"transform\": \"forge:default-item\"" + s
					+ "	}," + s + "	\"variants\": {" + s + "		\"type\": {" + s);

			for (int i = 0; i < types.size(); ++i)
			{
				builder.append("			\"" + types.get(i) + "\": {" + s);

				String model = models.get(i);
				if (model != null)
				{
					builder.append("				\"model\": \"" + model + "\"," + s);
				}

				builder.append("				\"textures\": {" + s
						+ "					\"layer0\": \"qmd:items/" + name + "/" + textures.get(i) + "\"" + s
						+ "				}" + s + "			" + (i < types.size() - 1 ? "}," : "}") + s);
			}

			builder.append("		}" + s + "	}" + s + "}" + s);

			try
			{
				FileUtils.writeStringToFile(new File("resources/qmd/blockstates/items/" + name + ".json"),
						builder.toString());
			}
			catch (IOException e)
			{
				Util.getLogger().catching(e);
			}
		}

		@Override
		public void preInit()
		{
			if (types.isEmpty())
			{
				return;
			}

			createModelJson();

			item = QMDItems.withName(new NCItemMetaArray(types), name);

			registerItem();

			if (CraftTweakerPlatformUtils.isClient())
			{
				registerRender();
			}
		}

		public void registerItem()
		{
			QMDItems.registerItem(item, tab);
		}

		public void registerRender()
		{
			for (int i = 0; i < types.size(); ++i)
			{
				QMDItems.registerRender(item, i, types.get(i));
			}
		}

		@Override
		public void recipeInit()
		{
		}

		@Override
		public void init()
		{
		}

		@Override
		public void postInit()
		{
		}
	}
	
	
	
	

}
