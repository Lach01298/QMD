package lach_01298.qmd.enums;

import lach_01298.qmd.QMDConstants;
import nc.enumm.*;
import net.minecraft.util.IStringSerializable;

import static lach_01298.qmd.config.QMDConfig.*;

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
		STRONTIUM("strontium",14),
		BARIUM("barium",15);
		
		
		
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
		IODINE("iodine",2),
		SAMARIUM("samarium",3),
		TERBIUM("terbium",4),
		ERBIUM("erbium",5),
		YTTERBIUM("ytterbium",6);
		
		
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
		STRONTIUM("strontium",14),
		BARIUM("barium",15);
		
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
		MERCURY("mercury", 2);
		
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
		NIOBIUM_TITANIUM("niobium_titanium", 3),
		OSMIRIDIUM("osmiridium", 4),
		NICHROME("nichrome", 5),
		SUPER_ALLOY("super_alloy", 6);
		
		
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
		BERYLLIUM_7("beryllium_7",1),
		MAGNESIUM_24("magnesium_24",2),
		MAGNESIUM_26("magnesium_26",3),
		Uranium_234("uranium_234",4),
		PROTACTINIUM_231("protactinium_231",5),
		COBALT_60("cobalt_60",6),
		IRIDIUM_192("iridium_192",7),
		CALCIUM_48("calcium_48",8);
		
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
		WIRE_CHAMBER_CASING("wire_chamber_casing", 7),
		MAGNET_ND("magnet_nd", 8),
		ACCELERATING_BARREL("accelerating_barrel", 9),
		LASER_ASSEMBLY("laser_assembly", 10),
		WIRE_SSFAF("wire_ssfaf", 11),
		WIRE_YBCO("wire_ybco", 12),
		MAGNET_SMC("magnet_smc", 13);
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
		HAFNIUM_OXIDE("hafnium_oxide", 5),
		STRONTIUM_CHLORIDE("strontium_chloride", 6),
		ZINC_SULFIDE("zinc_sulfide", 7),
		IRON_FLUORIDE("iron_fluoride",8),
		SSFAF("ssfaf",9),
		YBCO("ybco",10);
		
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
	
	public static enum LuminousPaintType implements IStringSerializable, IMetaEnum
	{
		GREEN("green", 0),
		BLUE("blue",1),
		ORANGE("orange", 2);
		
		private String name;
		private int id;

		private LuminousPaintType(String name, int id)
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
	
	public static enum FissionWasteType implements IStringSerializable, IMetaEnum
	{
		LIGHT("light", 0),
		HEAVY("heavy",1);
		
		private String name;
		private int id;

		private FissionWasteType(String name, int id)
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
	
	public static enum SpallationWasteType implements IStringSerializable, IMetaEnum
	{
		CALIFORNIUM("californium", 0),
		BERKELIUM("berkelium",1),
		CURIUM("curium", 2),
		AMERICIUM("americium",3),
		PLUTONIUM("plutonium", 4),
		NEPTUNIUM("neptunium",5),
		URANIUM("uranium",6),
		PROTACTINIUM("protactinium",7),
		THORIUM("thorium",8),
		RADIUM("radium",9),
		POLONIUM("polonium",10),
		BISMUTH("bismuth",11),
		LEAD("lead",12),
		GOLD("gold",13),
		PLATINUM("platinum",14),
		IRIDIUM("iridium",15);
		
		private String name;
		private int id;

		private SpallationWasteType(String name, int id)
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
	
	public static enum SpallationWasteType2 implements IStringSerializable, IMetaEnum
	{
		OSMIUM("osmium", 0),
		TUNGSTEN("tungsten",1),
		HAFNIUM("hafnium", 2),
		MERCURY("mercury",3);
		
		private String name;
		private int id;

		private SpallationWasteType2(String name, int id)
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
	
	
	public static enum CellType implements IItemCapacity
	{
		EMPTY("empty", 0, 0),
		ANTIHYDROGEN("antihydrogen", 1, QMDConstants.moleAmount/10),
		ANTIDEUTERIUM("antideuterium",2,QMDConstants.moleAmount/10),
		ANTITRITIUM("antitritium", 3,QMDConstants.moleAmount/10),
		ANTIHELIUM3("antihelium3", 4, QMDConstants.moleAmount/10),
		ANTIHELIUM("antihelium", 5, QMDConstants.moleAmount/10),
		POSITRONIUM("positronium", 6, QMDConstants.moleAmount/10),
		MUONIUM("muonium",7, QMDConstants.moleAmount/10),
		TAUONIUM("tauonium", 8, QMDConstants.moleAmount/10),
		GLUEBALLS("glueballs", 9, QMDConstants.moleAmount/10);
		
		private String name;
		private int id;
		private int capacity;

		private CellType(String name, int id, int capacity)
		{
			this.name = name;
			this.id = id;
			this.capacity = capacity;
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
		public int getCapacity()
		{
			return capacity;
		}
	}
	
	
	
	public static enum SourceType implements IItemCapacity
	{
		TUNGSTEN_FILAMENT("tungsten_filament", 0, 50 * QMDConstants.moleAmount),
		SODIUM_22("sodium_22", 1, 5* QMDConstants.moleAmount),
		COBALT_60("cobalt_60",2,1 * QMDConstants.moleAmount),
		IRIDIUM_192("iridium_192", 3, 1 * QMDConstants.moleAmount),
		CALCIUM_48("calcium_48", 4, 5 * QMDConstants.moleAmount);
		
		
		private String name;
		private int id;
		private int capacity;

		private SourceType(String name, int id, int capacity)
		{
			this.name = name;
			this.id = id;
			this.capacity = capacity;
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
		public int getCapacity()
		{
			return capacity;
		}
	}
	
	
	
	
	public static enum CoperniciumType implements IStringSerializable, IMetaEnum
	{
		_291("291", 0),
		_291_C("291_c", 1),
		_291_OX("291_ox", 2),
		_291_NI("291_ni", 3),
		_291_ZA("291_za", 4);

		
		private final String name;
		private final int id;
		
		private CoperniciumType(String name, int id) {
			this.name = name;
			this.id = id;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		@Override
		public int getID() {
			return id;
		}
	}
	
	public static enum CoperniciumPelletType implements IStringSerializable, IMetaEnum {
		MIX_291("mix_291", 0),
		MIX_291_C("mix_291_c", 1);
		
		private final String name;
		private final int id;
		
		private CoperniciumPelletType(String name, int id) {
			this.name = name;
			this.id = id;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		@Override
		public int getID() {
			return id;
		}
	}
	
	public static enum CoperniciumFuelType implements IStringSerializable, IFissionFuelEnum {
		MIX_291_TR("mix_291_tr", 0),
		MIX_291_OX("mix_291_ox", 1),
		MIX_291_NI("mix_291_ni", 2),
		MIX_291_ZA("mix_291_za", 3);
		
		private final String name;
		private final int id;
		private final int fuelTime, heatGen, criticality;
		private final double efficiency;
		private final boolean selfPriming;
		
		private CoperniciumFuelType(String name, int id) {
			this.name = name;
			this.id = id;
			fuelTime = copernicium_fuel_time[id + id / 4];
			heatGen = copernicium_heat_generation[id + id / 4];
			efficiency = copernicium_efficiency[id + id / 4];
			criticality = copernicium_criticality[id + id / 4];
			selfPriming = copernicium_self_priming[id + id / 4];
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		@Override
		public int getID() {
			return id;
		}
		
		@Override
		public int getBaseTime() {
			return fuelTime;
		}
		
		@Override
		public int getBaseHeat() {
			return heatGen;
		}
		
		@Override
		public double getBaseEfficiency() {
			return efficiency;
		}
		
		@Override
		public int getCriticality() {
			return criticality;
		}
		
		@Override
		public boolean getSelfPriming() {
			return selfPriming;
		}
	}
	
	public static enum CoperniciumDepletedFuelType implements IStringSerializable, IMetaEnum {
		MIX_291_TR("mix_291_tr", 0),
		MIX_291_OX("mix_291_ox", 1),
		MIX_291_NI("mix_291_ni", 2),
		MIX_291_ZA("mix_291_za", 3);
		
		private final String name;
		private final int id;
		
		private CoperniciumDepletedFuelType(String name, int id) {
			this.name = name;
			this.id = id;
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		@Override
		public int getID() {
			return id;
		}
	}
	
}
