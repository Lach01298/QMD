package lach_01298.qmd.multiblock.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.ByteUtil;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import net.minecraft.util.math.BlockPos;

public class ParticleChamberUpdatePacket extends MultiblockUpdatePacket
{

	public boolean isChamberOn;
	public int requiredEnergy;
	public double efficiency;
	public EnergyStorage energyStorage;
	public byte numberOfTanks;
	public List<TankInfo> tanksInfo;
	public List<ParticleStorageAccelerator> beams;
	
	public ParticleChamberUpdatePacket()
	{
		messageValid = false;
		beams = new ArrayList<ParticleStorageAccelerator>();
	}
	
	public ParticleChamberUpdatePacket(BlockPos pos, boolean isChamberOn, int requiredEnergy, double efficiency, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams)
	{
		
		this.pos = pos;
		this.isChamberOn = isChamberOn;
		this.requiredEnergy =requiredEnergy;
		this.efficiency = efficiency;
		this.energyStorage = energyStorage;
		
		numberOfTanks = (byte) tanks.size();
		tanksInfo = TankInfo.infoList(tanks);
		this.beams = beams;
		
		messageValid = true;
	}
	
	
	
	
	@Override
	public void readMessage(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isChamberOn = buf.readBoolean();
		requiredEnergy = buf.readInt();
		efficiency = buf.readDouble();
		energyStorage = ByteUtil.readBufEnergy(buf);
		numberOfTanks = buf.readByte();
		tanksInfo = TankInfo.readBuf(buf, numberOfTanks);
		
		int size = buf.readInt();
		for (int i = 0; i < size; i++)
		{
			beams.add(ByteUtil.readBufBeam(buf));
		}
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBoolean(isChamberOn);
		buf.writeInt(requiredEnergy);
		buf.writeDouble(efficiency);
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
