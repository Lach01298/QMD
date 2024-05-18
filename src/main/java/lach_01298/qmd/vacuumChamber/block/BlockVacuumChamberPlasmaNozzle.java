package lach_01298.qmd.vacuumChamber.block;

import lach_01298.qmd.pipe.TileBeamline;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaNozzle;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static nc.block.property.BlockProperties.AXIS_ALL;

public class BlockVacuumChamberPlasmaNozzle extends BlockVacuumChamberPart
{

	public BlockVacuumChamberPlasmaNozzle()
	{
		super();
		setDefaultState(blockState.getBaseState().withProperty(AXIS_ALL, Axis.X));
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		switch(meta)
		{
		case 0:
			return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.X);
		case 1:
			return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.Y);
		case 2:
			return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.Z);
		}
		return getDefaultState().withProperty(AXIS_ALL, EnumFacing.Axis.X);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		switch(state.getValue(AXIS_ALL))
		{
		case X:
			return 0;
		case Y:
			return 1;
		case Z:
			return 2;
		}
		return 0;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AXIS_ALL);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		Axis state = null;
		for(EnumFacing face : EnumFacing.VALUES)
    	{
    		if(world.getTileEntity(pos.offset(face)) instanceof TileBeamline)
    		{
    			state = world.getBlockState(pos.offset(face)).getValue(AXIS_ALL);
    		}
    	}
		if(state != null)
		{
			return getDefaultState().withProperty(AXIS_ALL, state);
		}
		
		return getDefaultState().withProperty(AXIS_ALL, EnumFacing.getDirectionFromEntityLiving(pos, placer).getAxis());
	}
	

	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileVacuumChamberPlasmaNozzle();
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
