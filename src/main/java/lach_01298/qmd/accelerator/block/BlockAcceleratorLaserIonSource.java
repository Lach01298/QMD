package lach_01298.qmd.accelerator.block;

import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.gui.GUI_ID;
import nc.util.BlockHelper;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAcceleratorLaserIonSource extends BlockAcceleratorSource
{

	public BlockAcceleratorLaserIonSource()
	{
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileAcceleratorIonSource.Laser();
	}


}