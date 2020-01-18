package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.ByteUtil;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import net.minecraft.util.math.BlockPos;

public class ParticleChamberUpdatePacket extends MultiblockUpdatePacket
{

	public boolean isChamberOn;
	public int requiredEnergy;
	public double efficiency;
	public EnergyStorage energyStorage;

	
	public ParticleChamberUpdatePacket()
	{
		messageValid = false;
	}
	
	public ParticleChamberUpdatePacket(BlockPos pos, boolean isChamberOn, int requiredEnergy, double efficiency, EnergyStorage energyStorage)
	{
		
		this.pos = pos;
		this.isChamberOn = isChamberOn;
		this.requiredEnergy =requiredEnergy;
		this.efficiency = efficiency;
		this.energyStorage = energyStorage;
		
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
		
		
	}

}
