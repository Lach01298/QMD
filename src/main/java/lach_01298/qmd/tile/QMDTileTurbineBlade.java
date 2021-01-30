package lach_01298.qmd.tile;

import lach_01298.qmd.block.QMDBlockTurbineBlade;
import lach_01298.qmd.enums.BlockTypes.TurbineBladeType;
import nc.multiblock.turbine.TurbineRotorBladeUtil;
import nc.multiblock.turbine.TurbineRotorBladeUtil.IRotorBladeType;
import nc.multiblock.turbine.tile.TileTurbineRotorBlade;
import net.minecraft.block.state.IBlockState;

public class QMDTileTurbineBlade extends TileTurbineRotorBlade
{

	public QMDTileTurbineBlade(IRotorBladeType bladeType)
	{
		super(bladeType);
	}

	public static class SuperAlloy extends QMDTileTurbineBlade
	{

		public SuperAlloy()
		{
			super(TurbineBladeType.SUPER_ALLOY);
		}
	}

	@Override
	public IBlockState getRenderState()
	{
		if (getBlockType() instanceof QMDBlockTurbineBlade)
		{
			return getBlockType().getDefaultState().withProperty(TurbineRotorBladeUtil.DIR, dir);
		}
		return getBlockType().getDefaultState();
	}
}
