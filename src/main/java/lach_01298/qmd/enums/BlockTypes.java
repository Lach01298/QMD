package lach_01298.qmd.enums;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.gui.GUI_ID;
import lach_01298.qmd.machine.tile.TileQMDProcessors.*;
import lach_01298.qmd.tab.QMDTabs;
import lach_01298.qmd.tile.*;
import nc.enumm.IBlockMetaEnum;
import nc.multiblock.turbine.TurbineRotorBladeUtil.IRotorBladeType;
import nc.radiation.RadSources;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;

public class BlockTypes
{
	public enum CoolerType1 implements IStringSerializable, IBlockMetaEnum, ICoolerEnum
	{
		WATER("water", 0, QMDConfig.cooler_heat_removed[0]),
		IRON("iron", 1, QMDConfig.cooler_heat_removed[1]),
		REDSTONE("redstone", 2, QMDConfig.cooler_heat_removed[2]),
		QUARTZ("quartz", 3, QMDConfig.cooler_heat_removed[3]),
		OBSIDIAN("obsidian", 4, QMDConfig.cooler_heat_removed[4]),
		NETHER_BRICK("nether_brick", 5, QMDConfig.cooler_heat_removed[5]),
		GLOWSTONE("glowstone", 6, QMDConfig.cooler_heat_removed[6]),
		LAPIS("lapis", 7, QMDConfig.cooler_heat_removed[7]),
		GOLD("gold", 8, QMDConfig.cooler_heat_removed[8]),
		PRISMARINE("prismarine", 9, QMDConfig.cooler_heat_removed[9]),
		SLIME("slime", 10, QMDConfig.cooler_heat_removed[10]),
		END_STONE("end_stone", 11, QMDConfig.cooler_heat_removed[11]),
		PURPUR("purpur", 12, QMDConfig.cooler_heat_removed[12]),
		DIAMOND("diamond", 13, QMDConfig.cooler_heat_removed[13]),
		EMERALD("emerald", 14, QMDConfig.cooler_heat_removed[14]),
		COPPER("copper", 15, QMDConfig.cooler_heat_removed[15]);
		
		
		private String name;
		private int id;
		private int heat;
		

		private CoolerType1(String name, int id,  int heat)
		{
			this.name = name;
			this.id = id;
			this.heat = heat;
			
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		public int getID()
		{
			return id;
		}


		@Override
		public int getHeatRemoved()
		{
			return heat;
		}
		@Override
		public int getHarvestLevel()
		{
			return 0;
		}

		@Override
		public String getHarvestTool()
		{
			return "pickaxe";
		}

		@Override
		public float getHardness()
		{
			return 2;
		}

		@Override
		public float getResistance()
		{
			return 10;
		}

		@Override
		public int getLightValue()
		{
			if(this == GLOWSTONE)
			{
				return 15;
			}
			return 0;
		}
		

	}



	public enum CoolerType2 implements IStringSerializable, IBlockMetaEnum, ICoolerEnum
	{
		TIN("tin", 0, QMDConfig.cooler_heat_removed[16]),
		LEAD("lead", 1, QMDConfig.cooler_heat_removed[17]),
		BORON("boron", 2, QMDConfig.cooler_heat_removed[18]),
		LITHIUM("lithium", 3, QMDConfig.cooler_heat_removed[19]),
		MAGNESIUM("magnesium", 4, QMDConfig.cooler_heat_removed[20]),
		MANGANESE("manganese", 5, QMDConfig.cooler_heat_removed[21]),
		ALUMINUM("aluminum", 6, QMDConfig.cooler_heat_removed[22]),
		SILVER("silver", 7, QMDConfig.cooler_heat_removed[23]),
		FLUORITE("fluorite", 8, QMDConfig.cooler_heat_removed[24]),
		VILLIAUMITE("villiaumite", 9, QMDConfig.cooler_heat_removed[25]),
		CAROBBIITE("carobbiite", 10, QMDConfig.cooler_heat_removed[26]),
		ARSENIC("arsenic", 11, QMDConfig.cooler_heat_removed[27]),
		LIQUID_NITROGEN("liquid_nitrogen", 12, QMDConfig.cooler_heat_removed[28]),
		LIQUID_HELIUM("liquid_helium", 13, QMDConfig.cooler_heat_removed[29]),
		ENDERIUM("enderium", 14, QMDConfig.cooler_heat_removed[30]),
		CRYOTHEUM("cryotheum", 15, QMDConfig.cooler_heat_removed[31]);
		
		
		private String name;
		private int id;
		private int heat;
		

