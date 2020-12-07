package lach_01298.qmd.accelerator;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import lach_01298.qmd.Util;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.enums.BlockTypes.RFCavityType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;

public class RFCavity
{
	private final Accelerator accelerator;
	private BlockPos pos;
	private  EnumFacing.Axis axis;
	private  RFCavityType type;
	public boolean is_active;

	private final Long2ObjectMap<IAcceleratorComponent> components = new Long2ObjectOpenHashMap<>();

	
	
	public RFCavity(Accelerator accelerator, BlockPos pos, EnumFacing.Axis axis)
	{
		this.accelerator = accelerator;
		this.pos = pos;
		this.axis = axis;
		this.is_active = true;
		setType();
		addComponents();
	}
	
	private void setType()
	{
		if( accelerator.WORLD.getTileEntity(pos.up()) instanceof TileAcceleratorRFCavity)
		{
			TileAcceleratorRFCavity cavity =(TileAcceleratorRFCavity) accelerator.WORLD.getTileEntity(pos.up());
			type = RFCavityType.valueOf(cavity.name.toUpperCase());
		}
		else
		{
			Util.getLogger().error("could not set RF Cavity type becuase" + accelerator.WORLD.getTileEntity(pos.up())+" at "+ pos.up() + " is not a instance of TileAcceleratorRFCavity");
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
			addComponent(pos.north().up());
			addComponent(pos.north().down());
			addComponent(pos.south().up());
			addComponent(pos.south().down());
		}
		if(axis == Axis.Z)
		{
			addComponent(pos.east());
			addComponent(pos.west());
			addComponent(pos.east().up());
			addComponent(pos.east().down());
			addComponent(pos.west().up());
			addComponent(pos.west().down());
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
			Util.getLogger().error("could not add tile to RF Cavity becuase" + accelerator.WORLD.getTileEntity(postion) +" at "+ postion + " is not a instance of IAcceleratorComponent");
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
	
	public RFCavityType getType() 
	{
		return type;
	}
	
	public Long2ObjectMap<IAcceleratorComponent> getComponents()
	{
		return components;
	}

}
