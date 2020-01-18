package lach_01298.qmd;

import nc.enumm.IMetaEnum;
import net.minecraft.util.IStringSerializable;

public class MaterialEnums
{

	
	
	
	
	public static enum DustType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN("tungsten", 0), 
		NIOBIUM("niobium", 1),
		CHROMIUM("chromium",2); 
		 
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
	
	
	
	public static enum IngotType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN("tungsten", 0), 
		NIOBIUM("niobium", 1),
		CHROMIUM("chromium",2); 
		 
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
	
	public static enum IngotAlloyType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN_CARBIDE("tungsten_carbide", 0), 
		NIOBIUM_TIN("niobium_tin", 1), 
		STAINLESS_STEEL("stainless_steel", 2); 
		 
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
		MAGNESIUM_26("magnesium_26",4);
		 
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
		EMTPY_COOLER("empty_cooler", 0),
		DETECTOR_CASING("detector_casing", 1),
		SCINTILLATOR_PWO("scintillator_pwo", 2),
		SCINTILLATOR_PLASTIC("scintillator_plastic",3);
		 
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
		SILICON_WAFER("silicon_wafer",3);
		 
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
	
	
	
	
}
