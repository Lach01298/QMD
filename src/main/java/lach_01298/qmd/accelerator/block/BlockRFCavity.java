package lach_01298.qmd.accelerator.block;

import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
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

public class BlockRFCavity extends BlockMetaQMDPart<RFCavityType>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", RFCavityType.class);

	public BlockRFCavity()
	{
		super(RFCavityType.class, TYPE);
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
		case 3:
			return new TileAcceleratorRFCavity.NiobiumTitanium();
		case 4:
			return new TileAcceleratorRFCavity.BSCCO();
		case 5:
			return new TileAcceleratorRFCavity.Aluminium();
		case 6:
			return new TileAcceleratorRFCavity.SSFAF();
		case 7:
			return new TileAcceleratorRFCavity.YBCO();
		
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
