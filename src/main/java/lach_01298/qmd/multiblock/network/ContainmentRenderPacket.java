package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.*;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.TileContainerInfo;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class ContainmentRenderPacket extends QMDMultiblockUpdatePacket
{

	public boolean isEmpty;
	public List<TankInfo> tanksInfo;

	public ContainmentRenderPacket()
	{

	}

	public ContainmentRenderPacket(BlockPos pos, boolean isEmpty, List<Tank> tanks)
	{
		this.pos = pos;
		this.isEmpty = isEmpty;
		tanksInfo = TankInfo.getInfoList(tanks);

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isEmpty = buf.readBoolean();
		tanksInfo = readTankInfos(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBoolean(isEmpty);
		writeTankInfos(buf, tanksInfo);
	}

	public static class Handler extends
			MultiblockUpdatePacket.Handler<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket, TileExoticContainmentController, TileContainerInfo<TileExoticContainmentController>, ContainmentRenderPacket>
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
