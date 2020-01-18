package lach_01298.qmd.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import nc.network.tile.ProcessorUpdatePacket;
import nc.network.tile.TileUpdatePacket;
import nc.tile.ITileGui;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import net.minecraft.util.math.BlockPos;

public class QMDTileUpdatePacket extends TileUpdatePacket
{

	public QMDTileUpdatePacket() 
	{
		messageValid = false;
	}
	
	
	public QMDTileUpdatePacket(BlockPos pos) 
	{
		this.pos = pos;
	
		
		messageValid = true;
	}
	
	
	
	
	@Override
	public void readMessage(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void writeMessage(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	
	public static class Handler extends TileUpdatePacket.Handler<QMDTileUpdatePacket, ITileGui> {
		
		@Override
		protected void onPacket(QMDTileUpdatePacket message, ITileGui processor) {
			processor.onGuiPacket(message);
		}
	}
	
	
	
}
