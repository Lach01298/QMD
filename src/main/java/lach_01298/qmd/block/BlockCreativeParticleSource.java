package lach_01298.qmd.block;

import lach_01298.qmd.QMD;
import lach_01298.qmd.tab.QMDTabs;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import nc.block.tile.BlockTile;
import nc.block.tile.ITileType;
import nc.tile.ITileGui;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockCreativeParticleSource extends BlockTile implements ITileType
{

	public BlockCreativeParticleSource()
	{
		super(Material.IRON);
		setCreativeTab(QMDTabs.BLOCKS);
		setBlockUnbreakable();
		setResistance(6000000.0F);
	}

	
	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileCreativeParticleSource();
	}


	@Override
	public String getTileName()
	{
		return "creative_particle_source";
	}
	
	@Override
	public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
    {
		return false;	
    }
	
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if (player == null || hand != EnumHand.MAIN_HAND) return false;
		
		TileEntity tile = world.getTileEntity(pos);
		
		if (player.isSneaking()) return false;
		if (!player.isCreative()) return false;
		
		if (tile instanceof ITileGui) 
		{
			if (world.isRemote) 
			{
				onGuiOpened(world, pos);
				return true;
			} else 
			{
				onGuiOpened(world, pos);
				FMLNetworkHandler.openGui(player, QMD.instance, ((ITileGui) tile).getGuiID(), world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		else return false;
		
		return true;
	}
	
	
}
