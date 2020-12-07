package lach_01298.qmd.multiblock.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import nc.multiblock.network.MultiblockUpdatePacket;
import net.minecraft.util.math.BlockPos;

public class ContainmentFormPacket extends MultiblockUpdatePacket
{
	
	public ContainmentFormPacket() {
		messageValid = false;
	}
	
	public ContainmentFormPacket(BlockPos pos) {
		this.pos = pos;
		
		
		messageValid = true;
	}
	
	@Override
	public void readMessage(ByteBuf buf) 
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		
		
	}
	
	@Override
	public void writeMessage(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		
	}
	
	public static class Handler extends MultiblockUpdatePacket.Handler<ContainmentFormPacket, Containment, TileNeutralContainmentController> {
		
		public Handler() {
			super(TileNeutralContainmentController.class);
		}
		
		@Override
		protected void onPacket(ContainmentFormPacket message, Containment multiblock) {
			multiblock.onFormPacket(message);
		}
	}
}
