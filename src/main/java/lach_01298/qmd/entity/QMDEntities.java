package lach_01298.qmd.entity;

import static nc.config.NCConfig.entity_tracking_range;

import lach_01298.qmd.QMD;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class QMDEntities
{

	public static void register() 
	{
		registerEntity("gamma_flash", EntityGammaFlash.class, 0);
	}
	
	private static void registerEntity(String name, Class<? extends Entity> clazz, int entityId) {
		EntityRegistry.registerModEntity(new ResourceLocation(QMD.MOD_ID, name), clazz, QMD.MOD_ID + "." + name, entityId, QMD.instance, 0, 1, true);
	}
	
	
	
	
}
