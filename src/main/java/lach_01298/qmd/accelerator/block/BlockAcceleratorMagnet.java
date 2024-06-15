package lach_01298.qmd.accelerator.block;

import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import lach_01298.qmd.multiblock.block.BlockMetaQMDPart;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAcceleratorMagnet extends BlockMetaQMDPart<MagnetType>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", MagnetType.class);

	public BlockAcceleratorMagnet()
	{
		super(MagnetType.class, TYPE);
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
			return new TileAcceleratorMagnet.Copper();
		case 1:
			return new TileAcceleratorMagnet.MagnesiumDiboride();
		case 2:
			return new TileAcceleratorMagnet.NiobiumTin();
		case 3:
			return new TileAcceleratorMagnet.NiobiumTitanium();
		case 4:
			return new TileAcceleratorMagnet.BSCCO();

		}
		return new TileAcceleratorMagnet.Copper();
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
