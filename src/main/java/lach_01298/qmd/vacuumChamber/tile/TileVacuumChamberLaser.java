package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

import static nc.block.property.BlockProperties.FACING_ALL;

public class TileVacuumChamberLaser extends TileVacuumChamberPart implements IVacuumChamberComponent
{
	
	public TileVacuumChamberLaser()
	{
		super(CuboidalPartPositionType.WALL);
	
		
	}


	@Override
	public void onMachineAssembled(VacuumChamber multiblock)
	{
		doStandardNullControllerResponse(multiblock);
		super.onMachineAssembled(multiblock);
		if (!getWorld().isRemote && getPartPosition().getFacing() != null)
		{
			getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(FACING_ALL, getPartPosition().getFacing().getOpposite()), 2);
		}

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
		return  QMDConfig.vacuum_chamber_part_max_temp[1];
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
		
		return QMDConfig.vacuum_chamber_part_heat[1];
	}


	@Override
	public int getPower()
	{
		return QMDConfig.vacuum_chamber_part_power[1];
	}




}
