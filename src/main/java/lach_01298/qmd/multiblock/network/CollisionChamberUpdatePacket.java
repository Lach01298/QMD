package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.*;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class CollisionChamberUpdatePacket extends ParticleChamberUpdatePacket
{
	public CollisionChamberUpdatePacket()
	{
		super();
	}

	public CollisionChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
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
			MultiblockUpdatePacket.Handler<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileCollisionChamberController, CollisionChamberUpdatePacket>
	{

		public Handler()
		{
			super(TileCollisionChamberController.class);
		}
		
		@Override
		protected void onPacket(CollisionChamberUpdatePacket message, ParticleChamber multiblock) {
			multiblock.onMultiblockUpdatePacket(message);
		}
	}
}
