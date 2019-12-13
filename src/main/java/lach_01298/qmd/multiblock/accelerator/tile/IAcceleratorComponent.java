package lach_01298.qmd.multiblock.accelerator.tile;

import javax.annotation.Nullable;

import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.IMultiblockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IAcceleratorComponent extends IMultiblockPart<Accelerator> {
	

	

	public boolean isFunctional();
	
	public void resetStats();
	
	
	
	// Helper methods
	
	public default boolean isBeam(BlockPos pos)
	{
		TileAcceleratorBeam beam = getMultiblock().getBeamMap().get(pos.toLong());
		return beam == null ? false : beam.isFunctional();
	}

	public default boolean isActiveRFCavity(BlockPos pos, String name)
	{
		TileAcceleratorRFCavity cavity = getMultiblock().getRFCavityMap().get(pos.toLong());
		return cavity == null ? false : (cavity.isFunctional() && cavity.name.equals(name))||(cavity.isFunctional() && name == null);
	}

	public default boolean isActiveCooler(BlockPos pos, String name)
	{
		TileAcceleratorCooler cooler = getMultiblock().getCoolerMap().get(pos.toLong());
		return cooler == null ? false : (cooler.isFunctional() && cooler.name.equals(name))||(cooler.isFunctional() && name == null);
	}

	public default boolean isActiveMagnet(BlockPos pos, String name)
	{
		TileAcceleratorMagnet magnet = getMultiblock().getMagnetMap().get(pos.toLong());
		return magnet == null ? false : (magnet.isFunctional() && magnet.name.equals(name))||(magnet.isFunctional() && name == null);
	}

	public default boolean isActiveYoke(BlockPos pos)
	{
		TileAcceleratorYoke yoke = getMultiblock().getYokeMap().get(pos.toLong());
		return yoke == null ? false : yoke.isFunctional();
	}
	
}
