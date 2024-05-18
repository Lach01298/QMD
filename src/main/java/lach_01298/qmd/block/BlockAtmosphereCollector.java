package lach_01298.qmd.block;

import lach_01298.qmd.enums.BlockTypes.SimpleTileType;
import lach_01298.qmd.tile.TileAtmosphereCollector;
import lach_01298.qmd.util.Units;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockAtmosphereCollector extends QMDBlockSimpleTile
{

	public BlockAtmosphereCollector()
	{
		super(SimpleTileType.ATMOSPHERE_COLLECTOR);
	}

	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand != EnumHand.MAIN_HAND)
		{
			return false;
		}
		
		if (player != null && player.getHeldItem(hand).isEmpty())
		{
			TileEntity tile = world.getTileEntity(pos);
			if (!world.isRemote && tile instanceof TileAtmosphereCollector)
			{
				TileAtmosphereCollector collector = (TileAtmosphereCollector) tile;
				Fluid fluid = collector.getCollectionFluid();
				if(fluid != null)
				{
					String name = collector.getCollectionFluid().getUnlocalizedName();
					player.sendMessage(new TextComponentString(Lang.localize("message.qmd.atmosphere_collector",Lang.localize(name),Units.getSIFormat(collector.getCollectionRate(), -3, "B/t"))));
				}
				else
				{
					player.sendMessage(new TextComponentString(Lang.localize("message.qmd.atmosphere_collector_no_fluid")));
				}
				
			}
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
}
