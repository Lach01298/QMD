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


public class TileCollisionChamberController extends TileParticleChamberPart implements IParticleChamberController<TileCollisionChamberController>
{
	protected final TileContainerInfo<TileCollisionChamberController> info = TileInfoHandler.getTileContainerInfo("collision_chamber_controller");

	public TileCollisionChamberController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"collision_chamber";
	}
	
	@Override
	public TileContainerInfo<TileCollisionChamberController> getContainerInfo()
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
				BlockPos chamberPos;
				if (getMultiblock().getExteriorLengthX() > getMultiblock().getExteriorLengthZ())
				{
					chamberPos = new BlockPos(detector.getPos().getX(), getMultiblock().getMiddleY(), getMultiblock().getMiddleZ());
				}
				else
				{
					chamberPos = new BlockPos(getMultiblock().getMiddleX(), getMultiblock().getMiddleY(), detector.getPos().getZ());
				}

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
