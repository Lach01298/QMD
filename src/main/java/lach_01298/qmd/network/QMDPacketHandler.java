package lach_01298.qmd.network;

import lach_01298.qmd.machine.network.QMDOpenSideConfigGuiPacket;
import lach_01298.qmd.machine.network.QMDOpenTileGuiPacket;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.BeamDiverterUpdatePacket;
import lach_01298.qmd.multiblock.network.DecayChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.TargetChamberUpdatePacket;
import nc.network.PacketHandler;
import nc.network.config.ConfigUpdatePacket;
import nc.network.gui.OpenSideConfigGuiPacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class QMDPacketHandler {
	
	public static SimpleNetworkWrapper instance = null;

	public QMDPacketHandler()
	{
	}

	public static void registerMessages(String channelName)
	{
		instance = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	
	public static void registerMessages()
	{
		// SERVER
		instance.registerMessage(QMDOpenSideConfigGuiPacket.Handler.class, QMDOpenSideConfigGuiPacket.class, nextID(), Side.SERVER);
		instance.registerMessage(QMDOpenTileGuiPacket.Handler.class, QMDOpenTileGuiPacket.class, nextID(), Side.SERVER);
		// CLIENT
		
		instance.registerMessage(LinearAcceleratorUpdatePacket.Handler.class, LinearAcceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(RingAcceleratorUpdatePacket.Handler.class, RingAcceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(BeamDiverterUpdatePacket.Handler.class, BeamDiverterUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(TargetChamberUpdatePacket.Handler.class, TargetChamberUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(DecayChamberUpdatePacket.Handler.class, DecayChamberUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(QMDTileUpdatePacket.Handler.class, QMDTileUpdatePacket.class, nextID(), Side.CLIENT);
	}


	private static int packetId = 0;
	
	public static int nextID()
	{
		return packetId++;
	}
	
}
