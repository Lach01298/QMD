package lach_01298.qmd.render.tile;

import lach_01298.qmd.vacuumChamber.ExoticContainmentLogic;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.TileExoticContainmentController;
import nc.render.IWorldRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderContainmentMaterial extends TileEntitySpecialRenderer<TileExoticContainmentController>
{
private static final Minecraft MC = Minecraft.getMinecraft();

	@Override
	public boolean isGlobalRenderer(TileExoticContainmentController tile) {
		return tile.isRenderer && tile.isMultiblockAssembled();
	}
	
	@Override
	public void render(TileExoticContainmentController controller, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha)
	{
		if (!controller.isRenderer || !controller.isMultiblockAssembled()) return;
		VacuumChamber chamber = controller.getMultiblock();
		if (chamber == null) return;
		if (!(chamber.getLogic() instanceof ExoticContainmentLogic)) return;
		ExoticContainmentLogic logic = (ExoticContainmentLogic) chamber.getLogic();

		FluidStack fluidStack = chamber.tanks.get(2).getFluid();

		if (fluidStack != null)
		{
			GlStateManager.pushMatrix();
				GlStateManager.color(1F, 1F, 1F, 1F);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0, 15 * 16);

				GlStateManager.enableCull();
				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				BlockPos posOffset = chamber.getExtremeInteriorCoord(false, false, false).subtract(controller.getPos());
				GlStateManager.translate(posX + posOffset.getX(), posY + posOffset.getY(), posZ + posOffset.getZ());
				GlStateManager.translate(chamber.getInteriorLengthX()/2,chamber.getInteriorLengthY()/2,chamber.getInteriorLengthZ()/2);

				// render material
				GlStateManager.pushMatrix();
					GlStateManager.translate(0.5f, 0.5f, 0.5f);
					long systemTime = Minecraft.getSystemTime();
					if (!MC.isGamePaused())
						logic.materialAngle = (logic.materialAngle + (systemTime - logic.prevRenderTime) * 0.05f) % 360F;
					logic.prevRenderTime = systemTime;
					GlStateManager.rotate(logic.materialAngle, 1F, 0F, 0F);
					GlStateManager.rotate(logic.materialAngle, 0F, 1F, 0F);
					GlStateManager.rotate(logic.materialAngle, 0F, 0F, 1F);

					GlStateManager.pushMatrix();
						double scale = (Math.cbrt(chamber.tanks.get(2).getFluidAmount() / 16000d));
						GlStateManager.translate(-0.5f*scale, -0.5f*scale, -0.5f*scale);

						IWorldRender.BlockModelCuboid model = new IWorldRender.BlockModelCuboid();
						model.setTexture(IWorldRender.getStillTexture(fluidStack.getFluid()));
						model.setSize(scale,scale,scale);
						IWorldRender.RenderModelCuboid.render(model);

					GlStateManager.popMatrix();

				GlStateManager.popMatrix();

				//render laser
				if (controller.laserAxis == EnumFacing.Axis.Z)
				{
					GlStateManager.rotate(90f, 0f, 1f, 0f);
					GlStateManager.translate(-1f, 0.0f, 0.0f);
				}
				GlStateManager.translate(-chamber.getInteriorLengthX()/2, 0.5f, 0.5f);
				GlStateManager.pushMatrix();
					GlStateManager.rotate(-90f, 0f, 0f, 1f);

					int bright = 0xC0;
					int brightX = bright % 65536;
					int brightY = bright / 65536;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX, brightY);
					GlStateManager.disableLighting();
					GlStateManager.depthMask(false);
					GlStateManager.disableTexture2D();
					GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);

					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();

					bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
					double radius = 0.10;
					double length = (double) chamber.getInteriorLengthX() + 0.5d;
					bufferbuilder.pos(-radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(-radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

					bufferbuilder.pos(radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

					bufferbuilder.pos(-radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(-radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

					bufferbuilder.pos(-radius, 0D, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(-radius, length, radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(-radius, length, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();
					bufferbuilder.pos(-radius, 0D, -radius).color(1.0f, 0.0f, 0.0f, 0.6f).endVertex();

					tessellator.draw();

					GlStateManager.enableTexture2D();
					GlStateManager.depthMask(true);
					GlStateManager.enableLighting();


				GlStateManager.popMatrix();

				GlStateManager.disableBlend();
				GlStateManager.disableCull();
			GlStateManager.popMatrix();

		}

	}



	

}
