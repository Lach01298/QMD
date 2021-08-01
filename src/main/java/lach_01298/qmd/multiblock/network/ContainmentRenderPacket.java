package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.TileExoticContainmentController;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import net.minecraft.util.math.BlockPos;

public class ContainmentRenderPacket extends MultiblockUpdatePacket
{

	public boolean isEmpty;
	public byte numberOfTanks;
	public List<TankInfo> tanksInfo;

	public ContainmentRenderPacket()
	{

	}

	public ContainmentRenderPacket(BlockPos pos, boolean isEmpty, List<Tank> tanks)
	{
		this.pos = pos;
		this.isEmpty = isEmpty;
		numberOfTanks = (byte) tanks.size();
		tanksInfo = TankInfo.infoList(tanks);

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isEmpty = buf.readBoolean();
		numberOfTanks = buf.readByte();
		tanksInfo = TankInfo.readBuf(buf, numberOfTanks);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBoolean(isEmpty);
		buf.writeByte(numberOfTanks);
		for (TankInfo info : tanksInfo)
			info.writeBuf(buf);
	}

	public static class Handler extends
			MultiblockUpdatePacket.Handler<ContainmentRenderPacket, VacuumChamber, TileExoticContainmentController>
	{

		public Handler()
		{
			super(TileExoticContainmentController.class);
		}

		@Override
		protected void onPacket(ContainmentRenderPacket message, VacuumChamber multiblock)
		{
			multiblock.onRenderPacket(message);
		}
	}
}