		private CoolerType2(String name, int id, int heat)
		{
			this.name = name;
			this.id = id;
			this.heat = heat;
			
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		public int getID()
		{
			return id;
		}


		@Override
		public int getHeatRemoved()
		{
			return heat;
		}

		@Override
		public int getHarvestLevel()
		{
			return 0;
		}

		@Override
		public String getHarvestTool()
		{
			return "pickaxe";
		}

		@Override
		public float getHardness()
		{
			return 2;
		}

		@Override
		public float getResistance()
		{
			return 10;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}

	}

	public enum RFCavityType implements IStringSerializable, IBlockMetaEnum
	{
		COPPER("copper", 0, QMDConfig.RF_cavity_voltage[0], QMDConfig.RF_cavity_efficiency[0],QMDConfig.RF_cavity_heat_generated[0],QMDConfig.RF_cavity_base_power[0],QMDConfig.RF_cavity_max_temp[0]),
		MAGNESIUM_DIBORIDE("magnesium_diboride", 1, QMDConfig.RF_cavity_voltage[1],  QMDConfig.RF_cavity_efficiency[1],QMDConfig.RF_cavity_heat_generated[1],QMDConfig.RF_cavity_base_power[1],QMDConfig.RF_cavity_max_temp[1]),
		NIOBIUM_TIN("niobium_tin", 2, QMDConfig.RF_cavity_voltage[2],  QMDConfig.RF_cavity_efficiency[2],QMDConfig.RF_cavity_heat_generated[2],QMDConfig.RF_cavity_base_power[2],QMDConfig.RF_cavity_max_temp[2]),
		NIOBIUM_TITANIUM("niobium_titanium", 3, QMDConfig.RF_cavity_voltage[3],  QMDConfig.RF_cavity_efficiency[3],QMDConfig.RF_cavity_heat_generated[3],QMDConfig.RF_cavity_base_power[3],QMDConfig.RF_cavity_max_temp[3]),
		BSCCO("bscco", 4, QMDConfig.RF_cavity_voltage[4],  QMDConfig.RF_cavity_efficiency[4],QMDConfig.RF_cavity_heat_generated[4],QMDConfig.RF_cavity_base_power[4],QMDConfig.RF_cavity_max_temp[4]),
		Aluminium("aluminium", 5, QMDConfig.RF_cavity_voltage[5],  QMDConfig.RF_cavity_efficiency[5],QMDConfig.RF_cavity_heat_generated[5],QMDConfig.RF_cavity_base_power[5],QMDConfig.RF_cavity_max_temp[5]),
		SSFAF("ssfaf", 6, QMDConfig.RF_cavity_voltage[6],  QMDConfig.RF_cavity_efficiency[6],QMDConfig.RF_cavity_heat_generated[6],QMDConfig.RF_cavity_base_power[6],QMDConfig.RF_cavity_max_temp[6]),
		YBCO("ybco", 7, QMDConfig.RF_cavity_voltage[7],  QMDConfig.RF_cavity_efficiency[7],QMDConfig.RF_cavity_heat_generated[7],QMDConfig.RF_cavity_base_power[7],QMDConfig.RF_cavity_max_temp[7]);

		private String name;
		private int id;
		private int voltage;
		private double efficiency;
		private int heat;
		private int basePower;
		private int maxTemp;

