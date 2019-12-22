package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.ring.tile.TileRingAcceleratorController;
import lach_01298.qmd.particle.ParticleBeam;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.solid.tile.TileSolidFissionController;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.multiblock.network.SolidFissionUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class RingAcceleratorUpdatePacket extends AcceleratorUpdatePacket
{
	public int dipoleNumber;
	public double dipoleStrength;

	RingAcceleratorUpdatePacket()
	{
		super();
	}

	public RingAcceleratorUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating,
			int requiredEnergy, double efficiency, int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber,
			double quadrupoleStrength, HeatBuffer heatBuffer, EnergyStorage energyStorage, ParticleBeam beam, int dipoleNumber, double dipoleStrength)
	{
		super(pos, isAcceleratorOn, cooling, rawHeating, requiredEnergy, efficiency, acceleratingVoltage,
				RFCavityNumber, quadrupoleNumber, quadrupoleStrength, heatBuffer, energyStorage, beam);
		this.dipoleNumber = dipoleNumber;
		this.dipoleStrength = dipoleStrength;
	}
	
	@Override
	public void readMessage(ByteBuf buf) {
		super.readMessage(buf);
		dipoleNumber = buf.readInt();
		dipoleStrength = buf.readDouble();
	
	}
	
	@Override
	public void writeMessage(ByteBuf buf) {
		super.writeMessage(buf);
		buf.writeInt(dipoleNumber);
		buf.writeDouble(dipoleStrength);
	
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<RingAcceleratorUpdatePacket, Accelerator, TileRingAcceleratorController> {
		
		public Handler() 
		{
			super(TileRingAcceleratorController.class);
		}
	}
	
	
	
	
	
	
}