package lach_01298.qmd.render.tile;

import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.tile.TileExoticContainmentController;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class RenderContainmentMaterial extends TileEntitySpecialRenderer<TileExoticContainmentController>
{
private static final Minecraft MC = Minecraft.getMinecraft();
	
	private final float[] brightness = new float[] {1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
	private byte count = 0;
	
	
	@Override
	public boolean isGlobalRenderer(TileExoticContainmentController tile) {
		return tile.isRenderer && tile.isMultiblockAssembled();
	}
	
	@Override
	public void render(TileExoticContainmentController controller, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha)
	{
		if (!controller.isRenderer || !controller.isMultiblockAssembled()) return;
		VacuumChamber containment = controller.getMultiblock();
		if (containment == null) return;
		
		BlockPos pos = controller.getPos();
		
		
		FluidStack fluidStack = containment.tanks.get(2).getFluid();
		
		
		if (fluidStack != null)
		{

			BlockRendererDispatcher renderer = MC.getBlockRendererDispatcher();

			MC.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0, 15 * 16);

			double scale = (Math.cbrt(containment.tanks.get(2).getFluidAmount() / 16000d)*0.8d);
			GlStateManager.pushMatrix();
			GlStateManager.translate(posX + 0.5f, posY + 0.5f, posZ + 0.5f);
			GlStateManager.translate(containment.getMiddleX() - pos.getX(), containment.getMiddleY() - pos.getY(),
					containment.getMiddleZ() - pos.getZ());

			GlStateManager.pushMatrix();

			{
				long systemTime = Minecraft.getSystemTime();
				if (!MC.isGamePaused())
					containment.materialAngle = (containment.materialAngle
							+ (systemTime - containment.prevRenderTime) * 0.05f) % 360F;
				containment.prevRenderTime = systemTime;
				GlStateManager.rotate(containment.materialAngle, 1F, 0F, 0F);
				GlStateManager.rotate(containment.materialAngle, 0F, 1F, 0F);
				GlStateManager.rotate(containment.materialAngle, 0F, 0F, 1F);
			}

			GlStateManager.pushMatrix();

			GlStateManager.pushMatrix();
			GlStateManager.scale(scale, scale * 1.125f, scale);
			GlStateManager.translate(-0.5f, -0.444f, -0.5f);
			GlStateManager.disableLighting();
			IBlockState blockstate = fluidStack.getFluid().getBlock().getDefaultState();
			BlockModelRenderer blockRender = renderer.getBlockModelRenderer();
			IBakedModel model = renderer.getModelForState(blockstate);
			int color = fluidStack.getFluid().getColor();

			float r = ((color >> 16) & 0xFF) / 255f;
			float g = ((color >> 8) & 0xFF) / 255f;
			float b = ((color >> 0) & 0xFF) / 255f;
			float a = ((color >> 24) & 0xFF) / 255f;
			
			blockRender.renderModelBrightnessColor(blockstate, model, a, r, g, b);
			
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
			

			GlStateManager.popMatrix();
			
			
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
		}

	}



	

}
