package lach_01298.qmd.capabilities;

import lach_01298.qmd.QMD;
import lach_01298.qmd.research.ResearchProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Capability handler
 * 
 * This class is responsible for attaching our capabilities
 */
public class CapabilityHandler
{
	public static final ResourceLocation Research_CAP = new ResourceLocation(QMD.MOD_ID, "research");

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event)
	{
		if (!(event.getObject() instanceof EntityPlayer))
			return;

		event.addCapability(Research_CAP, new ResearchProvider());
	}
}