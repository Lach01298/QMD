package lach_01298.qmd.particle;

import net.minecraft.util.EnumFacing;

public interface IParticleStackHandler
{

	/**
	 * 
	 * @param side
	 * @param stack - the ParticleStack to be inputed
	 * @return if the stack could be inputed
	 */
	boolean reciveParticle(EnumFacing side, ParticleStack stack);
	
	
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
	 *@param the amount of particles
	 * @return the extracted ParticleStack
	 */
	ParticleStack extractParticle(EnumFacing side, int Amount);
	
	/**
	 * 
	 * @param side
	 * @param type the type of particle
	 * @param the amount of particles
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
	 * @param stack - the ParticleStack to be inputed
	 * @return if the stack could be inputed
	 */
	boolean canReciveParticle(EnumFacing side, ParticleStack stack);
	
	/**
	 * 
	 * @param side
	 * @return if the a ParticleStack could be extracted
	 */
	boolean canExtractParticle(EnumFacing side);
	
	
	
}
