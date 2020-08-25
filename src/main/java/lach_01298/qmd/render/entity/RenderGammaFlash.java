package lach_01298.qmd.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import lach_01298.qmd.entity.EntityGammaFlash;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGammaFlash extends Render<EntityGammaFlash>
{

	public RenderGammaFlash(RenderManager renderManager)
	{
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGammaFlash entity)
	{
		return null;
	}

	@Override
	public void doRender(EntityGammaFlash entity, double x, double y, double z, float entityYaw, float partialTicks)
	{

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();
		float timeFactor = ((float) entity.ticksExisted + partialTicks) / 20.0F;
		float scaleFactor = timeFactor;
		
		if(scaleFactor> 4.0f)
		{
			scaleFactor= 4.0f;
		}
	
		Random random = new Random(432L);
		GlStateManager.disableTexture2D();
		GlStateManager.shadeModel(7425);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.disableAlpha();
		GlStateManager.enableCull();
		GlStateManager.depthMask(false);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float)x, (float)y, (float)z);
		GL11.glScalef((float)(scaleFactor*entity.getSize()), (float)(scaleFactor*entity.getSize()), (float)(scaleFactor*entity.getSize()));

		for (int i = 0;  i < 50 ; ++i)
		{
			GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
			GlStateManager.rotate(random.nextFloat() *10.0F*timeFactor, 0.0F, 0.0F, 1.0F);

			float width = random.nextFloat()*2.5F + 1.0F;
		
			bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
			
			bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
			bufferbuilder.pos((double)(-1*width) , 0.0, -10.0).color(0, 0, 0, 0).endVertex();
			bufferbuilder.pos((double)(1*width), 0.0, -10.0).color(0, 0, 0, 0).endVertex();
			bufferbuilder.pos((double)(1*width), 0.0, -10.0).color(0, 0, 0, 0).endVertex();
			bufferbuilder.pos((double)(-1*width) , 0.0, -10.0).color(0, 0, 0, 0).endVertex();

			tessellator.draw();
		}

		GlStateManager.popMatrix();
		GlStateManager.depthMask(true);
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.shadeModel(7424);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableTexture2D();
		GlStateManager.enableAlpha();
		RenderHelper.enableStandardItemLighting();

	}
}
