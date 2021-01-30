package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileCollisionChamberController;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import net.minecraft.util.math.BlockPos;

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
	public void readMessage(ByteBuf buf)
	{
		super.readMessage(buf);
	
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		super.writeMessage(buf);
			
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<CollisionChamberUpdatePacket, ParticleChamber, TileCollisionChamberController> 
	{
		
		public Handler() 
		{
			super(TileCollisionChamberController.class);
		}
	}
}