		private RFCavityType(String name, int id, int voltage, double efficiency, int heat, int basePower, int maxTemp)
		{
			this.name = name;
			this.id = id;
			this.voltage = voltage;
			this.efficiency = efficiency;
			this.heat = heat;
			this.basePower = basePower;
			this.maxTemp = maxTemp;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		public int getID()
		{
			return id;
		}

	
		public int getVoltage()
		{
			return voltage;
		}
		
		public double getEfficiency()
		{
			return efficiency;
		}

		public int getHeatGenerated()
		{
			return heat;
		}

		public int getBasePower()
		{
			return basePower;
		}
		
		public int getMaxOperatingTemp()
		{
			return maxTemp;
		}
		
		@Override
		public int getHarvestLevel()
		{
			return 0;
		}

		@Override
		public String getHarvestTool()
		{
			return "pickaxe";
		}

		@Override
		public float getHardness()
		{
			return 2;
		}

		@Override
		public float getResistance()
		{
			return 10;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}
	}
	
	public enum MagnetType implements IStringSerializable, IBlockMetaEnum
	{
		COPPER("copper", 0, QMDConfig.magnet_strength[0], QMDConfig.magnet_efficiency[0],QMDConfig.magnet_heat_generated[0],QMDConfig.magnet_base_power[0],QMDConfig.magnet_max_temp[0]),
		MAGNESIUM_DIBORIDE("magnesium_diboride", 1, QMDConfig.magnet_strength[1],  QMDConfig.magnet_efficiency[1],QMDConfig.magnet_heat_generated[1],QMDConfig.magnet_base_power[1],QMDConfig.magnet_max_temp[1]),
		NIOBIUM_TIN("niobium_tin", 2, QMDConfig.magnet_strength[2],  QMDConfig.magnet_efficiency[2],QMDConfig.magnet_heat_generated[2],QMDConfig.magnet_base_power[2],QMDConfig.magnet_max_temp[2]),
		NIOBIUM_TITANIUM("niobium_titanium", 3, QMDConfig.magnet_strength[3],  QMDConfig.magnet_efficiency[3],QMDConfig.magnet_heat_generated[3],QMDConfig.magnet_base_power[3],QMDConfig.magnet_max_temp[3]),
		BSCCO("bscco", 4, QMDConfig.magnet_strength[4],  QMDConfig.magnet_efficiency[4],QMDConfig.magnet_heat_generated[4],QMDConfig.magnet_base_power[4],QMDConfig.magnet_max_temp[4]),
		Aluminium("aluminium", 5, QMDConfig.magnet_strength[5],  QMDConfig.magnet_efficiency[5],QMDConfig.magnet_heat_generated[5],QMDConfig.magnet_base_power[5],QMDConfig.magnet_max_temp[5]),
		SSFAF("ssfaf", 6, QMDConfig.magnet_strength[6],  QMDConfig.magnet_efficiency[6],QMDConfig.magnet_heat_generated[6],QMDConfig.magnet_base_power[6],QMDConfig.magnet_max_temp[6]),
		YBCO("ybco", 7, QMDConfig.magnet_strength[7],  QMDConfig.magnet_efficiency[7],QMDConfig.magnet_heat_generated[7],QMDConfig.magnet_base_power[7],QMDConfig.magnet_max_temp[7]);

		private String name;
		private int id;
		private double strength;
		private double efficiency;
		private int heat;
		private int basePower;
		private int maxTemp;
		
		private MagnetType(String name, int id, double strength, double efficiency, int heat, int basePower, int maxTemp)
		{
			this.name = name;
			this.id = id;
			this.strength = strength;
			this.efficiency = efficiency;
			this.heat = heat;
			this.basePower = basePower;
			this.maxTemp = maxTemp;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		public int getID()
		{
			return id;
		}

		public double getStrength()
		{
			return strength;
		}
		
		public double getEfficiency()
		{
			return efficiency;
		}

		public int getHeatGenerated()
		{
			return heat;
		}

		public int getBasePower()
		{
			return basePower;
		}
		
		public int getMaxOperatingTemp()
		{
			return maxTemp;
		}
		
		@Override
		public int getHarvestLevel()
		{
			return 0;
		}

		@Override
		public String getHarvestTool()
		{
			return "pickaxe";
		}

		@Override
		public float getHardness()
		{
			return 2;
		}

		@Override
		public float getResistance()
		{
			return 10;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}
	}
	
	
	
