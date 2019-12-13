package lach_01298.qmd.multiblock.accelerator.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public abstract class TileAcceleratorRFCavity extends TileAcceleratorPartBase implements IAcceleratorComponent
{

	public final int voltage;
	public final double efficiency;
	public final int heat;
	public final int basePower;
	public final String name;

	public boolean isInValidPosition = false;

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

		@Override
		public boolean isCavityValid()
		{
			//TODO
			return true;
		}
	}

	public static class MagnesiumDiboride extends TileAcceleratorRFCavity
	{

		public MagnesiumDiboride()
		{
			super(QMDConfig.RF_cavity_voltage[1], QMDConfig.RF_cavity_efficiency[1], QMDConfig.RF_cavity_heat_generated[1], QMDConfig.magnet_base_power[1], "magnesium_diboride");
		}

		@Override
		public boolean isCavityValid()
		{
			//TODO
			return true;
		}
	}

	public static class NiobiumTin extends TileAcceleratorRFCavity
	{

		public NiobiumTin()
		{
			super(QMDConfig.RF_cavity_voltage[2], QMDConfig.RF_cavity_efficiency[2], QMDConfig.RF_cavity_heat_generated[2], QMDConfig.magnet_base_power[2], "niobium_tin");
		}

		@Override
		public boolean isCavityValid()
		{
			//TODO
			return true;
		}
	}

	
	

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		// if (getWorld().isRemote) return;
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		// if (getWorld().isRemote) return;
		// getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()), 2);
	}


	@Override
	public void update()
	{
		if (!isAdded)
		{
			onAdded();
			isAdded = true;
		}
		if (isMarkedDirty)
		{
			markDirty();
			isMarkedDirty = false;
		}
	}


	@Override
	public boolean isFunctional()
	{
		return isInValidPosition;
	}

	public abstract boolean isCavityValid();

	@Override
	public void resetStats()
	{
		isInValidPosition = false;
	}
}
