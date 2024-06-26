package lach_01298.qmd.vacuumChamber.tile;

import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import nc.handler.TileInfoHandler;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.render.BlockHighlightTracker;
import nc.tile.TileContainerInfo;
import nc.util.Lang;
import nc.util.NCMath;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

import static nc.block.property.BlockProperties.FACING_ALL;

public class TileNucleosynthesisChamberController extends TileVacuumChamberPart implements IVacuumChamberController<TileNucleosynthesisChamberController>
{
	protected final TileContainerInfo<TileNucleosynthesisChamberController> info = TileInfoHandler.getTileContainerInfo("nucleosynthesis_chamber_controller");
	
	public boolean isRenderer = false;
	
	public TileNucleosynthesisChamberController()
	{
		super(CuboidalPartPositionType.WALL);
	}

	@Override
	public String getLogicID()
	{
		return	"nucleosynthesis_chamber";
	}
	
	@Override
	public TileContainerInfo<TileNucleosynthesisChamberController> getContainerInfo()
	{
		return info;
	}

	@Override
	public void onMachineAssembled(VacuumChamber controller)
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
		return super.writeAll(nbt);
	}
	
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		if (!isRenderer || !isMultiblockAssembled())
		{
			return Block.FULL_BLOCK_AABB.offset(pos);
		}
		return new AxisAlignedBB(getMultiblock().getMinimumCoord(), getMultiblock().getMaximumCoord());
	}

	@Override
	public double getDistanceSq(double x, double y, double z)
	{
		double dX, dY, dZ;
		if (!isRenderer || !isMultiblockAssembled())
		{
			dX = pos.getX() + 0.5D - x;
			dY = pos.getY() + 0.5D - y;
			dZ = pos.getZ() + 0.5D - z;
		}
		else
		{
			dX = getMultiblock().getMiddleX() + 0.5D - x;
			dY = getMultiblock().getMiddleY() + 0.5D - y;
			dZ = getMultiblock().getMiddleZ() + 0.5D - z;
		}
		return dX * dX + dY * dY + dZ * dZ;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		double defaultDistSq = super.getMaxRenderDistanceSquared();
		if (!isRenderer || !isMultiblockAssembled())
		{
			return defaultDistSq;
		}
		return defaultDistSq
				+ (NCMath.sq(getMultiblock().getExteriorLengthX()) + NCMath.sq(getMultiblock().getExteriorLengthY())
						+ NCMath.sq(getMultiblock().getExteriorLengthZ())) / 4D;
	}

	public boolean isRenderer()
	{
		return isRenderer;
	}
	
	public void setIsRenderer(boolean isRenderer)
	{
		this.isRenderer = isRenderer;
	}
	
	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayerMP player, World world, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(player.isSneaking() && this.isMultiblockAssembled())
		{
			int invalidAmount = 0;
			for (TileVacuumChamberHeater heater : this.getMultiblock().getPartMap(TileVacuumChamberHeater.class).values())
			{		
					if (!heater.isFunctional())
					{
						BlockHighlightTracker.sendPacket(player, heater.getPos(), 10000);
						invalidAmount++;
					}		
			}
			player.sendMessage(new TextComponentString(Lang.localize("qmd.multiblock_validation.vacuum_chamber.invalid_heaters", invalidAmount)));
			return true;
		}
		
		return false;
	}

}
