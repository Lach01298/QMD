package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

public class DecayChamberUpdatePacket extends ParticleChamberUpdatePacket
{
	public DecayChamberUpdatePacket()
	{
		super();
	}

	public DecayChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
			EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams)
	{
		super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

	}

	public static class Handler extends
			MultiblockUpdatePacket.Handler<DecayChamberUpdatePacket, ParticleChamber, TileDecayChamberController>
	{

		public Handler()
		{
			super(TileDecayChamberController.class);
		}
	}
}