package lach_01298.qmd.multiblock.accelerator.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileAcceleratorRFCavity extends TileAcceleratorPart implements IAcceleratorComponent
{

	public final int voltage;
	public final double efficiency;
	public final int heat;
	public final int basePower;
	public final String name;

	public boolean isFunctional = false;

	public TileAcceleratorRFCavity(int voltage,double efficiency, int heat, int basePower, String name)
	{
		super(CuboidalPartPositionType.INTERIOR);
		this.voltage = voltage;
		this.efficiency =efficiency;
		this.heat = heat;
		this.basePower = basePower;
		this.name = name;
	}

	

	public static class Copper extends TileAcceleratorRFCavity
	{

		public Copper()
		{
			super(QMDConfig.RF_cavity_voltage[0], QMDConfig.RF_cavity_efficiency[0], QMDConfig.RF_cavity_heat_generated[0], QMDConfig.magnet_base_power[0], "copper");
		}
	}

	public static class MagnesiumDiboride extends TileAcceleratorRFCavity
	{

		public MagnesiumDiboride()
		{
			super(QMDConfig.RF_cavity_voltage[1], QMDConfig.RF_cavity_efficiency[1], QMDConfig.RF_cavity_heat_generated[1], QMDConfig.magnet_base_power[1], "magnesium_diboride");
		}

	}

	public static class NiobiumTin extends TileAcceleratorRFCavity
	{

		public NiobiumTin()
		{
			super(QMDConfig.RF_cavity_voltage[2], QMDConfig.RF_cavity_efficiency[2], QMDConfig.RF_cavity_heat_generated[2], QMDConfig.magnet_base_power[2], "niobium_tin");
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
	public void resetStats() 
	{
		isFunctional = false;
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
