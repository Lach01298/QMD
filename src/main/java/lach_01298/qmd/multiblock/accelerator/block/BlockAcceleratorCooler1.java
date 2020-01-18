package lach_01298.qmd.multiblock.accelerator.block;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCooler;
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

public class BlockAcceleratorCooler1 extends BlockMetaQMDPart<EnumTypes.CoolerType1>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", EnumTypes.CoolerType1.class);

	public BlockAcceleratorCooler1()
	{
		super(EnumTypes.CoolerType1.class, TYPE);
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
			return new TileAcceleratorCooler.Water();
		case 1:
			return new TileAcceleratorCooler.Iron();
		case 2:
			return new TileAcceleratorCooler.Redstone();
		case 3:
			return new TileAcceleratorCooler.Quartz();
		case 4:
			return new TileAcceleratorCooler.Obsidian();
		case 5:
			return new TileAcceleratorCooler.NetherBrick();
		case 6:
			return new TileAcceleratorCooler.Glowstone();
		case 7:
			return new TileAcceleratorCooler.Lapis();
		case 8:
			return new TileAcceleratorCooler.Gold();
		case 9:
			return new TileAcceleratorCooler.Prismarine();
		case 10:
			return new TileAcceleratorCooler.Slime();
		case 11:
			return new TileAcceleratorCooler.EndStone();
		case 12:
			return new TileAcceleratorCooler.Purpur();
		case 13:
			return new TileAcceleratorCooler.Diamond();
		case 14:
			return new TileAcceleratorCooler.Emerald();
		case 15:
			return new TileAcceleratorCooler.Copper();
		}
		return new TileAcceleratorCooler.Water();
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
