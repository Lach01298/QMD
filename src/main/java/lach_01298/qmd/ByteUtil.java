package lach_01298.qmd;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class ByteUtil
{

	
	
	public static void writeBufHeat(HeatBuffer heatBuffer,ByteBuf buf) 
	{
		buf.writeLong(heatBuffer.getHeatCapacity());	
		buf.writeLong(heatBuffer.getHeatStored());
	}
	
	public static HeatBuffer readBufHeat(ByteBuf buf)
	{
		
		long heatCapacity = buf.readLong();
		long heatStored = buf.readLong();
		HeatBuffer buffer = new HeatBuffer(heatCapacity);
		buffer.setHeatStored(heatStored);
		return buffer;
	}
	
	public static void writeBufEnergy(EnergyStorage energyStorage,ByteBuf buf) 
	{
		
		buf.writeInt(energyStorage.getMaxEnergyStored());
		buf.writeInt(energyStorage.getEnergyStored());
	}
	
	public static EnergyStorage readBufEnergy(ByteBuf buf)
	{
		int  maxEnergy = buf.readInt();
		int energy = buf.readInt();
		EnergyStorage buffer = new EnergyStorage(maxEnergy);
		buffer.setEnergyStored(energy);
		return buffer;
	}
	
	
	public static void writeBufBeam(ParticleStorageAccelerator beam,ByteBuf buf) 
	{
		ParticleStack stack = beam.getParticleStack();
		
		
		if(stack == null)
		{
			ByteBufUtils.writeUTF8String(buf,"none");
			buf.writeLong(0);
			buf.writeInt(0);
			buf.writeDouble(0);
			buf.writeInt(0);
		}
		else
		{
			if(stack.getParticle() == null)
			{
				ByteBufUtils.writeUTF8String(buf, "none");
			}
			else
			{
				ByteBufUtils.writeUTF8String(buf, stack.getParticle().getName());
			}
			
			buf.writeLong(stack.getMeanEnergy());
			buf.writeInt(stack.getAmount());
			buf.writeDouble(stack.getEnergySpread());
			buf.writeInt(stack.getLuminosity());
		}
	
		
		
		buf.writeLong(beam.getMaxEnergy());
		buf.writeLong(beam.getMinEnergy());
	}
	

	
	public static ParticleStorageAccelerator readBufBeam(ByteBuf buf)
	{
		ParticleStorageAccelerator beam = new ParticleStorageAccelerator();
		
		String string = ByteBufUtils.readUTF8String(buf);
		if(string.equals("none"))
		{
			beam.setParticleStack(null);
			buf.readLong();
			buf.readInt();
			buf.readDouble();
			buf.readInt();
			
		}
		else
		{
			Particle p = Particles.getParticleFromName(string);
			long energy = buf.readLong();
			int amount = buf.readInt();
			double spread = buf.readDouble();
			int lum = buf.readInt();
			
			ParticleStack stack = new ParticleStack(p,energy, amount, spread,lum);
			beam.setParticleStack(stack);
		}
		
		beam.setMaxEnergy(buf.readLong());
		beam.setMinEnergy(buf.readLong());

		return beam;
	}
	
	
}
