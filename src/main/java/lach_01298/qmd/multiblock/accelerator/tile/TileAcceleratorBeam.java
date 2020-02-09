package lach_01298.qmd.multiblock.accelerator.tile;

import javax.annotation.Nonnull;

import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
	public void update()
	{
		// TODO Auto-generated method stub
		
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
	public void resetStats()
	{
		isFunctional = false;
	}
	
}
