package lach_01298.qmd.network;

import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import nc.network.PacketHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class QMDPacketHandler {
	

	
	
	public static void registerMessages()
	{
		// SERVER
		
		// CLIENT
		PacketHandler.instance.registerMessage(LinearAcceleratorUpdatePacket.Handler.class, LinearAcceleratorUpdatePacket.class, PacketHandler.nextID(), Side.CLIENT);
		PacketHandler.instance.registerMessage(RingAcceleratorUpdatePacket.Handler.class, RingAcceleratorUpdatePacket.class, PacketHandler.nextID(), Side.CLIENT);
	}


}
