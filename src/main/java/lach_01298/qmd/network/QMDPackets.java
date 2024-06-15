package lach_01298.qmd.network;

import lach_01298.qmd.machine.network.*;
import lach_01298.qmd.multiblock.network.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class QMDPackets {
	
	public static SimpleNetworkWrapper wrapper = null;

	public QMDPackets()
	{
	}

	public static void registerMessages(String channelName)
	{
		wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
		registerMessages();
	}
	
	
	public static void registerMessages()
	{
		// SERVER
		wrapper.registerMessage(QMDOpenSideConfigGuiPacket.Handler.class, QMDOpenSideConfigGuiPacket.class, nextID(), Side.SERVER);
		wrapper.registerMessage(QMDOpenTileGuiPacket.Handler.class, QMDOpenTileGuiPacket.class, nextID(), Side.SERVER);
		wrapper.registerMessage(CreativeParticleSourceGuiPacket.Handler.class, CreativeParticleSourceGuiPacket.class, nextID(), Side.SERVER);
		wrapper.registerMessage(QMDClearTankPacket.Handler.class, QMDClearTankPacket.class, nextID(), Side.SERVER);
		
		// CLIENT
		
		wrapper.registerMessage(QMDTileUpdatePacket.Handler.class, QMDTileUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(LinearAcceleratorUpdatePacket.Handler.class, LinearAcceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(RingAcceleratorUpdatePacket.Handler.class, RingAcceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(BeamDiverterUpdatePacket.Handler.class, BeamDiverterUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(BeamSplitterUpdatePacket.Handler.class, BeamSplitterUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(DeceleratorUpdatePacket.Handler.class, DeceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(MassSpectrometerUpdatePacket.Handler.class, MassSpectrometerUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(TargetChamberUpdatePacket.Handler.class, TargetChamberUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(DecayChamberUpdatePacket.Handler.class, DecayChamberUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(BeamDumpUpdatePacket.Handler.class, BeamDumpUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(NeutralContainmentUpdatePacket.Handler.class, NeutralContainmentUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(ContainmentRenderPacket.Handler.class, ContainmentRenderPacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(CollisionChamberUpdatePacket.Handler.class, CollisionChamberUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(CreativeParticleSourceUpdatePacket.Handler.class, CreativeParticleSourceUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(BeamProjectileUpdatePacket.Handler.class, BeamProjectileUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(LeptonBeamUpdatePacket.Handler.class, LeptonBeamUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(AntimatterProjectileUpdatePacket.Handler.class, AntimatterProjectileUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(NucleosynthesisChamberUpdatePacket.Handler.class, NucleosynthesisChamberUpdatePacket.class, nextID(), Side.CLIENT);
		wrapper.registerMessage(AcceleratorSourceUpdatePacket.Handler.class, AcceleratorSourceUpdatePacket.class, nextID(), Side.CLIENT);
	}


	private static int packetId = 0;
	
	public static int nextID()
	{
		return packetId++;
	}
	
}
