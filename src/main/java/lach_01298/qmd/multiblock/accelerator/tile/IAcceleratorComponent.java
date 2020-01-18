package lach_01298.qmd.multiblock.accelerator.tile;

import javax.annotation.Nullable;

import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IAcceleratorComponent extends IAcceleratorPart {
	
	

	public boolean isFunctional();
	
	public void setFunctional(boolean func);
	
	public void resetStats();
	
	
	
	// Helper methods
	
	public default boolean isBeam(BlockPos pos)
	{
		TileAcceleratorBeam beam = getMultiblock().getPartMap(TileAcceleratorBeam.class).get(pos.toLong());
		return beam == null ? false : beam.isFunctional();
	}

	public default boolean isActiveRFCavity(BlockPos pos, String name)
	{
		TileAcceleratorRFCavity cavity =  getMultiblock().getPartMap(TileAcceleratorRFCavity.class).get(pos.toLong());
		return cavity == null ? false : (cavity.isFunctional() && (cavity.name.equals(name))||(cavity.isFunctional() && name == null));
	}

	public default boolean isActiveCooler(BlockPos pos, String name)
	{
		TileAcceleratorCooler cooler =  getMultiblock().getPartMap(TileAcceleratorCooler.class).get(pos.toLong());
		
		if(cooler != null)
		{
			if(cooler.name.equals(name)||name == null)
			{
				return  cooler.isFunctional();
			}
		
		}
		
		
		return false;
	}

	public default boolean isActiveMagnet(BlockPos pos, String name)
	{
		TileAcceleratorMagnet magnet = getMultiblock().getPartMap(TileAcceleratorMagnet.class).get(pos.toLong());
		return magnet == null ? false : (magnet.isFunctional() && magnet.name.equals(name))||(magnet.isFunctional() && name == null);
	}

	public default boolean isActiveYoke(BlockPos pos)
	{
		TileAcceleratorYoke yoke  = getMultiblock().getPartMap(TileAcceleratorYoke.class).get(pos.toLong());
		return yoke == null ? false : yoke.isFunctional();
	}


	
}
