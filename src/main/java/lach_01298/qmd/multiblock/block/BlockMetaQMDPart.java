package lach_01298.qmd.multiblock.block;

import lach_01298.qmd.tab.QMDTabs;
import nc.enumm.IBlockMetaEnum;
import nc.multiblock.block.BlockMultiblockMetaPart;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

public abstract class BlockMetaQMDPart<T extends Enum<T> & IStringSerializable & IBlockMetaEnum> extends BlockMultiblockMetaPart<T>
{

	public BlockMetaQMDPart(Class<T> enumm, PropertyEnum<T> property)
	{
		super(enumm, property, Material.IRON, QMDTabs.MULTIBLOCKS);
	}

}
