package lach_01298.qmd.multiblock.particleChamber.tile;

import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public interface IParticleChamberComponent extends IParticleChamberPart {
	
	

	public boolean isFunctional();
	
	public void setFunctional(boolean func);
	
	public void resetStats();
	
	
	
	// Helper methods
	

	public default boolean isActiveDetector(BlockPos pos, String name)
	{
	
		return false;
	}




	
}
