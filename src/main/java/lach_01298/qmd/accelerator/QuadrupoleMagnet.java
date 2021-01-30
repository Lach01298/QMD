package lach_01298.qmd.accelerator;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.util.Util;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.enums.BlockTypes.MagnetType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class QuadrupoleMagnet
{
	private final Accelerator accelerator;
	private BlockPos pos;
	private  EnumFacing.Axis axis;
	
	private final Long2ObjectMap<IAcceleratorComponent> components = new Long2ObjectOpenHashMap<>();

	private  MagnetType type;
	
	public QuadrupoleMagnet(Accelerator accelerator, BlockPos pos, EnumFacing.Axis axis)
	{
		this.accelerator = accelerator;
		this.pos = pos;
		this.axis = axis;
		setType();
		addComponents();
	}
	
	
	private void setType()
	{
		if( accelerator.WORLD.getTileEntity(pos.up()) instanceof TileAcceleratorMagnet)
		{
			TileAcceleratorMagnet magnet =(TileAcceleratorMagnet) accelerator.WORLD.getTileEntity(pos.up());
			type = MagnetType.valueOf(magnet.name.toUpperCase());
		}
		else
		{
			Util.getLogger().error("could not set quadrupole type becuase" + accelerator.WORLD.getTileEntity(pos.up())+" at "+ pos.up() + " is not a instance of TileAcceleratorMagnet");
		}
		
	}
	
	private void addComponents()
	{
		addComponent(pos);
		addComponent(pos.up());
		addComponent(pos.down());
		if(axis == Axis.X)
		{
			addComponent(pos.north());
			addComponent(pos.south());
		}
		if(axis == Axis.Z)
		{
			addComponent(pos.east());
			addComponent(pos.west());
		}
		
	}
	
	private void addComponent(BlockPos postion)
	{
		if (accelerator.WORLD.getTileEntity(postion) instanceof IAcceleratorComponent)
		{
			components.put(postion.toLong(),(IAcceleratorComponent) accelerator.WORLD.getTileEntity(postion));
		}
		else
		{
			Util.getLogger().error("could not add tile to quadrupole becuase" + accelerator.WORLD.getTileEntity(postion) +" at "+ postion + " is not a instance of IAcceleratorComponent");
		}	
	}

	public BlockPos getPos() 
	{
		return pos;
	}
	
	public EnumFacing.Axis getAxis() 
	{
		return axis;
	}
	
	public MagnetType getType() 
	{
		return type;
	}
	
	public Long2ObjectMap<IAcceleratorComponent> getComponents()
	{
		return components;
	}

}
