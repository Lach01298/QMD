package lach_01298.qmd.particleChamber.tile;

import static nc.block.property.BlockProperties.FACING_ALL;

import lach_01298.qmd.particleChamber.ParticleChamber;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.render.BlockHighlightTracker;
import nc.tile.TileContainerInfo;
import nc.util.Lang;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


public class TileDecayChamberController extends TileParticleChamberPart implements IParticleChamberController<TileDecayChamberController>
{
	protected final TileContainerInfo<TileDecayChamberController> info = TileInfoHandler.getTileContainerInfo("decay_chamber_controller");

	public TileDecayChamberController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"decay_chamber";
	}
	
	@Override
	public TileContainerInfo<TileDecayChamberController> getContainerInfo()
	{
		return info;
	}

	@Override
	public void onMachineAssembled(ParticleChamber controller)
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
		if (getMultiblock() != null) getMultiblock().updateActivity();
	}

	
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);

		return nbt;
	}
	
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		
	}
	
	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayerMP player, World world, EnumFacing facing,float hitX, float hitY, float hitZ)
	{
		if (player.isSneaking() && this.isMultiblockAssembled())
		{
			int invalidAmount = 0;
			for (TileParticleChamberDetector detector : this.getMultiblock().getPartMap(TileParticleChamberDetector.class).values())
			{
				BlockPos chamberPos = new BlockPos(getMultiblock().getMiddleX(), getMultiblock().getMiddleY(),getMultiblock().getMiddleZ());
				if (!detector.isValidPostion(chamberPos))
				{
					BlockHighlightTracker.sendPacket(player, detector.getPos(), 10000);
					invalidAmount++;
				}
			}
			player.sendMessage(new TextComponentString(Lang.localize("qmd.multiblock_validation.chamber.invalid_detectors", invalidAmount)));
			return true;
		}

		return false;
	}
}
