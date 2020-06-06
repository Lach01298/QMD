package lach_01298.qmd.particle;

import javax.annotation.Nullable;

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
