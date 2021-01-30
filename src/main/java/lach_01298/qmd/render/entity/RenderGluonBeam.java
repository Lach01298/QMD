package lach_01298.qmd.render.entity;

import org.lwjgl.opengl.GL11;

import lach_01298.qmd.entity.EntityGluonBeam;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGluonBeam  extends Render<EntityGluonBeam>
{

	private static final ResourceLocation Texture = new ResourceLocation("qmd:textures/entities/gluon_beam.png");
	
	public RenderGluonBeam(RenderManager renderManager)
	{
		super(renderManager);	
	}

	@Override
	public ResourceLocation getEntityTexture(EntityGluonBeam entity)
	{
		return Texture;
	}

	
	
	@Override
	public void doRender(EntityGluonBeam entity, double x, double y, double z, float entityYaw, float partialTicks) 
	{
		float width = 0.15F;
		float brightness = 1.0F;
		
		this.bindEntityTexture(entity);
		float timeFactor = ((float) entity.ticksExisted + partialTicks) / 0.05F;

		GlStateManager.pushMatrix();
		GlStateManager.translate(entity.posX-entity.owner.posX, entity.posY-entity.owner.posY, entity.posZ-entity.owner.posZ);
		

		GlStateManager.rotate(-90f-entity.owner.rotationYaw, 0.0f,1.0f,0.0f);
	    GlStateManager.rotate(-entity.owner.rotationPitch, 0.0f,0.0f,1.0f);
	    GlStateManager.pushMatrix();
	    
	    if(entity.hand == EnumHand.MAIN_HAND && entity.owner.getPrimaryHand() == EnumHandSide.RIGHT || entity.hand == EnumHand.OFF_HAND && entity.owner.getPrimaryHand() == EnumHandSide.LEFT)
		{
	    	GlStateManager.translate(0.45, -0.24, 0.3);
		}
		else
		{
			GlStateManager.translate(0.5, -0.24, -0.3);
		}
	
	    GlStateManager.pushMatrix();
	    GlStateManager.rotate(timeFactor, 1.0f,0.0f,0.0f);
	    
	    
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		
		float lastBrightnessX = OpenGlHelper.lastBrightnessX;
		float lastBrightnessY = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		GlStateManager.disableLighting();
		
		
		int lastBlendFuncSrc = GlStateManager.glGetInteger(GL11.GL_BLEND_SRC);
		int lastBlendFuncDest = GlStateManager.glGetInteger(GL11.GL_BLEND_DST);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.disableCull(); 
		GlStateManager.enableRescaleNormal();
		
		
		
		for (int i = 0; i < 2; ++i)
		{
			GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			GlStateManager.glNormal3f(0.0F, 0.0F, 0.0125F);

			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			bufferbuilder.pos(entity.length, -width, 0.0D).tex(0, entity.length - timeFactor / 200d).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
			bufferbuilder.pos(0d, -width, 0.0D).tex(0, 0 - timeFactor / 200d).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
			bufferbuilder.pos(0d, width, 0.0D).tex(1, 0 - timeFactor / 200d).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
			bufferbuilder.pos(entity.length, width, 0.0D).tex(1, entity.length - timeFactor / 200d).color(1.0f, 1.0f, 1.0f, brightness).endVertex();
			tessellator.draw();
		}
		
		
		
        
		GlStateManager.disableRescaleNormal();
        GlStateManager.enableCull();
       
        GlStateManager.disableBlend();
        
        GlStateManager.enableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		
	}
	
	
	
	
}
