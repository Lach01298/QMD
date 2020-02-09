package lach_01298.qmd.particle;

import javax.annotation.Nullable;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

public interface IParticleStorage
{
	  /**
     * @return ParticleStack representing the particles in the tank
     */
	 @Nullable
    ParticleStack getParticleStack();

    /**
     * @return The Maximum energy of a particle that the tank can hold.
     */
    long getMaxEnergy();

    /**
     * @return The Minimum energy of a particle that the tank can hold.
     */
    long getMinEnergy();

    /**
     * @return The Maximum number of particles that the tank can hold.
     */
    int getCapacity();
    
    /**
     * @return State information for the IParticleStorage.
     */
    ParticleStorageInfo getInfo();

}