	public enum DetectorType implements IStringSerializable, IBlockMetaEnum
	{
		BUBBLE_CHAMBER("bubble_chamber", 0,  QMDConfig.detector_efficiency[0], QMDConfig.detector_base_power[0]),
		SILLICON_TRACKER("silicon_tracker", 1,  QMDConfig.detector_efficiency[1], QMDConfig.detector_base_power[1]),
		WIRE_CHAMBER("wire_chamber", 2,  QMDConfig.detector_efficiency[2], QMDConfig.detector_base_power[2]),
		EM_CALORIMETER("em_calorimeter", 3,  QMDConfig.detector_efficiency[3], QMDConfig.detector_base_power[3]),
		HADRON_CALORIMETER("hadron_calorimeter", 4,  QMDConfig.detector_efficiency[4], QMDConfig.detector_base_power[4]);
		
		private String name;
		private int id;
		private double efficiency;
		private int basePower;

		private DetectorType(String name, int id, double efficiency, int basePower)
		{
			this.name = name;
			this.id = id;
			this.efficiency = efficiency;
			this.basePower = basePower;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		public int getID()
		{
			return id;
		}

		
		public double getEfficiency()
		{
			return efficiency;
		}


		public int getBasePower()
		{
			return basePower;
		}
		
		@Override
		public int getHarvestLevel()
		{
			return 0;
		}

		@Override
		public String getHarvestTool()
		{
			return "pickaxe";
		}

		@Override
		public float getHardness()
		{
			return 2;
		}

		@Override
		public float getResistance()
		{
			return 10;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}
	}
	
	
	
	
	
	public enum ProcessorType implements IStringSerializable
	{
		ORE_LEACHER("ore_leacher", GUI_ID.ORE_LEACHER, "splash", "reddust"),
		IRRADIATOR("irradiator",  GUI_ID.IRRADIATOR, "endRod", "reddust");

		private String name;
		private int id;
		private String particle1;
		private String particle2;

		private ProcessorType(String name, int id, String particle1, String particle2)
		{
			this.name = name;
			this.id = id;
			this.particle1 = particle1;
			this.particle2 = particle2;
		}

		@Override
		public String getName()
		{
			return name;
		}

		public int getID()
		{
			return id;
		}

		public TileEntity getTile()
		{
			switch (this)
			{
			case ORE_LEACHER:
				return new TileOreLeacher();
			case IRRADIATOR:
				return new TileIrradiator();

			default:
				return null;
			}
		}

		public Block getBlock()
		{
			switch (this)
			{
			case ORE_LEACHER:
				return QMDBlocks.oreLeacher;
			case IRRADIATOR:
				return QMDBlocks.irradiator;

			default:
				return QMDBlocks.oreLeacher;
			}
		}

		public CreativeTabs getCreativeTab()
		{
			switch (this)
			{
			default:
				return QMDTabs.BLOCKS;
			}
		}

		public String getParticle1()
		{
			return particle1;
		}

		public String getParticle2()
		{
			return particle2;
		}
	}
	
	public static enum NeutronReflectorType implements IStringSerializable, IBlockMetaEnum
	{
		TUNGSTEN_CABIDE("tungsten_carbide", 0, QMDConfig.fission_reflector_efficiency[0], QMDConfig.fission_reflector_reflectivity[0], 0, "pickaxe", 2, 15, 0);
		
		private String name;
		private int id;
		private double efficiency;
		private double reflectivity;
		private int harvestLevel;
		private String harvestTool;
		private float hardness;
		private float resistance;
		private int lightValue;
		
		private NeutronReflectorType(String name, int id, double efficiency, double reflectivity, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue)
		{
			this.name = name;
			this.id = id;
			this.efficiency = efficiency;
			this.reflectivity = reflectivity;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
		}
		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		@Override
		public int getID()
		{
			return id;
		}

