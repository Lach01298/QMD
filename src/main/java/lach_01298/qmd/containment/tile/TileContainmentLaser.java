package lach_01298.qmd.containment.tile;

import lach_01298.qmd.containment.Containment;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public class TileContainmentLaser extends TileContainmentPart implements IContainmentPart
{
	
	public TileContainmentLaser()
	{
		super(CuboidalPartPositionType.WALL);
	
		
	}


	@Override
	public void onMachineAssembled(Containment controller)
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




}