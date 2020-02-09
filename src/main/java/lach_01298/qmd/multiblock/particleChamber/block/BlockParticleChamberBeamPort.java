package lach_01298.qmd.multiblock.particleChamber.block;

import static lach_01298.qmd.block.BlockProperties.IO;
import static nc.block.property.BlockProperties.FACING_HORIZONTAL;

import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberBeamPort;
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

public class BlockParticleChamberBeamPort extends BlockParticleChamberPart
{

	public BlockParticleChamberBeamPort()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(IO, EnumTypes.IOType.INPUT));
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
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileParticleChamberBeamPort();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand != EnumHand.MAIN_HAND || player == null)
			return false;

		if (player.getHeldItemMainhand().isEmpty() && world.getTileEntity(pos) instanceof TileParticleChamberBeamPort)
		{
			TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) world.getTileEntity(pos);
			if(player.isSneaking())
			{
				if (!world.isRemote)
				{
					if(port.switchOutputs())
					{
						
							player.sendMessage(getSwitchMessage(player, port));
					}
					else
					{
						return false;
					}
				}
				
				
			}
			else
			{
			port.toggleSetting();
			if (!world.isRemote)
				player.sendMessage(getToggleMessage(player, port));
			}
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
	private static TextComponentString getToggleMessage(EntityPlayer player, TileParticleChamberBeamPort port)
	{
		return new TextComponentString(TextFormatting.AQUA + Lang.localise("qmd.block_toggle." + port.getIOType().name())) ;
	}
	
	private static TextComponentString getSwitchMessage(EntityPlayer player, TileParticleChamberBeamPort port)
	{
		return new TextComponentString(TextFormatting.AQUA + Lang.localise("qmd.block_switch.particle_chamber." + port.getIONumber())) ;
	}
	
	
}