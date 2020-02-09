package lach_01298.qmd.multiblock.particleChamber.block;



import lach_01298.qmd.enums.EnumTypes;
import lach_01298.qmd.multiblock.particleChamber.tile.TileParticleChamberEnergyPort;
import nc.util.Lang;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class BlockParticleChamberEnergyPort extends BlockParticleChamberPart
{

	public BlockParticleChamberEnergyPort()
	{
		super();
		
	}







	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileParticleChamberEnergyPort();
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