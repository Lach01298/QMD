package lach_01298.qmd.machine.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageSource;
import lach_01298.qmd.util.ByteUtil;
import nc.network.tile.TileUpdatePacket;
import nc.tile.ITileGui;
import net.minecraft.util.math.BlockPos;

public class CreativeParticleSourceUpdatePacket extends TileUpdatePacket
{

	public List<ParticleStorageSource> beams;
	
	
	public CreativeParticleSourceUpdatePacket() 
	{
		messageValid = false;
		beams = new ArrayList<ParticleStorageSource>();
	}
	
	
	public CreativeParticleSourceUpdatePacket(BlockPos pos, List<ParticleStorageSource> tanks) 
	{
		this.pos = pos;
		this.beams = tanks;
		
		messageValid = true;
	}
	
	
	
	
	@Override
	public void readMessage(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		
		int size = buf.readInt();
		for (int i = 0; i < size; i++)
		{
			beams.add((ParticleStorageSource) ByteUtil.readBufBeam(buf));
		}

	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		
		buf.writeInt(beams.size());
		for(ParticleStorageSource beam : beams)
		{
			ByteUtil.writeBufBeam(beam, buf);
		}

	}
	
	
public static class Handler extends TileUpdatePacket.Handler<CreativeParticleSourceUpdatePacket, ITileGui> {
		
		@Override
		protected void onPacket(CreativeParticleSourceUpdatePacket message, ITileGui processor) {
			processor.onGuiPacket(message);
		}
	}

}
