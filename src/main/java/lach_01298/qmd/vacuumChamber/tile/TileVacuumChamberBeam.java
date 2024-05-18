package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public class TileVacuumChamberBeam extends TileVacuumChamberPart implements IVacuumChamberComponent
{
	private boolean isFunctional;
	
	public TileVacuumChamberBeam()
	{
		super(CuboidalPartPositionType.INTERIOR);
		isFunctional = false;
		
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
		nbt.setBoolean("isFunctional", isFunctional);
		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		isFunctional = nbt.getBoolean("isFunctional");
	}

	@Override
	public boolean isFunctional()
	{
		return isFunctional && this.isMultiblockAssembled();
	}
	
	@Override
	public void setFunctional(boolean func)
	{
		isFunctional = func;
	}

	@Override
	public int getMaxOperatingTemp()
	{
		return  QMDConfig.vacuum_chamber_part_max_temp[2];
	}


	@Override
	public int getHeating()
	{
		return QMDConfig.vacuum_chamber_part_heat[2];
	}


	@Override
	public int getPower()
	{
		return QMDConfig.vacuum_chamber_part_power[2];
	}
	

}
