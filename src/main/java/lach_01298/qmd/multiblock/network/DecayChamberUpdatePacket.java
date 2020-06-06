package lach_01298.qmd.multiblock.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.ByteUtil;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileDecayChamberController;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import net.minecraft.util.math.BlockPos;

public class DecayChamberUpdatePacket extends ParticleChamberUpdatePacket
{
	public List<ParticleStorageAccelerator> beams;
	public long particleWorkDone, recipeParticleWork;
	
	public DecayChamberUpdatePacket() 
	{
		super();
		beams = new ArrayList<ParticleStorageAccelerator>();
	}

	public DecayChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
			EnergyStorage energyStorage, List<ParticleStorageAccelerator> beams, long particleCount, long particleRecipeCount)
	{
		super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage);
		this.beams = beams;
		this.particleWorkDone =particleCount;
		this.recipeParticleWork=particleRecipeCount;
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

		particleWorkDone = buf.readLong();
		recipeParticleWork = buf.readLong();
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);
		
		buf.writeInt(beams.size());
		for(ParticleStorageAccelerator beam : beams)
		{
			ByteUtil.writeBufBeam(beam, buf);
		}
		buf.writeLong(particleWorkDone);
		buf.writeLong(recipeParticleWork);
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<DecayChamberUpdatePacket, ParticleChamber, TileDecayChamberController> 
	{
		
		public Handler() 
		{
			super(TileDecayChamberController.class);
		}
	}
}