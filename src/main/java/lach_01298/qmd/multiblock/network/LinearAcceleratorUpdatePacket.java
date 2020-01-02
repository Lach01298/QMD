package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.TileLinearAcceleratorController;
import lach_01298.qmd.particle.ParticleBeam;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.solid.tile.TileSolidFissionController;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.multiblock.network.SolidFissionUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class LinearAcceleratorUpdatePacket extends AcceleratorUpdatePacket
{
	public LinearAcceleratorUpdatePacket() {
		super();
	}
	
	public LinearAcceleratorUpdatePacket(BlockPos pos, boolean isAcceleratorOn, long cooling, long rawHeating, double maxCoolantIn, double maxCoolantOut,
			int requiredEnergy, double efficiency, int acceleratingVoltage, int RFCavityNumber, int quadrupoleNumber,
			double quadrupoleStrength, HeatBuffer heatBuffer, EnergyStorage energyStorage, ParticleBeam beam, List<Tank> tanks)
	{
		super(pos, isAcceleratorOn, cooling, rawHeating, maxCoolantIn, maxCoolantOut, requiredEnergy, efficiency, acceleratingVoltage,
				RFCavityNumber, quadrupoleNumber, quadrupoleStrength, heatBuffer, energyStorage, beam,tanks);

	}
	
	@Override
	public void readMessage(ByteBuf buf) {
		super.readMessage(buf);
		
	}
	
	@Override
	public void writeMessage(ByteBuf buf) {
		super.writeMessage(buf);
		
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<LinearAcceleratorUpdatePacket, Accelerator, TileLinearAcceleratorController> {
		
		public Handler() {
			super(TileLinearAcceleratorController.class);
		}
	}
}
