package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.config.QMDConfig;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileAcceleratorRFCavity extends TileAcceleratorPart implements IAcceleratorComponent
{

	public final int voltage;
	public final double efficiency;
	public final int heat;
	public final int basePower;
	public final int maxTemp;
	public final String name;

	public boolean isFunctional = false;

	public TileAcceleratorRFCavity(int voltage, double efficiency, int heat, int basePower, int maxTemp, String name)
	{
		super(CuboidalPartPositionType.INTERIOR);
		this.voltage = voltage;
		this.efficiency =efficiency;
		this.heat = heat;
		this.basePower = basePower;
		this.maxTemp = maxTemp;
		this.name = name;
	}

	

	public static class Copper extends TileAcceleratorRFCavity
	{

		public Copper()
		{
			super(QMDConfig.RF_cavity_voltage[0], QMDConfig.RF_cavity_efficiency[0], QMDConfig.RF_cavity_heat_generated[0], QMDConfig.RF_cavity_base_power[0], QMDConfig.RF_cavity_max_temp[0], "copper");
		}
	}

	public static class MagnesiumDiboride extends TileAcceleratorRFCavity
	{

		public MagnesiumDiboride()
		{
			super(QMDConfig.RF_cavity_voltage[1], QMDConfig.RF_cavity_efficiency[1], QMDConfig.RF_cavity_heat_generated[1], QMDConfig.RF_cavity_base_power[1], QMDConfig.RF_cavity_max_temp[1], "magnesium_diboride");
		}

	}

	public static class NiobiumTin extends TileAcceleratorRFCavity
	{

		public NiobiumTin()
		{
			super(QMDConfig.RF_cavity_voltage[2], QMDConfig.RF_cavity_efficiency[2], QMDConfig.RF_cavity_heat_generated[2], QMDConfig.RF_cavity_base_power[2], QMDConfig.RF_cavity_max_temp[2], "niobium_tin");
		}
	}
	
	public static class NiobiumTitanium extends TileAcceleratorRFCavity
	{

		public NiobiumTitanium()
		{
			super(QMDConfig.RF_cavity_voltage[3], QMDConfig.RF_cavity_efficiency[3], QMDConfig.RF_cavity_heat_generated[3], QMDConfig.RF_cavity_base_power[3], QMDConfig.RF_cavity_max_temp[3], "niobium_titanium");
		}
	}
	
	public static class BSCCO extends TileAcceleratorRFCavity
	{

		public BSCCO()
		{
			super(QMDConfig.RF_cavity_voltage[4], QMDConfig.RF_cavity_efficiency[4], QMDConfig.RF_cavity_heat_generated[4], QMDConfig.RF_cavity_base_power[4], QMDConfig.RF_cavity_max_temp[4], "bscco");
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
