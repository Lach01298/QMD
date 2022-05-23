package lach_01298.qmd.multiblock;

import lach_01298.qmd.network.QMDPacketHandler;
import nc.multiblock.IPacketMultiblock;
import nc.multiblock.Multiblock;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.network.multiblock.MultiblockUpdatePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

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
			QMDPacketHandler.instance.sendTo(packet, (EntityPlayerMP) player);
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
		QMDPacketHandler.instance.sendTo(packet, (EntityPlayerMP) player);
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
		QMDPacketHandler.instance.sendToAll(packet);
	}
}
