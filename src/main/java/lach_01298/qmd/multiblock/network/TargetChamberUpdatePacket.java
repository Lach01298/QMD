package lach_01298.qmd.multiblock.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.ByteUtil;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.TileLinearAcceleratorController;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import lach_01298.qmd.multiblock.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.particle.AcceleratorStorage;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.util.math.BlockPos;

public class TargetChamberUpdatePacket extends ParticleChamberUpdatePacket
{
	public List<AcceleratorStorage> beams;
	public int particleCount, recipeParticleCount;
	
	public TargetChamberUpdatePacket() {
		super();
		beams = new ArrayList<AcceleratorStorage>();
	}

	public TargetChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
			EnergyStorage energyStorage, List<AcceleratorStorage> beams, int particleCount, int particleRecipeCount)
	{
		super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage);
		this.beams = beams;
		this.particleCount =particleCount;
		this.recipeParticleCount=particleRecipeCount;
	}

	@Override
	public void readMessage(ByteBuf buf)
	{
		super.readMessage(buf);
		
		int size = buf.readInt();
		for (int i = 0; i < size; i++)
		{
			beams.add(ByteUtil.readBufBeam(buf));
		}

		particleCount = buf.readInt();
		recipeParticleCount = buf.readInt();
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);
		
		buf.writeInt(beams.size());
		for(AcceleratorStorage beam : beams)
		{
			ByteUtil.writeBufBeam(beam, buf);
		}
		buf.writeInt(particleCount);
		buf.writeInt(recipeParticleCount);
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<TargetChamberUpdatePacket, ParticleChamber, TileTargetChamberController> {
		
		public Handler() {
			super(TileTargetChamberController.class);
		}
	}
}
