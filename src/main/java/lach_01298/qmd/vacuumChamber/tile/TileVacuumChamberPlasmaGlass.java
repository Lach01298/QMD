package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;

public class TileVacuumChamberPlasmaGlass extends TileVacuumChamberPart implements IVacuumChamberComponent
{

	public TileVacuumChamberPlasmaGlass()
	{
		super(CuboidalPartPositionType.INTERIOR);
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
	public int getMaxOperatingTemp()
	{
		return  QMDConfig.vacuum_chamber_part_max_temp[3];
	}


	@Override
	public boolean isFunctional()
	{
		return false;
	}


	@Override
	public void setFunctional(boolean func)
	{

	}


	@Override
	public int getHeating()
	{
		return QMDConfig.vacuum_chamber_part_heat[3];
	}


	@Override
	public int getPower()
	{
		return QMDConfig.vacuum_chamber_part_power[3];
	}

}