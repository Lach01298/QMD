package lach_01298.qmd.multiblock.accelerator.block;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
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

public class BlockRFCavity extends BlockMetaAcceleratorPart<EnumTypes.RFCavityType>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", EnumTypes.RFCavityType.class);

	public BlockRFCavity()
	{
		super(EnumTypes.RFCavityType.class, TYPE);
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
			return new TileAcceleratorRFCavity.Copper();
		case 1:
			return new TileAcceleratorRFCavity.MagnesiumDiboride();
		case 2:
			return new TileAcceleratorRFCavity.NiobiumTin();
		
		}
		return new TileAcceleratorRFCavity.Copper();
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
}
