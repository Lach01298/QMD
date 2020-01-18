package lach_01298.qmd.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static lach_01298.qmd.block.BlockProperties.AXIS_HORIZONTAL;
import static nc.block.property.BlockProperties.ACTIVE;
import static nc.block.property.BlockProperties.FACING_ALL;

import java.util.List;

import javax.annotation.Nullable;

import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.tab.QMDTabs;
import lach_01298.qmd.tile.TileBeamline;
import nc.block.NCBlock;
import nc.tab.NCTabs;

public class BlockBeamline extends NCBlock implements ITileEntityProvider
{
	 protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] 
			 {
					 new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D),
					 new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125d, 1.0D)
					 };
	public BlockBeamline()
	{
		super(Material.IRON);
		setCreativeTab(QMDTabs.BLOCKS);
		setDefaultState(blockState.getBaseState().withProperty(AXIS_HORIZONTAL, EnumFacing.Axis.X));
		
	}


	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileBeamline();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta == 1)
		{
			return getDefaultState().withProperty(AXIS_HORIZONTAL, EnumFacing.Axis.Z);
		}
		else
		{
			return getDefaultState().withProperty(AXIS_HORIZONTAL, EnumFacing.Axis.X);
		}
	}

	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		if(state.getValue(AXIS_HORIZONTAL) == EnumFacing.Axis.Z)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AXIS_HORIZONTAL);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(AXIS_HORIZONTAL, placer.getHorizontalFacing().getAxis());
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);	
	}
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return true;
	}
	
	
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }


    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
	
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
    	addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOXES[getBoundingBoxIdx(state)]);
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOXES[getBoundingBoxIdx(state)];
    }
	
    private static int getBoundingBoxIdx(IBlockState state)
    {
    	if(state.getValue(AXIS_HORIZONTAL) == EnumFacing.Axis.Z)
		{
			return 1;
		}

        return 0;
    }

}