package lach_01298.qmd.multiblock.accelerator.block;

import static lach_01298.qmd.block.BlockProperties.IO_SIMPLE;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCasing;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPort;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import nc.multiblock.heatExchanger.tile.TileCondenserTube;
import nc.util.Lang;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockAcceleratorPort extends BlockAcceleratorPartBase
{

	public BlockAcceleratorPort()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(IO_SIMPLE, EnumTypes.IOType.INPUT));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, IO_SIMPLE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(IO_SIMPLE, EnumTypes.IOType.getSimpleTypeFromID(meta));
		
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(IO_SIMPLE).getSimpleID();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileAcceleratorPort();
	}



	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand != EnumHand.MAIN_HAND || player == null)
			return false;

		if (player.getHeldItemMainhand().isEmpty() && world.getTileEntity(pos) instanceof TileAcceleratorPort)
		{
			TileAcceleratorPort port = (TileAcceleratorPort) world.getTileEntity(pos);
			
			port.toggleSetting();
			if (!world.isRemote)
				player.sendMessage(getToggleMessage(player, port));
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
	private static TextComponentString getToggleMessage(EntityPlayer player, TileAcceleratorPort port)
	{
		TextFormatting color = TextFormatting.WHITE;
		return new TextComponentString(Lang.localise("qmd.block_toggle") + " " + color + Lang.localise("qmd.block.accelerator_port_config." + port.getIOType().name()));
	}
	
	
	
}