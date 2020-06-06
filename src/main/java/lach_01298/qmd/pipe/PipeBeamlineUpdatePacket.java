package lach_01298.qmd.pipe;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.ByteUtil;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.network.MultiblockUpdatePacket;

public class PipeBeamlineUpdatePacket extends PipeUpdatePacket
{

	public ParticleStorageAccelerator storage;
	
	
	public PipeBeamlineUpdatePacket()
	{
		messageValid = false;
	}
	
	public PipeBeamlineUpdatePacket(ParticleStorageAccelerator storage)
	{
		this.storage = storage;
		messageValid = true;
	}
	
	
	@Override
	public void readMessage(ByteBuf buf)
	{
		storage = (ParticleStorageAccelerator) ByteUtil.readBufParticleStorage(buf);
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		ByteUtil.writeBufParticleStorage(storage, buf);
	}

	
public static class Handler extends MultiblockUpdatePacket.Handler<PipeBeamlineUpdatePacket, Pipe, TileBeamline> 
{
		
		public Handler() 
		{
			super(TileBeamline.class);
		}
	}
	
	
	
}