		public double getEfficiency()
		{
			return efficiency;
		}

		public double getReflectivity()
		{
			return reflectivity;
		}

		@Override
		public int getHarvestLevel()
		{
			return harvestLevel;
		}

		@Override
		public String getHarvestTool()
		{
			return harvestTool;
		}

		@Override
		public float getHardness()
		{
			return hardness;
		}

		@Override
		public float getResistance()
		{
			return resistance;
		}

		@Override
		public int getLightValue()
		{
			return lightValue;
		}
	}
	
	public enum RTGType
	{
		STRONTIUM(0, RadSources.STRONTIUM_90 / 8D);

		private int id;
		private double radiation;

		private RTGType(int id, double radiation)
		{
			this.id = id;
			this.radiation = radiation;
		}

		public int getPower()
		{
			return QMDConfig.rtg_power[id];
		}

		public double getRadiation()
		{
			return radiation;
		}

		public TileEntity getTile()
		{
			switch (this)
			{
			case STRONTIUM:
				return new QMDTileRTG.Strontium();
			default:
				return null;
			}
		}
	}
	
	public static enum NeutronShieldType implements IStringSerializable, IBlockMetaEnum
	{
		HAFNIUM("hafnium", 0, QMDConfig.fission_shield_heat_per_flux[0], QMDConfig.fission_shield_efficiency[0], 0, "pickaxe", 2, 15, 0);
		
		private final String name;
		private final int id;
		private final double heatPerFlux;
		private final double efficiency;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		private NeutronShieldType(String name, int id, double heatPerFlux, double efficiency, int harvestLevel,
				String harvestTool, float hardness, float resistance, int lightValue)
		{
			this.name = name;
			this.id = id;
			this.heatPerFlux = heatPerFlux;
			this.efficiency = efficiency;
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		@Override
		public int getID()
		{
			return id;
		}

		public double getHeatPerFlux()
		{
			return heatPerFlux;
		}

		public double getEfficiency()
		{
			return efficiency;
		}

		@Override
		public int getHarvestLevel()
		{
			return harvestLevel;
		}

		@Override
		public String getHarvestTool()
		{
			return harvestTool;
		}

		@Override
		public float getHardness()
		{
			return hardness;
		}

		@Override
		public float getResistance()
		{
			return resistance;
		}

		@Override
		public int getLightValue()
		{
			return lightValue;
		}
	}

	public static enum LampType implements IStringSerializable, IBlockMetaEnum
	{
		EMPTY("empty", 0, 0, "pickaxe", 2, 15, 15),
		HYDROGEN("hydrogen", 1, 0, "pickaxe", 2, 15, 15),
		HELIUM("helium", 2, 0, "pickaxe", 2, 15, 15),
		NITROGEN("nitrogen", 3, 0, "pickaxe", 2, 15, 15),
		OXYGEN("oxygen", 4, 0, "pickaxe", 2, 15, 15),
		NEON("neon", 5, 0, "pickaxe", 2, 15, 15),
		ARGON("argon", 6, 0, "pickaxe", 2, 15, 15);
		
		
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		private LampType(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue)
		{
			this.name = name;
			this.id = id;
			
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		@Override
		public int getID()
		{
			return id;
		}


		@Override
		public int getHarvestLevel()
		{
			return harvestLevel;
		}

		@Override
		public String getHarvestTool()
		{
			return harvestTool;
		}

		@Override
		public float getHardness()
		{
			return hardness;
		}

		@Override
		public float getResistance()
		{
			return resistance;
		}

		@Override
		public int getLightValue()
		{
			return lightValue;
		}
	}
	
	public static enum LampType2 implements IStringSerializable, IBlockMetaEnum
	{
		SODIUM("sodium", 0, 0, "pickaxe", 2, 15, 15),
		MERCURY("mercury", 1, 0, "pickaxe", 2, 15, 15);
		
		
		
