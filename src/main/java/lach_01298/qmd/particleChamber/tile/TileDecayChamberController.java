package lach_01298.qmd.particleChamber.tile;


import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.multiblock.container.ContainerDecayChamberController;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.block.BlockDecayChamberController;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class TileDecayChamberController extends TileParticleChamberPart implements IParticleChamberController
{

	public TileDecayChamberController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"decay_chamber";
	}


	@Override
	public void onMachineAssembled(ParticleChamber controller)
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
		if (getBlockType() instanceof BlockDecayChamberController)
		{
			((BlockDecayChamberController) getBlockType()).setState(isActive, this);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}


	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);

		return nbt;
	}
	
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		
	}
	
	@Override
	public ContainerMultiblockController getContainer(EntityPlayer player) {
		return new ContainerDecayChamberController(player, this);
	}
		

}