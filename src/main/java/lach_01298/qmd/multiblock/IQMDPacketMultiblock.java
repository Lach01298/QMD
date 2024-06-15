package lach_01298.qmd.multiblock;

import lach_01298.qmd.network.QMDPackets;
import nc.multiblock.*;
import nc.network.multiblock.MultiblockUpdatePacket;
import nc.tile.multiblock.ITileMultiblockPart;
import net.minecraft.entity.player.*;

public interface IQMDPacketMultiblock<MULTIBLOCK extends Multiblock<MULTIBLOCK, T>, T extends ITileMultiblockPart<MULTIBLOCK, T>, PACKET extends MultiblockUpdatePacket> extends IPacketMultiblock<MULTIBLOCK, T, PACKET>
{

	public default void sendMultiblockUpdatePacketToListeners()
	{
		if (getWorld().isRemote) {
			return;
		}
		PACKET packet = getMultiblockUpdatePacket();
		if (packet == null)
		{
			return;
		}
		for (EntityPlayer player : getMultiblockUpdatePacketListeners())
		{
			QMDPackets.wrapper.sendTo(packet, (EntityPlayerMP) player);
		}
	}

	public default void sendMultiblockUpdatePacketToPlayer(EntityPlayer player)
	{
		if (getWorld().isRemote) {
			return;
		}
		PACKET packet = getMultiblockUpdatePacket();
		if (packet == null)
		{
			return;
		}
		QMDPackets.wrapper.sendTo(packet, (EntityPlayerMP) player);
	}
	
	public default void sendMultiblockUpdatePacketToAll()
	{
		if (getWorld().isRemote)
		{
			return;
		}
		PACKET packet = getMultiblockUpdatePacket();
		if (packet == null)
		{
			return;
		}
		QMDPackets.wrapper.sendToAll(packet);
	}
}
