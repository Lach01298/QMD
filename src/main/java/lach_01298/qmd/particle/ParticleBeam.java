package lach_01298.qmd.particle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import nc.tile.internal.fluid.Tank.TankInfo;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class ParticleBeam implements INBTSerializable<NBTTagCompound>
{

	private Particle particle;
	private int meanEnergy, luminosity;
	private double energySpread;
	private int meanEnery;
	
	
	public ParticleBeam()
	{
		this.particle =Particles.none;
		this.meanEnergy = 0;
		this.energySpread = 0;
		this.luminosity = 0;
	}
	
	/**
	 * 
	 * @return The type of particle the beam is
	 */
	public Particle getParticle()
	{
		return particle;
	}
	
	/**
	 * 
	 * @return The average energy in keV of the beam
	 */
	public int getMeanEnergy()
	{
		return meanEnergy;
	}
	
	/**
	 * 
	 * @return The energy spread percentage around the meanEnergy
	 */
	public double getEnergySpread()
	{
		return energySpread;
	}
	
	/**
	 * 
	 * @return The Luminosity of the beam ie particle collisions per area per second   1~ 10^28 1/(cm^2*s)
	 */
	public int getLuminosity()
	{
		return luminosity;
	}
	
	
	public void setParticle(Particle newParticle)
	{
		this.particle =newParticle;
	}
	
	public void setMeanEnergy(int newMeanEnergy)
	{
		this.meanEnergy = newMeanEnergy;
	}
	
	public void setEnergySpread(double newEnergySpread)
	{
		this.energySpread = newEnergySpread;
	}
	
	public void setLuminosity(int newLuminosity)
	{
		this.luminosity = newLuminosity;
	}
	
	public int getRandomEnergy()
	{
		Random rand = new Random();
		int deviation =(int) (rand.nextGaussian() * energySpread * meanEnergy);
		
		return meanEnergy + deviation;
	}


	// NBT

	@Override
	public NBTTagCompound serializeNBT()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		readAll(nbt);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		
		nbt.setString("particle", particle.getName());
		nbt.setInteger("meanEnergy", meanEnergy);
		nbt.setDouble("energySpread", energySpread);
		nbt.setInteger("luminosity", luminosity);
		return nbt;
	}

	public final NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		NBTTagCompound heatTag = new NBTTagCompound();
		writeToNBT(heatTag);
		nbt.setTag("particleBeam", heatTag);
		return nbt;

	}

	public ParticleBeam readFromNBT(NBTTagCompound nbt)
	{

		this.particle = Particles.getParticleFromName(nbt.getString("particle"));
		this.meanEnergy = nbt.getInteger("meanEnergy");
		this.energySpread = nbt.getDouble("energySpread");
		this.luminosity = nbt.getInteger("luminosity");
		return this;
	}

	public final void readAll(NBTTagCompound nbt)
	{
		if (nbt.hasKey("particleBeam"))
			readFromNBT(nbt.getCompoundTag("particleBeam"));
	}
	
	
	
	
	public void writeBuf(ByteBuf buf) 
	{
		ByteBufUtils.writeUTF8String(buf, particle.getName());
		buf.writeInt(meanEnery);
		buf.writeDouble(energySpread);
		buf.writeInt(luminosity);
	}
	
	public static ParticleBeam readBuf(ByteBuf buf)
	{
		String string = ByteBufUtils.readUTF8String(buf);
		Particle p = Particles.getParticleFromName(string);
		int energy = buf.readInt();
		double spread = buf.readDouble();
		int lum = buf.readInt();
		ParticleBeam beam = new ParticleBeam();
		beam.setParticle(p);
		beam.setMeanEnergy(energy);
		beam.setEnergySpread(spread);
		beam.setLuminosity(lum);
		
		return beam;
	}
	
	
	
	
	
	
}
