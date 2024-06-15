package lach_01298.qmd.particleChamber.tile;

import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.TileContainerInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static nc.block.property.BlockProperties.FACING_ALL;

public class TileBeamDumpController extends TileParticleChamberPart implements IParticleChamberController<TileBeamDumpController>
{
	protected final TileContainerInfo<TileBeamDumpController> info = TileInfoHandler.getTileContainerInfo("beam_dump_controller");

	public TileBeamDumpController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"beam_dump";
	}
	
	@Override
	public TileContainerInfo<TileBeamDumpController> getContainerInfo()
	{
		return info;
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
	
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);

		return nbt;
	}
	
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		
	}
}
