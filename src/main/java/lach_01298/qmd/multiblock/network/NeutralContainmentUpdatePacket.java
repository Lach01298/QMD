package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class NeutralContainmentUpdatePacket extends ContainmentUpdatePacket
{
	public long particle1WorkDone, particle2WorkDone, recipeParticle1Work, recipeParticle2Work;
	
	

	public NeutralContainmentUpdatePacket()
	{
		super();
	}

	public NeutralContainmentUpdatePacket(BlockPos pos, boolean isContainmentOn, long heating,
			double maxCoolantIn, double maxCoolantOut,int maxOperatingTemp, int requiredEnergy, HeatBuffer heatBuffer, EnergyStorage energyStorage, List<Tank> tanks,
			List<ParticleStorageAccelerator> beams, long particle1WorkDone, long particle2WorkDone, long recipeParticle1Work,  long recipeParticle2Work)
	{
		super(pos, isContainmentOn, heating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy ,heatBuffer, energyStorage,tanks, beams);
		this.particle1WorkDone =particle1WorkDone;
		this.particle2WorkDone =particle2WorkDone;
		this.recipeParticle1Work=recipeParticle1Work;
		this.recipeParticle2Work=recipeParticle2Work;
	
	}

	@Override
	public void readMessage(ByteBuf buf)
	{
		super.readMessage(buf);
		this.particle1WorkDone =buf.readLong();
		this.particle2WorkDone =buf.readLong();
		this.recipeParticle1Work=buf.readLong();
		this.recipeParticle2Work=buf.readLong();


	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);
		buf.writeLong(particle1WorkDone);
		buf.writeLong(particle2WorkDone);
		buf.writeLong(recipeParticle1Work);
		buf.writeLong(recipeParticle2Work);
	}

	public static class Handler extends MultiblockUpdatePacket.Handler<NeutralContainmentUpdatePacket, Containment, TileNeutralContainmentController>
	{

		public Handler()
		{
			super(TileNeutralContainmentController.class);
		}
	}
	
	
	
}