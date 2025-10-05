package lach_01298.qmd.render.tile;

import lach_01298.qmd.liquefier.LiquefierLogic;
import lach_01298.qmd.liquefier.tile.TileLiquefierController;
import nc.multiblock.hx.HeatExchanger;
import nc.render.IWorldRender;
import nc.tile.internal.fluid.Tank;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLiquefier extends TileEntitySpecialRenderer<TileLiquefierController> implements IWorldRender
{
	@Override
	public boolean isGlobalRenderer(TileLiquefierController controller) {
		return controller.isRenderer() && controller.isMultiblockAssembled();
	}

	@Override
	public void render(TileLiquefierController controller, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha) {
		if (!controller.isRenderer() || !controller.isMultiblockAssembled()) {
			return;
		}

		HeatExchanger hx = controller.getMultiblock();
		if (hx == null) {
			return;
		}

		if(hx.getLogic() instanceof LiquefierLogic)
		{
			LiquefierLogic liquefier = (LiquefierLogic) hx.getLogic();

			GlStateManager.pushMatrix();

			GlStateManager.color(1F, 1F, 1F, 1F);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 240F);

			GlStateManager.enableCull();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

			BlockPos posOffset = hx.getExtremeInteriorCoord(false, false, false).subtract(controller.getPos());
			GlStateManager.translate(posX + posOffset.getX(), posY + posOffset.getY(), posZ + posOffset.getZ());

			int xSize = hx.getInteriorLengthX(), ySize = hx.getInteriorLengthY()-2, zSize = hx.getInteriorLengthZ();

			GlStateManager.pushMatrix();
			GlStateManager.translate(-PIXEL, -PIXEL+2D, -PIXEL);
			for (Tank tank : hx.shellTanks) {
				IWorldRender.renderFluid(tank, xSize + 2D * PIXEL, ySize + 2D * PIXEL, zSize + 2D * PIXEL, EnumFacing.UP, x -> true);
			}
			GlStateManager.popMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.translate(-PIXEL, -PIXEL, -PIXEL);
			IWorldRender.renderFluid(liquefier.tanks.get(1).getFluid(),liquefier.tanks.get(1).getCapacity(), xSize + 2D * PIXEL, 1D + 2D * PIXEL, zSize + 2D * PIXEL, EnumFacing.UP,x -> x.getFluid().isGaseous(x), w -> w+liquefier.tanks.get(1).getCapacity()*0.2);
			GlStateManager.popMatrix();



			GlStateManager.disableBlend();
			GlStateManager.disableCull();

			GlStateManager.popMatrix();
		}
	}
}
