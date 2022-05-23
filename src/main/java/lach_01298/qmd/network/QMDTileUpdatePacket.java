package lach_01298.qmd.network;

import io.netty.buffer.ByteBuf;
import nc.network.tile.TileUpdatePacket;
import nc.tile.ITilePacket;
import net.minecraft.util.math.BlockPos;

public class QMDTileUpdatePacket extends TileUpdatePacket
{

	public QMDTileUpdatePacket()
	{

	}

	public QMDTileUpdatePacket(BlockPos pos)
	{
		this.pos = pos;

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	public static class Handler extends TileUpdatePacket.Handler<QMDTileUpdatePacket, ITilePacket<QMDTileUpdatePacket>>
	{

		@Override
		protected void onTileUpdatePacket(QMDTileUpdatePacket message, ITilePacket<QMDTileUpdatePacket> processor)
		{
			processor.onTileUpdatePacket(message);
		}
	}

}
