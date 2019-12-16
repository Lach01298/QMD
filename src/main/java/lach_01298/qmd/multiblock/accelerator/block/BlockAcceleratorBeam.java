package lach_01298.qmd.multiblock.accelerator.block;


import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.EnumTypes.IOType;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import nc.block.property.ISidedProperty;
import nc.block.property.PropertySidedEnum;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import nc.multiblock.heatExchanger.HeatExchangerTubeType;
import nc.multiblock.heatExchanger.tile.TileCondenserTube;
import nc.tile.internal.fluid.FluidConnection;
import nc.util.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAcceleratorBeam extends BlockAcceleratorPartBase implements ISidedProperty<EnumTypes.IOType>
{

	
	private static EnumFacing placementSide = null;
	
	private static final PropertySidedEnum<EnumTypes.IOType> NORTH = PropertySidedEnum.create("north", EnumTypes.IOType.class, EnumFacing.NORTH);
	private static final PropertySidedEnum<EnumTypes.IOType> SOUTH = PropertySidedEnum.create("south", EnumTypes.IOType.class, EnumFacing.SOUTH);
	private static final PropertySidedEnum<EnumTypes.IOType> WEST = PropertySidedEnum.create("west", EnumTypes.IOType.class, EnumFacing.WEST);
	private static final PropertySidedEnum<EnumTypes.IOType> EAST = PropertySidedEnum.create("east", EnumTypes.IOType.class, EnumFacing.EAST);
	
	
	
	
	public BlockAcceleratorBeam()
	{
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileAcceleratorBeam();
	}

	
	@Override
	public IOType getProperty(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		if (world.getTileEntity(pos) instanceof TileAcceleratorBeam) {
			return ((TileAcceleratorBeam) world.getTileEntity(pos)).getBeamSetting(facing);
		}
		return EnumTypes.IOType.DISABLED;
	}
	
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, NORTH, SOUTH, WEST, EAST);
	}
	
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return state.withProperty(NORTH, getProperty(world, pos, EnumFacing.NORTH))
				.withProperty(SOUTH, getProperty(world, pos, EnumFacing.SOUTH))
				.withProperty(WEST, getProperty(world, pos, EnumFacing.WEST))
				.withProperty(EAST, getProperty(world, pos, EnumFacing.EAST));
	}
	
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		placementSide = null;
		if (placer != null && placer.isSneaking())
			placementSide = facing.getOpposite();
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
	}
	
	
	
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (placementSide == null)
			return;
		BlockPos from = pos.offset(placementSide);
		if (world.getTileEntity(pos) instanceof TileAcceleratorBeam
				&& world.getTileEntity(from) instanceof TileAcceleratorBeam)
		{
			TileAcceleratorBeam beam = (TileAcceleratorBeam) world.getTileEntity(pos);
			TileAcceleratorBeam other = (TileAcceleratorBeam) world.getTileEntity(from);
			beam.setBeamSettings(other.getBeamSettings().clone());
			beam.markDirtyAndNotify();
		}
	}


	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand != EnumHand.MAIN_HAND || player == null)
			return false;

		if (player.getHeldItemMainhand().isEmpty() && world.getTileEntity(pos) instanceof TileAcceleratorBeam)
		{
			TileAcceleratorBeam beam = (TileAcceleratorBeam) world.getTileEntity(pos);
			EnumFacing side = player.isSneaking() ? facing.getOpposite() : facing;
			if(side != EnumFacing.UP && side != EnumFacing.DOWN)
			{
				beam.toggleBeamSetting(side);
				if (!world.isRemote)
					player.sendMessage(getToggleMessage(player, beam, side));
				return true;
			}
			
			
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}

	private static TextComponentString getToggleMessage(EntityPlayer player, TileAcceleratorBeam beam, EnumFacing side)
	{
		
		IOType setting = beam.getBeamSetting(side);
		String message = player.isSneaking() ? "qmd.block.beam_toggle_opposite" : "qmd.block.beam_toggle";
		TextFormatting color = TextFormatting.WHITE;
		return new TextComponentString(Lang.localise(message) + " " + color + Lang.localise("qmd.block.accelerator_beam_side." + setting.getName()));
	}



}