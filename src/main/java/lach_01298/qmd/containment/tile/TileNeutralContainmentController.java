package lach_01298.qmd.containment.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.accelerator.block.BlockLinearAcceleratorController;
import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.multiblock.container.ContainerNeutralContainmentController;
import lach_01298.qmd.containment.block.BlockNeutralContainmentController;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.container.ContainerTurbineController;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.NCMath;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileNeutralContainmentController extends TileContainmentPart implements IContainmentController
{

	public boolean isRenderer = false;
	
	public TileNeutralContainmentController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"neutral_containment";
	}


	@Override
	public void onMachineAssembled(Containment controller)
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
		if (getBlockType() instanceof BlockNeutralContainmentController)
		{
			((BlockNeutralContainmentController) getBlockType()).setState(isActive, this);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}


	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContainerMultiblockController getContainer(EntityPlayer player) 
	{
		return new ContainerNeutralContainmentController(player, this);
	}

	
	
	public boolean isRenderer() 
	{
		return isRenderer;
	}
	

	public void setIsRenderer(boolean isRenderer)
	{
		this.isRenderer = isRenderer;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		double defaultDistSq = super.getMaxRenderDistanceSquared();
		if (!isRenderer || !isMultiblockAssembled())
		{
			return defaultDistSq;
		}
		return defaultDistSq
				+ (NCMath.sq(getMultiblock().getExteriorLengthX()) + NCMath.sq(getMultiblock().getExteriorLengthY())
						+ NCMath.sq(getMultiblock().getExteriorLengthZ())) / 4D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		if (!isRenderer || !isMultiblockAssembled())
		{
			return Block.FULL_BLOCK_AABB.offset(pos);
		}
		return new AxisAlignedBB(getMultiblock().getMinimumCoord(), getMultiblock().getMaximumCoord());
	}
	
	@Override
	public double getDistanceSq(double x, double y, double z)
	{
		double dX, dY, dZ;
		if (!isRenderer || !isMultiblockAssembled())
		{
			dX = pos.getX() + 0.5D - x;
			dY = pos.getY() + 0.5D - y;
			dZ = pos.getZ() + 0.5D - z;
		}
		else
		{
			dX = getMultiblock().getMiddleX() + 0.5D - x;
			dY = getMultiblock().getMiddleY() + 0.5D - y;
			dZ = getMultiblock().getMiddleZ() + 0.5D - z;
		}
		return dX * dX + dY * dY + dZ * dZ;
	}
}
