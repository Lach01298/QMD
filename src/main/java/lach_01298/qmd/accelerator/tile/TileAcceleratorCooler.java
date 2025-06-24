package lach_01298.qmd.accelerator.tile;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.accelerator.*;
import lach_01298.qmd.enums.BlockTypes.*;
import nc.multiblock.PlacementRule;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileAcceleratorCooler extends TileAcceleratorPart implements IAcceleratorComponent
{
	
	public String name;
	public int coolingRate;
	
	public String ruleID;
	public PlacementRule<Accelerator, IAcceleratorPart> placementRule;
	
	public boolean isSearched = false, isInValidPosition = false;

	
	/** Don't use this constructor! */
	public TileAcceleratorCooler() {
		super(CuboidalPartPositionType.INTERIOR);
	}
	
	public TileAcceleratorCooler(String name, int coolingRate, String ruleID)
	{
		this();
		this.name = name;
		this.coolingRate = coolingRate;
		this.ruleID = ruleID;
		this.placementRule = CoolerPlacement.RULE_MAP.get(ruleID);
	}

	protected TileAcceleratorCooler(String name, int coolingRate) {
		this(name, coolingRate, name + "_cooler");
	}
	
	public static class Water extends TileAcceleratorCooler
	{
		public Water()
		{
			super("water", CoolerType1.WATER.getHeatRemoved());
		}
	}

	public static class Iron extends TileAcceleratorCooler
	{
		public Iron()
		{
			super("iron", CoolerType1.IRON.getHeatRemoved());
		}
	}

	public static class Redstone extends TileAcceleratorCooler
	{

		public Redstone()
		{
			super("redstone", CoolerType1.REDSTONE.getHeatRemoved());
		}
	}

	public static class Quartz extends TileAcceleratorCooler
	{
		public Quartz()
		{
			super("quartz",CoolerType1.QUARTZ.getHeatRemoved());
		}
	}

	public static class Obsidian extends TileAcceleratorCooler
	{
		public Obsidian()
		{
			super("obsidian", CoolerType1.OBSIDIAN.getHeatRemoved());
		}
	}

	public static class NetherBrick extends TileAcceleratorCooler
	{
		public NetherBrick()
		{
			super("nether_brick",CoolerType1.NETHER_BRICK.getHeatRemoved());
		}

	}

	public static class Glowstone extends TileAcceleratorCooler
	{
		public Glowstone()
		{
			super("glowstone", CoolerType1.GLOWSTONE.getHeatRemoved());
		}
	}

	public static class Lapis extends TileAcceleratorCooler
	{
		public Lapis()
		{
			super("lapis", CoolerType1.LAPIS.getHeatRemoved());
		}
	}

	public static class Gold extends TileAcceleratorCooler
	{
		public Gold()
		{
			super("gold", CoolerType1.GOLD.getHeatRemoved());
		}
	}

	public static class Prismarine extends TileAcceleratorCooler
	{
		public Prismarine()
		{
			super("prismarine", CoolerType1.PRISMARINE.getHeatRemoved());
		}
	}

	public static class Slime extends TileAcceleratorCooler
	{

		public Slime()
		{
			super("slime", CoolerType1.SLIME.getHeatRemoved());
		}
	}

	public static class EndStone extends TileAcceleratorCooler
	{
		public EndStone()
		{
			super("end_stone", CoolerType1.END_STONE.getHeatRemoved());
		}
	}

	public static class Purpur extends TileAcceleratorCooler
	{
		public Purpur()
		{
			super("purpur",CoolerType1.PURPUR.getHeatRemoved());
		}
	}

	public static class Diamond extends TileAcceleratorCooler
	{
		public Diamond()
		{
			super("diamond", CoolerType1.DIAMOND.getHeatRemoved());
		}
	}

	public static class Emerald extends TileAcceleratorCooler
	{
		public Emerald()
		{
			super("emerald", CoolerType1.EMERALD.getHeatRemoved());
		}
	}

	public static class Copper extends TileAcceleratorCooler
	{
		public Copper()
		{
			super("copper", CoolerType1.COPPER.getHeatRemoved());
		}
	}

	public static class Tin extends TileAcceleratorCooler
	{
		public Tin()
		{
			super("tin", CoolerType2.TIN.getHeatRemoved());
		}

	}

	public static class Lead extends TileAcceleratorCooler
	{
		public Lead()
		{
			super("lead", CoolerType2.LEAD.getHeatRemoved());
		}
	}

	public static class Boron extends TileAcceleratorCooler
	{
		public Boron()
		{
			super("boron", CoolerType2.BORON.getHeatRemoved());
		}
	}

	public static class Lithium extends TileAcceleratorCooler
	{
		public Lithium()
		{
			super("lithium", CoolerType2.LITHIUM.getHeatRemoved());
		}
	}

	public static class Magnesium extends TileAcceleratorCooler
	{
		public Magnesium()
		{
			super("magnesium", CoolerType2.MAGNESIUM.getHeatRemoved());
		}
	}

	public static class Manganese extends TileAcceleratorCooler
	{
		public Manganese()
		{
			super("manganese", CoolerType2.MANGANESE.getHeatRemoved());
		}
	}

	public static class Aluminum extends TileAcceleratorCooler
	{
		public Aluminum()
		{
			super("aluminum", CoolerType2.ALUMINUM.getHeatRemoved());
		}
	}

	public static class Silver extends TileAcceleratorCooler
	{
		public Silver()
		{
			super("silver", CoolerType2.SILVER.getHeatRemoved());
		}
	}

	public static class Fluorite extends TileAcceleratorCooler
	{
		public Fluorite()
		{
			super("fluorite", CoolerType2.FLUORITE.getHeatRemoved());
		}
	}

	public static class Villiaumite extends TileAcceleratorCooler
	{
		public Villiaumite()
		{
			super("villiaumite", CoolerType2.VILLIAUMITE.getHeatRemoved());
		}
	}

	public static class Carobbiite extends TileAcceleratorCooler
	{
		public Carobbiite()
		{
			super("carobbiite", CoolerType2.CAROBBIITE.getHeatRemoved());
		}
	}

	public static class Arsenic extends TileAcceleratorCooler
	{
		public Arsenic()
		{
			super("arsenic", CoolerType2.ARSENIC.getHeatRemoved());
		}
	}

	public static class LiquidNitrogen extends TileAcceleratorCooler
	{
		public LiquidNitrogen()
		{
			super("liquid_nitrogen", CoolerType2.LIQUID_NITROGEN.getHeatRemoved());
		}
	}

	public static class LiquidHelium extends TileAcceleratorCooler
	{
		public LiquidHelium()
		{
			super("liquid_helium", CoolerType2.LIQUID_HELIUM.getHeatRemoved());
		}
	}

	public static class Enderium extends TileAcceleratorCooler
	{
		public Enderium()
		{
			super("enderium", CoolerType2.ENDERIUM.getHeatRemoved());
		}
	}

	public static class Cryotheum extends TileAcceleratorCooler
	{
		public Cryotheum()
		{
			super("cryotheum", CoolerType2.CRYOTHEUM.getHeatRemoved());
		}
	}
	

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
	}

	@Override
	public boolean isFunctional()
	{
		return isInValidPosition;
	}
	
	@Override
	public void setFunctional(boolean functional)
	{
		isInValidPosition = false;
	}

	@Override
	public int getMaxOperatingTemp()
	{
		return Accelerator.MAX_TEMP;
	}


	public void coolerSearch(final ObjectSet<TileAcceleratorCooler> validCache, final ObjectSet<TileAcceleratorCooler> searchCache, final Long2ObjectMap<TileAcceleratorCooler> partFailCache, final Long2ObjectMap<TileAcceleratorCooler> assumedValidCache)
	{
		if (!isCoolerValid(partFailCache, assumedValidCache))
		{
			return;
		}
		
		if (isSearched)
		{
			return;
		}
		
		isSearched = true;
		validCache.add(this);
		
		for (EnumFacing dir : EnumFacing.VALUES)
		{
			TileAcceleratorCooler part = getMultiblock().getPartMap(TileAcceleratorCooler.class).get(getTilePos().offset(dir).toLong());
			if (part != null)
			{
				searchCache.add(part);
			}
		}
	}
	
	public boolean isCoolerValid(final Long2ObjectMap<TileAcceleratorCooler> partFailCache, final Long2ObjectMap<TileAcceleratorCooler> assumedValidCache)
	{
		if (partFailCache.containsKey(pos.toLong()))
		{
			return isInValidPosition = false;
		}
		else if (placementRule.requiresRecheck())
		{
			isInValidPosition = placementRule.satisfied(this,false);
			if (isInValidPosition)
			{
				assumedValidCache.put(pos.toLong(), this);
			}
			return isInValidPosition;
		}
		else if (isInValidPosition)
		{
			return true;
		}
		return isInValidPosition = placementRule.satisfied(this,false);
	}
	
	public boolean isSearchRoot()
	{
		for (String dep : placementRule.getDependencies())
		{
			if (dep.equals("magnet")||dep.equals("cavity")||dep.equals("yoke")||dep.equals("beam"))
				return true;
		}
		return false;
	}
	

	
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		nbt.setString("name", name);
		nbt.setInteger("coolingRate", coolingRate);
		nbt.setString("ruleID", ruleID);
		
		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		if (nbt.hasKey("name"))
			name = nbt.getString("name");
		if (nbt.hasKey("coolingRate"))
			coolingRate = nbt.getInteger("coolingRate");
		if (nbt.hasKey("ruleID")) {
			ruleID = nbt.getString("ruleID");
			placementRule = CoolerPlacement.RULE_MAP.get(ruleID);
		}
	}

}
