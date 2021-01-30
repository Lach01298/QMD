package lach_01298.qmd.fluid;

import lach_01298.qmd.QMDDamageSources;
import nc.block.fluid.NCBlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockFluidExotic extends NCBlockFluid 
{
	
	public BlockFluidExotic(FluidExotic fluid) 
	{
		super(fluid, Material.WATER);
	}
	
	@Override
	public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) 
	{	
			entityIn.attackEntityFrom(QMDDamageSources.ANTIMATTER_ANNIHLATION, 10000F);
			worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 10f, true);
	}
}
