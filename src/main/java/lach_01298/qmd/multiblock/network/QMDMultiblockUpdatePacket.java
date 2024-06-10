package lach_01298.qmd.multiblock.network;

import lach_01298.qmd.network.QMDPackets;
import nc.network.multiblock.MultiblockUpdatePacket;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public abstract class QMDMultiblockUpdatePacket extends MultiblockUpdatePacket {
	
	public QMDMultiblockUpdatePacket() {
		super();
	}
	
	public QMDMultiblockUpdatePacket(BlockPos pos) {
		this.pos = pos;
	}
	
	public SimpleNetworkWrapper getWrapper() {
		return QMDPackets.wrapper;
	}
}
