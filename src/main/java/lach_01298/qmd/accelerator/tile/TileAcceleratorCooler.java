package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.enums.BlockTypes.CoolerType1;
import lach_01298.qmd.enums.BlockTypes.CoolerType2;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.BlockPosHelper;
import net.minecraft.util.EnumFacing;

public abstract class TileAcceleratorCooler extends TileAcceleratorPart implements IAcceleratorComponent
{

	public final int coolingRate;
	public final String name;

	public boolean isInValidPosition = false;

	public TileAcceleratorCooler(int coolingRate, String name)
	{
		super(CuboidalPartPositionType.INTERIOR);
		this.coolingRate = coolingRate;
		this.name = name;
	}

	public static class Water extends TileAcceleratorCooler
	{

		public Water()
		{
			super(CoolerType1.WATER.getHeatRemoved(), "water");
		}

		
		
		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveRFCavity(pos.offset(dir),null))
					return true;
			}
			return false;
		}
	}

	public static class Iron extends TileAcceleratorCooler
	{

		public Iron()
		{
			super(CoolerType1.IRON.getHeatRemoved(), "iron");
		}

		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveMagnet(pos.offset(dir),null))
					return true;
			}
			return false;
		}
	}

	public static class Redstone extends TileAcceleratorCooler
	{

		public Redstone()
		{
			super(CoolerType1.REDSTONE.getHeatRemoved(), "redstone");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean cavity = false, magnet = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!cavity && isActiveRFCavity(pos.offset(dir),null))
					cavity = true;
				if (!magnet && isActiveMagnet(pos.offset(dir),null))
					magnet = true;
				if (cavity && magnet)
					return true;
			}
			return false;
		}
	}

	public static class Quartz extends TileAcceleratorCooler
	{

		public Quartz()
		{
			super(CoolerType1.QUARTZ.getHeatRemoved(), "quartz");
		}

		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "redstone"))
					return true;
			}
			return false;
		}
	}

	public static class Obsidian extends TileAcceleratorCooler
	{

		public Obsidian()
		{
			super(CoolerType1.OBSIDIAN.getHeatRemoved(), "obsidian");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte glowstone = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir),"glowstone"))
					glowstone++;
				if (glowstone >= 2)
					return true;
			}
			return false;
		}
	}

	public static class NetherBrick extends TileAcceleratorCooler
	{

		public NetherBrick()
		{
			super(CoolerType1.NETHER_BRICK.getHeatRemoved(), "nether_brick");
		}

		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "obsidian"))
					return true;
			}
			return false;
		}
	}

	public static class Glowstone extends TileAcceleratorCooler
	{

		public Glowstone()
		{
			super(CoolerType1.GLOWSTONE.getHeatRemoved(), "glowstone");
		}

		@Override
		public boolean isCoolerValid()
		{
			String magnet1 = "";
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if(isActiveMagnet(pos.offset(dir), null) && magnet1.isEmpty())
				{
					magnet1 = getMultiblock().getPartMap(TileAcceleratorMagnet.class).get(pos.offset(dir).toLong()).name;
				}
				else if(isActiveMagnet(pos.offset(dir), null))
				{
					if(!magnet1.equals(getMultiblock().getPartMap(TileAcceleratorMagnet.class).get(pos.offset(dir).toLong()).name))
					{
						return true;
					}
				}		
			}
			return false;
		}
	}

	public static class Lapis extends TileAcceleratorCooler
	{

		public Lapis()
		{
			super(CoolerType1.LAPIS.getHeatRemoved(), "lapis");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean magnet = false, yoke = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!magnet && isActiveMagnet(pos.offset(dir),null))
					magnet = true;
				if (!yoke && isActiveYoke(pos.offset(dir)))
					yoke = true;
				if (magnet && yoke)
					return true;
			}
			return false;
		}
	}

	public static class Gold extends TileAcceleratorCooler
	{

		public Gold()
		{
			super(CoolerType1.GOLD.getHeatRemoved(), "gold");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte iron = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "iron"))
					iron++;
				if (iron >= 2)
					return true;
			}
			return false;
		}
	}

	public static class Prismarine extends TileAcceleratorCooler
	{

		public Prismarine()
		{
			super(CoolerType1.PRISMARINE.getHeatRemoved(), "prismarine");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte water = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "water"))
					water++;
				if (water >= 2)
					return true;
			}
			return false;
		}
	}

	public static class Slime extends TileAcceleratorCooler
	{

		public Slime()
		{
			super(CoolerType1.SLIME.getHeatRemoved(), "slime");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte water = 0, lead = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "water"))
					water++;
				if (water > 1)
					return false;
				if (lead < 2 && isActiveCooler(pos.offset(dir), "lead"))
					lead++;
			}
			return water == 1 && lead >= 2;
		}
	}

	public static class EndStone extends TileAcceleratorCooler
	{

		public EndStone()
		{
			super(CoolerType1.END_STONE.getHeatRemoved(), "end_stone");
		}

		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isBeam(pos.offset(dir)))
					return true;
			}
			return false;
		}
	}

	public static class Purpur extends TileAcceleratorCooler
	{

		public Purpur()
		{
			super(CoolerType1.PURPUR.getHeatRemoved(), "purpur");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte iron = 0;
			boolean endStone = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "iron"))
					iron++;
				if (iron > 1)
					return false;
				if (!endStone && isActiveCooler(pos.offset(dir), "end_stone"))
					endStone = true;
			}
			return iron == 1 && endStone;
		}
	}

	public static class Diamond extends TileAcceleratorCooler
	{

		public Diamond()
		{
			super(CoolerType1.DIAMOND.getHeatRemoved(), "diamond");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean cell = false, gold = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!cell && isActiveRFCavity(pos.offset(dir),null))
					cell = true;
				if (!gold && isActiveCooler(pos.offset(dir), "gold"))
					gold = true;
				if (cell && gold)
					return true;
			}
			return false;
		}
	}

	public static class Emerald extends TileAcceleratorCooler
	{

		public Emerald()
		{
			super(CoolerType1.EMERALD.getHeatRemoved(), "emerald");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean moderator = false, prismarine = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!moderator && isActiveMagnet(pos.offset(dir),null))
					moderator = true;
				if (!prismarine && isActiveCooler(pos.offset(dir), "prismarine"))
					prismarine = true;
				if (moderator && prismarine)
					return true;
			}
			return false;
		}
	}

	public static class Copper extends TileAcceleratorCooler
	{

		public Copper()
		{
			super(CoolerType1.COPPER.getHeatRemoved(), "copper");
		}

		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "water"))
					return true;
			}
			return false;
		}
	}

	public static class Tin extends TileAcceleratorCooler
	{

		public Tin()
		{
			super(CoolerType2.TIN.getHeatRemoved(), "tin");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte lapis = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "lapis"))
					lapis++;
				if (lapis >= 2)
					return true;
			}
			return false;
		}
	}

	public static class Lead extends TileAcceleratorCooler
	{

		public Lead()
		{
			super(CoolerType2.LEAD.getHeatRemoved(), "lead");
		}

		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "iron"))
					return true;
			}
			return false;
		}
	}

	public static class Boron extends TileAcceleratorCooler
	{

		public Boron()
		{
			super(CoolerType2.BORON.getHeatRemoved(), "boron");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean yoke = false;
			boolean cavity = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveYoke(pos.offset(dir)))
					yoke = true;
				if (!cavity && isActiveRFCavity(pos.offset(dir),null))
					cavity = true;
			}
			return yoke && cavity;
		}
	}

	public static class Lithium extends TileAcceleratorCooler
	{

		public Lithium()
		{
			super(CoolerType2.LITHIUM.getHeatRemoved(), "lithium");
		}

		@Override
		public boolean isCoolerValid()
		{
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "boron"))
				{
					return true;
				}
			}
			return false;
		}
	}

	public static class Magnesium extends TileAcceleratorCooler
	{

		public Magnesium()
		{
			super(CoolerType2.MAGNESIUM.getHeatRemoved(), "magnesium");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean yokr = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveYoke(pos.offset(dir)))
				{
					return true;
				}
			}
			return false;
		}
	}

	public static class Manganese extends TileAcceleratorCooler
	{

		public Manganese()
		{
			super(CoolerType2.MANGANESE.getHeatRemoved(), "manganese");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte cavity = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveRFCavity(pos.offset(dir),null))
					cavity++;
				if (cavity >= 2)
					return true;
			}
			return false;
		}
	}

	public static class Aluminum extends TileAcceleratorCooler
	{

		public Aluminum()
		{
			super(CoolerType2.ALUMINUM.getHeatRemoved(), "aluminum");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean quartz = false, tin = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!quartz && isActiveCooler(pos.offset(dir), "quartz"))
					quartz = true;
				if (!tin && isActiveCooler(pos.offset(dir), "tin"))
					tin = true;
				if (quartz && tin)
					return true;
			}
			return false;
		}
	}

	public static class Silver extends TileAcceleratorCooler
	{

		public Silver()
		{
			super(CoolerType2.SILVER.getHeatRemoved(), "silver");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte arsenic = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir),"arsenic"))
					arsenic++;
				if (arsenic >= 2)
					return true;
			}
			return false;
		}
	}

	public static class Fluorite extends TileAcceleratorCooler
	{

		public Fluorite()
		{
			super(CoolerType2.FLUORITE.getHeatRemoved(), "fluorite");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean gold = false, prismarine = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!gold && isActiveCooler(pos.offset(dir), "gold"))
					gold = true;
				if (!prismarine && isActiveCooler(pos.offset(dir), "prismarine"))
					prismarine = true;
				if (gold && prismarine)
					return true;
			}
			return false;
		}
	}

	public static class Villiaumite extends TileAcceleratorCooler
	{

		public Villiaumite()
		{
			super(CoolerType2.VILLIAUMITE.getHeatRemoved(), "villiaumite");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean yoke = false, gold = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!yoke && isActiveYoke(pos.offset(dir)))
					yoke = true;
				if (!gold && isActiveCooler(pos.offset(dir), "gold"))
					gold = true;
				if (yoke && gold)
					return true;
			}
			return false;
		}
	}

	public static class Carobbiite extends TileAcceleratorCooler
	{

		public Carobbiite()
		{
			super(CoolerType2.CAROBBIITE.getHeatRemoved(), "carobbiite");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean endStone = false, copper = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!endStone && isActiveCooler(pos.offset(dir), "end_stone"))
					endStone = true;
				if (!copper && isActiveCooler(pos.offset(dir), "copper"))
					copper = true;
				if (endStone && copper)
					return true;
			}
			return false;
		}
	}

	public static class Arsenic extends TileAcceleratorCooler
	{

		public Arsenic()
		{
			super(CoolerType2.ARSENIC.getHeatRemoved(), "arsenic");
		}

		@Override
		public boolean isCoolerValid()
		{
			String cavity1 = "";
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if(isActiveRFCavity(pos.offset(dir), null) && cavity1.isEmpty())
				{
					cavity1 = getMultiblock().getPartMap(TileAcceleratorRFCavity.class).get(pos.offset(dir).toLong()).name;
				}
				else if(isActiveRFCavity(pos.offset(dir), null))
				{
					if(!cavity1.equals(getMultiblock().getPartMap(TileAcceleratorRFCavity.class).get(pos.offset(dir).toLong()).name))
					{
						return true;
					}
				}		
			}
			return false;
		}
	}

	public static class LiquidNitrogen extends TileAcceleratorCooler
	{

		public LiquidNitrogen()
		{
			super(CoolerType2.LIQUID_NITROGEN.getHeatRemoved(), "liquid_nitrogen");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean lapis = false;
			boolean purpur = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (!lapis && isActiveCooler(pos.offset(dir), "lapis"))
					lapis = true;
				if (!purpur && isActiveCooler(pos.offset(dir), "purpur"))
					purpur = true;
				if (lapis && purpur)
					return true;
			}
			return false;
		}
	}

	public static class LiquidHelium extends TileAcceleratorCooler
	{

		public LiquidHelium()
		{
			super(CoolerType2.LIQUID_HELIUM.getHeatRemoved(), "liquid_helium");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte copper = 0;

			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (copper < 3 && isActiveCooler(pos.offset(dir), "copper"))
					copper++;
				if (copper >= 3)
					return true;
			}
			return false;
		}
	}

	public static class Enderium extends TileAcceleratorCooler
	{

		public Enderium()
		{
			super(CoolerType2.ENDERIUM.getHeatRemoved(), "enderium");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean prismarine = false;
			boolean magnesium = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "prismarine"))
					prismarine= true;
				if (isActiveCooler(pos.offset(dir),"magnesium"))
					magnesium = true;
			}
			
			return prismarine && magnesium;
		}
	}

	public static class Cryotheum extends TileAcceleratorCooler
	{

		public Cryotheum()
		{
			super(CoolerType2.CRYOTHEUM.getHeatRemoved(), "cryotheum");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte tin = 0;
			
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (tin < 3 && isActiveCooler(pos.offset(dir), "tin"))
					tin++;
				if (tin >= 3)
					return true;
			}
			return false;
		}
	}
	

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		resetStats(); 
		super.onMachineBroken();
		
	}




	@Override
	public boolean isFunctional()
	{
		if (isInValidPosition) return true;
		isInValidPosition = isCoolerValid();
		return isInValidPosition = isCoolerValid();
	}

	@Override
	public int getMaxOperatingTemp()
	{
		return Accelerator.MAX_TEMP;
	}

	
	public abstract boolean isCoolerValid();

	@Override
	public void setFunctional(boolean func)
	{
	}
	
	@Override
	public void resetStats() 
	{
		isInValidPosition = false;
	}


}
