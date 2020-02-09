package lach_01298.qmd.multiblock.accelerator.block;

import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.multiblock.block.BlockMetaQMDPart;
import nc.enumm.MetaEnums;
import nc.multiblock.fission.block.BlockMetaFissionPartBase;
import nc.multiblock.fission.solid.tile.TileSolidFissionSink;
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

public class BlockAcceleratorCooler2 extends BlockMetaQMDPart<EnumTypes.CoolerType2>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", EnumTypes.CoolerType2.class);

	public BlockAcceleratorCooler2()
	{
		super(EnumTypes.CoolerType2.class, TYPE);
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
			return new TileAcceleratorCooler.Tin();
		case 1:
			return new TileAcceleratorCooler.Lead();
		case 2:
			return new TileAcceleratorCooler.Boron();
		case 3:
			return new TileAcceleratorCooler.Lithium();
		case 4:
			return new TileAcceleratorCooler.Magnesium();
		case 5:
			return new TileAcceleratorCooler.Manganese();
		case 6:
			return new TileAcceleratorCooler.Aluminum();
		case 7:
			return new TileAcceleratorCooler.Silver();
		case 8:
			return new TileAcceleratorCooler.Fluorite();
		case 9:
			return new TileAcceleratorCooler.Villiaumite();
		case 10:
			return new TileAcceleratorCooler.Carobbiite();
		case 11:
			return new TileAcceleratorCooler.Arsenic();
		case 12:
			return new TileAcceleratorCooler.LiquidNitrogen();
		case 13:
			return new TileAcceleratorCooler.LiquidHelium();
		case 14:
			return new TileAcceleratorCooler.Enderium();
		case 15:
			return new TileAcceleratorCooler.Cryotheum();
		}
		return new TileAcceleratorCooler.Tin();
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