		private final String name;
		private final int id;
		private final int harvestLevel;
		private final String harvestTool;
		private final float hardness;
		private final float resistance;
		private final int lightValue;
		
		private LampType2(String name, int id, int harvestLevel, String harvestTool, float hardness, float resistance, int lightValue)
		{
			this.name = name;
			this.id = id;
			
			this.harvestLevel = harvestLevel;
			this.harvestTool = harvestTool;
			this.hardness = hardness;
			this.resistance = resistance;
			this.lightValue = lightValue;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		@Override
		public int getID()
		{
			return id;
		}


		@Override
		public int getHarvestLevel()
		{
			return harvestLevel;
		}

		@Override
		public String getHarvestTool()
		{
			return harvestTool;
		}

		@Override
		public float getHardness()
		{
			return hardness;
		}

		@Override
		public float getResistance()
		{
			return resistance;
		}

		@Override
		public int getLightValue()
		{
			return lightValue;
		}
	}
	
	
	public enum SimpleTileType implements IStringSerializable
	{
		ATMOSPHERE_COLLECTOR("atmosphere_collector", QMDTabs.BLOCKS);
		
		
		private final String name;
		private final CreativeTabs tab;
		
		private SimpleTileType(String name, CreativeTabs tab)
		{
			this.name = name;
			this.tab = tab;
		}
		
		@Override
		public String getName()
		{
			return name;
		}
		
		public TileEntity getTile()
		{
			switch (this) {
				case ATMOSPHERE_COLLECTOR:
					return new TileAtmosphereCollector();
				default:
					return null;
			}
		}
		
		public CreativeTabs getCreativeTab()
		{
			return tab;
		}
	}
	
	public enum TurbineBladeType implements IRotorBladeType
	{
		SUPER_ALLOY("super_alloy",QMDConfig.turbine_blade_efficiency[0],QMDConfig.turbine_blade_expansion[0]);

		
		private final String name;
		private final double efficiency;
		private final double expansion;
		
		private TurbineBladeType(String name, double efficiency, double expansion)
		{
			this.name = name;
			this.efficiency = efficiency;
			this.expansion = expansion;
		}
		
		@Override
		public String getName()
		{
			return name;
		}
		
		@Override
		public String toString()
		{
			return getName();
		}
		
		@Override
		public double getEfficiency()
		{
			return efficiency;
		}
		
		@Override
		public double getExpansionCoefficient()
		{
			return expansion;
		}
		
	}
	
	
	
	public enum HeaterType implements IStringSerializable, IBlockMetaEnum, ICoolerEnum
	{
		
		IRON("iron", 0, QMDConfig.heater_heat_removed[0]),
		REDSTONE("redstone", 1, QMDConfig.heater_heat_removed[1]),
		QUARTZ("quartz", 2, QMDConfig.heater_heat_removed[2]),
		OBSIDIAN("obsidian", 3, QMDConfig.heater_heat_removed[3]),
		GLOWSTONE("glowstone", 4, QMDConfig.heater_heat_removed[4]),
		LAPIS("lapis", 5, QMDConfig.heater_heat_removed[5]),
		GOLD("gold", 6, QMDConfig.heater_heat_removed[6]),
		DIAMOND("diamond", 7, QMDConfig.heater_heat_removed[7]);
		
		
		
		private String name;
		private int id;
		private int heat;
		

		private HeaterType(String name, int id,  int heat)
		{
			this.name = name;
			this.id = id;
			this.heat = heat;
			
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return getName();
		}

		public int getID()
		{
			return id;
		}


		@Override
		public int getHeatRemoved()
		{
			return heat;
		}
		@Override
		public int getHarvestLevel()
		{
			return 0;
		}

		@Override
		public String getHarvestTool()
		{
			return "pickaxe";
		}

		@Override
		public float getHardness()
		{
			return 2;
		}

		@Override
		public float getResistance()
		{
			return 10;
		}

		@Override
		public int getLightValue()
		{
			if(this == GLOWSTONE)
			{
				return 15;
			}
			return 0;
		}
		

	}
	
	
	
	

}
