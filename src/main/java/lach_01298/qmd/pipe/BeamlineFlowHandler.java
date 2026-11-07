package lach_01298.qmd.pipe;

import lach_01298.qmd.particle.ParticleStorageBeamline;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class BeamlineFlowHandler
{
	private static final Map<World, Set<ParticleStorageBeamline>> STORAGE_MAP = new WeakHashMap<>();

	public static void register(World world, ParticleStorageBeamline storage)
	{
		if (world.isRemote)
		{
			return;
		}
		
		STORAGE_MAP.computeIfAbsent(world, key -> Collections.newSetFromMap(new WeakHashMap<>())).add(storage);
	}

	public static void unregister(World world, ParticleStorageBeamline storage)
	{
		if (world.isRemote)
		{
			return;
		}
		
		Set<ParticleStorageBeamline> storages = STORAGE_MAP.get(world);
		if (storages != null)
		{
			storages.remove(storage);
			if (storages.isEmpty())
			{
				STORAGE_MAP.remove(world);
			}
		}
		storage.clearFlow();
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onWorldTickStart(TickEvent.WorldTickEvent event)
	{
		if (event.world.isRemote || event.phase != TickEvent.Phase.START)
		{
			return;
		}
		
		Set<ParticleStorageBeamline> storages = STORAGE_MAP.get(event.world);
		if (storages != null)
		{
			storages.forEach(ParticleStorageBeamline::onBeginFlowTick);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onWorldTickEnd(TickEvent.WorldTickEvent event)
	{
		if (event.world.isRemote || event.phase != TickEvent.Phase.END)
		{
			return;
		}
		
		Set<ParticleStorageBeamline> storages = STORAGE_MAP.get(event.world);
		if (storages != null)
		{
			storages.forEach(ParticleStorageBeamline::onEndFlowTick);
		}
	}

	@SubscribeEvent
	public void onWorldUnload(WorldEvent.Unload event)
	{
		if (event.getWorld().isRemote)
		{
			return;
		}
		
		Set<ParticleStorageBeamline> storages = STORAGE_MAP.remove(event.getWorld());
		if (storages != null)
		{
			storages.forEach(ParticleStorageBeamline::clearFlow);
		}
	}
}
