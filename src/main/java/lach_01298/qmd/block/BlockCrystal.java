package lach_01298.qmd.block;

import lach_01298.qmd.tab.QMDTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystal extends Block
{

	public BlockCrystal()
	{
		super(Material.GLASS);
		this.setCreativeTab(QMDTabs.BLOCKS);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side)
	{

		IBlockState otherState = world.getBlockState(pos.offset(side));
		Block block = otherState.getBlock();

		if (blockState != otherState)
		{
			return true;
		}

		return block == this ? false : super.shouldSideBeRendered(blockState, world, pos, side);
	}
}
