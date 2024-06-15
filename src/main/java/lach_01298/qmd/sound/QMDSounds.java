package lach_01298.qmd.sound;

import it.unimi.dsi.fastutil.objects.*;
import lach_01298.qmd.QMD;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class QMDSounds
{
	public static final ObjectSet<String> TICKABLE_SOUNDS = new ObjectOpenHashSet<>();
	public static SoundEvent lepton_cannon;
	public static SoundEvent gluon_gun;
	public static SoundEvent gluon_gun_start;
	public static SoundEvent gluon_gun_stop;

	
	
	public static void init()
	{
		lepton_cannon = register("neutral.qmd.lepton_cannon");
		gluon_gun = register("neutral.qmd.gluon_gun");
		gluon_gun_start = register("neutral.qmd.gluon_gun_start");
		gluon_gun_stop = register("neutral.qmd.gluon_gun_stop");
	}
	
	
	
	
	private static SoundEvent register(String name)
	{
		return register(name, false);
	}

	private static SoundEvent register(String name, boolean tickable)
	{
		ResourceLocation location = new ResourceLocation(QMD.MOD_ID, name);
		if (tickable)
		{
			TICKABLE_SOUNDS.add(location.toString());
		}
		SoundEvent event = new SoundEvent(location);

		ForgeRegistries.SOUND_EVENTS.register(event.setRegistryName(location));
		return event;
	}
}
