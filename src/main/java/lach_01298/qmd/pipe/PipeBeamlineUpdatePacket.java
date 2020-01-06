package lach_01298.qmd.pipe;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.ByteUtil;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.particle.AcceleratorStorage;
import lach_01298.qmd.tile.TileBeamline;
import nc.multiblock.network.MultiblockUpdatePacket;

public class PipeBeamlineUpdatePacket extends MultiblockUpdatePacket
{

	public AcceleratorStorage storage;
	
	
	public PipeBeamlineUpdatePacket()
	{
		messageValid = false;
	}
	
	public PipeBeamlineUpdatePacket(AcceleratorStorage storage)
	{
		this.storage = storage;
		messageValid = true;
	}
	
	
	@Override
	public void readMessage(ByteBuf buf)
	{
		storage = ByteUtil.readBufBeam(buf);
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		ByteUtil.writeBufBeam(storage, buf);
	}

	
public static class Handler extends MultiblockUpdatePacket.Handler<PipeBeamlineUpdatePacket, PipeBeamline, TileBeamline> {
		
		public Handler() {
			super(TileBeamline.class);
		}
	}
	
	
	
}
