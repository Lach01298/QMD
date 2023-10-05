package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.network.QMDTileUpdatePacket;
import nc.network.tile.TileUpdatePacket;
import nc.tile.ITilePacket;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import net.minecraft.util.math.BlockPos;

public class AcceleratorSourceUpdatePacket extends QMDTileUpdatePacket
{
	public List<TankInfo> tanksInfo;
	
	public AcceleratorSourceUpdatePacket()
	{
		super();
	}

	public AcceleratorSourceUpdatePacket(BlockPos pos, List<Tank> tanks)
	{
		super(pos);
		tanksInfo = TankInfo.infoList(tanks);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		byte numberOfTanks = buf.readByte();
		tanksInfo = TankInfo.readBuf(buf, numberOfTanks);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeByte(tanksInfo.size());
		for (TankInfo info : tanksInfo)
		{
			info.writeBuf(buf);
		}
	}

	public static class Handler extends TileUpdatePacket.Handler<AcceleratorSourceUpdatePacket, ITilePacket<AcceleratorSourceUpdatePacket>>
	{

		@Override
		protected void onTileUpdatePacket(AcceleratorSourceUpdatePacket message, ITilePacket<AcceleratorSourceUpdatePacket> processor)
		{
			processor.onTileUpdatePacket(message);
		}
	}

}
