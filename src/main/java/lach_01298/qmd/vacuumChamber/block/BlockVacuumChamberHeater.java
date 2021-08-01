package lach_01298.qmd.vacuumChamber.block;

import lach_01298.qmd.enums.BlockTypes.HeaterType;
import lach_01298.qmd.multiblock.block.BlockMetaQMDPart;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberHeater;
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

public class BlockVacuumChamberHeater extends BlockMetaQMDPart<HeaterType>
{

	public final static PropertyEnum TYPE = PropertyEnum.create("type", HeaterType.class);

	public BlockVacuumChamberHeater()
	{
		super(HeaterType.class, TYPE);
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
			return new TileVacuumChamberHeater.Iron();
		case 1:
			return new TileVacuumChamberHeater.Redstone();
		case 2:
			return new TileVacuumChamberHeater.Quartz();
		case 3:
			return new TileVacuumChamberHeater.Obsidian();
		case 4:
			return new TileVacuumChamberHeater.Glowstone();
		case 5:
			return new TileVacuumChamberHeater.Lapis();
		case 6:
			return new TileVacuumChamberHeater.Gold();
		case 7:
			return new TileVacuumChamberHeater.Diamond();
		}
		return new TileVacuumChamberHeater.Iron();
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
