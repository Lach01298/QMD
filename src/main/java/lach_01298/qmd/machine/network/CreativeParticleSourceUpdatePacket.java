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
		beams = new ArrayList<ParticleStorageSource>();
	}

	public CreativeParticleSourceUpdatePacket(BlockPos pos, List<ParticleStorageSource> tanks)
	{
		this.pos = pos;
		this.beams = tanks;

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		int size = buf.readInt();
		for (int i = 0; i < size; i++)
		{
			beams.add((ParticleStorageSource) ByteUtil.readBufBeam(buf));
		}

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());

		buf.writeInt(beams.size());
		for (ParticleStorageSource beam : beams)
		{
			ByteUtil.writeBufBeam(beam, buf);
		}

	}

	public static class Handler extends TileUpdatePacket.Handler<CreativeParticleSourceUpdatePacket, ITileGui<CreativeParticleSourceUpdatePacket>>
	{

		@Override
		protected void onTileUpdatePacket(CreativeParticleSourceUpdatePacket message, ITileGui<CreativeParticleSourceUpdatePacket> processor)
		{
			processor.onTileUpdatePacket(message);
		}
	}

}
