package lach_01298.qmd.util;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.*;
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
	
	
	public static void writeBufBeam(ParticleStorage storage,ByteBuf buf)
	{
		ParticleStack stack = storage.getParticleStack();
		
		
		if(stack == null)
		{
			ByteBufUtils.writeUTF8String(buf,"none");
			buf.writeInt(0);
			buf.writeLong(0);
			buf.writeDouble(0);
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
			
			buf.writeInt(stack.getAmount());
			buf.writeLong(stack.getMeanEnergy());
			buf.writeDouble(stack.getFocus());
		}
	
		buf.writeLong(storage.getMaxEnergy());
		buf.writeLong(storage.getMinEnergy());
	}
	

	
	public static ParticleStorage readBufBeam(ByteBuf buf)
	{
		ParticleStorage storage = new ParticleStorage();
		
		String string = ByteBufUtils.readUTF8String(buf);
		if(string.equals("none"))
		{
			storage.setParticleStack(null);
			buf.readInt();
			buf.readLong();
			buf.readDouble();
		}
		else
		{
			Particle p = Particles.getParticleFromName(string);
			int amount = buf.readInt();
			long energy = buf.readLong();
			double focus = buf.readDouble();
			
			
			ParticleStack stack = new ParticleStack(p, amount, energy, focus);
			storage.setParticleStack(stack);
		}
		
		storage.setMaxEnergy(buf.readLong());
		storage.setMinEnergy(buf.readLong());
		
		return storage;
	}
	
	
	
	public static void writeBufParticleStorage(ParticleStorage storage,ByteBuf buf)
	{
		ParticleStack stack = storage.getParticleStack();
		
		
		if(stack == null)
		{
			ByteBufUtils.writeUTF8String(buf,"none");
			buf.writeInt(0);
			buf.writeLong(0);
			buf.writeDouble(0);
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
			
			buf.writeInt(stack.getAmount());
			buf.writeLong(stack.getMeanEnergy());
			buf.writeDouble(stack.getFocus());
		}
	
		buf.writeLong(storage.getMaxEnergy());
		buf.writeLong(storage.getMinEnergy());
	}
	

	
	public static ParticleStorage readBufParticleStorage(ByteBuf buf)
	{
		ParticleStorage storage = new ParticleStorage();
		
		String string = ByteBufUtils.readUTF8String(buf);
		if(string.equals("none"))
		{
			storage.setParticleStack(null);
			buf.readInt();
			buf.readLong();
			buf.readDouble();
		}
		else
		{
			Particle p = Particles.getParticleFromName(string);
			int amount = buf.readInt();
			long energy = buf.readLong();
			double focus = buf.readDouble();
			
			
			ParticleStack stack = new ParticleStack(p, amount, energy, focus);
			storage.setParticleStack(stack);
		}
		
		storage.setMaxEnergy(buf.readLong());
		storage.setMinEnergy(buf.readLong());
		
		return storage;
	}
	
	
}
