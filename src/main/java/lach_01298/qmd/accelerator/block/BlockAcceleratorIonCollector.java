package lach_01298.qmd.accelerator.block;

import lach_01298.qmd.accelerator.tile.TileAcceleratorIonCollector;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static nc.block.property.BlockProperties.FACING_ALL;

public class BlockAcceleratorIonCollector extends BlockAcceleratorPart
{

	public BlockAcceleratorIonCollector()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(FACING_ALL, EnumFacing.NORTH));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileAcceleratorIonCollector();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.byIndex(meta & 7);
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
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING_ALL, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite());
	}

	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND || player.isSneaking())
			return false;
		return rightClickOnPart(world, pos, player, hand, facing);
	}

}
