package lach_01298.qmd.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import lach_01298.qmd.entity.EntityGammaFlash;
import lach_01298.qmd.entity.EntityLeptonBeam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLeptonBeam  extends Render<EntityLeptonBeam>
{

	private static final ResourceLocation Texture = new ResourceLocation("qmd:textures/entities/lepton_beam.png");
	
	public RenderLeptonBeam(RenderManager renderManager)
	{
		super(renderManager);	
	}

	@Override
	public ResourceLocation getEntityTexture(EntityLeptonBeam entity)
	{
		return Texture;
	}

	
	
	@Override
	public void doRender(EntityLeptonBeam entity, double x, double y, double z, float entityYaw, float partialTicks) 
	{
		
		float width = 0.1F;
		float brightness = 1.0F;
		this.bindEntityTexture(entity);
		

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		

		GlStateManager.rotate(-90f-entity.rotationYaw, 0.0f,1.0f,0.0f);
	    GlStateManager.rotate(-entity.rotationPitch, 0.0f,0.0f,1.0f);
	    GlStateManager.pushMatrix();
	    
	    if(entity.hand == EnumHand.MAIN_HAND && entity.owner.getPrimaryHand() == EnumHandSide.RIGHT || entity.hand == EnumHand.OFF_HAND && entity.owner.getPrimaryHand() == EnumHandSide.LEFT)
		{
	    	GlStateManager.translate(0.6, -0.18, 0.2);
		}
		else
		{
			GlStateManager.translate(0.6, -0.18, -0.2);
		}
	    
	    
	    
	    
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		
		int lastBlendFuncSrc = GlStateManager.glGetInteger(GL11.GL_BLEND_SRC);
		int lastBlendFuncDest = GlStateManager.glGetInteger(GL11.GL_BLEND_DST);
		
		float lastBrightnessX = OpenGlHelper.lastBrightnessX;
		float lastBrightnessY = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		GlStateManager.disableLighting();
		
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
		GlStateManager.disableCull(); 
		GlStateManager.enableRescaleNormal();
		
	    
         double v1 = 0.5;
         double v2 = 0.5;
         double u = 0.5;
         
         float red = entity.color.getRed()/ 255f;
         float green = entity.color.getGreen()/ 255f;
         float blue = entity.color.getBlue()/ 255f;
         
         for (int i = 0; i < 2; ++i)
         {
        	 GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
             GlStateManager.glNormal3f(0.0F, 0.0F, 0.0125F);
             
             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);             
             bufferbuilder.pos(entity.length, -width, 0.0D).tex(0, 1).color(red, green, blue, brightness).endVertex();
             bufferbuilder.pos(0, -width, 0.0D).tex(0, 0).color(red, green, blue, brightness).endVertex();
             bufferbuilder.pos(0, width, 0.0D).tex(1, 0).color(red, green, blue, brightness).endVertex();
             bufferbuilder.pos(entity.length, width, 0.0D).tex(1, 1).color(red, green, blue, brightness).endVertex();
             tessellator.draw();
         }
      
        
		GlStateManager.disableRescaleNormal();
        GlStateManager.enableCull();
       
        GlStateManager.disableBlend();
        
        GlStateManager.enableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
       
        GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		
	}
	
	
	
	
}
