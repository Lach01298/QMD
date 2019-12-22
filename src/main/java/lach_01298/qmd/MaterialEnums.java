package lach_01298.qmd;

import nc.enumm.IMetaEnum;
import net.minecraft.util.IStringSerializable;

public class MaterialEnums
{

	
	
	
	
	public static enum DustType implements IStringSerializable, IMetaEnum
	{
		TUNGSTEN("tungsten", 0), 
		NIOBIUM("niobium", 1); 
		 
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
		NIOBIUM("niobium", 1); 
		 
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
		NIOBIUM_TIN("niobium_tin", 1); 
		 
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
	
	
	
	
}
