package lach_01298.qmd.block;

import lach_01298.qmd.enums.BlockTypes.LampType;
import lach_01298.qmd.tab.QMDTabs;
import nc.block.BlockMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.Random;

import static nc.block.property.BlockProperties.ACTIVE;

public class BlockLamp extends BlockMeta
{

	
	public final static PropertyEnum TYPE = PropertyEnum.create("type", LampType.class);
	
	public BlockLamp()
	{
		super(LampType.class, TYPE, Material.REDSTONE_LIGHT);
		setDefaultState(getDefaultState().withProperty(ACTIVE, Boolean.valueOf(true)));
		setCreativeTab(QMDTabs.BLOCKS);
	}

	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		if(state.getValue(ACTIVE).booleanValue())
		{
			return ((LampType) state.getValue(type)).getLightValue();
		}
		else
		{
			return 0;
		}
		
		
	}
	
	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
	{
		return ((LampType) state.getValue(type)).getHardness();
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return ((LampType) state.getValue(type)).getHarvestTool();
	}

	public int getHarvestLevel(IBlockState state)
	{
		return ((LampType) state.getValue(type)).getHarvestLevel();
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		if(state.getValue(ACTIVE).booleanValue())
		{
			return ((LampType) state.getValue(type)).getID();
		}
		else
		{
			return ((LampType) state.getValue(type)).getID() + values.length;
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if(meta >= values.length)
		{
			return getDefaultState().withProperty(type, values[meta -values.length]).withProperty(ACTIVE, Boolean.valueOf(false));
		}
		else
		{
			return getDefaultState().withProperty(type, values[meta]);
		}
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return ((LampType) state.getValue(type)).getID();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((LampType) state.getValue(type)).getID());
	}
	
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, TYPE, ACTIVE);
	}
	
	
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            if (!state.getValue(ACTIVE).booleanValue() && !worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, state.withProperty(ACTIVE, Boolean.valueOf(true)), 2);
            }
            else if (state.getValue(ACTIVE).booleanValue() && worldIn.isBlockPowered(pos))
            {
                worldIn.setBlockState(pos, state.withProperty(ACTIVE, Boolean.valueOf(false)), 2);
            }
        }
    }
	
		
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (!state.getValue(ACTIVE).booleanValue() && !worldIn.isBlockPowered(pos))
            {
            	worldIn.setBlockState(pos, state.withProperty(ACTIVE, Boolean.valueOf(true)), 2);
            }
        }
    }
	
	
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!worldIn.isRemote)
        {
            if (state.getValue(ACTIVE).booleanValue() && worldIn.isBlockPowered(pos))
            {
            	 worldIn.setBlockState(pos, state.withProperty(ACTIVE, Boolean.valueOf(false)), 2);
            }
            else if (!state.getValue(ACTIVE).booleanValue() && !worldIn.isBlockPowered(pos))
            {
            	worldIn.setBlockState(pos, state.withProperty(ACTIVE, Boolean.valueOf(true)), 2);
            }
        }
    }
	
	
	
	
}
