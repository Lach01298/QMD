package lach_01298.qmd;

import lach_01298.qmd.config.QMDConfig;
import nc.config.NCConfig;
import nc.enumm.IBlockMetaEnum;
import net.minecraft.util.IStringSerializable;

public class EnumTypes
{
	public enum CoolerType1 implements IStringSerializable, IBlockMetaEnum 
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
			return 15;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}
		

	}



	public enum CoolerType2 implements IStringSerializable, IBlockMetaEnum
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
			return 15;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}

	}

	public enum RFCavityType implements IStringSerializable, IBlockMetaEnum 
	{
		COPPER("copper", 0, QMDConfig.RF_cavity_voltage[0], QMDConfig.RF_cavity_efficiency[0],QMDConfig.RF_cavity_heat_generated[0],QMDConfig.RF_cavity_base_power[0]),
		MAGNESIUM_DIBORIDE("magnesium_diboride", 1, QMDConfig.RF_cavity_voltage[1],  QMDConfig.RF_cavity_efficiency[1],QMDConfig.RF_cavity_heat_generated[1],QMDConfig.RF_cavity_base_power[1]),
		NIOBIUM_TIN("niobium_tin", 2, QMDConfig.RF_cavity_voltage[2],  QMDConfig.RF_cavity_efficiency[2],QMDConfig.RF_cavity_heat_generated[2],QMDConfig.RF_cavity_base_power[2]);

		private String name;
		private int id;
		private int voltage;
		private double efficiency;
		private int heat;
		private int basePower;

		private RFCavityType(String name, int id, int voltage, double efficiency, int heat, int basePower)
		{
			this.name = name;
			this.id = id;
			this.voltage = voltage;
			this.efficiency = efficiency;
			this.heat = heat;
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
			return 15;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}
	}
	
	public enum MagnetType implements IStringSerializable, IBlockMetaEnum 
	{
		COPPER("copper", 0, QMDConfig.magnet_strength[0], QMDConfig.magnet_efficiency[0],QMDConfig.magnet_heat_generated[0],QMDConfig.magnet_base_power[0]),
		MAGNESIUM_DIBORIDE("magnesium_diboride", 1, QMDConfig.magnet_strength[1],  QMDConfig.magnet_efficiency[1],QMDConfig.magnet_heat_generated[1],QMDConfig.magnet_base_power[1]),
		NIOBIUM_TIN("niobium_tin", 2, QMDConfig.magnet_strength[2],  QMDConfig.magnet_efficiency[2],QMDConfig.magnet_heat_generated[2],QMDConfig.magnet_base_power[2]);

		private String name;
		private int id;
		private double strength;
		private double efficiency;
		private int heat;
		private int basePower;

		private MagnetType(String name, int id, double strength, double efficiency, int heat, int basePower)
		{
			this.name = name;
			this.id = id;
			this.strength = strength;
			this.efficiency = efficiency;
			this.heat = heat;
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
			return 15;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}
	}






}


