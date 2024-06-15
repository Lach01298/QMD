package lach_01298.qmd.block;

import lach_01298.qmd.enums.BlockTypes.RTGType;
import lach_01298.qmd.tab.QMDTabs;
import nc.block.multiblock.BlockMultiblockPart;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QMDBlockRTG extends BlockMultiblockPart
{

	private final RTGType type;

	public QMDBlockRTG(RTGType type)
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
