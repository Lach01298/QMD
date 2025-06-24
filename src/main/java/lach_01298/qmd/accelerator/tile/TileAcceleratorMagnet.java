package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.config.QMDConfig;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public class TileAcceleratorMagnet extends TileAcceleratorPart implements IAcceleratorComponent
{

	public final double strength;
	public final double efficiency;
	public final int heat;
	public final int basePower;
	public final int maxTemp;
	public final String name;

	public boolean isFunctional = false;

	public TileAcceleratorMagnet(double strength, double efficiency, int heat, int basePower, int maxTemp, String name)
	{
		super(CuboidalPartPositionType.INTERIOR);
		this.strength = strength;
		this.efficiency =efficiency;
		this.heat = heat;
		this.basePower = basePower;
		this.maxTemp = maxTemp;
		this.name = name;
	}



	public static class Copper extends TileAcceleratorMagnet
	{
		public Copper()
		{
			super(QMDConfig.magnet_strength[0], QMDConfig.magnet_efficiency[0], QMDConfig.magnet_heat_generated[0], QMDConfig.magnet_base_power[0], QMDConfig.magnet_max_temp[0], "copper");
		}
	}

	public static class MagnesiumDiboride extends TileAcceleratorMagnet
	{

		public MagnesiumDiboride()
		{
			super(QMDConfig.magnet_strength[1], QMDConfig.magnet_efficiency[1], QMDConfig.magnet_heat_generated[1], QMDConfig.magnet_base_power[1], QMDConfig.magnet_max_temp[1], "magnesium_diboride");
		}
	}

	public static class NiobiumTin extends TileAcceleratorMagnet
	{

		public NiobiumTin()
		{
			super(QMDConfig.magnet_strength[2], QMDConfig.magnet_efficiency[2], QMDConfig.magnet_heat_generated[2], QMDConfig.magnet_base_power[2], QMDConfig.magnet_max_temp[2], "niobium_tin");
		}
	}

	public static class NiobiumTitanium extends TileAcceleratorMagnet
	{

		public NiobiumTitanium()
		{
			super(QMDConfig.magnet_strength[3], QMDConfig.magnet_efficiency[3], QMDConfig.magnet_heat_generated[3], QMDConfig.magnet_base_power[3], QMDConfig.magnet_max_temp[3], "niobium_titanium");
		}
	}
	
	public static class BSCCO extends TileAcceleratorMagnet
	{

		public BSCCO()
		{
			super(QMDConfig.magnet_strength[4], QMDConfig.magnet_efficiency[4], QMDConfig.magnet_heat_generated[4], QMDConfig.magnet_base_power[4], QMDConfig.magnet_max_temp[4], "bscco");
		}
	}

	public static class Aluminium extends TileAcceleratorMagnet
	{
		public Aluminium()
		{
			super(QMDConfig.magnet_strength[5], QMDConfig.magnet_efficiency[5], QMDConfig.magnet_heat_generated[5], QMDConfig.magnet_base_power[5], QMDConfig.magnet_max_temp[5], "aluminium");
		}
	}

	public static class SSFAF extends TileAcceleratorMagnet
	{
		public SSFAF()
		{
			super(QMDConfig.magnet_strength[6], QMDConfig.magnet_efficiency[6], QMDConfig.magnet_heat_generated[6], QMDConfig.magnet_base_power[6], QMDConfig.magnet_max_temp[6], "ssfaf");
		}
	}

	public static class YBCO extends TileAcceleratorMagnet
	{

		public YBCO()
		{
			super(QMDConfig.magnet_strength[7], QMDConfig.magnet_efficiency[7], QMDConfig.magnet_heat_generated[7], QMDConfig.magnet_base_power[7], QMDConfig.magnet_max_temp[7], "ybco");
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
		super.onMachineBroken();
		
	}



	@Override
	public boolean isFunctional()
	{
		return isFunctional;
	}

	@Override
	public void setFunctional(boolean func)
	{
		isFunctional = func;
	}

	@Override
	public int getMaxOperatingTemp()
	{
		return maxTemp;
	}
	
	
	// NBT
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt)
		{
			super.writeAll(nbt);
			nbt.setBoolean("isFunctional", isFunctional);
			return nbt;
		}

		@Override
		public void readAll(NBTTagCompound nbt)
		{
			super.readAll(nbt);
			isFunctional = nbt.getBoolean("isFunctional");
		}
	
	
	
		
	
}