package lach_01298.qmd.particleChamber.block;

import static nc.block.property.BlockProperties.ACTIVE;
import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GUI_ID;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import nc.block.tile.IActivatable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBeamDumpController extends BlockParticleChamberPart implements IActivatable
{
	public BlockBeamDumpController()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(FACING_ALL, EnumFacing.NORTH).withProperty(ACTIVE,Boolean.valueOf(false)));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileBeamDumpController();
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
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING_ALL, ACTIVE);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(FACING_ALL, EnumFacing.getDirectionFromEntityLiving(pos, placer)).withProperty(ACTIVE, Boolean.valueOf(false));
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(world, pos, state);
		setDefaultDirection(world, pos, state);
	}

	private static void setDefaultDirection(World world, BlockPos pos, IBlockState state)
	{
		if (!world.isRemote)
		{
			EnumFacing enumfacing = state.getValue(FACING_ALL);
			boolean flag = world.getBlockState(pos.north()).isFullBlock();
			boolean flag1 = world.getBlockState(pos.south()).isFullBlock();

			if (enumfacing == EnumFacing.NORTH && flag && !flag1)
				enumfacing = EnumFacing.SOUTH;
			else if (enumfacing == EnumFacing.SOUTH && flag1 && !flag)
				enumfacing = EnumFacing.NORTH;

			else
			{
				boolean flag2 = world.getBlockState(pos.west()).isFullBlock();
				boolean flag3 = world.getBlockState(pos.east()).isFullBlock();

				if (enumfacing == EnumFacing.WEST && flag2 && !flag3)
					enumfacing = EnumFacing.EAST;
				else if (enumfacing == EnumFacing.EAST && flag3 && !flag2)
					enumfacing = EnumFacing.WEST;
			}
			world.setBlockState(pos,state.withProperty(FACING_ALL, enumfacing).withProperty(ACTIVE, Boolean.valueOf(false)), 2);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND || player.isSneaking())
			return false;

		if (!world.isRemote)
		{
			if (world.getTileEntity(pos) instanceof TileBeamDumpController)
			{
				TileBeamDumpController controller = (TileBeamDumpController) world.getTileEntity(pos);

				if (controller.getMultiblock() != null && controller.getMultiblock().isAssembled())
				{
					player.openGui(QMD.instance, GUI_ID.BEAM_DUMP, world, pos.getX(), pos.getY(), pos.getZ());
					return true;
				}
			}
		}
		return rightClickOnPart(world, pos, player, hand, facing, true);
	}

	
}