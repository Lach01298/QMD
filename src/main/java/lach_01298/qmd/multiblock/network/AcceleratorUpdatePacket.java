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
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public abstract class AcceleratorUpdatePacket extends MultiblockUpdatePacket
{

	public boolean isAcceleratorOn;
	public long cooling,rawHeating;
	public double maxCoolantIn;
	public double maxCoolantOut;
	public int maxOperatingTemp;
	public int requiredEnergy;
	public double efficiency, quadrupoleStrength, dipoleStrength;
	public int quadrupoleNumber, RFCavityNumber, acceleratingVoltage, dipoleNumber;
	public HeatBuffer heatBuffer;
	public EnergyStorage energyStorage;
	public byte numberOfTanks;
	public List<TankInfo> tanksInfo;
	public List<ParticleStorageAccelerator> beams;
	public int errorCode;
	

	
	public AcceleratorUpdatePacket()
	{
		messageValid = false;
		beams = new ArrayList<ParticleStorageAccelerator>();
	}

	public AcceleratorUpdatePacket(BlockPos pos,boolean isAcceleratorOn, long cooling, long rawHeating,double maxCoolantIn, double maxCoolantOut, int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage,
int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength,int dipoleNumber, double dipoleStrength ,int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams)
	{
		this.pos = pos;
		this.isAcceleratorOn = isAcceleratorOn;
		this.cooling = cooling;
		this.rawHeating = rawHeating;
		this.maxCoolantIn = maxCoolantIn;
		this.maxCoolantOut = maxCoolantOut;
		this.maxOperatingTemp = maxOperatingTemp;
		this.requiredEnergy = requiredEnergy;
		this.efficiency = efficiency;
		this.acceleratingVoltage = acceleratingVoltage;
		this.RFCavityNumber = RFCavityNumber;
		this.quadrupoleNumber = quadrupoleNumber;
		this.quadrupoleStrength = quadrupoleStrength;
		this.dipoleNumber = dipoleNumber;
		this.dipoleStrength = dipoleStrength;
		this.errorCode = errorCode;
		
		this.heatBuffer = heatBuffer;
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
		isAcceleratorOn = buf.readBoolean();
		cooling = buf.readLong();
		rawHeating = buf.readLong();
		maxCoolantIn = buf.readDouble();
		maxCoolantOut = buf.readDouble();
		maxOperatingTemp = buf.readInt();
		requiredEnergy = buf.readInt();
		efficiency = buf.readDouble();
		acceleratingVoltage = buf.readInt();
		RFCavityNumber = buf.readInt();
		quadrupoleNumber = buf.readInt();
		quadrupoleStrength = buf.readDouble();
		dipoleNumber = buf.readInt();
		dipoleStrength = buf.readDouble();
		errorCode = buf.readInt();
		
		heatBuffer = ByteUtil.readBufHeat(buf);
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
		buf.writeBoolean(isAcceleratorOn);
		buf.writeLong(cooling);
		buf.writeLong(rawHeating);
		buf.writeDouble(maxCoolantIn);
		buf.writeDouble(maxCoolantOut);
		buf.writeInt(maxOperatingTemp);
		buf.writeInt(requiredEnergy);
		buf.writeDouble(efficiency);
		buf.writeInt(acceleratingVoltage);
		buf.writeInt(RFCavityNumber);
		buf.writeInt(quadrupoleNumber);
		buf.writeDouble(quadrupoleStrength);
		buf.writeInt(dipoleNumber);
		buf.writeDouble(dipoleStrength);
		buf.writeInt(errorCode);
		
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
