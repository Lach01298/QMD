package lach_01298.qmd.block;

import nc.block.tile.BlockSimpleTile;
import nc.block.tile.BlockTile;
import nc.block.tile.ITileType;
import nc.multiblock.block.BlockMultiblockPart;
import nc.multiblock.rtg.RTGType;
import nc.tab.NCTabs;
import lach_01298.qmd.enums.QMDRTGType;
import lach_01298.qmd.tab.QMDTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QMDBlockRTG extends BlockMultiblockPart
{

	private final QMDRTGType type;

	public QMDBlockRTG(QMDRTGType type)
	{
		super(Material.IRON, QMDTabs.BLOCKS);
		this.type = type;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return type.getTile();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND)
			return false;

		return rightClickOnPart(world, pos, player, hand, facing, false);
	}
}

