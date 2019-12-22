package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleBeam;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.heat.HeatBuffer;
import lach_01298.qmd.ByteUtil;
import net.minecraft.util.math.BlockPos;

public abstract class AcceleratorUpdatePacket extends MultiblockUpdatePacket
{

	public boolean isAcceleratorOn;
	public long cooling,rawHeating;
	public int requiredEnergy;
	public double efficiency, quadrupoleStrength;
	public int quadrupoleNumber, RFCavityNumber, acceleratingVoltage;
	public HeatBuffer heatBuffer;
	public EnergyStorage energyStorage;
	public ParticleBeam beam;
	

	
	public AcceleratorUpdatePacket()
	{
		messageValid = false;
	}

	public AcceleratorUpdatePacket(BlockPos pos,boolean isAcceleratorOn, long cooling, long rawHeating, int requiredEnergy, double efficiency, int acceleratingVoltage,
int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength, HeatBuffer heatBuffer, EnergyStorage energyStorage, ParticleBeam beam)
	{
		this.pos = pos;
		this.isAcceleratorOn = isAcceleratorOn;
		this.cooling = cooling;
		this.rawHeating = rawHeating;
		this.requiredEnergy = requiredEnergy;
		this.efficiency = efficiency;
		this.acceleratingVoltage = acceleratingVoltage;
		this.RFCavityNumber = RFCavityNumber;
		this.quadrupoleNumber = quadrupoleNumber;
		this.quadrupoleStrength = quadrupoleStrength;
		
		this.heatBuffer = heatBuffer;
		this.energyStorage = energyStorage;
		this.beam = beam;
		
		
		
		messageValid = true;
	}


	@Override
	public void readMessage(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isAcceleratorOn = buf.readBoolean();
		cooling = buf.readLong();
		rawHeating = buf.readLong();
		requiredEnergy = buf.readInt();
		efficiency = buf.readDouble();
		acceleratingVoltage = buf.readInt();
		RFCavityNumber = buf.readInt();
		quadrupoleNumber = buf.readInt();
		quadrupoleStrength = buf.readDouble();
		
		heatBuffer = ByteUtil.readBufHeat(buf);
		energyStorage = ByteUtil.readBufEnergy(buf);
		beam = ParticleBeam.readBuf(buf);

		
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
		buf.writeInt(requiredEnergy);
		buf.writeDouble(efficiency);
		buf.writeInt(acceleratingVoltage);
		buf.writeInt(RFCavityNumber);
		buf.writeInt(quadrupoleNumber);
		buf.writeDouble(quadrupoleStrength);

		ByteUtil.writeBufHeat(heatBuffer, buf);
		ByteUtil.writeBufEnergy(energyStorage, buf);
		beam.writeBuf(buf);

	
	}

	
	

}
