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

public class BlockQMD extends Block
{

	public BlockQMD(Material material)
	{
		super(material);
		this.setCreativeTab(QMDTabs.BLOCKS);
	}

	
}
