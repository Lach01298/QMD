package lach_01298.qmd.enums;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.machine.tile.TileQMDProcessor;
import nc.config.NCConfig;
import nc.enumm.IBlockMetaEnum;
import nc.init.NCBlocks;
import nc.tab.NCTabs;
import nc.tile.processor.TileProcessor;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundEvent;

public class EnumTypes
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
			return 15;
		}

		@Override
		public int getLightValue()
		{
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
		NIOBIUM_TIN("niobium_tin", 2, QMDConfig.magnet_strength[2],  QMDConfig.magnet_efficiency[2],QMDConfig.magnet_heat_generated[2],QMDConfig.magnet_base_power[2]),
		BSCCO("bscco", 3, QMDConfig.magnet_strength[3],  QMDConfig.magnet_efficiency[3],QMDConfig.magnet_heat_generated[3],QMDConfig.magnet_base_power[3]);
		
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
			return 15;
		}

		@Override
		public int getLightValue()
		{
			return 0;
		}
	}
	
	
	public enum IOType implements IStringSerializable
	{
		INPUT("input", 0),
		OUTPUT("output", 1),
		DISABLED("disabled", 2);
		

		private String name;
		private int id;


		private IOType(String name, int id)
		{
			this.name = name;
			this.id = id;

		}
		
		public IOType getNextIO()
		{
			if(this.getID() >= IOType.values().length-1)
			{
				return getTypeFromID(0);
				
			}
			return getTypeFromID(this.getID()+1);
		}
		
		
		public static IOType getTypeFromID(int id)
		{
			return IOType.values()[id];
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
		
				
	
	}
	
	
	public enum ProcessorType implements IStringSerializable
	{
		ORE_LEACHER("ore_leacher", 0, "portal", "reddust");

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
			//	return new TileQMDProcessor.OreLeacher();

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

			default:
				return QMDBlocks.oreLeacher;
			}
		}

		public CreativeTabs getCreativeTab()
		{
			switch (this)
			{
			default:
				return NCTabs.MACHINE;
			}
		}

		@SuppressWarnings("static-method")
		public SoundEvent getSound()
		{
			return null;
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
	
	
	
	
	
	

	
}


