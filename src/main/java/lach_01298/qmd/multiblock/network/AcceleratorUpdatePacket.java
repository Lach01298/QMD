package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.multiblock.turbine.Turbine;
import nc.multiblock.turbine.tile.TileTurbineController;
import net.minecraft.util.math.BlockPos;

public class AcceleratorUpdatePacket extends MultiblockUpdatePacket
{

	public boolean isAcceleratorOn;
	public long cooling,heating;
	public int requiredEnergy, requiredCoolant;
	public double totalEfficiency;
	public int quadrapoleNumber;
	public double luminosity;
	public int dipoleNumber, RFCavityNumber, maxParticleEnergy;
	public long heatCapacity,heat;
	public int energy, maxEnergy;
	
	public AcceleratorUpdatePacket()
	{
		messageValid = false;
	}

	public AcceleratorUpdatePacket(BlockPos pos,boolean isAcceleratorOn, long cooling, long heating, int requiredEnergy, int requiredCoolant, double totalEfficiency, int quadrapoleNumber, double luminosity,
			int dipoleNumber, int RFCavityNumber, int maxParticleEnergy, long heatCapacity, long heat, int maxEnergy, int energy)
	{
		this.pos = pos;
		this.isAcceleratorOn = isAcceleratorOn;
		this.cooling = cooling;
		this.heating = heating;
		this.requiredEnergy = requiredEnergy;
		this.requiredCoolant = requiredCoolant;
		this.totalEfficiency = totalEfficiency;
		this.quadrapoleNumber = quadrapoleNumber;
		this.luminosity = luminosity;
		this.dipoleNumber = dipoleNumber;
		this.RFCavityNumber = RFCavityNumber;
		this.maxParticleEnergy = maxParticleEnergy;
		this.heatCapacity = heatCapacity;
		this.heat = heat;
		this.maxEnergy = maxEnergy;
		this.energy = energy;
		
		
		messageValid = true;
	}


	@Override
	public void readMessage(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isAcceleratorOn = buf.readBoolean();
		cooling = buf.readLong();
		heating = buf.readLong();
		requiredEnergy = buf.readInt();
		requiredCoolant = buf.readInt();
		totalEfficiency = buf.readDouble();
		quadrapoleNumber = buf.readInt();
		luminosity = buf.readDouble();
		dipoleNumber = buf.readInt();
		RFCavityNumber = buf.readInt();
		maxParticleEnergy = buf.readInt();
		heatCapacity = buf.readLong();
		heat = buf.readLong();
		maxEnergy = buf.readInt();
		energy = buf.readInt();
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBoolean(isAcceleratorOn);
		buf.writeLong(cooling);
		buf.writeLong(heating);
		buf.writeInt(requiredEnergy);
		buf.writeInt(requiredCoolant);
		buf.writeDouble(totalEfficiency);
		buf.writeInt(quadrapoleNumber);
		buf.writeDouble(luminosity);
		buf.writeInt(RFCavityNumber);
		buf.writeInt(maxParticleEnergy);
		buf.writeLong(heatCapacity);
		buf.writeLong(heat);
		buf.writeInt(maxEnergy);
		buf.writeInt(energy);
	
	}


}
