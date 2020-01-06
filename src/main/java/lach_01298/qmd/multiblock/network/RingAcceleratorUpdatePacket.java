package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.ByteUtil;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.particle.AcceleratorStorage;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.solid.tile.TileSolidFissionController;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.multiblock.network.SolidFissionUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class RingAcceleratorUpdatePacket extends AcceleratorUpdatePacket
{
	public int dipoleNumber;
	public double dipoleStrength;
	public AcceleratorStorage beamIn;
	public AcceleratorStorage beamOut;

	public RingAcceleratorUpdatePacket()
	{
		super();
	}

	public RingAcceleratorUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating,
			double maxCoolantIn, double maxCoolantOut, int requiredEnergy, double efficiency, int acceleratingVoltage,
			int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength, HeatBuffer heatBuffer,
			EnergyStorage energyStorage, List<Tank> tanks, int dipoleNumber, double dipoleStrength,
			AcceleratorStorage beamIn, AcceleratorStorage beamOut)
	{
		super(pos, isAcceleratorOn, cooling, rawHeating, maxCoolantIn, maxCoolantOut, requiredEnergy, efficiency,
				acceleratingVoltage, RFCavityNumber, quadrupoleNumber, quadrupoleStrength, heatBuffer, energyStorage,
				tanks);
		this.dipoleNumber = dipoleNumber;
		this.dipoleStrength = dipoleStrength;
		this.beamIn = beamIn;
		this.beamOut = beamOut;
	}

	@Override
	public void readMessage(ByteBuf buf)
	{
		super.readMessage(buf);
		dipoleNumber = buf.readInt();
		dipoleStrength = buf.readDouble();
		this.beamIn = ByteUtil.readBufBeam(buf);
		this.beamOut = ByteUtil.readBufBeam(buf);

	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);
		buf.writeInt(dipoleNumber);
		buf.writeDouble(dipoleStrength);
		ByteUtil.writeBufBeam(beamIn, buf);
		ByteUtil.writeBufBeam(beamOut, buf);
	}

	public static class Handler extends
			MultiblockUpdatePacket.Handler<RingAcceleratorUpdatePacket, Accelerator, TileRingAcceleratorController>
	{

		public Handler()
		{
			super(TileRingAcceleratorController.class);
		}
	}
	
	
	
}