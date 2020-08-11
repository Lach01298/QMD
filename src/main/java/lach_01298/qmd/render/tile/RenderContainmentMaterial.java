package lach_01298.qmd.render.tile;

import java.util.List;

import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.multiblock.network.ContainmentResendFormPacket;
import lach_01298.qmd.network.QMDPacketHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderContainmentMaterial extends TileEntitySpecialRenderer<TileNeutralContainmentController>
{
private static final Minecraft MC = Minecraft.getMinecraft();
	
	private final float[] brightness = new float[] {1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F};
	private byte count = 0;
	
	
	@Override
	public boolean isGlobalRenderer(TileNeutralContainmentController tile) {
		return tile.isRenderer && tile.isMultiblockAssembled();
	}
	
	@Override
	public void render(TileNeutralContainmentController controller, double posX, double posY, double posZ, float partialTicks, int destroyStage, float alpha) 
	{
		if (!controller.isRenderer || !controller.isMultiblockAssembled()) return;
		Containment containment = controller.getMultiblock();
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
			
			
			
			
			
			
			
			
			
			
			
			//renderer.renderBlockBrightness(blockstate, bright);
		
			
			GlStateManager.popMatrix();
			
			
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
		}

	}

    private void renderModelBrightnessColorQuads(float brightness, float red, float green, float blue, List<BakedQuad> listQuads)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        int i = 0;

        for (int j = listQuads.size(); i < j; ++i)
        {
            BakedQuad bakedquad = listQuads.get(i);
            bufferbuilder.begin(7, DefaultVertexFormats.ITEM);
            bufferbuilder.addVertexData(bakedquad.getVertexData());

            if (bakedquad.hasTintIndex())
            {
                bufferbuilder.putColorRGB_F4(red * brightness, green * brightness, blue * brightness);
            }
            else
            {
                bufferbuilder.putColorRGB_F4(brightness, brightness, brightness);
            }

            Vec3i vec3i = bakedquad.getFace().getDirectionVec();
            bufferbuilder.putNormal((float)vec3i.getX(), (float)vec3i.getY(), (float)vec3i.getZ());
            tessellator.draw();
        }
    }


	

}
