package lach_01298.qmd.multiblock.accelerator.tile;

import javax.annotation.Nullable;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import nc.config.NCConfig;
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
			super(QMDConfig.cooler_heat_removed[0], "water");
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
			super(NCConfig.fission_sink_cooling_rate[1], "iron");
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
			super(NCConfig.fission_sink_cooling_rate[2], "redstone");
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
			super(NCConfig.fission_sink_cooling_rate[3], "quartz");
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
			super(NCConfig.fission_sink_cooling_rate[4], "obsidian");
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
			super(NCConfig.fission_sink_cooling_rate[5], "nether_brick");
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
			super(NCConfig.fission_sink_cooling_rate[6], "glowstone");
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
			super(NCConfig.fission_sink_cooling_rate[7], "lapis");
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
			super(NCConfig.fission_sink_cooling_rate[8], "gold");
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
			super(NCConfig.fission_sink_cooling_rate[9], "prismarine");
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
			super(NCConfig.fission_sink_cooling_rate[10], "slime");
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
			super(NCConfig.fission_sink_cooling_rate[11], "end_stone");
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
			super(NCConfig.fission_sink_cooling_rate[12], "purpur");
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
			super(NCConfig.fission_sink_cooling_rate[13], "diamond");
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
			super(NCConfig.fission_sink_cooling_rate[14], "emerald");
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
			super(NCConfig.fission_sink_cooling_rate[15], "copper");
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
			super(NCConfig.fission_sink_cooling_rate[16], "tin");
		}

		@Override
		public boolean isCoolerValid()
		{
			axialDirsLoop: for (EnumFacing[] axialDirs : BlockPosHelper.axialDirsList())
			{
				for (EnumFacing dir : axialDirs)
				{
					if (!isActiveCooler(pos.offset(dir), "lapis"))
						continue axialDirsLoop;
				}
				return true;
			}
			return false;
		}
	}

	public static class Lead extends TileAcceleratorCooler
	{

		public Lead()
		{
			super(NCConfig.fission_sink_cooling_rate[17], "lead");
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
			super(NCConfig.fission_sink_cooling_rate[18], "boron");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte quartz = 0;
			boolean cavity = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "quartz"))
					quartz++;
				if (quartz > 1)
					return false;
				if (!cavity && isActiveRFCavity(pos.offset(dir),null))
					cavity = true;
			}
			return quartz >= 1 && cavity;
		}
	}

	public static class Lithium extends TileAcceleratorCooler
	{

		public Lithium()
		{
			super(NCConfig.fission_sink_cooling_rate[19], "lithium");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean lead = false;
			axialDirsLoop: for (EnumFacing[] axialDirs : BlockPosHelper.axialDirsList())
			{
				for (EnumFacing dir : axialDirs)
				{
					if (!isActiveCooler(pos.offset(dir), "lead"))
						continue axialDirsLoop;
				}
				lead = true;
				break;
			}
			return lead;
		}
	}

	public static class Magnesium extends TileAcceleratorCooler
	{

		public Magnesium()
		{
			super(NCConfig.fission_sink_cooling_rate[20], "magnesium");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte magnet = 0;
			boolean beam = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveMagnet(pos.offset(dir),null))
					magnet++;
				if (magnet > 1)
					return false;
				if (!beam && isBeam(pos.offset(dir)))
					beam = true;
			}
			return magnet >= 1 && beam;
		}
	}

	public static class Manganese extends TileAcceleratorCooler
	{

		public Manganese()
		{
			super(NCConfig.fission_sink_cooling_rate[21], "manganese");
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
			super(NCConfig.fission_sink_cooling_rate[22], "aluminum");
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
			super(NCConfig.fission_sink_cooling_rate[23], "silver");
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
			super(NCConfig.fission_sink_cooling_rate[24], "fluorite");
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
			super(NCConfig.fission_sink_cooling_rate[25], "villiaumite");
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
			super(NCConfig.fission_sink_cooling_rate[26], "carobbiite");
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
			super(NCConfig.fission_sink_cooling_rate[27], "arsenic");
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
			super(NCConfig.fission_sink_cooling_rate[28], "liquid_nitrogen");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte copper = 0;
			boolean purpur = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (copper < 2 && isActiveCooler(pos.offset(dir), "copper"))
					copper++;
				if (!purpur && isActiveCooler(pos.offset(dir), "purpur"))
					purpur = true;
				if (copper >= 2 && purpur)
					return true;
			}
			return false;
		}
	}

	public static class LiquidHelium extends TileAcceleratorCooler
	{

		public LiquidHelium()
		{
			super(NCConfig.fission_sink_cooling_rate[29], "liquid_helium");
		}

		@Override
		public boolean isCoolerValid()
		{
			boolean copper = false;
			boolean magnesium = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir), "copper"))
					copper= true;
				if (isActiveCooler(pos.offset(dir),"magnesium"))
					magnesium = true;
			}
			
			return copper && magnesium;
		}
	}

	public static class Enderium extends TileAcceleratorCooler
	{

		public Enderium()
		{
			super(NCConfig.fission_sink_cooling_rate[30], "enderium");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte obsidian = 0;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (isActiveCooler(pos.offset(dir),"obsidian"))
					obsidian++;
				if (obsidian >= 2)
					return true;
			}
			return false;
		}
	}

	public static class Cryotheum extends TileAcceleratorCooler
	{

		public Cryotheum()
		{
			super(NCConfig.fission_sink_cooling_rate[31], "cryotheum");
		}

		@Override
		public boolean isCoolerValid()
		{
			byte endstone = 0;
			boolean yoke = false;
			boolean cavity = false;
			for (EnumFacing dir : EnumFacing.VALUES)
			{
				if (endstone < 2 && isActiveCooler(pos.offset(dir), "copper"))
					endstone++;
				if (!yoke && isActiveYoke(pos.offset(dir)))
					yoke = true;
				if (!cavity && isActiveRFCavity(pos.offset(dir),null))
					cavity = true;
				if (endstone >= 2 && yoke && cavity)
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
	public void update()
	{

	}


	@Override
	public boolean isFunctional()
	{
		if (isInValidPosition) return true;
		isInValidPosition = isCoolerValid();
		return isInValidPosition = isCoolerValid();
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
