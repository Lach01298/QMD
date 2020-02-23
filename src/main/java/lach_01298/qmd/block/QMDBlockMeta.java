package lach_01298.qmd.block;

import lach_01298.qmd.enums.EnumTypes;
import nc.block.BlockMeta;
import nc.enumm.MetaEnums;
import nc.tab.NCTabs;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;

public class QMDBlockMeta
{

	
public static class BlockFissionReflector extends BlockMeta<EnumTypes.NeutronReflectorType> 
{
		
		public final static PropertyEnum<EnumTypes.NeutronReflectorType> TYPE = PropertyEnum.create("type", EnumTypes.NeutronReflectorType.class);
		
		public BlockFissionReflector() 
		{
			super(EnumTypes.NeutronReflectorType.class, TYPE, Material.IRON);
			setCreativeTab(NCTabs.MULTIBLOCK);
		}
		
		@Override
		protected BlockStateContainer createBlockState() 
		{
			return new BlockStateContainer(this, new IProperty[] {TYPE});
		}
	}
	
	
}
