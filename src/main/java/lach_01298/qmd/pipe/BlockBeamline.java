package lach_01298.qmd.pipe;

import lach_01298.qmd.tab.QMDTabs;
import nc.block.NCBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.List;

import static nc.block.property.BlockProperties.AXIS_ALL;

public class BlockBeamline extends NCBlock implements ITileEntityProvider
{
	protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] {
			new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D),
			new AxisAlignedBB(0.1875D, 0D, 0.1875D,0.8125D, 1D, 0.8125D),
			new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125d, 1.0D) };
	public BlockBeamline()
	{
		super(Material.IRON);
		setCreativeTab(QMDTabs.BLOCKS);
		setDefaultState(blockState.getBaseState().withProperty(AXIS_ALL, EnumFacing.Axis.X));
		
	}


	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileBeamline();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
			return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.X);
		case 1:
			return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.Y);
		case 2:
			return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.Z);
		}
		return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.X);
	}

	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		switch(state.getValue(AXIS_ALL))
		{
		case X:
			return 0;
		case Y:
			return 1;
		case Z:
			return 2;
		}
		return 0;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AXIS_ALL);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		Axis state = null;
		for(EnumFacing face : EnumFacing.VALUES)
    	{
    		if(world.getTileEntity(pos.offset(face)) instanceof TileBeamline)
    		{
    			state = world.getBlockState(pos.offset(face)).getValue(AXIS_ALL);
    		}
    	}
		if(state != null)
		{
			return getDefaultState().withProperty(AXIS_ALL, state);
		}
		
		return getDefaultState().withProperty(AXIS_ALL, EnumFacing.getDirectionFromEntityLiving(pos, placer).getAxis());
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
	
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
    	int amount = 0;
    	int facingCorrect = 0;
    	for(EnumFacing face : EnumFacing.VALUES)
    	{
    		if(world.getTileEntity(pos.offset(face)) instanceof TileBeamline)
    		{
    			amount++;
    		}
    	}
    	if(amount <= 2)
    	{
    		
    		for(EnumFacing face : EnumFacing.VALUES)
        	{
        		Axis state = null;
    			if(world.getTileEntity(pos.offset(face)) instanceof TileBeamline)
        		{
        			if(state == null)
        			{
        				if(world.getBlockState(pos.offset(face)).getValue(AXIS_ALL) == face.getAxis())
            			{
        					state = world.getBlockState(pos.offset(face)).getValue(AXIS_ALL);
            			}
        			}
					if (state == world.getBlockState(pos.offset(face)).getValue(AXIS_ALL))
					{
						facingCorrect++;
					}
        		}
        	}
    	}
    	return facingCorrect == amount;
  
    }
    
    
    
    private static int getBoundingBoxIdx(IBlockState state)
    {
    	switch(state.getValue(AXIS_ALL))
		{
		case X:
			return 0;
		case Y:
			return 1;
		case Z:
			return 2;
		}
		return 0;
    }

}
