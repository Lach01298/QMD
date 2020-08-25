package lach_01298.qmd.render;

import lach_01298.qmd.containment.tile.TileContainmentLaser;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.entity.EntityGammaFlash;
import lach_01298.qmd.render.entity.RenderGammaFlash;
import lach_01298.qmd.render.tile.RenderContainmentLaser;
import lach_01298.qmd.render.tile.RenderContainmentMaterial;
import nc.multiblock.turbine.tile.TileTurbineController;
import nc.render.tile.RenderTurbineRotor;
import nc.util.NCUtil;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class QMDRenderHandler
{
	public static void init() 
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileNeutralContainmentController.class, new RenderContainmentMaterial());
		ClientRegistry.bindTileEntitySpecialRenderer(TileContainmentLaser.class, new RenderContainmentLaser());
		
		registerEntityRender(EntityGammaFlash.class, RenderGammaFlash.class);
	}

	private static <E extends Entity, R extends Render<E>> void registerEntityRender(Class<E> entityClass, Class<R> renderClass) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, manager -> {
			R render = null;
			try {
				render = NCUtil.newInstance(renderClass, manager);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return render;
		});
	}



}
