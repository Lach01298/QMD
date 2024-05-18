package lach_01298.qmd.pipe;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.util.ByteUtil;
import nc.network.multiblock.MultiblockUpdatePacket;


public class PipeBeamlineUpdatePacket extends PipeUpdatePacket
{

	public ParticleStorageAccelerator storage;
	
	
	public PipeBeamlineUpdatePacket()
	{
	
	}
	
	public PipeBeamlineUpdatePacket(ParticleStorageAccelerator storage)
	{
		this.storage = storage;
		
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		storage = (ParticleStorageAccelerator) ByteUtil.readBufParticleStorage(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteUtil.writeBufParticleStorage(storage, buf);
	}

	
public static class Handler extends MultiblockUpdatePacket.Handler<Pipe, IPipePart, PipeUpdatePacket, TileBeamline, PipeBeamlineUpdatePacket>
{
		
		public Handler()
		{
			super(TileBeamline.class);
		}

		@Override
		protected void onPacket(PipeBeamlineUpdatePacket message, Pipe multiblock) {
			multiblock.onMultiblockUpdatePacket(message);
		}
	}
	
	
	
}
