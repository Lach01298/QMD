package lach_01298.qmd.particleChamber.block;

import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static lach_01298.qmd.block.BlockProperties.IO;

public class BlockParticleChamberBeamPort extends BlockParticleChamberPart
{

	public BlockParticleChamberBeamPort()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(IO, EnumTypes.IOType.INPUT));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, IO);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
	
		return getDefaultState().withProperty(IO, EnumTypes.IOType.getTypeFromID(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return  state.getValue(IO).getID();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileParticleChamberBeamPort();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState();
	}

	
}
