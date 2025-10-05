package lach_01298.qmd.block;

import lach_01298.qmd.enums.BlockTypes.FluidCollectorType;
import lach_01298.qmd.tile.TileFluidCollector;
import nc.block.tile.BlockTile;
import nc.block.tile.ITileType;
import nc.util.Lang;
import nc.util.UnitHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidCollector extends BlockTile implements ITileType
{

	private final FluidCollectorType type;

	public BlockFluidCollector(FluidCollectorType type)
	{
		super(Material.IRON);
		this.type = type;
		setCreativeTab(type.getCreativeTab());
	}

	@Override
	public String getTileName()
	{
		return type.getName();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return type.getTile();
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
			if (!world.isRemote && tile instanceof TileFluidCollector)
			{
				TileFluidCollector collector = (TileFluidCollector) tile;
				Fluid fluid = collector.getCollectionFluid();
				if(fluid != null)
				{
					String name = collector.getCollectionFluid().getUnlocalizedName();
					player.sendMessage(new TextComponentString(Lang.localize("message.qmd.collector",Lang.localize(name), UnitHelper.prefix(collector.getCollectionRate(), 5, "B/t",-1))));
				}
				else
				{
					player.sendMessage(new TextComponentString(Lang.localize("message.qmd.collector_no_fluid")));
				}

			}
			return true;
		}
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
}
