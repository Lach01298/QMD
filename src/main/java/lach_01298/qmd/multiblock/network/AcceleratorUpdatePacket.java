package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import nc.tile.internal.heat.HeatBuffer;
import lach_01298.qmd.ByteUtil;
import net.minecraft.util.math.BlockPos;

public abstract class AcceleratorUpdatePacket extends MultiblockUpdatePacket
{

	public boolean isAcceleratorOn;
	public long cooling,rawHeating;
	public double maxCoolantIn;
	public double maxCoolantOut;
	public int requiredEnergy;
	public double efficiency, quadrupoleStrength;
	public int quadrupoleNumber, RFCavityNumber, acceleratingVoltage;
	public HeatBuffer heatBuffer;
	public EnergyStorage energyStorage;
	public byte numberOfTanks;
	public List<TankInfo> tanksInfo;

	

	
	public AcceleratorUpdatePacket()
	{
		messageValid = false;
	}

	public AcceleratorUpdatePacket(BlockPos pos,boolean isAcceleratorOn, long cooling, long rawHeating,double maxCoolantIn, double maxCoolantOut, int requiredEnergy, double efficiency, int acceleratingVoltage,
int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks)
	{
		this.pos = pos;
		this.isAcceleratorOn = isAcceleratorOn;
		this.cooling = cooling;
		this.rawHeating = rawHeating;
		this.maxCoolantIn = maxCoolantIn;
		this.maxCoolantOut = maxCoolantOut;
		this.requiredEnergy = requiredEnergy;
		this.efficiency = efficiency;
		this.acceleratingVoltage = acceleratingVoltage;
		this.RFCavityNumber = RFCavityNumber;
		this.quadrupoleNumber = quadrupoleNumber;
		this.quadrupoleStrength = quadrupoleStrength;
		
		this.heatBuffer = heatBuffer;
		this.energyStorage = energyStorage;
		
		numberOfTanks = (byte) tanks.size();
		tanksInfo = TankInfo.infoList(tanks);
		
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
		requiredEnergy = buf.readInt();
		efficiency = buf.readDouble();
		acceleratingVoltage = buf.readInt();
		RFCavityNumber = buf.readInt();
		quadrupoleNumber = buf.readInt();
		quadrupoleStrength = buf.readDouble();
		
		heatBuffer = ByteUtil.readBufHeat(buf);
		energyStorage = ByteUtil.readBufEnergy(buf);
		numberOfTanks = buf.readByte();
		tanksInfo = TankInfo.readBuf(buf, numberOfTanks);
		
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
		buf.writeInt(requiredEnergy);
		buf.writeDouble(efficiency);
		buf.writeInt(acceleratingVoltage);
		buf.writeInt(RFCavityNumber);
		buf.writeInt(quadrupoleNumber);
		buf.writeDouble(quadrupoleStrength);

		ByteUtil.writeBufHeat(heatBuffer, buf);
		ByteUtil.writeBufEnergy(energyStorage, buf);
		buf.writeByte(numberOfTanks);
		for (TankInfo info : tanksInfo) info.writeBuf(buf);
	
	}

	
	

}
