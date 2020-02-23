package lach_01298.qmd.multiblock.accelerator.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.block.BlockBeamDiverterController;
import lach_01298.qmd.multiblock.accelerator.block.BlockLinearAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPart;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileBeamDiverterController extends TileAcceleratorPart implements IAcceleratorController
{

	public TileBeamDiverterController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"beam_diverter";
	}


	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().getFacing() != null)
		{
			getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(FACING_ALL, getPartPosition().getFacing()), 2);
		}	
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos)
	{
		super.onBlockNeighborChanged(state, world, pos, fromPos);
		if (getMultiblock() != null) getMultiblock().updateActivity();
	}

	@Override
	public void updateBlockState(boolean isActive)
	{
		if (getBlockType() instanceof BlockBeamDiverterController)
		{
			((BlockBeamDiverterController) getBlockType()).setState(isActive, this);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}

	@Override
	public void doMeltdown()
	{

	}

}