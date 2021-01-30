package lach_01298.qmd.multiblock.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.util.ByteUtil;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

public class TargetChamberUpdatePacket extends ParticleChamberUpdatePacket
{
	public long particleCount, recipeParticleCount;
	
	public TargetChamberUpdatePacket() 
	{
		super();
		beams = new ArrayList<ParticleStorageAccelerator>();
	}

	public TargetChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
			EnergyStorage energyStorage, long particleCount, long particleRecipeCount, List<Tank> tanks, List<ParticleStorageAccelerator> beams)
	{
		super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
		this.particleCount =particleCount;
		this.recipeParticleCount=particleRecipeCount;
	}

	@Override
	public void readMessage(ByteBuf buf)
	{
		super.readMessage(buf);

		particleCount = buf.readLong();
		recipeParticleCount = buf.readLong();
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);
		
		buf.writeLong(particleCount);
		buf.writeLong(recipeParticleCount);
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<TargetChamberUpdatePacket, ParticleChamber, TileTargetChamberController> {
		
		public Handler() 
		{
			super(TileTargetChamberController.class);
		}
	}
}
