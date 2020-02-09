package lach_01298.qmd.multiblock.particleChamber.block;


import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberBeam;
import nc.block.property.ISidedProperty;
import nc.block.property.PropertySidedEnum;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import nc.multiblock.heatExchanger.HeatExchangerTubeType;
import nc.multiblock.heatExchanger.tile.TileCondenserTube;
import nc.tile.internal.fluid.FluidConnection;
import nc.util.Lang;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockParticleChamberBeam extends BlockParticleChamberPart
{

	
	
	public BlockParticleChamberBeam()
	{
		super();
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileParticleChamberBeam();
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