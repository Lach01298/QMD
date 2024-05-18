package lach_01298.qmd.fission.block;

import lach_01298.qmd.enums.BlockTypes.NeutronReflectorType;
import lach_01298.qmd.tab.QMDTabs;
import nc.block.BlockMeta;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.BlockStateContainer;



public class BlockFissionReflector extends BlockMeta<NeutronReflectorType>
{
		
		public final static PropertyEnum<NeutronReflectorType> TYPE = PropertyEnum.create("type", NeutronReflectorType.class);
		
		public BlockFissionReflector()
		{
			super(NeutronReflectorType.class, TYPE, Material.IRON);
			setCreativeTab(QMDTabs.MULTIBLOCKS);
		}
		
		@Override
		protected BlockStateContainer createBlockState()
		{
			return new BlockStateContainer(this, new IProperty[] {TYPE});
		}
	}
