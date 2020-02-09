package lach_01298.qmd.multiblock.accelerator.block;



import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCasing;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorInlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorOutlet;
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

public class BlockAcceleratorInlet extends BlockAcceleratorPart
{

	public BlockAcceleratorInlet()
	{
		super();
		
	}

	

	

	

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileAcceleratorInlet();
	}



	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (hand != EnumHand.MAIN_HAND || player == null)
			return false;

		
		return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	}
	
	
	
	
	
}