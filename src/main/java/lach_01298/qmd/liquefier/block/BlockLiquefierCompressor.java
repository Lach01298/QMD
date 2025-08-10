package lach_01298.qmd.liquefier.block;

import lach_01298.qmd.enums.BlockTypes;
import lach_01298.qmd.liquefier.tile.TileLiquefierCompressor;
import lach_01298.qmd.multiblock.block.BlockMetaQMDPart;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockLiquefierCompressor extends BlockMetaQMDPart<BlockTypes.CompressorType>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", BlockTypes.CompressorType.class);

	public BlockLiquefierCompressor()
	{
		super(BlockTypes.CompressorType.class, TYPE);
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
				return new TileLiquefierCompressor.Copper();
			case 1:
				return new TileLiquefierCompressor.Neodymium();
			case 2:
				return new TileLiquefierCompressor.SamariumCobalt();
		}
		return new TileLiquefierCompressor.Copper();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
									EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player == null)
			return false;
		if (hand != EnumHand.MAIN_HAND || player.isSneaking())
			return false;
		return rightClickOnPart(world, pos, player, hand, facing);
	}

}
