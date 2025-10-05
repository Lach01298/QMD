package lach_01298.qmd.render;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.entity.*;
import lach_01298.qmd.item.*;
import lach_01298.qmd.liquefier.tile.TileLiquefierController;
import lach_01298.qmd.render.entity.*;
import lach_01298.qmd.render.tile.*;
import lach_01298.qmd.vacuumChamber.tile.*;
import nc.util.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.*;

public class QMDRenderHandler
{
	public static void init()
	{
		QMDBlocks.registerRenders();
		QMDItems.registerRenders();
		QMDArmour.registerRenders();
		
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileExoticContainmentController.class, new RenderContainmentMaterial());
		ClientRegistry.bindTileEntitySpecialRenderer(TileLiquefierController.class, new RenderLiquefier());
		
		registerEntityRender(EntityGammaFlash.class, RenderGammaFlash.class);
		registerEntityRender(EntityLeptonBeam.class, RenderLeptonBeam.class);
		registerEntityRender(EntityGluonBeam.class, RenderGluonBeam.class);
		registerEntityRender(EntityAntimatterProjectile.class, RenderAntimatterProjectile.class);
	}

	private static <E extends Entity, R extends Render<E>> void registerEntityRender(Class<E> entityClass, Class<R> renderClass) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, manager -> {
			R render = null;
			try {
				render = ReflectionHelper.newInstance(renderClass, manager);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return render;
		});
	}



}
