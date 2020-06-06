package lach_01298.qmd.particleChamber.tile;

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
