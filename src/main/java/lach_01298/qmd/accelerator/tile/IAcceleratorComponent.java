package lach_01298.qmd.accelerator.tile;

import javax.annotation.Nullable;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IAcceleratorComponent extends IAcceleratorPart 
{
	
	public int getMaxOperatingTemp();

	public boolean isFunctional();
	
	public void setFunctional(boolean func);
	
	public default boolean isToHot()
	{
		if (getMultiblock().getTemperature() > this.getMaxOperatingTemp())
		{
			return true;
		}
		return false;
	}
	

}
