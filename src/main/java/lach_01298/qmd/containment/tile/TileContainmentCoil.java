package lach_01298.qmd.containment.tile;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.containment.Containment;
import nc.block.property.BlockProperties;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileContainmentCoil extends TileContainmentPart implements IContainmentPart
{
	
	public TileContainmentCoil()
	{
		super(CuboidalPartPositionType.INTERIOR);
	
		
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