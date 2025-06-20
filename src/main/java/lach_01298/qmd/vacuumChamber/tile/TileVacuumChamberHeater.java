package lach_01298.qmd.vacuumChamber.tile;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.enums.BlockTypes.HeaterType;
import lach_01298.qmd.vacuumChamber.*;
import nc.multiblock.PlacementRule;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileVacuumChamberHeater extends TileVacuumChamberPart implements IVacuumChamberComponent
{
	
	public String name;
	public int coolingRate;
	
	public String ruleID;
	public PlacementRule<VacuumChamber, IVacuumChamberPart> placementRule;
	
	public boolean isSearched = false, isInValidPosition = false;

	
	/** Don't use this constructor! */
	public TileVacuumChamberHeater() {
		super(CuboidalPartPositionType.INTERIOR);
	}
	
	public TileVacuumChamberHeater(String name, int coolingRate, String ruleID)
	{
		this();
		this.name = name;
		this.coolingRate = coolingRate;
		this.ruleID = ruleID;
		this.placementRule = HeaterPlacement.RULE_MAP.get(ruleID);
	}

	protected TileVacuumChamberHeater(String name, int coolingRate) {
		this(name, coolingRate, name + "_heater");
	}
	

	public static class Iron extends TileVacuumChamberHeater
	{
		public Iron()
		{
			super("iron", HeaterType.IRON.getHeatRemoved());
		}
	}

	public static class Redstone extends TileVacuumChamberHeater
	{

		public Redstone()
		{
			super("redstone", HeaterType.REDSTONE.getHeatRemoved());
		}
	}

	public static class Quartz extends TileVacuumChamberHeater
	{
		public Quartz()
		{
			super("quartz",HeaterType.QUARTZ.getHeatRemoved());
		}
	}

	public static class Obsidian extends TileVacuumChamberHeater
	{
		public Obsidian()
		{
			super("obsidian", HeaterType.OBSIDIAN.getHeatRemoved());
		}
	}

	
	public static class Glowstone extends TileVacuumChamberHeater
	{
		public Glowstone()
		{
			super("glowstone",HeaterType.GLOWSTONE.getHeatRemoved());
		}
	}

	public static class Lapis extends TileVacuumChamberHeater
	{
		public Lapis()
		{
			super("lapis",HeaterType.LAPIS.getHeatRemoved());
		}
	}

	public static class Gold extends TileVacuumChamberHeater
	{
		public Gold()
		{
			super("gold", HeaterType.GOLD.getHeatRemoved());
		}
	}

	public static class Diamond extends TileVacuumChamberHeater
	{
		public Diamond()
		{
			super("diamond",HeaterType.DIAMOND.getHeatRemoved());
		}
	}

	
	

	@Override
	public void onMachineAssembled(VacuumChamber controller)
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


	public void coolerSearch(final ObjectSet<TileVacuumChamberHeater> validCache, final ObjectSet<TileVacuumChamberHeater> searchCache, final Long2ObjectMap<TileVacuumChamberHeater> partFailCache, final Long2ObjectMap<TileVacuumChamberHeater> assumedValidCache)
	{
		if (!isHeaterValid(partFailCache, assumedValidCache))
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
			TileVacuumChamberHeater part = getMultiblock().getPartMap(TileVacuumChamberHeater.class).get(getTilePos().offset(dir).toLong());
			if (part != null)
			{
				searchCache.add(part);
			}
		}
	}
	
	public boolean isHeaterValid(final Long2ObjectMap<TileVacuumChamberHeater> partFailCache, final Long2ObjectMap<TileVacuumChamberHeater> assumedValidCache)
	{
		
		if (partFailCache.containsKey(pos.toLong()))
		{
			return isInValidPosition = false;
		}
		else if (placementRule.requiresRecheck())
		{
			isInValidPosition = placementRule.satisfied(this, false);
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
		
		return isInValidPosition = placementRule.satisfied(this, false);
	}
	
	public boolean isSearchRoot()
	{
		for (String dep : placementRule.getDependencies())
		{
			if (dep.equals("beam")||dep.equals("vacuum_chamber_casing")||dep.equals("plasma_glass")||dep.equals("nozzle"))
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
			placementRule = HeaterPlacement.RULE_MAP.get(ruleID);
		}
	}

	@Override
	public int getHeating()
	{
		return 0;
	}

	@Override
	public int getPower()
	{
		return 0;
	}

}
