package lach_01298.qmd.multiblock.block;

import lach_01298.qmd.tab.QMDTabs;
import nc.enumm.IBlockMetaEnum;
import nc.multiblock.BlockMultiblockMetaPart;
import nc.tab.NCTabs;
import nc.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockMetaQMDPart<T extends Enum<T> & IStringSerializable & IBlockMetaEnum> extends BlockMultiblockMetaPart<T>
{

	public BlockMetaQMDPart(Class<T> enumm, PropertyEnum<T> property)
	{
		super(enumm, property, Material.IRON, QMDTabs.BLOCKS);
	}

}
