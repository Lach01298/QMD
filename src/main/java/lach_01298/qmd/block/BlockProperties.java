package lach_01298.qmd.block;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.EnumTypes.IOType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;

public class BlockProperties
{
	public static final PropertyEnum<EnumTypes.IOType> IO = PropertyEnum.create("io", EnumTypes.IOType.class);
	public static final PropertyEnum<EnumTypes.IOType> IO_SIMPLE = PropertyEnum.create("io_simple", EnumTypes.IOType.class,IOType.INPUT,IOType.OUTPUT);
}
