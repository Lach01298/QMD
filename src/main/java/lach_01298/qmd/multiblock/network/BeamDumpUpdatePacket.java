package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.particleChamber.tile.TileBeamDumpController;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

public class BeamDumpUpdatePacket extends ParticleChamberUpdatePacket
{
	public long particleWorkDone, recipeParticleWork;

	public BeamDumpUpdatePacket()
	{
		super();
	}

	public BeamDumpUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
			EnergyStorage energyStorage, long particleCount, long particleRecipeCount, List<Tank> tanks,
			List<ParticleStorageAccelerator> beams)
	{
		super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);

		this.particleWorkDone = particleCount;
		this.recipeParticleWork = particleRecipeCount;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);

		particleWorkDone = buf.readLong();
		recipeParticleWork = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);

		buf.writeLong(particleWorkDone);
		buf.writeLong(recipeParticleWork);
	}

	public static class Handler
			extends MultiblockUpdatePacket.Handler<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket, TileBeamDumpController, BeamDumpUpdatePacket>
	{

		public Handler()
		{
			super(TileBeamDumpController.class);
		}
		
		@Override
		protected void onPacket(BeamDumpUpdatePacket message, ParticleChamber multiblock) {
			multiblock.onMultiblockUpdatePacket(message);
		}
	}
}