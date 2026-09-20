package lach_01298.qmd.particle;

import net.minecraft.util.EnumFacing;

public interface IParticleStackHandler
{

	/**
	 *
	 * @param side
	 * @param stack - the ParticleStack to be inputted
	 * @return if the stack could be inputted
	 */
	boolean receiveParticle(EnumFacing side, ParticleStack stack);
	
	
	/**
	 *
	 * @param side
	 * @return the extracted ParticleStack
	 */
	ParticleStack extractParticle(EnumFacing side);
	
	/**
	 *
	 * @param side
	 * @param type the type of particle
	 * @return the extracted ParticleStack
	 */
	ParticleStack extractParticle(EnumFacing side, Particle type);
	
	/**
	 *
	 * @param side
	 *@param Amount the amount of particles
	 * @return the extracted ParticleStack
	 */
	ParticleStack extractParticle(EnumFacing side, int Amount);
	
	/**
	 *
	 * @param side
	 * @param type the type of particle
	 * @param Amount the amount of particles
	 * @return the extracted ParticleStack
	 */
	ParticleStack extractParticle(EnumFacing side, Particle type, int Amount);
	
	/**
	 *
	 *
	 * @return a copy of the ParticleStack
	 */
	ParticleStack getParticle();
	
	

	/**
	 *
	 * @param side
	 * @param stack - the ParticleStack to be inputted
	 * @return if the stack could be inputted
	 */
	boolean canReceiveParticle(EnumFacing side, ParticleStack stack);
	
	/**
	 *
	 * @param side
	 * @return if the ParticleStack could be extracted
	 */
	boolean canExtractParticle(EnumFacing side);
	
	
	
}
