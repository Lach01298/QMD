package lach_01298.qmd.block;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.EnumTypes.IOType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;

public class BlockProperties
{
	public static final PropertyEnum<EnumTypes.IOType> IO = PropertyEnum.create("io", EnumTypes.IOType.class);
	public static final PropertyEnum<EnumTypes.IOType> IO_SIMPLE = PropertyEnum.create("io_simple", EnumTypes.IOType.class,IOType.INPUT,IOType.OUTPUT);
	public static final PropertyEnum<EnumFacing.Axis> AXIS_HORIZONTAL = PropertyEnum.create("axis_horizontal", EnumFacing.Axis.class,EnumFacing.Axis.X,EnumFacing.Axis.Z);
}
