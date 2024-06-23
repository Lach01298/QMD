package lach_01298.qmd.particleChamber.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.util.Util;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;

public class TileParticleChamberDetector extends TileParticleChamberPart
{

	public final double efficiency;
	public final int basePower;
	public final String name;
	public final int taxiDistance;
	public final boolean within;
	
	public TileParticleChamberDetector(double efficiency, int basePower, String name, int taxiDistance, boolean within)
	{
		super(CuboidalPartPositionType.INTERIOR);
	
		this.efficiency =efficiency;
	
		this.basePower = basePower;
		this.name = name;
		this.taxiDistance = taxiDistance;
		this.within = within;
	}
	
	
	
	

	public static class BubbleChamber extends TileParticleChamberDetector
	{
		public BubbleChamber()
		{
			super( QMDConfig.detector_efficiency[0], QMDConfig.detector_base_power[0], DetectorType.BUBBLE_CHAMBER.getName(), 2, true);
		}		
	}
	
	public static class SiliconTracker extends TileParticleChamberDetector
	{
		public SiliconTracker()
		{
			super( QMDConfig.detector_efficiency[1], QMDConfig.detector_base_power[1], DetectorType.SILLICON_TRACKER.getName(), 1, true);
		}

	}
	
	public static class WireChamber extends TileParticleChamberDetector
	{
		public WireChamber()
		{
			super( QMDConfig.detector_efficiency[2], QMDConfig.detector_base_power[2], DetectorType.WIRE_CHAMBER.getName(), 2, true);
		}

	}
	
	public static class EMCalorimeter extends TileParticleChamberDetector
	{
		public EMCalorimeter()
		{
			super( QMDConfig.detector_efficiency[3], QMDConfig.detector_base_power[3], DetectorType.EM_CALORIMETER.getName(), 3, true);
		}

	}
	
	public static class HadronCalorimeter extends TileParticleChamberDetector
	{
		public HadronCalorimeter()
		{
			super( QMDConfig.detector_efficiency[4], QMDConfig.detector_base_power[4], DetectorType.HADRON_CALORIMETER.getName(), 5, true);
		}
	}



	
	

	@Override
	public void onMachineAssembled(ParticleChamber controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
	}


	
	// NBT
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt)
		{
			super.writeAll(nbt);
			return nbt;
		}

		@Override
		public void readAll(NBTTagCompound nbt)
		{
			super.readAll(nbt);
		}
	

		public boolean isValidPostion(BlockPos target)
		{
			
			if(within)
			{
				if(Util.getTaxiDistance(target, getPos()) <= taxiDistance)
				{
					return true;
				}
			}
			else
			{
				if(Util.getTaxiDistance(target, getPos()) >= taxiDistance)
				{
					return true;
				}
			}
			
			return false;
		}
	
}