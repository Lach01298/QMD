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
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

public class DecayChamberUpdatePacket extends ParticleChamberUpdatePacket
{
	public long particleWorkDone, recipeParticleWork;
	
	public DecayChamberUpdatePacket() 
	{
		super();
	}

	public DecayChamberUpdatePacket(BlockPos pos, boolean isAcceleratorOn, int requiredEnergy, double efficiency,
			EnergyStorage energyStorage, long particleCount, long particleRecipeCount, List<Tank> tanks, List<ParticleStorageAccelerator> beams)
	{
		super(pos, isAcceleratorOn, requiredEnergy, efficiency, energyStorage, tanks, beams);
		
		this.particleWorkDone =particleCount;
		this.recipeParticleWork=particleRecipeCount;
	}

	@Override
	public void readMessage(ByteBuf buf)
	{
		super.readMessage(buf);

		particleWorkDone = buf.readLong();
		recipeParticleWork = buf.readLong();
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);
		
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