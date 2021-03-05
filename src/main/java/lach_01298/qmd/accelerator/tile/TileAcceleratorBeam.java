package lach_01298.qmd.accelerator.tile;

import lach_01298.qmd.accelerator.Accelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.nbt.NBTTagCompound;

public class TileAcceleratorBeam extends TileAcceleratorPart implements IAcceleratorComponent
{

	private boolean isFunctional;

	
	public TileAcceleratorBeam()
	{
		super(CuboidalPartPositionType.INTERIOR);
		isFunctional = false;
		
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
		return isFunctional && this.isMultiblockAssembled();
	}

	@Override
	public void setFunctional(boolean func)
	{
		isFunctional = func;
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
	public int getMaxOperatingTemp()
	{
		return Accelerator.MAX_TEMP;
	}
	
}
