package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public class TileVacuumChamberCoil extends TileVacuumChamberPart implements IVacuumChamberComponent
{
	
	public TileVacuumChamberCoil()
	{
		super(CuboidalPartPositionType.INTERIOR);
	
		
	}


	@Override
	public void onMachineAssembled(VacuumChamber controller)
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


	@Override
	public int getMaxOperatingTemp()
	{
		return  QMDConfig.vacuum_chamber_part_max_temp[0];
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
		
		return QMDConfig.vacuum_chamber_part_heat[0];
	}


	@Override
	public int getPower()
	{
		return QMDConfig.vacuum_chamber_part_power[0];
	}




}
