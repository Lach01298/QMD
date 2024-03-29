package lach_01298.qmd.network;

import lach_01298.qmd.machine.network.CreativeParticleSourceGuiPacket;
import lach_01298.qmd.machine.network.CreativeParticleSourceUpdatePacket;
import lach_01298.qmd.machine.network.QMDOpenSideConfigGuiPacket;
import lach_01298.qmd.machine.network.QMDOpenTileGuiPacket;
import lach_01298.qmd.multiblock.network.AcceleratorSourceUpdatePacket;
import lach_01298.qmd.multiblock.network.BeamDiverterUpdatePacket;
import lach_01298.qmd.multiblock.network.BeamDumpUpdatePacket;
import lach_01298.qmd.multiblock.network.BeamSplitterUpdatePacket;
import lach_01298.qmd.multiblock.network.ClearTankPacket;
import lach_01298.qmd.multiblock.network.CollisionChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.DecayChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.DeceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.LinearAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.MassSpectrometerUpdatePacket;
import lach_01298.qmd.multiblock.network.NeutralContainmentUpdatePacket;
import lach_01298.qmd.multiblock.network.NucleosynthesisChamberUpdatePacket;
import lach_01298.qmd.multiblock.network.RingAcceleratorUpdatePacket;
import lach_01298.qmd.multiblock.network.TargetChamberUpdatePacket;
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
		instance.registerMessage(CreativeParticleSourceGuiPacket.Handler.class, CreativeParticleSourceGuiPacket.class, nextID(), Side.SERVER);
		instance.registerMessage(ClearTankPacket.Handler.class, ClearTankPacket.class, nextID(), Side.SERVER);
		
		// CLIENT
		
		instance.registerMessage(QMDTileUpdatePacket.Handler.class, QMDTileUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(LinearAcceleratorUpdatePacket.Handler.class, LinearAcceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(RingAcceleratorUpdatePacket.Handler.class, RingAcceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(BeamDiverterUpdatePacket.Handler.class, BeamDiverterUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(BeamSplitterUpdatePacket.Handler.class, BeamSplitterUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(DeceleratorUpdatePacket.Handler.class, DeceleratorUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(MassSpectrometerUpdatePacket.Handler.class, MassSpectrometerUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(TargetChamberUpdatePacket.Handler.class, TargetChamberUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(DecayChamberUpdatePacket.Handler.class, DecayChamberUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(BeamDumpUpdatePacket.Handler.class, BeamDumpUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(NeutralContainmentUpdatePacket.Handler.class, NeutralContainmentUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(ContainmentRenderPacket.Handler.class, ContainmentRenderPacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(CollisionChamberUpdatePacket.Handler.class, CollisionChamberUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(CreativeParticleSourceUpdatePacket.Handler.class, CreativeParticleSourceUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(BeamProjectileUpdatePacket.Handler.class, BeamProjectileUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(LeptonBeamUpdatePacket.Handler.class, LeptonBeamUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(AntimatterProjectileUpdatePacket.Handler.class, AntimatterProjectileUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(NucleosynthesisChamberUpdatePacket.Handler.class, NucleosynthesisChamberUpdatePacket.class, nextID(), Side.CLIENT);
		instance.registerMessage(AcceleratorSourceUpdatePacket.Handler.class, AcceleratorSourceUpdatePacket.class, nextID(), Side.CLIENT);
	}


	private static int packetId = 0;
	
	public static int nextID()
	{
		return packetId++;
	}
	
}
