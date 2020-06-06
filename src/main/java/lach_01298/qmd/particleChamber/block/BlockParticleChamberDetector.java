package lach_01298.qmd.particleChamber.block;

import lach_01298.qmd.enums.BlockTypes.DetectorType;
import lach_01298.qmd.multiblock.block.BlockMetaQMDPart;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberDetector;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
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

public class BlockParticleChamberDetector extends BlockMetaQMDPart<DetectorType>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", DetectorType.class);

	public BlockParticleChamberDetector()
	{
		super(DetectorType.class, TYPE);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] { TYPE });
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		switch (metadata)
		{
		case 0:
			return new TileParticleChamberDetector.BubbleChamber();
		case 1:
			return new  TileParticleChamberDetector.SiliconTracker();
		case 2:
			return new  TileParticleChamberDetector.WireChamber();
		case 3:
			return new  TileParticleChamberDetector.EMCalorimeter();
		case 4:
			return new  TileParticleChamberDetector.HadronCalorimeter();

		}
		return  new  TileParticleChamberDetector.BubbleChamber();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND || player.isSneaking())
			return false;
		return rightClickOnPart(world, pos, player, hand, facing);
	}
	
	
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		if(state.getValue(TYPE) == DetectorType.SILLICON_TRACKER || state.getValue(TYPE) == DetectorType.WIRE_CHAMBER)
		{
			return true;
		}
		return false;
	}
	
	

	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() 
	{
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) 
	{

		IBlockState otherState = blockAccess.getBlockState(pos.offset(side));
		Block block = otherState.getBlock();
		
		if (blockState != otherState) return true;
		
		return block == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
	
}

