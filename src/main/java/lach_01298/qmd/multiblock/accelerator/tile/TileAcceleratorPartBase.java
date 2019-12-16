package lach_01298.qmd.multiblock.accelerator.tile;

import lach_01298.qmd.multiblock.TileCuboidalOrToroidalMultiblockPartBase;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.cuboidal.TileCuboidalMultiblockPartBase;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileAcceleratorPartBase extends TileCuboidalOrToroidalMultiblockPartBase<Accelerator>
{

	public boolean isAcceleratorOn;

	public TileAcceleratorPartBase(CuboidalPartPositionType positionType)
	{
		super(Accelerator.class, positionType,5);
	}

	@Override
	public Accelerator createNewMultiblock()
	{
		return new Accelerator(getWorld());
	}

	public void setIsAcceleratorOn()
	{
		if (getMultiblock() != null) isAcceleratorOn = getMultiblock().isAcceleratorOn;
	}

	// NBT

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		nbt.setBoolean("isAcceleratorOn", isAcceleratorOn);
		return nbt;
	}

	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		isAcceleratorOn = nbt.getBoolean("isAcceleratorOn");
	}
}
