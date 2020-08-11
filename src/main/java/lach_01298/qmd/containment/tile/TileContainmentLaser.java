package lach_01298.qmd.containment.tile;

import lach_01298.qmd.containment.Containment;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.util.NCMath;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileContainmentLaser extends TileContainmentPart implements IContainmentPart
{
	public boolean isRenderer = false;
	
	public TileContainmentLaser()
	{
		super(CuboidalPartPositionType.WALL);
	
		
	}


	@Override
	public void onMachineAssembled(Containment controller)
	{
		super.onMachineAssembled(controller);
		
	}

	@Override
	public void onMachineBroken()
	{
		super.onMachineBroken();
		
	}
	
	
	// NBT
	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		return nbt;
	}

	@Override
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



}