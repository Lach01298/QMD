package lach_01298.qmd.block;

import static nc.block.property.BlockProperties.ACTIVE;

import java.util.Random;

import lach_01298.qmd.enums.BlockTypes.LampType2;
import lach_01298.qmd.tab.QMDTabs;
import nc.block.BlockMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLamp2 extends BlockMeta
{

	
	public final static PropertyEnum TYPE = PropertyEnum.create("type", LampType2.class);
	
	public BlockLamp2()
	{
		super(LampType2.class, TYPE, Material.REDSTONE_LIGHT);
		setDefaultState(getDefaultState().withProperty(ACTIVE, Boolean.valueOf(true)));
		setCreativeTab(QMDTabs.BLOCKS);
	}

	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) 
	{
		if(state.getValue(ACTIVE).booleanValue())
		{
			return ((LampType2) state.getValue(type)).getLightValue();
		}
		else
		{
			return 0;
		}
		
		
	}
	
	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos) 
	{
		return ((LampType2) state.getValue(type)).getHardness();
	}

	@Override
	public String getHarvestTool(IBlockState state)
	{
		return ((LampType2) state.getValue(type)).getHarvestTool();
	}

	public int getHarvestLevel(IBlockState state)
	{
		return ((LampType2) state.getValue(type)).getHarvestLevel();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		if(state.getValue(ACTIVE).booleanValue())
		{
			return ((LampType2) state.getValue(type)).getID();
		}
		else
		{
			return ((LampType2) state.getValue(type)).getID() + values.length;
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
		return ((LampType2) state.getValue(type)).getID();
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((LampType2) state.getValue(type)).getID());
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
