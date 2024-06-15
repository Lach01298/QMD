package lach_01298.qmd.vacuumChamber.block;

import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeamPort;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static lach_01298.qmd.block.BlockProperties.IO;

public class BlockVacuumChamberBeamPort extends BlockVacuumChamberPart
{

	public BlockVacuumChamberBeamPort()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(IO, EnumTypes.IOType.INPUT));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileVacuumChamberBeamPort();
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, IO);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
	
		return getDefaultState().withProperty(IO, EnumTypes.IOType.getTypeFromID(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return  state.getValue(IO).getID();
	}

	

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState();
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
