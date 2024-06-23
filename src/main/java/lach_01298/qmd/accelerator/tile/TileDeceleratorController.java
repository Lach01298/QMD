package lach_01298.qmd.accelerator.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.accelerator.Accelerator;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.render.BlockHighlightTracker;
import nc.tile.TileContainerInfo;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class TileDeceleratorController extends TileAcceleratorPart implements IAcceleratorController<TileDeceleratorController>
{
	protected final TileContainerInfo<TileDeceleratorController> info = TileInfoHandler.getTileContainerInfo("decelerator_controller");

	public TileDeceleratorController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"decelerator";
	}
	
	@Override
	public TileContainerInfo<TileDeceleratorController> getContainerInfo()
	{
		return info;
	}

	@Override
	public void onMachineAssembled(Accelerator controller)
	{
		super.onMachineAssembled(controller);
		if (!getWorld().isRemote && getPartPosition().getFacing() != null)
		{
			getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(FACING_ALL, getPartPosition().getFacing()), 2);
		}
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	{
		return oldState.getBlock() != newState.getBlock();
	}

	@Override
	public void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos)
	{
		super.onBlockNeighborChanged(state, world, pos, fromPos);
		
	}
	
	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayerMP player, World world, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(player.isSneaking() && this.isMultiblockAssembled())
		{
			int invalidAmount = 0;
			for (TileAcceleratorCooler cooler : this.getMultiblock().getPartMap(TileAcceleratorCooler.class).values())
			{		
					if (!cooler.isFunctional())
					{
						BlockHighlightTracker.sendPacket(player, cooler.getPos(), 10000);
						invalidAmount++;
					}		
			}
			player.sendMessage(new TextComponentString(Lang.localize("qmd.multiblock_validation.accelerator.invalid_coolers", invalidAmount)));
			return true;
		}
		
		return false;
	}
}
