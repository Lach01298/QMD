package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.util.ByteUtil;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

import java.util.*;

public abstract class AcceleratorUpdatePacket extends QMDMultiblockUpdatePacket
{

	public boolean isAcceleratorOn;
	public long cooling, rawHeating, currentHeating;
	public int maxCoolantIn, maxCoolantOut;
	public int maxOperatingTemp;
	public int requiredEnergy;
	public double efficiency, quadrupoleStrength, dipoleStrength;
	public int quadrupoleNumber, RFCavityNumber, acceleratingVoltage, dipoleNumber;
	public HeatBuffer heatBuffer;
	public EnergyStorage energyStorage;
	public List<TankInfo> tanksInfo;
	public List<ParticleStorageAccelerator> beams;
	public int errorCode;
	

	
	public AcceleratorUpdatePacket()
	{
		beams = new ArrayList<ParticleStorageAccelerator>();
	}

	public AcceleratorUpdatePacket(BlockPos pos,boolean isAcceleratorOn, long cooling, long rawHeating,long currentHeating, int maxCoolantIn, int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage,
int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength,int dipoleNumber, double dipoleStrength ,int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams)
	{
		this.pos = pos;
		this.isAcceleratorOn = isAcceleratorOn;
		this.cooling = cooling;
		this.rawHeating = rawHeating;
		this.currentHeating = currentHeating;
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
		
		tanksInfo = TankInfo.getInfoList(tanks);
		
		this.beams = beams;
	}


	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isAcceleratorOn = buf.readBoolean();
		cooling = buf.readLong();
		rawHeating = buf.readLong();
		currentHeating = buf.readLong();
		maxCoolantIn = buf.readInt();
		maxCoolantOut = buf.readInt();
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
		tanksInfo = readTankInfos(buf);
		
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
		buf.writeBoolean(isAcceleratorOn);
		buf.writeLong(cooling);
		buf.writeLong(rawHeating);
		buf.writeLong(currentHeating);
		buf.writeInt(maxCoolantIn);
		buf.writeInt(maxCoolantOut);
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
		writeTankInfos(buf, tanksInfo);
		
		buf.writeInt(beams.size());
		for(ParticleStorageAccelerator beam : beams)
		{
			ByteUtil.writeBufBeam(beam, buf);
		}
	
	}

	
	

}
