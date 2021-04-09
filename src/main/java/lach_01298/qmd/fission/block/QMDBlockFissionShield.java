package lach_01298.qmd.fission.block;

import static nc.block.property.BlockProperties.ACTIVE;

import lach_01298.qmd.enums.BlockTypes.NeutronShieldType;
import lach_01298.qmd.fission.tile.QMDTileFissionShield;
import lach_01298.qmd.tab.QMDTabs;
import nc.multiblock.fission.block.BlockFissionMetaPart;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QMDBlockFissionShield extends BlockFissionMetaPart<NeutronShieldType>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", NeutronShieldType.class);

	public QMDBlockFissionShield()
	{
		super(NeutronShieldType.class, TYPE);
		setDefaultState(getDefaultState().withProperty(ACTIVE, Boolean.valueOf(false)));
		setCreativeTab(QMDTabs.MULTIBLOCKS);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, TYPE, ACTIVE);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof QMDTileFissionShield)
		{
			QMDTileFissionShield shield = (QMDTileFissionShield) tile;
			return state.withProperty(ACTIVE, shield.isShielding);
		}
		return state;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		switch (metadata)
		{
		case 0:
			return new QMDTileFissionShield.Hafnium();
		}
		return new QMDTileFissionShield.Hafnium();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getStateFromMeta(meta);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof QMDTileFissionShield)
		{
			QMDTileFissionShield shield = (QMDTileFissionShield) tile;
			world.setBlockState(pos, state.withProperty(ACTIVE, shield.isShielding), 2);
		}
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
		if (!world.isRemote && state.getBlock() instanceof QMDBlockFissionShield && isActive != state.getValue(ACTIVE))
		{
			world.setBlockState(pos, state.withProperty(ACTIVE, isActive), 2);
		}
	}

	// Rendering

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		// return state.getValue(ACTIVE);
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		// return state.getValue(ACTIVE);
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		IBlockState otherState = world.getBlockState(pos.offset(side));
		Block block = otherState.getBlock();

		if (blockState != otherState)
			return true;

		return block == this ? false : super.shouldSideBeRendered(blockState, world, pos, side);
	}
}
