package lach_01298.qmd.block;

import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import lach_01298.qmd.item.QMDItems;
import nc.block.NCBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.Random;

import static nc.block.property.BlockProperties.FACING_ALL;

public class BlockLuminousPaint extends NCBlock
{
	 /** Ordering index for D-U-N-S-W-E */
    protected static final AxisAlignedBB[] PAINT_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D), new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D)};

	
	public BlockLuminousPaint()
	{
		super(Material.CIRCUITS);
		setDefaultState(blockState.getBaseState().withProperty(FACING_ALL, EnumFacing.UP));
		setLightLevel(0.8F);
		setHardness(0.1F);
		setResistance(0.1F);
	}
	
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return PAINT_AABB[state.getValue(FACING_ALL).getIndex()];
    }
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	 public boolean isFullCube(IBlockState state)
	 {
	        return false;
	 }
	 
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.byIndex(meta);
		return getDefaultState().withProperty(FACING_ALL, enumfacing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING_ALL).getIndex();
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING_ALL);
	}
	
	
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);

	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
		return QMDItems.luminousPaint;
    }
	
	public int damageDropped(IBlockState state)
    {
		if(state.getBlock() == QMDBlocks.greenLuminousPaint)
		{
			return LuminousPaintType.GREEN.getID();
		}
		
		if(state.getBlock() == QMDBlocks.blueLuminousPaint)
		{
			return LuminousPaintType.BLUE.getID();
		}
		
		if(state.getBlock() == QMDBlocks.orangeLuminousPaint)
		{
			return LuminousPaintType.ORANGE.getID();
		}
		return 0;
    }
	
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
		if(state.getBlock() == QMDBlocks.greenLuminousPaint)
		{
			return new ItemStack(QMDItems.luminousPaint, 1, LuminousPaintType.GREEN.getID());
		}
		else if(state.getBlock() == QMDBlocks.blueLuminousPaint)
		{
			return new ItemStack(QMDItems.luminousPaint, 1, LuminousPaintType.BLUE.getID());
		}
		else if(state.getBlock() == QMDBlocks.orangeLuminousPaint)
		{
			return new ItemStack(QMDItems.luminousPaint, 1, LuminousPaintType.ORANGE.getID());
		}
		
		return new ItemStack(QMDItems.luminousPaint);
    }
	
	
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return canPlaceBlock(worldIn, pos, side);
    }

	
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
        	if (canPlaceBlock(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }
	
	protected static boolean canPlaceBlock(World world, BlockPos pos, EnumFacing direction)
	{
		BlockPos blockpos = pos.offset(direction.getOpposite());
		IBlockState iblockstate = world.getBlockState(blockpos);
		
		return iblockstate.getBlockFaceShape(world, blockpos, direction) == BlockFaceShape.SOLID;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		
		return getDefaultState().withProperty(FACING_ALL, facing);
	}
	
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (this.checkForDrop(worldIn, pos, state) && !canPlaceBlock(worldIn, pos, (EnumFacing)state.getValue(FACING_ALL)))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
	
	private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.canPlaceBlockAt(worldIn, pos))
        {
            return true;
        }
        else
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }
	
	
	 public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	    {
	        return BlockFaceShape.UNDEFINED;
	    }
	
	 @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
}
