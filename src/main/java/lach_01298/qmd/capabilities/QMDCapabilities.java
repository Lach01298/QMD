package lach_01298.qmd.capabilities;

import lach_01298.qmd.research.IResearch;
import lach_01298.qmd.research.Research;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class QMDCapabilities
{

	@CapabilityInject(IResearch.class)
	public static Capability<IResearch> Research_CAP = null;

	public static void register()
	{

		CapabilityManager.INSTANCE.register(IResearch.class, new IResearch.ResearchStorage(), Research::new);

	}

}