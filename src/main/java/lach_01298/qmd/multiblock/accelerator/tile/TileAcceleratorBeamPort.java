package lach_01298.qmd.multiblock.accelerator.tile;

import static lach_01298.qmd.block.BlockProperties.IO;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.EnumTypes.IOType;
import lach_01298.qmd.EnumTypes.IOType;
import lach_01298.qmd.io.IIOType;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.multiblock.heatExchanger.HeatExchangerTubeSetting;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileAcceleratorBeamPort extends TileAcceleratorPartBase implements IIOType
{

	private EnumTypes.IOType type;
	
	
	public TileAcceleratorBeamPort()
	{
		super(CuboidalPartPositionType.WALL);
		this.type = EnumTypes.IOType.DEFAULT;
		
		
	}

	boolean isFunctional()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) 
	{
		return oldState.getBlock() != newSate.getBlock();
	}
	
	@Override
	public IOType getIOType()
	{
		
		return type;
	}

	@Override
	public void setIOType(IOType type)
	{
		this.type = type;
	}
	
	public void toggleSetting()
	{
		setIOType(type.getNextIO());
		getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(IO, type));
		markDirtyAndNotify();
	}
	
	public NBTTagCompound writeAll(NBTTagCompound nbt) 
	{
		super.writeAll(nbt);
		nbt.setInteger("setting", type.getID());
		return nbt;
	}
		
	public void readAll(NBTTagCompound nbt) 
	{
		super.readAll(nbt);
		type =EnumTypes.IOType.getTypeFromID(nbt.getInteger("setting"));
	}
		
		
	
	
}
