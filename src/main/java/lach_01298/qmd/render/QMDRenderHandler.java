package lach_01298.qmd.render;

import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.render.tile.RenderContainmentMaterial;
import nc.multiblock.turbine.tile.TileTurbineController;
import nc.render.tile.RenderTurbineRotor;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class QMDRenderHandler
{
	public static void init() 
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileNeutralContainmentController.class, new RenderContainmentMaterial());
	}
}
