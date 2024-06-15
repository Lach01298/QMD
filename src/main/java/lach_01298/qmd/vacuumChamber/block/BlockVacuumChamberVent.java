package lach_01298.qmd.vacuumChamber.block;

import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberVent;
import nc.util.BlockHelper;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static nc.block.property.BlockProperties.*;

public class BlockVacuumChamberVent extends BlockVacuumChamberPart
{

	public BlockVacuumChamberVent()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(FACING_ALL, EnumFacing.NORTH).withProperty(ACTIVE,
				Boolean.valueOf(false)));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING_ALL, ACTIVE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.byIndex(meta & 7);
		return getDefaultState().withProperty(FACING_ALL, enumfacing).withProperty(ACTIVE,
				Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = state.getValue(FACING_ALL).getIndex();
		if (state.getValue(ACTIVE).booleanValue())
			i |= 8;
		return i;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileVacuumChamberVent();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING_ALL, EnumFacing.getDirectionFromEntityLiving(pos, placer))
				.withProperty(ACTIVE, Boolean.valueOf(false));
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		BlockHelper.setDefaultFacing(world, pos, state, FACING_ALL);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND || player.isSneaking())
			return false;
		return rightClickOnPart(world, pos, player, hand, facing);
	}

	public void setState(boolean isActive, TileEntity tile)
	{
		World world = tile.getWorld();
		BlockPos pos = tile.getPos();
		IBlockState state = world.getBlockState(pos);
		if (!world.isRemote && state.getBlock() instanceof BlockVacuumChamberVent)
		{
			if (isActive != state.getValue(ACTIVE))
			{
				world.setBlockState(pos, state.withProperty(ACTIVE, isActive), 2);
			}
		}
	}

}
