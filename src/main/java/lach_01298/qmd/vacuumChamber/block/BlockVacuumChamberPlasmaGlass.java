package lach_01298.qmd.vacuumChamber.block;



import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberGlass;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockVacuumChamberPlasmaGlass extends BlockVacuumChamberPart.Transparent
{

	public BlockVacuumChamberPlasmaGlass()
	{
		super(true);
		
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileVacuumChamberPlasmaGlass();
		
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() 
	{
		return BlockRenderLayer.TRANSLUCENT;
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
