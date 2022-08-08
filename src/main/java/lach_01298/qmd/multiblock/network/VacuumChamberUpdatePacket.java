package lach_01298.qmd.multiblock.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.util.ByteUtil;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public abstract class VacuumChamberUpdatePacket extends MultiblockUpdatePacket
{

	public boolean isContainmentOn;
	public long heating, currentHeating;
	public int maxCoolantIn, maxCoolantOut;
	public int maxOperatingTemp;
	public int requiredEnergy;
	
	public HeatBuffer heatBuffer;
	public EnergyStorage energyStorage;
	public byte numberOfTanks;
	public List<TankInfo> tanksInfo;
	public List<ParticleStorageAccelerator> beams;
	

	
	public VacuumChamberUpdatePacket()
	{
		beams = new ArrayList<ParticleStorageAccelerator>();
	}

	public VacuumChamberUpdatePacket(BlockPos pos,boolean isContainmentOn, long heating, long currentHeating, int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams)
	{
		this.pos = pos;
		this.isContainmentOn = isContainmentOn;
		
		this.heating = heating;
		this.currentHeating = currentHeating;
		this.maxCoolantIn = maxCoolantIn;
		this.maxCoolantOut = maxCoolantOut;
		this.maxOperatingTemp = maxOperatingTemp;
		this.requiredEnergy = requiredEnergy;
		
		this.heatBuffer = heatBuffer;
		this.energyStorage = energyStorage;
		
		numberOfTanks = (byte) tanks.size();
		tanksInfo = TankInfo.infoList(tanks);
		
		this.beams = beams;
		
	}


	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isContainmentOn = buf.readBoolean();
		heating = buf.readLong();
		currentHeating = buf.readLong();
		maxCoolantIn = buf.readInt();
		maxCoolantOut = buf.readInt();
		maxOperatingTemp = buf.readInt();
		requiredEnergy = buf.readInt();
		
		heatBuffer = ByteUtil.readBufHeat(buf);
		energyStorage = ByteUtil.readBufEnergy(buf);
		numberOfTanks = buf.readByte();
		tanksInfo = TankInfo.readBuf(buf, numberOfTanks);
		
		int size = buf.readInt();
		for (int i = 0; i < size; i++)
		{
			ParticleStorage storage = ByteUtil.readBufBeam(buf);
			ParticleStorageAccelerator beam = new ParticleStorageAccelerator();
			beam.setParticleStack(storage.getParticleStack());
			beam.setMaxEnergy(storage.getMaxEnergy());
			beam.setMinEnergy(storage.getMinEnergy());
			beam.setCapacity(storage.getCapacity());
			beams.add(beam);
		}
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBoolean(isContainmentOn);
		buf.writeLong(heating);
		buf.writeLong(currentHeating);
		buf.writeInt(maxCoolantIn);
		buf.writeInt(maxCoolantOut);
		buf.writeInt(maxOperatingTemp);
		buf.writeInt(requiredEnergy);
		
		ByteUtil.writeBufHeat(heatBuffer, buf);
		ByteUtil.writeBufEnergy(energyStorage, buf);
		buf.writeByte(numberOfTanks);
		for (TankInfo info : tanksInfo) info.writeBuf(buf);
		
		buf.writeInt(beams.size());
		for(ParticleStorageAccelerator beam : beams)
		{
			ByteUtil.writeBufBeam(beam, buf);
		}
	
	}

	
	

}
