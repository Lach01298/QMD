package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import nc.multiblock.network.MultiblockUpdatePacket;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.Tank.TankInfo;
import net.minecraft.util.math.BlockPos;

public class ContainmentRenderPacket extends MultiblockUpdatePacket {
	
	public boolean isEmpty;
	public byte numberOfTanks;
	public List<TankInfo> tanksInfo;
	
	public ContainmentRenderPacket() {
		messageValid = false;
	}
	
	public ContainmentRenderPacket(BlockPos pos, boolean isEmpty, List<Tank> tanks) 
	{
		this.pos = pos;
		this.isEmpty = isEmpty;
		numberOfTanks = (byte) tanks.size();
		tanksInfo = TankInfo.infoList(tanks);
		
		messageValid = true;
	}
	
	@Override
	public void readMessage(ByteBuf buf) {
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		isEmpty = buf.readBoolean();
		numberOfTanks = buf.readByte();
		tanksInfo = TankInfo.readBuf(buf, numberOfTanks);
	}
	
	@Override
	public void writeMessage(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeBoolean(isEmpty);
		buf.writeByte(numberOfTanks);
		for (TankInfo info : tanksInfo) info.writeBuf(buf);
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<ContainmentRenderPacket, Containment, TileNeutralContainmentController> {
		
		public Handler() {
			super(TileNeutralContainmentController.class);
		}
		
		@Override
		protected void onPacket(ContainmentRenderPacket message, Containment multiblock) {
			multiblock.onRenderPacket(message);
		}
	}
}
