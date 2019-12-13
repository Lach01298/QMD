package lach_01298.qmd.multiblock.accelerator.tile;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.block.property.BlockProperties;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileAcceleratorYoke extends TileAcceleratorPartBase
{

	public TileAcceleratorYoke()
	{
		super(CuboidalPartPositionType.INTERIOR);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		doStandardNullControllerResponse(controller);
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().isFrame())
		{
			getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, true), 2);
		}
	}

	@Override
	public void onMachineBroken()
	{
		if (!getWorld().isRemote && getPartPosition().isFrame())
		{
			getWorld().setBlockState(getPos(), getWorld().getBlockState(getPos()).withProperty(BlockProperties.FRAME, false), 2);
		}
		super.onMachineBroken();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	public boolean isFunctional()
	{
		// TODO Auto-generated method stub
		return false;
	}

}