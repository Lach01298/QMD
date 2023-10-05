package lach_01298.qmd.accelerator.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.multiblock.container.ContainerRingAcceleratorController;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileRingAcceleratorController extends TileAcceleratorPart implements IAcceleratorController<TileRingAcceleratorController>
{

	public TileRingAcceleratorController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"ring_accelerator";
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
		
	}
	
	@Override
	public ContainerMultiblockController getContainer(EntityPlayer player) {
		return new ContainerRingAcceleratorController(player, this);
	}

}