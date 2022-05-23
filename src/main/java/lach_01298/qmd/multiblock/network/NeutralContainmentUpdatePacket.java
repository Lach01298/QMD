package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberPart;
import lach_01298.qmd.vacuumChamber.tile.TileExoticContainmentController;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class NeutralContainmentUpdatePacket extends VacuumChamberUpdatePacket
{
	public long particle1WorkDone, particle2WorkDone, recipeParticle1Work, recipeParticle2Work;

	public NeutralContainmentUpdatePacket()
	{
		super();
	}

	public NeutralContainmentUpdatePacket(BlockPos pos, boolean isContainmentOn, long heating, long currentHeating, int maxCoolantIn,
			int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, HeatBuffer heatBuffer,
			EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams,
			long particle1WorkDone, long particle2WorkDone, long recipeParticle1Work, long recipeParticle2Work)
	{
		super(pos, isContainmentOn, heating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, heatBuffer,
				energyStorage, tanks, beams);
		this.particle1WorkDone = particle1WorkDone;
		this.particle2WorkDone = particle2WorkDone;
		this.recipeParticle1Work = recipeParticle1Work;
		this.recipeParticle2Work = recipeParticle2Work;

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.particle1WorkDone = buf.readLong();
		this.particle2WorkDone = buf.readLong();
		this.recipeParticle1Work = buf.readLong();
		this.recipeParticle2Work = buf.readLong();

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeLong(particle1WorkDone);
		buf.writeLong(particle2WorkDone);
		buf.writeLong(recipeParticle1Work);
		buf.writeLong(recipeParticle2Work);
	}

	public static class Handler extends
			MultiblockUpdatePacket.Handler<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileExoticContainmentController, NeutralContainmentUpdatePacket>
	{

		public Handler()
		{
			super(TileExoticContainmentController.class);
		}

		@Override
		protected void onPacket(NeutralContainmentUpdatePacket message, VacuumChamber multiblock)
		{
			multiblock.onMultiblockUpdatePacket(message);
		}
	}

}