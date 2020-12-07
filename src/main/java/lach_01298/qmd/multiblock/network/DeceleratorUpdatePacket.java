package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.TileDeceleratorController;
import lach_01298.qmd.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class DeceleratorUpdatePacket extends AcceleratorUpdatePacket
{
	

	public DeceleratorUpdatePacket()
	{
		super();
	}

	public DeceleratorUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating,
			double maxCoolantIn, double maxCoolantOut,int maxOperatingTemp, int requiredEnergy, double efficiency, int acceleratingVoltage,
			int RFCavityNumber, int quadrupoleNumber, double quadrupoleStrength, int dipoleNumber, double dipoleStrength,
			int errorCode, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks,
			List<ParticleStorageAccelerator> beams)
	{
		super(pos, isAcceleratorOn, cooling, rawHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, efficiency,
				acceleratingVoltage, RFCavityNumber, quadrupoleNumber, quadrupoleStrength, dipoleNumber, dipoleStrength, errorCode ,heatBuffer, energyStorage,
				tanks, beams);
	
	
	}

	@Override
	public void readMessage(ByteBuf buf)
	{
		super.readMessage(buf);
		


	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);

	}

	public static class Handler extends MultiblockUpdatePacket.Handler<DeceleratorUpdatePacket, Accelerator, TileDeceleratorController>
	{

		public Handler()
		{
			super(TileDeceleratorController.class);
		}
	}
	
	
	
}