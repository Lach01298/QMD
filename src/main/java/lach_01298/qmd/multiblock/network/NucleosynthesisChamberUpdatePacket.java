package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.util.ByteUtil;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.*;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class NucleosynthesisChamberUpdatePacket extends VacuumChamberUpdatePacket
{
	public long particleWorkDone, recipeParticleWork;
	public double casingHeating;
	public long casingCooling;
	public int maxCasingCoolantIn, maxCasingCoolantOut;
	public HeatBuffer casingHeatBuffer;
	
	public NucleosynthesisChamberUpdatePacket()
	{
		super();
	}

	public NucleosynthesisChamberUpdatePacket(BlockPos pos, boolean isContainmentOn, long heating, long currentHeating, int maxCoolantIn,
			int maxCoolantOut, int maxOperatingTemp, int requiredEnergy, HeatBuffer heatBuffer,
			EnergyStorage energyStorage, List<Tank> tanks, List<ParticleStorageAccelerator> beams,
			long particleWorkDone, long recipeParticleWork, double casingHeating, long casingCooling, int maxCasingCoolantIn, int maxCasingCoolantOut, HeatBuffer casingHeatBuffer)
	{
		super(pos, isContainmentOn, heating, currentHeating, maxCoolantIn, maxCoolantOut, maxOperatingTemp, requiredEnergy, heatBuffer,
				energyStorage, tanks, beams);
		this.particleWorkDone = particleWorkDone;
		this.recipeParticleWork = recipeParticleWork;
		this.casingHeating = casingHeating;
		this.casingCooling = casingCooling;
		this.maxCasingCoolantIn = maxCasingCoolantIn;
		this.maxCasingCoolantOut = maxCasingCoolantOut;
		this.casingHeatBuffer = casingHeatBuffer;
		

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		this.particleWorkDone = buf.readLong();
		this.recipeParticleWork = buf.readLong();
		this.casingHeating = buf.readDouble();
		this.casingCooling = buf.readLong();
		this.maxCasingCoolantIn = buf.readInt();
		this.maxCasingCoolantOut = buf.readInt();
		this.casingHeatBuffer = ByteUtil.readBufHeat(buf);

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeLong(particleWorkDone);
		buf.writeLong(recipeParticleWork);
		buf.writeDouble(casingHeating);
		buf.writeLong(casingCooling);
		buf.writeInt(maxCasingCoolantIn);
		buf.writeInt(maxCasingCoolantOut);
		ByteUtil.writeBufHeat(casingHeatBuffer, buf);
	}

	public static class Handler extends
			MultiblockUpdatePacket.Handler<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileNucleosynthesisChamberController, TileContainerInfo<TileNucleosynthesisChamberController>, NucleosynthesisChamberUpdatePacket>
	{

		public Handler()
		{
			super(TileNucleosynthesisChamberController.class);
		}
		
		@Override
		protected void onPacket(NucleosynthesisChamberUpdatePacket message, VacuumChamber multiblock) {
			multiblock.onMultiblockUpdatePacket(message);
		}
	}

}
