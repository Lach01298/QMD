package lach_01298.qmd.enums;

import nc.enumm.IMetaEnum;
import net.minecraft.util.IStringSerializable;

public class MaterialTypes
{

	
	
	
	
	public static enum DustType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN("tungsten", 0), 
		NIOBIUM("niobium", 1),
		CHROMIUM("chromium",2), 
		TITANIUM("titanium",3),
		COBALT("cobalt",4),
		NICKEL("nickel",5),
		HAFNIUM("hafnium",6),
		ZINC("zinc", 7),
		OSMIUM("osmium",8),
		IRIDIUM("iridium",9),
		PLATNIUM("platinum",10),
		SODIUM("sodium",11),
		POTASSIUM("potassium",12),
		CALCIUM("calcium",13),
		STRONTIUM("strontium",14);
		
		
		
		 
		private String name;
		private int id;

		private DustType(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	public static enum DustType2 implements IStringSerializable, IMetaEnum
	{
		YTTRIUM("yttrium", 0), 
		NEODYMIUM("neodymium", 1),
		GERMANIUM("germanium",2);
		
		
		
		 
		private String name;
		private int id;

		private DustType2(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	
	
	public static enum IngotType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN("tungsten", 0), 
		NIOBIUM("niobium", 1),
		CHROMIUM("chromium",2),
		TITANIUM("titanium",3),
		COBALT("cobalt",4),
		NICKEL("nickel",5),
		HAFNIUM("hafnium",6),
		ZINC("zinc", 7),
		OSMIUM("osmium",8),
		IRIDIUM("iridium",9),
		PLATNIUM("platinum",10),
		SODIUM("sodium",11),
		POTASSIUM("potassium",12),
		CALCIUM("calcium",13),
		STRONTIUM("strontium",14);
		
		 
		private String name;
		private int id;

		private IngotType(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	public static enum IngotType2 implements IStringSerializable, IMetaEnum
	{
		YTTRIUM("yttrium", 0), 
		NEODYMIUM("neodymium", 1),
		GERMANIUM("germanium",2);
		 
		private String name;
		private int id;

		private IngotType2(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	public static enum IngotAlloyType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN_CARBIDE("tungsten_carbide", 0), 
		NIOBIUM_TIN("niobium_tin", 1), 
		STAINLESS_STEEL("stainless_steel", 2),
		NIOBIUM_TITANIUM("niobium_titanium", 3);
		 
		 
		private String name;
		private int id;

		private IngotAlloyType(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	
	public static enum IsotopeType implements IStringSerializable, IMetaEnum
	{
		SODIUM_22("sodium_22", 0),
		PROMETHIUM_147("promethium_147",1),
		BERYLLIUM_7("beryllium_7",2),
		MAGNESIUM_24("magnesium_24",3),
		MAGNESIUM_26("magnesium_26",4),
		LEAD_210("lead_210",5),
		STRONTIUM_90("strontium_90",6),
		Uranium_234("uranium_234",7),
		PROTACTINIUM_231("protactinium_231",8),
		COBALT_60("cobalt_60",9),
		IRIDIUM_192("iridium_192",10);
		 
		private String name;
		private int id;

		private IsotopeType(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	
	
	
	
	
	public static enum PartType implements IStringSerializable, IMetaEnum
	{
		EMPTY_COOLER("empty_cooler", 0),
		DETECTOR_CASING("detector_casing", 1),
		SCINTILLATOR_PWO("scintillator_pwo", 2),
		SCINTILLATOR_PLASTIC("scintillator_plastic",3),
		WIRE_BSCCO("wire_bscco", 4),
		ROD_ND_YAG("rod_nd_yag", 5),
		WIRE_GOLD_TUNGSTEN("wire_gold_tungsten", 6),
		WIRE_CHAMBER_CASING("wire_chamber_casing", 7);
		private String name;
		private int id;

		private PartType(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	public static enum SemiconductorType implements IStringSerializable, IMetaEnum
	{
		SILICON_P_DOPED("silicon_p_doped", 0),
		SILICON_N_DOPED("silicon_n_doped", 1),
		SILICON_BOULE("silicon_boule", 2),
		SILICON_WAFER("silicon_wafer",3),
		BASIC_PROCESSOR("basic_processor",4),
		ADVANCED_PROCESSOR("advanced_processor",5),
		ELITE_PROCESSOR("elite_processor",6);
		 
		private String name;
		private int id;

		private SemiconductorType(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	public static enum ChemicalDustType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN_OXIDE("tungsten_oxide", 0),
		BSCCO("bscco",1), 
		SODIUM_NITRATE("sodium_nitrate", 2),
		SODIUM_CHLORIDE("sodium_chloride", 3),
		COPPER_OXIDE("copper_oxide", 4),
		HAFNIUM_OXIDE("hafnium_oxide", 5);
		 
		private String name;
		private int id;

		private ChemicalDustType(String name, int id)
		{
			this.name = name;
			this.id = id;
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
	}
	
	
	
}
